package model;

/** One attack a monster can use. Owner: Person A. */
public class Move {
    private final String name, type;
    private final int power, accuracy, maxPp;
    private int pp;

    public Move(String name, String type, int power, int accuracy, int pp) {
        if (name.isBlank() || !validType(type) || power < 1 || power > 200
                || accuracy < 1 || accuracy > 100 || pp < 1 || pp > 99) {
            throw new IllegalArgumentException("Invalid move: " + name);
        }
        this.name = name;
        this.type = type;
        this.power = power;
        this.accuracy = accuracy;
        this.maxPp = pp;
        this.pp = pp;
    }

    public static boolean validType(String type) {
        return type.equals("Fire") || type.equals("Water")
                || type.equals("Grass") || type.equals("Normal");
    }

    public String getName() { return name; }
    public String getType() { return type; }
    public int getPower() { return power; }
    public int getAccuracy() { return accuracy; }
    public int getPp() { return pp; }
    public int getMaxPp() { return maxPp; }

    public void spendPp() {
        if (pp > 0) {
            pp--;
        }
    }

    public void restorePp() { pp = maxPp; }
}
