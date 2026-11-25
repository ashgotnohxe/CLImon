package ui;

import battle.Battle;
import battle.Round;
import java.util.ArrayList;
import java.util.Scanner;
import model.Move;
import model.Pokemon;

/**
 * Runs the on-screen part of one fight: shows the arena, lists the moves,
 * reads the player's choice and asks Battle to play each round.
 * Owner: Person C.
 */
public class BattleScreen {
    private static final int NO_INPUT = -2;

    /**
     * Plays the fight until one monster faints.
     * Returns false if the keyboard input ended before the fight was over.
     */
    public static boolean run(Scanner input, Battle battle, Pokemon player, Pokemon enemy,
                              boolean animated) {
        TerminalArena arena = new TerminalArena(animated, player, enemy);
        System.out.println();
        arena.show(player.healthBar(), enemy.healthBar());

        int turn = 1;
        while (!battle.isOver()) {
            System.out.println("\n--- Turn " + turn + " ---");
            int choice = chooseMove(input, battle, player);
            if (choice == NO_INPUT) {
                return false;
            }
            String playerHpBefore = player.healthBar();
            String enemyHpBefore = enemy.healthBar();
            Round round = battle.playRound(choice);
            System.out.println();
            arena.reveal(round, playerHpBefore, enemyHpBefore,
                    player.healthBar(), enemy.healthBar());
            System.out.println(round.getMessage());
            turn++;
        }
        return true;
    }

    /** Returns a move index (0-2), Battle.STRUGGLE, or NO_INPUT. */
    private static int chooseMove(Scanner input, Battle battle, Pokemon player) {
        ArrayList<Move> moves = player.getMoves();
        for (int i = 0; i < moves.size(); i++) {
            Move move = moves.get(i);
            System.out.printf("%d. %s (%s, power %d, %d%% accuracy, PP %d/%d)%n", i + 1,
                    move.getName(), move.getType(), move.getPower(),
                    move.getAccuracy(), move.getPp(), move.getMaxPp());
        }
        if (battle.needsFallback()) {
            System.out.println("All moves are out of PP. Using Struggle.");
            return Battle.STRUGGLE;
        }
        while (true) {
            int choice = Menu.readChoice(input, "Choose a move", 1, moves.size());
            if (choice == -1) {
                return NO_INPUT;
            }
            if (moves.get(choice - 1).getPp() > 0) {
                return choice - 1;
            }
            System.out.println("That move is out of PP.");
        }
    }
}
