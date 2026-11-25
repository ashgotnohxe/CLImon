package data;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import model.Move;
import model.Pokemon;

/** Reads monsters.txt and builds Pokemon objects. Owner: Person A. */
public class MonsterLoader {

    /** Returns every valid monster in the file. Bad lines are reported and skipped. */
    public static ArrayList<Pokemon> loadAll(String path) {
        ArrayList<Pokemon> monsters = new ArrayList<>();
        HashSet<String> names = new HashSet<>();
        String text = readFile(path);
        if (text == null) {
            return monsters;
        }
        String[] lines = text.split("\n");
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i].strip();
            if (line.isEmpty() || line.startsWith("#")) {
                continue; // blank lines and comments
            }
            try {
                Pokemon monster = parse(line);
                if (!names.add(monster.getName())) {
                    throw new IllegalArgumentException("Duplicate monster: " + monster.getName());
                }
                monsters.add(monster);
            } catch (IllegalArgumentException error) {
                // NumberFormatException is also an IllegalArgumentException.
                System.err.println(path + ":" + (i + 1) + " - " + error.getMessage());
            }
        }
        return monsters;
    }

    private static String readFile(String path) {
        FileReader reader = null;
        try {
            reader = new FileReader(path);
            StringBuilder text = new StringBuilder();
            int symbol;
            while ((symbol = reader.read()) != -1) {
                text.append((char) symbol);
            }
            return text.toString();
        } catch (IOException error) {
            System.err.println("Could not read " + path + ": " + error.getMessage());
            return null;
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ignored) {
                    // Nothing useful can be done if closing fails.
                }
            }
        }
    }

    // name,type,hp,attack,defense,speed,move1,move2,move3
    // each move is name:type:power:accuracy:pp
    private static Pokemon parse(String line) {
        String[] fields = line.split("\\s*,\\s*", -1);
        if (fields.length != 9) {
            throw new IllegalArgumentException("Expected six monster fields and three moves.");
        }
        ArrayList<Move> moves = new ArrayList<>();
        for (int i = 6; i < fields.length; i++) {
            String[] move = fields[i].split("\\s*:\\s*", -1);
            if (move.length != 5) {
                throw new IllegalArgumentException("Expected five move fields: name:type:power:accuracy:pp");
            }
            moves.add(new Move(move[0], move[1], Integer.parseInt(move[2]),
                    Integer.parseInt(move[3]), Integer.parseInt(move[4])));
        }
        return new Pokemon(fields[0], fields[1], Integer.parseInt(fields[2]),
                Integer.parseInt(fields[3]), Integer.parseInt(fields[4]),
                Integer.parseInt(fields[5]), moves);
    }
}
