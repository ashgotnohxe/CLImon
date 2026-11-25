package ui;

public class AsciiArt {
    public static String forMonster(String name) {
        switch (name) {
            case "Cinderjay":
                return "   _\n  (o)>\n <(  )\n  /||\n   ^^";
            case "Reeflynx":
                return " /\\_/\\\n( o.o )\n > ^ <\n /|_|\\\n   ~~~";
            case "Mossback":
                return "   __\n _/##\\_\n(o  ## )\n /_||_\\\n   ..";
            case "Dusthare":
                return " (\\ /)\n (o.o)\n ( > )\n /| |\\\n  ~~~";
            case "Flareyak":
                return " \\   /\n  \\_/\n (o o)___\n /___/   )\n   ||  ||";
            case "Brookowl":
                return " ,___,\n (O,O)\n /)_(\\\n   ^^ \n  ~~~~";
            default:
                return "  .-.\n (o o)\n | ^ |\n /___\\\n  / \\";
        }
    }

    static String[] profile(String name, String type, boolean faceRight) {
        String art;
        switch (name) {
            case "Cinderjay":
                art = forMonster(name);
                break;
            case "Reeflynx":
                art = "  /\\_/\\\n _(   o)>\n~(    )\n /_/\\_/\n  ~~~";
                break;
            case "Mossback":
                art = "  ___\n /###\\_(o)>\n(____/   )\n /_/\\___/\n  ..";
                break;
            case "Dusthare":
                art = "   |/|\n  (  o)>\n _(   )\n(_/  /_\n   ~~~";
                break;
            case "Flareyak":
                art = "   \\ /\n __/ (o)>\n(      )\n ||  ||\n ^^  ^^";
                break;
            case "Brookowl":
                art = "  ,___,\n  (  O)>\n /)___)\n   ^^\n  ~~~~";
                break;
            default:
                art = type.equals("Grass")
                        ? "   _/\n _(  o)>\n(     )\n /_/\\_/\n  ''"
                        : "  /\\\n _(  o)>\n(     )\n /_/\\_/\n  ..";
        }
        String[] rows = art.split("\n");
        for (int i = 0; i < rows.length; i++) {
            String row = rows[i].stripTrailing();
            rows[i] = " ".repeat(12 - row.length()) + row;
            if (!faceRight) {
                rows[i] = mirror(rows[i]);
            }
        }
        return rows;
    }

    private static String mirror(String row) {
        StringBuilder result = new StringBuilder();
        for (int i = row.length() - 1; i >= 0; i--) {
            char symbol = row.charAt(i);
            switch (symbol) {
                case '/': symbol = '\\'; break;
                case '\\': symbol = '/'; break;
                case '(': symbol = ')'; break;
                case ')': symbol = '('; break;
                case '>': symbol = '<'; break;
                case '<': symbol = '>'; break;
                default: break;
            }
            result.append(symbol);
        }
        return result.toString();
    }
}
