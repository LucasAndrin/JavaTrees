package trees.binary;

public class BinaryNode {

    public int value, leftHeight, rightHeight, recurrences, level;

    public BinaryNode left, right;

    public BinaryNode(int value, int level) {
        this.value = value;
        this.level = level;
    }

    protected int getBalanceFactor() {
        return rightHeight - leftHeight;
    }

    protected void updateLeftHeight() {
        leftHeight = getNewHeight(left) + 1;
    }

    protected void updateRightHeight() {
        rightHeight = getNewHeight(right) + 1;
    }

    private int getNewHeight(BinaryNode node) {
        if (node == null) {
            return - 1;
        }
        return Math.max(node.leftHeight, node.rightHeight);
    }

    protected boolean isBalanced() {
        int bf = getBalanceFactor();
        return bf < 2 && bf > -2 && (left == null || left.isBalanced()) && (right == null || right.isBalanced());
    }

    protected boolean isPrimeNumber() {
        if (value <= 1) {
            return false;
        }

        if (value <= 3) {
            return true;
        }

        if (value % 2 == 0 || value % 3 == 0) {
            return false;
        }

        for (int i = 5; i * i <= value; i += 6) {
            if (value % i == 0 || value % (i + 2) == 0) {
                return false;
            }
        }

        return true;
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

    protected BinaryNode getSmaller() {
        BinaryNode aux = this;
        while (aux.left != null) {
            aux = aux.left;
        }
        return aux;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append('{').append(level).append('}');
        sb.append('[').append(recurrences).append(']');
        sb.append('(').append(leftHeight).append(", ").append(rightHeight).append(')');
        sb.append("->").append(value);
        return sb.toString();
    }
}
