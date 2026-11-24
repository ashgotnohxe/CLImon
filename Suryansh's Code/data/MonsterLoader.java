package data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import model.Move;
import model.Pokemon;

public class MonsterLoader {
    public static ArrayList<Pokemon> loadAll(String path) {
        ArrayList<Pokemon> monsters = new ArrayList<>();
        HashSet<String> names = new HashSet<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            int number = 0;
            while ((line = reader.readLine()) != null) {
                number++;
                if (line.isBlank() || line.strip().startsWith("#")) {
                    continue;
                }
                try {
                    Pokemon monster = parse(line);
                    if (!names.add(monster.getName())) {
                        throw new IllegalArgumentException("Duplicate monster: " + monster.getName());
                    }
                    monsters.add(monster);
                } catch (IllegalArgumentException error) {
                    System.err.println(path + ":" + number + " - " + error.getMessage());
                }
            }
        } catch (IOException error) {
            System.err.println("Could not read " + path + ": " + error.getMessage());
        }
        return monsters;
    }

    private static Pokemon parse(String line) {
        String[] fields = line.strip().split("\\s*,\\s*", -1);
        if (fields.length != 9) {
            throw new IllegalArgumentException("Expected six monster fields and three moves.");
        }
        ArrayList<Move> moves = new ArrayList<>();
        for (int i = 6; i < fields.length; i++) {
            String[] move = fields[i].split("\\s*:\\s*", -1);
            if (move.length != 5 && move.length != 6) {
                throw new IllegalArgumentException("Expected five move fields and an optional effect.");
            }
            moves.add(new Move(move[0], move[1], Integer.parseInt(move[2]),
                    Integer.parseInt(move[3]), Integer.parseInt(move[4]),
                    move.length == 6 ? move[5] : "none"));
        }
        return new Pokemon(fields[0], fields[1], Integer.parseInt(fields[2]),
                Integer.parseInt(fields[3]), Integer.parseInt(fields[4]),
                Integer.parseInt(fields[5]), moves);
    }
}
