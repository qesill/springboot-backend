package pl.com.wikann.springboot.utils;


public class MathExpressionCalculator {
    public static Float calculateResult(String equation) {
        ExpressionTreeBuilder builder = new ExpressionTreeBuilder();
        ExpressionNode root = builder.buildTree(equation);
        return root.evaluate();
    }
}
