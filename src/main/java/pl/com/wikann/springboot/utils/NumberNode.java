package pl.com.wikann.springboot.utils;

public class NumberNode extends ExpressionNode {
    private float value;

    public NumberNode(float value) {
        this.value = value;
    }

    @Override
    public float evaluate() {
        return value;
    }
}
