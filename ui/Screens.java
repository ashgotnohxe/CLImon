package ui;

import model.Pokemon;

/** Title screen and the result screens. Owner: Person C. */
public class Screens {
    public static void title() {
        System.out.println("==============================");
        System.out.println("       WILD SIGIL ARENA");
        System.out.println("==============================");
        System.out.println("Choose a monster, win duels, and build your streak.");
        System.out.println("(Animation needs a terminal that understands ANSI codes, such as the");
        System.out.println(" VS Code terminal. If the screen looks garbled, turn Animation off.)");
    }

    public static void youWon(Pokemon player, Pokemon enemy, int streak) {
        System.out.println();
        System.out.println("==============================");
        System.out.println("          YOU WON!");
        System.out.println("==============================");
        System.out.println(player.getName() + " defeated " + enemy.getName() + ".");
        System.out.println("Current streak: " + streak);
    }

    public static void youLose(Pokemon player, Pokemon enemy, int streak) {
        System.out.println();
        System.out.println("==============================");
        System.out.println("          YOU LOSE");
        System.out.println("==============================");
        System.out.println(player.getName() + " was defeated by " + enemy.getName() + ".");
        System.out.println("Final streak: " + streak);
    }
}
