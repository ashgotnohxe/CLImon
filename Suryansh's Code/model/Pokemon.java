package model;

import java.util.ArrayList;
import java.util.Random;

/** A monster: its stats, current HP and its three moves. Owner: Person A. */
public class Pokemon {
    private final String name, type;
    private final int maxHp, attack, defense, speed;
    private int hp;
    private final ArrayList<Move> moves = new ArrayList<>();

    public Pokemon(String name, String type, int hp, int attack, int defense, int speed,
                   ArrayList<Move> moves) {
        if (name.isBlank() || !Move.validType(type) || hp < 1 || hp > 999
                || attack < 1 || attack > 999 || defense < 1 || defense > 999
                || speed < 1 || speed > 999 || moves.size() != 3) {
            throw new IllegalArgumentException("Invalid monster: " + name);
        }
        this.name = name;
        this.type = type;
        this.maxHp = hp;
        this.hp = hp;
        this.attack = attack;
        this.defense = defense;
        this.speed = speed;
        // Each monster gets its own copies of the moves so PP is never shared.
        for (Move move : moves) {
            this.moves.add(new Move(move.getName(), move.getType(), move.getPower(),
                    move.getAccuracy(), move.getMaxPp()));
        }
    }

    public String getName() { return name; }
    public String getType() { return type; }
    public int getHp() { return hp; }
    public int getMaxHp() { return maxHp; }
    public int getAttack() { return attack; }
    public int getDefense() { return defense; }
    public int getSpeed() { return speed; }
    public ArrayList<Move> getMoves() { return new ArrayList<>(moves); }

    /** HP never goes below zero. */
    public void takeDamage(int amount) {
        hp = Math.max(0, hp - Math.max(0, amount));
    }

    public boolean isFainted() { return hp == 0; }

    /** Picks a random move that still has PP, or null if every move is empty. */
    public Move randomMove(Random random) {
        ArrayList<Move> available = new ArrayList<>();
        for (Move move : moves) {
            if (move.getPp() > 0) {
                available.add(move);
            }
        }
        return available.isEmpty() ? null : available.get(random.nextInt(available.size()));
    }

    /** Example: [######----] 60/100 */
    public String healthBar() {
        int filled = (hp * 10 + maxHp - 1) / maxHp;
        return "[" + "#".repeat(filled) + "-".repeat(10 - filled) + "] " + hp + "/" + maxHp;
    }

    /** Restores HP and all PP so the same monster can fight again. */
    public void heal() {
        hp = maxHp;
        for (Move move : moves) {
            move.restorePp();
        }
    }
}
