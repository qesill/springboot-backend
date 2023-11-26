package pl.com.wikann.springboot.utils;

public class OperatorNode extends ExpressionNode {

    private char operator;
    private ExpressionNode left;
    private ExpressionNode right;

    public OperatorNode(char operator, ExpressionNode left, ExpressionNode right) {
        this.operator = operator;
        this.left = left;
        this.right = right;
    }

    public char getOperator() {
        return operator;
    }

    public void setOperator(char operator) {
        this.operator = operator;
    }

    public ExpressionNode getLeft() {
        return left;
    }

    public void setLeft(ExpressionNode left) {
        this.left = left;
    }

    public ExpressionNode getRight() {
        return right;
    }

    public void setRight(ExpressionNode right) {
        this.right = right;
    }

    @Override
    public float evaluate() { // Implement the method
        float leftValue = left.evaluate();
        float rightValue = right.evaluate();

        switch (operator) {
            case '+':
                return leftValue + rightValue;
            case '-':
                return leftValue - rightValue;
            case '*':
                return leftValue * rightValue;
            case '/':
                if (rightValue == 0) {
                    throw new ArithmeticException("Division by zero");
                }
                return leftValue / rightValue;
            default:
                throw new IllegalArgumentException("Unknown operator: " + operator);
        }
    }
}