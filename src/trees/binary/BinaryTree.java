package trees.binary;

import trees.binary.modifiers.FilterInterface;

public class BinaryTree {

    private BinaryNode root;

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

    private BinaryNode addNode(BinaryNode node, int value, int level) {
        if (node == null) {
            return new BinaryNode(value, level);
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

    private BinaryNode removeNode(BinaryNode node, int value) {
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

    private BinaryNode balance(BinaryNode node, int level) {
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

    private BinaryNode rotateRight(BinaryNode node) {
        BinaryNode aux = node.left;
        node.left = aux.right;
        aux.right = node;

        node.updateLeftHeight();
        aux.updateRightHeight();

        return aux;
    }

    private BinaryNode rotateLeft(BinaryNode node) {
        BinaryNode aux = node.right;
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

    private int count(BinaryNode node) {
        if (node == null) {
            return 0;
        }

        return count(node.left) + count(node.right) + 1;
    }

    public BinaryTree filter(FilterInterface<BinaryNode> func) {
        BinaryTree tree = create();
        filter(tree, root, func);
        return tree;
    }

    private void filter(BinaryTree tree, BinaryNode node, FilterInterface<BinaryNode> func) {
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

    private int sum(BinaryNode node) {
        if (node == null) {
            return 0;
        }

        return node.value + sum(node.left) + sum(node.right);
    }

    public String show() {
        final StringBuilder sb = new StringBuilder();
        sb.append("root").append(root != null ? show(root, 1) : "->null");
        return sb.toString();
    }

    private String show(BinaryNode node, int level) {
        final StringBuilder sb = new StringBuilder();

        String space = "    ".repeat(Math.max(0, level));
        sb.append("(occurrences: ").append(node.recurrences).append(", level: ").append(node.level).append(')');
        sb.append('[').append(node.leftHeight)
                .append(", ").append(node.rightHeight)
                .append("]->")
                .append(node.value).append(":\n");

        if (node.left != null) {
            sb.append(space).append("left").append(show(node.left, level + 1));
        }

        if (node.right != null) {
            sb.append(space).append("right").append(show(node.right, level + 1));
        }

        return sb.toString();
    }

}
