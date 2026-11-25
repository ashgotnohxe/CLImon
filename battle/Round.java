package battle;

import java.util.ArrayList;

/**
 * The result of one turn: what each side did and the text to print.
 * An attack is null when that side did not get to act (it fainted first).
 * Owner: Person B.
 */
public class Round {
    private final AttackResult playerAttack, enemyAttack;
    private final String message;

    public Round(AttackResult playerAttack, AttackResult enemyAttack, ArrayList<String> log) {
        this.playerAttack = playerAttack;
        this.enemyAttack = enemyAttack;
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < log.size(); i++) {
            if (i > 0) {
                text.append(System.lineSeparator());
            }
            text.append(log.get(i));
        }
        this.message = text.toString();
    }

    public AttackResult getPlayerAttack() { return playerAttack; }

    public AttackResult getEnemyAttack() { return enemyAttack; }

    public String getMessage() { return message; }
}
