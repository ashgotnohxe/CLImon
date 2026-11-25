import battle.Battle;
import data.MonsterLoader;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import model.Pokemon;
import ui.BattleScreen;
import ui.Menu;
import ui.Screens;

/** Entry point: loads the monsters and runs the menus. Owner: Person C. */
public class Main {
    private static final String ROSTER_PATH = "data/monsters.txt";

    public static void main(String[] args) {
        ArrayList<Pokemon> roster = MonsterLoader.loadAll(ROSTER_PATH);
        if (roster.size() < 2) {
            System.err.println("At least two valid monsters are required. Run from the project folder.");
            return;
        }

        Random random = new Random();
        Scanner input = new Scanner(System.in);
        boolean animated = true;
        int best = 0;

        Screens.title();
        int option = Menu.mainMenu(input, best, animated);
        while (option == 1 || option == 2) {
            if (option == 2) {
                animated = !animated;
            } else {
                best = Math.max(best, playSession(input, roster, random, animated));
            }
            option = Menu.mainMenu(input, best, animated);
        }
        System.out.println("Thanks for playing. Best streak: " + best);
        input.close();
    }

    /** Lets the player pick a monster and fight until they go back. Returns the best streak. */
    private static int playSession(Scanner input, ArrayList<Pokemon> roster, Random random,
                                   boolean animated) {
        Pokemon player = Menu.selectMonster(input, roster);
        if (player == null) {
            return 0;
        }
        int streak = 0;
        int bestStreak = 0;
        Pokemon previousEnemy = null;
        boolean keepPlaying = true;
        while (keepPlaying) {
            Pokemon enemy = pickEnemy(roster, player, previousEnemy, random);
            player.heal();
            enemy.heal();
            Battle battle = new Battle(player, enemy, random);
            boolean finished = BattleScreen.run(input, battle, player, enemy, animated);
            if (!finished) {
                return bestStreak;
            }
            if (battle.playerWon()) {
                streak++;
                bestStreak = Math.max(bestStreak, streak);
                Screens.youWon(player, enemy, streak);
                System.out.println("1. Fight again\n2. Main menu");
            } else {
                Screens.youLose(player, enemy, streak);
                streak = 0;
                System.out.println("1. Rematch with this monster\n2. Main menu");
            }
            keepPlaying = Menu.readChoice(input, "Choose an option", 1, 2) == 1;
            previousEnemy = enemy;
        }
        return bestStreak;
    }

    /** A random enemy that is not the player's monster (or the previous enemy, if avoidable). */
    private static Pokemon pickEnemy(ArrayList<Pokemon> roster, Pokemon player,
                                     Pokemon previousEnemy, Random random) {
        ArrayList<Pokemon> choices = new ArrayList<>(roster);
        choices.remove(player);
        if (choices.size() > 1) {
            choices.remove(previousEnemy);
        }
        return choices.get(random.nextInt(choices.size()));
    }
}
