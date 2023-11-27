package pl.com.wikann.springboot.utils;
import java.util.Stack;

public class ExpressionTreeBuilder {

    static class TreeNode {
        int weight;
        String str;
        TreeNode left, right;

        public TreeNode(int weight, String str) {
            this.weight = weight;
            this.str = str;
        }
    }

    public float calculate(String expression) {
        if (expression == null || expression.length() == 0) return 0;
        expression = replaceDoubleMinus(expression);
        TreeNode root = buildTree(expression);
        return evaluate(root);
    }

    private String replaceDoubleMinus(String expression) {
        return expression.replaceAll("--", "+");
    }

    private TreeNode buildTree(String expression) {
        int n = expression.length();
        char[] chars = expression.trim().toCharArray();
        Stack<TreeNode> stack = new Stack<>();
        int base = 0;
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < n; i++) {
            char c = chars[i];

            if (c == ' ') {
                continue;
            } else if (c == '(' || c == ')') {
                base = getWeight(base, c);
                continue;
            } else if (i < n - 1 && Character.isDigit(chars[i]) && Character.isDigit(chars[i + 1])) {
                sb.append(c);
                continue;
            }

            String str = (Character.isDigit(c)) ? extractNumber(chars, sb, i) : Character.toString(c);
            TreeNode node = new TreeNode(getWeight(base, c), str);

            while (!stack.isEmpty() && node.weight <= stack.peek().weight) {
                node.left = stack.pop();
            }

            if (!stack.isEmpty()) {
                stack.peek().right = node;
            }

            stack.push(node);
        }

        return extractRoot(stack);
    }

    private String extractNumber(char[] chars, StringBuilder sb, int i) {
        sb.append(chars[i]);
        String str = sb.toString();
        sb.setLength(0); // Clean up
        return str;
    }

    private TreeNode extractRoot(Stack<TreeNode> stack) {
        TreeNode root = null;
        while (!stack.isEmpty()) {
            root = stack.pop();
        }
        return root;
    }

    private float evaluate(TreeNode root) {
        if (root == null) return 0;
        float left = evaluate(root.left);
        float right = evaluate(root.right);
        float result = 0;

        switch (root.str) {
            case "+":
                result = left + right;
                break;
            case "-":
                result = left - right;
                break;
            case "*":
                result = left * right;
                break;
            case "/":
                if (right != 0) {
                    result = left / right;
                } else {
                    throw new ArithmeticException("Division by zero");
                }
                break;
            default:
                result = Float.parseFloat(root.str);
        }

        return result;
    }

    private int getWeight(int base, char c) {
        if (c == '(') return base + 10;
        if (c == ')') return base - 10;
        if (c == '+' || c == '-') return base + 1;
        if (c == '*' || c == '/') return base + 2;
        return Integer.MAX_VALUE;
    }
}
