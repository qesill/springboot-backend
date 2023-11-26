package pl.com.wikann.springboot.utils;

public abstract class ExpressionNode {

    protected ExpressionNode leftChild;
    protected ExpressionNode rightChild;

    public abstract float evaluate();

    public ExpressionNode getLeftChild() {
        return leftChild;
    }

    public void setLeftChild(ExpressionNode leftChild) {
        this.leftChild = leftChild;
    }

    public ExpressionNode getRightChild() {
        return rightChild;
    }

    public void setRightChild(ExpressionNode rightChild) {
        this.rightChild = rightChild;
    }
}
