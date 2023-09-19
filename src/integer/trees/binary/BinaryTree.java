package integer.trees.binary;

import integer.trees.FilterInterface;

public class BinaryTree {

    private integer.trees.binary.BinaryNode root;

    private boolean balance = true;

    private BinaryTree() {

    }

    public static BinaryTree create() {
        return new BinaryTree();
    }

    public BinaryTree withoutBalance() {
        balance = false;
        return this;
    }

    public void add(int value) {
        root = addNode(root, value, 1);
    }

    private integer.trees.binary.BinaryNode addNode(integer.trees.binary.BinaryNode node, int value, int level) {
        if (node == null) {
            return new integer.trees.binary.BinaryNode(value, level);
        }

        if (value > node.value) {
            node.right = addNode(node.right, value, level + 1);
            node.updateRightHeight();
        } else if (value < node.value) {
            node.left = addNode(node.left, value, level + 1);
            node.updateLeftHeight();
        } else {
            node.recurrences++;
        }
        if (balance && node.value != value) {
            node = balance(node, level);
        }

        return node;
    }

    public void remove(int value) {
        root = removeNode(root, value);
    }

    private integer.trees.binary.BinaryNode removeNode(integer.trees.binary.BinaryNode node, int value) {
        if (node == null) {
            return null;
        }

        if (value < node.value) {
            node.left = removeNode(node.left, value);
            node.updateLeftHeight();
        } else if (value > node.value) {
            node.right = removeNode(node.right, value);
            node.updateRightHeight();
        } else if (node.recurrences > 0) {
            node.recurrences--;
            return node;
        } else if (node.left == null) {
            return node.right;
        } else if (node.right == null) {
            return node.left;
        } else {
            node.value = node.right.getSmaller().value;
            node.right = removeNode(node.right, node.value);
            node.updateRightHeight();
        }

        if (balance) {
            node = balance(node, node.level);
        }

        return node;
    }

    private integer.trees.binary.BinaryNode balance(integer.trees.binary.BinaryNode node, int level) {
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

    private integer.trees.binary.BinaryNode rotateRight(integer.trees.binary.BinaryNode node) {
        integer.trees.binary.BinaryNode aux = node.left;
        node.left = aux.right;
        aux.right = node;

        node.updateLeftHeight();
        aux.updateRightHeight();

        return aux;
    }

    private integer.trees.binary.BinaryNode rotateLeft(integer.trees.binary.BinaryNode node) {
        integer.trees.binary.BinaryNode aux = node.right;
        node.right = aux.left;
        aux.left = node;

        node.updateRightHeight();
        aux.updateLeftHeight();

        return aux;
    }

    public boolean isBalanced() {
        return root == null || root.isBalanced();
    }

    public int count() {
        return count(root);
    }

    private int count(integer.trees.binary.BinaryNode node) {
        if (node == null) {
            return 0;
        }

        return count(node.left) + count(node.right) + 1;
    }

    public BinaryTree filter(FilterInterface<integer.trees.binary.BinaryNode> func) {
        BinaryTree tree = create();
        filter(tree, root, func);
        return tree;
    }

    private void filter(BinaryTree tree, integer.trees.binary.BinaryNode node, FilterInterface<integer.trees.binary.BinaryNode> func) {
        if (node == null) {
            return;
        }

        if (func.apply(node)) {
            tree.add(node.value);
        }

        filter(tree, node.left, func);
        filter(tree, node.right, func);
    }

    public int sum() {
        return sum(root);
    }

    private int sum(integer.trees.binary.BinaryNode node) {
        if (node == null) {
            return 0;
        }

        return node.value + sum(node.left) + sum(node.right);
    }

    public String show() {
        final StringBuilder sb = new StringBuilder();
        sb.append("root").append(root != null ? show(root) : "->null");
        return sb.toString();
    }

    private String show(integer.trees.binary.BinaryNode node) {
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
