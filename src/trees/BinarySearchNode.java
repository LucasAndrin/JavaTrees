package trees;

public class BinarySearchNode<V extends Comparable<V>> extends BinaryNode<V> {

    protected int leftHeight, rightHeight;

    protected BinarySearchNode<V> left, right;

    protected BinarySearchNode(int level, V value) {
        super(level, value);
    }

    protected void updateLeftHeight() {
        leftHeight = getNewHeight(left) + 1;
    }

    protected void updateRightHeight() {
        rightHeight = getNewHeight(right) + 1;
    }

    protected int getNewHeight(BinarySearchNode<V> node) {
        if (node == null) {
            return - 1;
        }
        return Math.max(node.leftHeight, node.rightHeight);
    }

    protected void updateLevels(int currentLevel) {
        level = currentLevel;
        if (left != null) {
            left.updateLevels(currentLevel + 1);
        }
        if (right != null) {
            right.updateLevels(currentLevel + 1);
        }
    }

    protected int getBalanceFactor() {
        return rightHeight - leftHeight;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append('{').append(level).append('}');
        sb.append("->").append(value);
        return sb.toString();
    }
}
