package trees.BalancedBinary;

import trees.Tree;

import java.util.function.Predicate;

public class BalancedBinaryTree<V extends Comparable<V>> extends Tree<V> {

    private BinaryNode<V> root;

    public void add(V value) {
        root = addNode(root, value, 1);
    }

    private BinaryNode<V> addNode(BinaryNode<V> node, V value, int level) {
        if (node == null) {
            return new BinaryNode<>(value, level);
        }

        if (node.isLowerThan(value)) {
            node.left = addNode(node.left, value, level + 1);
            node.updateLeftHeight();
        } else if (node.isGreaterThan(value)) {
            node.right = addNode(node.right, value, level + 1);
            node.updateRightHeight();
        }

        if (!node.value.equals(value)) {
            node = balance(node, level);
        }

        return node;
    }

    public void remove(V value) {
        root = removeNode(root, value);
    }

    private BinaryNode<V> removeNode(BinaryNode<V> node, V value) {
        if (node == null) {
            return null;
        }

        if (node.isLowerThan(value)) {
            node.left = removeNode(node.left, value);
            node.updateLeftHeight();
        } else if (node.isGreaterThan(value)) {
            node.right = removeNode(node.right, value);
            node.updateRightHeight();
        } else if (node.left == null) {
            return node.right;
        } else if (node.right == null) {
            return node.left;
        } else {
            node.value = node.right.getSmaller().value;
            node.right = removeNode(node.right, node.value);
            node.updateRightHeight();
        }

        node = balance(node, node.level);

        return node;
    }

    private BinaryNode<V> balance(BinaryNode<V> node, int level) {
        int bf = node.getBalanceFactor();
        if (bf == 2) {
            int bfRight = node.right.getBalanceFactor();
            if (bfRight < 0) {
                node.right = rotateRight(node.right);
            }
            node = rotateLeft(node);
            node.updateLevels(level);
        } else if (bf == -2) {
            int bfLeft = node.left.getBalanceFactor();
            if (bfLeft > 0) {
                node.left = rotateLeft(node.left);
            }
            node = rotateRight(node);
            node.updateLevels(level);
        }
        return node;
    }

    private BinaryNode<V> rotateRight(BinaryNode<V> node) {
        BinaryNode<V> aux = node.left;
        node.left = aux.right;
        aux.right = node;

        node.updateLeftHeight();
        aux.updateRightHeight();

        return aux;
    }

    private BinaryNode<V> rotateLeft(BinaryNode<V> node) {
        BinaryNode<V> aux = node.right;
        node.right = aux.left;
        aux.left = node;

        node.updateRightHeight();
        aux.updateLeftHeight();

        return aux;
    }

    public BalancedBinaryTree<V> filter(Predicate<BinaryNode<V>> func) {
        BalancedBinaryTree<V> tree = new BalancedBinaryTree<>();
        filter(tree, root, func);
        return tree;
    }

    private void filter(BalancedBinaryTree<V> tree, BinaryNode<V> node, Predicate<BinaryNode<V>> func) {
        if (node == null) {
            return;
        }

        if (func.test(node)) {
            tree.add(node.value);
        }

        filter(tree, node.left, func);
        filter(tree, node.right, func);
    }

    public int count() {
        return count(root);
    }

    private int count(BinaryNode<V> node) {
        if (node == null) {
            return 0;
        }

        return count(node.left) + count(node.right) + 1;
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
