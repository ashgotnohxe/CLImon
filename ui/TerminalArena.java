package ui;

import battle.AttackResult;
import battle.Round;
import model.Pokemon;

/**
 * Draws the two monsters, their HP bars and the attack animation.
 * Owner: Person C.
 */
public class TerminalArena {
    private static final String ESC = "\u001b[";
    private static final int LANE_WIDTH = 36;
    private static final int STEPS = 10;
    private static final int PROJECTILE_WIDTH = 3;
    private static final int ATTACK_ROW = 1;
    private final boolean animated;
    private final String playerName, enemyName;
    private final String[] playerArt, enemyArt;

    public TerminalArena(boolean animated, Pokemon player, Pokemon enemy) {
        this.animated = animated;
        playerName = player.getName();
        enemyName = enemy.getName();
        playerArt = AsciiArt.profile(playerName, player.getType(), true);
        enemyArt = AsciiArt.profile(enemyName, enemy.getType(), false);
    }

    public void show(String playerHp, String enemyHp) {
        draw(playerHp, enemyHp, "", false);
    }

    public void reveal(Round round, String oldPlayerHp, String oldEnemyHp,
                       String playerHp, String enemyHp) {
        AttackResult left = round.getPlayerAttack();
        AttackResult right = round.getEnemyAttack();
        if (!animated || (left == null && right == null)) {
            show(playerHp, enemyHp);
            return;
        }

        boolean clash = left != null && right != null;
        int distance = clash ? LANE_WIDTH / 2 - PROJECTILE_WIDTH
                : LANE_WIDTH - PROJECTILE_WIDTH;
        draw(oldPlayerHp, oldEnemyHp, projectiles(left, right, 0), false);
        try {
            for (int step = 1; step <= STEPS; step++) {
                Thread.sleep(55);
                draw(oldPlayerHp, oldEnemyHp,
                        projectiles(left, right, step * distance / STEPS), true);
            }
            Thread.sleep(55);
            String impact;
            if (clash) {
                impact = centered("* BOOM *", LANE_WIDTH);
            } else {
                String result = (left == null ? right : left).isHit() ? "* HIT *" : "- MISS -";
                impact = left == null ? result : " ".repeat(LANE_WIDTH - result.length()) + result;
            }
            draw(oldPlayerHp, oldEnemyHp, impact, true);
            Thread.sleep(260);
        } catch (InterruptedException error) {
            Thread.currentThread().interrupt();
        } finally {
            draw(playerHp, enemyHp, "", true);
        }
    }

    private static String projectiles(AttackResult left, AttackResult right, int distance) {
        StringBuilder lane = new StringBuilder(" ".repeat(LANE_WIDTH));
        if (left != null) {
            lane.replace(distance, distance + PROJECTILE_WIDTH, projectile(left.getMoveType(), true));
        }
        if (right != null) {
            int start = LANE_WIDTH - PROJECTILE_WIDTH - distance;
            lane.replace(start, start + PROJECTILE_WIDTH, projectile(right.getMoveType(), false));
        }
        return lane.toString();
    }

    private static String projectile(String type, boolean rightward) {
        String tip = rightward ? ">" : "<";
        String tail;
        switch (type) {
            case "Fire": tail = "**"; break;
            case "Water": tail = "~~"; break;
            case "Grass": tail = "++"; break;
            default: tail = tip.repeat(2);
        }
        return rightward ? tail + tip : tip + tail;
    }

    private void draw(String playerHp, String enemyHp, String effect, boolean replace) {
        StringBuilder frame = new StringBuilder();
        int rows = 2 + playerArt.length;
        if (replace) {
            frame.append(ESC).append(rows).append('F');
        }
        appendRow(frame, centered(playerName, 28) + "    " + centered(enemyName, 28), replace);
        appendRow(frame, centered(playerHp, 28) + "    " + centered(enemyHp, 28), replace);
        for (int i = 0; i < playerArt.length; i++) {
            String lane = i == ATTACK_ROW ? effect : "";
            appendRow(frame, playerArt[i] + lane + " ".repeat(LANE_WIDTH - lane.length()) + enemyArt[i], replace);
        }
        System.out.print(frame);
        System.out.flush();
    }

    private static void appendRow(StringBuilder frame, String row, boolean replace) {
        if (replace) {
            frame.append(ESC).append("2K");
        }
        frame.append(row.stripTrailing()).append(System.lineSeparator());
    }

    private static String centered(String value, int width) {
        String text = value.length() > width ? value.substring(0, width - 3) + "..." : value;
        int left = (width - text.length()) / 2;
        return " ".repeat(left) + text + " ".repeat(width - left - text.length());
    }
}
