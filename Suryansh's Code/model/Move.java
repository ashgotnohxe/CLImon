package model;

public class Move {
    private final String name, type, effect;
    private final int power, accuracy, maxPp;
    private int pp;

    public Move(String name, String type, int power, int accuracy, int pp) {
        this(name, type, power, accuracy, pp, "none");
    }

    public Move(String name, String type, int power, int accuracy, int pp, String effect) {
        if (name.isBlank() || !validType(type) || power < 0 || power > 200
                || accuracy < 1 || accuracy > 100 || pp < 1 || pp > 99
                || !(effect.equals("none") || effect.equals("poison") || effect.equals("sleep")
                || effect.equals("paralysis") || effect.equals("weaken"))
                || (power == 0 && effect.equals("none"))
                || (power > 0 && !effect.equals("none"))) {
            throw new IllegalArgumentException("Invalid move: " + name);
        }
        this.name = name;
        this.type = type;
        this.power = power;
        this.accuracy = accuracy;
        this.maxPp = pp;
        this.pp = pp;
        this.effect = effect;
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
    public String getEffect() { return effect; }

    public void spendPp() {
        if (pp > 0) {
            pp--;
        }
    }

    public void restorePp() { pp = maxPp; }
}
