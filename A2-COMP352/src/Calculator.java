public class Calculator {
    private static Stack<Object> valStk = new ArrayStack<>();
    private static Stack<String> opStk = new ArrayStack<>();

    public static Object evalExp(String expression) { // returns either a number or true/false
        String[] tokens = expression.split(" ");
        
        // Check for matching parentheses
        if (!parenMatch(tokens)) {
            return "Error: Mismatched parentheses";
        }
        
        int i = 0;
        while (i < tokens.length) {
            String z = tokens[i];
            if (isNumber(z)) {
                valStk.push(Double.parseDouble(z));
            } else if (z.equals("(")) {
                opStk.push(z);
            } else if (z.equals(")")) {
                while (!opStk.isEmpty() && !opStk.top().equals("(")) {
                    doOp();
                }
                opStk.pop();
            } else {
                repeatOps(z);
                opStk.push(z);
            }
            i++;
        }
        repeatOps("$");
        return valStk.top();
    }

    public static void repeatOps(String z) {
        while (valStk.size() > 1 && (prec(z) >= prec(opStk.top()))) {
            if (opStk.top().equals("(")) {
                break;
            }
            doOp();
        }
    }

    public static void doOp() {
        String op = opStk.pop();
        double x = (double) valStk.pop();
        double y = (double) valStk.pop();

        switch (op) {
            case "+":
                valStk.push(y + x);
                break;
            case "-":
                valStk.push(y - x);
                break;
            case "*":
                valStk.push(y * x);
                break;
            case "/":
                valStk.push(y / x);
                break;
            case "^":
                valStk.push(Math.pow(y, x));
                break;
            case ">":
                valStk.push(y > x);
                break;
            case ">=":
                valStk.push(y >= x);
                break;
            case "<":
                valStk.push(y < x);
                break;
            case "<=":
                valStk.push(y <= x);
                break;
            case "==":
                valStk.push(y == x);
                break;
            case "!=":
                valStk.push(y != x);
                break;
        }
    }

    public static boolean isNumber(String z) {
        for (int i = 0; i < z.length(); i++) {
            if (z.charAt(i) < '0' || z.charAt(i) > '9') {
                return false;
            }
        }
        return true;
    }

    public static int prec(String op) {
        switch (op) {
            case "(":
            case ")":
                return 1;
            case "^":
                return 2;
            case "*":
            case "/":
                return 3;
            case "+":
            case "-":
                return 4;
            case ">":
            case "<":
            case ">=":
            case "<=":
                return 5;
            case "==":
            case "!=":
                return 6;
            case "$":
                return 7;
        }
        return -1;
    }

    public static boolean parenMatch(String[] tokens) {
        Stack<String> tempStack = new ArrayStack<>();
        for (String token : tokens) {
            if (token.equals("(")) {
                tempStack.push(token);
            } else if (token.equals(")")) {
                if (tempStack.isEmpty() || !tempStack.pop().equals("(")) {
                    return false;
                }
            }
        }
        return tempStack.isEmpty();
    }
}
