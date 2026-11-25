package battle;

/**
 * What one attack did, in a form the screen can use for the animation.
 * Owner: Person B.
 */
public class AttackResult {
    private final String moveType;
    private final boolean hit;

    public AttackResult(String moveType, boolean hit) {
        this.moveType = moveType;
        this.hit = hit;
    }

    public String getMoveType() { return moveType; }

    public boolean isHit() { return hit; }
}
