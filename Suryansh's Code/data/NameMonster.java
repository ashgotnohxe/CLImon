package data;

import java.util.ArrayList;
import java.util.Locale;
import model.Move;
import model.Pokemon;

public class NameMonster {
    public static Pokemon create(String name) {
        String letters = name.toLowerCase(Locale.ROOT).replaceAll("[^a-z]", "");
        if (letters.isEmpty()) {
            throw new IllegalArgumentException("Enter a name containing at least one A-Z letter.");
        }
        int sum = 0;
        for (int i = 0; i < letters.length(); i++) {
            sum += letters.charAt(i) - 'a' + 1;
        }
        String[] types = {"Fire", "Water", "Grass", "Normal"};
        String type = types[(letters.charAt(0) - 'a') % types.length];
        ArrayList<Move> moves = new ArrayList<>();
        moves.add(new Move(name.strip() + " Strike", type, 42, 95, 10));
        moves.add(new Move(type + " Burst", type, 49, 85, 7));
        moves.add(new Move("Body Check", "Normal", 46, 100, 14));
        return new Pokemon(name.strip(), type, 80 + sum % 41, 40 + sum % 21,
                40 + (sum / 2) % 21, 40 + (sum / 3) % 41, moves);
    }
}
