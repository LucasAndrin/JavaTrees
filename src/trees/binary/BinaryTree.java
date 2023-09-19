package trees.binary;

import integer.trees.FilterInterface;
import trees.interfaces.CompareInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class BinaryTree<V> {
    private BinaryNode<V> root;

    private boolean balance = true;

    private final CompareInterface<V> leftCompareInterface, rightCompareInterface;

    public  BinaryTree(CompareInterface<V> leftCompareInterface, CompareInterface<V> rightCompareInterface) {
        this.leftCompareInterface = leftCompareInterface;
        this.rightCompareInterface = rightCompareInterface;
    }

    public BinaryTree<V> withoutBalance() {
        balance = false;
        return this;
    }

    public void add(V value) {
        root = addNode(root, value, 1);
    }

    private BinaryNode<V> addNode(BinaryNode<V> node, V value, int level) {
        if (node == null) {
            return new BinaryNode<>(value, level);
        }

        if (leftCompareInterface.compare(value, node.value)) {
            node.left = addNode(node.left, value, level + 1);
            node.updateLeftHeight();
        } else if (rightCompareInterface.compare(value, node.value)) {
            node.right = addNode(node.right, value, level + 1);
            node.updateRightHeight();
        } else {
            node.recurrences++;
        }

        if (balance && !node.value.equals(value)) {
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

        if (leftCompareInterface.compare(value, node.value)) {
            node.left = removeNode(node.left, value);
            node.updateLeftHeight();
        } else if (rightCompareInterface.compare(value, node.value)) {
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

    public boolean isBalanced() {
        return root == null || root.isBalanced();
    }

    public BinaryTree<V> filter(FilterInterface<BinaryNode<V>> func) {
        BinaryTree<V> tree = new BinaryTree<>(leftCompareInterface, rightCompareInterface);
        filter(tree, root, func);
        return tree;
    }

    private void filter(BinaryTree<V> tree, BinaryNode<V> node, FilterInterface<BinaryNode<V>> func) {
        if (node == null) {
            return;
        }

        if (func.apply(node)) {
            tree.add(node.value);
        }

        filter(tree, node.left, func);
        filter(tree, node.right, func);
    }

    public <R> List<R> pluck(Function<BinaryNode<V>, R> attributeExtractor) {
        List<R> extractedAttributes = new ArrayList<>();
        pluckNode(root, extractedAttributes, attributeExtractor);
        return extractedAttributes;
    }

    public <R> void pluckNode(BinaryNode<V> node, List<R> extractedAttributes, Function<BinaryNode<V>, R> attributeExtractor) {
        if (node == null) {
            return;
        }

        extractedAttributes.add(attributeExtractor.apply(node));

        pluckNode(node.left, extractedAttributes, attributeExtractor);
        pluckNode(node.right, extractedAttributes, attributeExtractor);
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

    public List<V> getArrayList() {
        List<V> list = new ArrayList<>();
        getArrayList(list, root);
        return list;
    }

    private void getArrayList(List<V> list, BinaryNode<V> node) {
        if (node == null) {
            return;
        }

        list.add(node.value);

        getArrayList(list, node.left);
        getArrayList(list, node.right);
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
