package pl.com.wikann.springboot.utils;
import java.util.Stack;

public class ExpressionTreeBuilder {

    public ExpressionNode buildTree(String expression) {
        Stack<ExpressionNode> nodeStack = new Stack<>();
        Stack<OperatorNode> operatorStack = new Stack<>();

        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);

            if (Character.isDigit(c) || c == '-') {
                StringBuilder num = new StringBuilder();
                while (i < expression.length() && (Character.isDigit(expression.charAt(i)) || expression.charAt(i) == '.' || c == '-')) {
                    num.append(expression.charAt(i));
                    i++;
                }
                i--;

                float value = Float.parseFloat(num.toString());
                nodeStack.push(new NumberNode(value));

            } else if (c == '(') {
                operatorStack.push(new OperatorNode('(', null, null));

            } else if (c == ')') {
                while (!operatorStack.isEmpty() && operatorStack.peek().getOperator() != '(') {
                    OperatorNode operatorNode = operatorStack.pop();
                    ExpressionNode right = nodeStack.pop();
                    ExpressionNode left = nodeStack.pop();
                    nodeStack.push(new OperatorNode(operatorNode.getOperator(), left, right));
                }

                OperatorNode openingParenNode = operatorStack.pop(); // Remove opening parenthesis '('

                ExpressionNode subExpression = nodeStack.pop();
                openingParenNode.setRightChild(subExpression);

                // Push the updated opening parenthesis node with the sub-expression as its right child back onto the stack
                operatorStack.push(openingParenNode);

            } else if (c == '+' || c == '-' || c == '*' || c == '/') {
                while (!operatorStack.isEmpty() && hasPrecedence(c, operatorStack.peek().getOperator())) {
                    OperatorNode operatorNode = operatorStack.pop();
                    ExpressionNode right = nodeStack.pop();
                    ExpressionNode left = nodeStack.pop();
                    nodeStack.push(new OperatorNode(operatorNode.getOperator(), left, right));
                }
                operatorStack.push(new OperatorNode(c, null, null));
            }
        }

        while (!operatorStack.isEmpty()) {
            OperatorNode operatorNode = operatorStack.pop();
            ExpressionNode right = nodeStack.pop();
            ExpressionNode left = nodeStack.pop();
            nodeStack.push(new OperatorNode(operatorNode.getOperator(), left, right));
        }

        return nodeStack.isEmpty() ? null : nodeStack.pop();
    }

    private boolean hasPrecedence(char operator1, char operator2) {
        if ((operator1 == '*' || operator1 == '/') && (operator2 == '+' || operator2 == '-')) {
            return false;
        }
        return true;
    }
}