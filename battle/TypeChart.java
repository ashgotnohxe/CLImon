package battle;

/**
 * Which type beats which: Fire beats Grass, Water beats Fire, Grass beats Water.
 * Owner: Person B.
 */
public class TypeChart {
    /** 2.0 = super effective, 0.5 = not very effective, 1.0 = normal damage. */
    public static double effectiveness(String moveType, String defenderType) {
        if (beats(moveType, defenderType)) {
            return 2.0;
        }
        return beats(defenderType, moveType) ? 0.5 : 1.0;
    }

    private static boolean beats(String first, String second) {
        return (first.equals("Fire") && second.equals("Grass"))
                || (first.equals("Water") && second.equals("Fire"))
                || (first.equals("Grass") && second.equals("Water"));
    }
}
