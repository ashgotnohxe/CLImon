package ui;

import java.util.ArrayList;
import java.util.Scanner;
import model.Pokemon;

/** Menus and safe number input. Owner: Person C. */
public class Menu {

    /** Asks until the player types a number in range. Returns -1 if the input ends. */
    public static int readChoice(Scanner input, String prompt, int minimum, int maximum) {
        while (true) {
            System.out.printf("%s [%d-%d]: ", prompt, minimum, maximum);
            if (!input.hasNextLine()) {
                System.out.println();
                return -1;
            }
            try {
                int choice = Integer.parseInt(input.nextLine().strip());
                if (choice >= minimum && choice <= maximum) {
                    return choice;
                }
            } catch (NumberFormatException error) {
                // Letters and blank lines fall through to the same message below.
            }
            System.out.println("Enter a number from " + minimum + " to " + maximum + ".");
        }
    }

    /** 1 = play, 2 = toggle animation, 3 = quit, -1 = input ended. */
    public static int mainMenu(Scanner input, int best, boolean animated) {
        System.out.println("\nBest streak this session: " + best);
        System.out.println("1. Play");
        System.out.println("2. Animation: " + (animated ? "ON (choose to turn off)" : "OFF (choose to turn on)"));
        System.out.println("3. Quit");
        return readChoice(input, "Choose an option", 1, 3);
    }

    /** Returns the chosen monster, or null if the player went back. */
    public static Pokemon selectMonster(Scanner input, ArrayList<Pokemon> roster) {
        System.out.println("\n--- Choose your monster ---");
        for (int i = 0; i < roster.size(); i++) {
            Pokemon monster = roster.get(i);
            System.out.printf("%d. %-10s %-6s HP %3d | ATK %2d | DEF %2d | SPD %2d%n",
                    i + 1, monster.getName(), monster.getType(), monster.getMaxHp(),
                    monster.getAttack(), monster.getDefense(), monster.getSpeed());
        }
        System.out.println("0. Back");
        int choice = readChoice(input, "Choose a monster", 0, roster.size());
        return choice <= 0 ? null : roster.get(choice - 1);
    }
}
