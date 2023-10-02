package trees.binary;

public class BinaryNode<V> {

    public V value;

    public int leftHeight, rightHeight, level;

    public BinaryNode<V> left, right;

    public BinaryNode(V value, int level) {
        this.value = value;
        this.level = level;
    }

    protected void updateLeftHeight() {
        leftHeight = getNewHeight(left) + 1;
    }

    protected void updateRightHeight() {
        rightHeight = getNewHeight(right) + 1;
    }

    private int getNewHeight(BinaryNode<V> node) {
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

    protected BinaryNode<V> getSmaller() {
        BinaryNode<V> aux = this;
        while (aux.left != null) {
            aux = aux.left;
        }
        return aux;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append('{').append(level).append('}');
        sb.append('(').append(leftHeight).append(", ").append(rightHeight).append(')');
        sb.append("->").append(value);
        return sb.toString();
    }

}
