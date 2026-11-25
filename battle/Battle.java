package battle;

import java.util.ArrayList;
import java.util.Random;
import model.Move;
import model.Pokemon;

/**
 * The rules of one fight between the player's monster and an enemy monster.
 * This class never prints menus or reads the keyboard: the screen code in
 * the ui package asks it to play a round and shows the result.
 * Owner: Person B.
 */
public class Battle {
    /** Pass this to playRound when every move is out of PP. */
    public static final int STRUGGLE = -1;

    private final Pokemon player, enemy;
    private final Random random;
    private final Move struggle = new Move("Struggle", "Normal", 35, 100, 1);

    public Battle(Pokemon player, Pokemon enemy) {
        this(player, enemy, new Random());
    }

    /** A seeded Random makes a fight repeatable, which the checks use. */
    public Battle(Pokemon player, Pokemon enemy, Random random) {
        this.player = player;
        this.enemy = enemy;
        this.random = random;
    }

    public boolean isOver() {
        return player.isFainted() || enemy.isFainted();
    }

    /** Only meaningful once isOver() is true. */
    public boolean playerWon() {
        return enemy.isFainted();
    }

    /** True when the player has no move with PP left. */
    public boolean needsFallback() {
        for (Move move : player.getMoves()) {
            if (move.getPp() > 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Plays one turn. choice is the index of the player's move (0, 1 or 2),
     * or STRUGGLE when all moves are out of PP.
     */
    public Round playRound(int choice) {
        if (isOver()) {
            throw new IllegalStateException("The battle is over.");
        }
        ArrayList<Move> moves = player.getMoves();
        Move playerMove;
        if (choice == STRUGGLE && needsFallback()) {
            playerMove = struggle;
        } else if (choice >= 0 && choice < moves.size() && moves.get(choice).getPp() > 0) {
            playerMove = moves.get(choice);
        } else {
            throw new IllegalArgumentException("Choose an available move.");
        }
        Move enemyMove = chooseEnemyMove();

        // The faster monster attacks first; on a tie the player goes first.
        boolean playerFirst = player.getSpeed() >= enemy.getSpeed();
        Pokemon first = playerFirst ? player : enemy;
        Pokemon second = playerFirst ? enemy : player;
        Move firstMove = playerFirst ? playerMove : enemyMove;
        Move secondMove = playerFirst ? enemyMove : playerMove;

        ArrayList<String> log = new ArrayList<>();
        AttackResult firstResult = attack(first, second, firstMove, log);
        // A monster that fainted never gets to take its action.
        AttackResult secondResult = second.isFainted() ? null : attack(second, first, secondMove, log);

        if (playerFirst) {
            return new Round(firstResult, secondResult, log);
        }
        return new Round(secondResult, firstResult, log);
    }

    private Move chooseEnemyMove() {
        Move move = enemy.randomMove(random);
        return move == null ? struggle : move;
    }

    private AttackResult attack(Pokemon attacker, Pokemon defender, Move move, ArrayList<String> log) {
        if (move != struggle) {
            move.spendPp();
        }
        log.add(attacker.getName() + " used " + move.getName() + ".");

        // Accuracy check: a roll of 0-99 must be below the accuracy to hit.
        if (random.nextInt(100) >= move.getAccuracy()) {
            log.add("It missed.");
            return new AttackResult(move.getType(), false);
        }

        double multiplier = TypeChart.effectiveness(move.getType(), defender.getType());
        double variation = 0.85 + random.nextDouble() * 0.15;
        int damage = calculateDamage(move.getPower(), attacker.getAttack(),
                defender.getDefense(), multiplier, variation);

        int hpBefore = defender.getHp();
        defender.takeDamage(damage);
        log.add(defender.getName() + " lost " + (hpBefore - defender.getHp()) + " HP.");
        if (multiplier > 1) {
            log.add("It's super effective!");
        } else if (multiplier < 1) {
            log.add("It's not very effective.");
        }
        return new AttackResult(move.getType(), true);
    }

    /** (power x attack / defense) / 5 + 2, then x type multiplier, then x random variation. */
    static int calculateDamage(int power, int attack, int defense,
                               double multiplier, double variation) {
        double base = power * (double) attack / defense / 5 + 2;
        return Math.max(1, (int) (base * multiplier * variation));
    }
}
