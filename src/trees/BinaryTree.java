package trees;

public class BinaryTree<V extends Comparable<V>> implements Tree<V> {

    private BinaryNode<V> root;

    public void clear() {
        root = null;
    }

    @Override
    public void add(V value) {
        root = addNode(root, 1, value);
    }

    private BinaryNode<V> addNode(BinaryNode<V> node, int level, V value) {
        if (node == null) {
            return new BinaryNode<>(level, value);
        }

        if (node.isLowerThan(value)) {
            node.left = addNode(node.left, level + 1, value);
        } else if (node.isGreaterThan(value)) {
            node.right = addNode(node.right, level + 1, value);
        }

        return node;
    }

    @Override
    public void remove(V value) {
        root = removeNode(root, value);
    }

    private BinaryNode<V> removeNode(BinaryNode<V> node, V value) {
        if (node == null) {
            return null;
        }

        if (node.isLowerThan(value)) {
            node.left = removeNode(node.left, value);
        } else if (node.isGreaterThan(value)) {
            node.right = removeNode(node.right, value);
        } else if (node.left == null) {
            return node.right;
        } else if (node.right == null) {
            return node.left;
        } else {
            node.value = node.right.getLower().value;
            node.right = removeNode(node.right, node.value);
        }

        return node;
    }

    @Override
    public boolean exists(V value) {
        return existsNode(root, value);
    }

    boolean existsNode(BinaryNode<V> node, V value) {
        if (node == null) {
            return false;
        }

        if (node.isLowerThan(value)) {
            return existsNode(node.left, value);
        }

        if (node.isGreaterThan(value)) {
            return existsNode(node.right, value);
        }

        return true;
    }

    public String show() {
        final StringBuilder sb = new StringBuilder();
        sb.append("root").append(root != null ? show(root) : "->null");
        return sb.toString();
    }

    private String show(BinaryNode<V> node) {
        final StringBuilder sb = new StringBuilder();

        String space = "    ".repeat(Math.max(0, node.level));
        sb.append(node).append(":\n");

        if (node.left != null) {
            sb.append(space).append("left").append(show(node.left));
        }

        if (node.right != null) {
            sb.append(space).append("right").append(show(node.right));
        }

        return sb.toString();
    }

}
