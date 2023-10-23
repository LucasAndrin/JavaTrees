package trees;

public class BinaryTree<V extends Comparable<V>> implements Tree<V> {

    private BinaryNode<V> root;

    public void clear() {
        root = null;
    }

    @Override
    public void add(V value) {
        int level = 1;

        if (root == null) {
            root = new BinaryNode<>(level, value);
        } else {
            BinaryNode<V> prev = null;
            BinaryNode<V> node = root;
            while (node != null) {
                prev = node;
                if (node.isGreaterThan(value)) {
                    node = node.left;
                } else if (node.isLowerThan(value)) {
                    node = node.right;
                } else {
                    return;
                }
                level++;
            }

            if (prev.isGreaterThan(value)) {
                prev.left = new BinaryNode<>(level, value);
            } else if (prev.isLowerThan(value)) {
                prev.right = new BinaryNode<>(level, value);
            }
        }
    }

    @Override
    public void remove(V value) {
        root = removeNode(root, value);
    }

    private BinaryNode<V> removeNode(BinaryNode<V> node, V value) {
        BinaryNode<V> current = node;
        BinaryNode<V> parent = null;

        while (current != null) {
            if (current.isGreaterThan(value)) {
                parent = current;
                current = current.left;
            } else if (current.isLowerThan(value)) {
                parent = current;
                current = current.right;
            } else {
                if (current.left == null) {
                    if (parent == null) {
                        return current.right;
                    }
                    if (parent.left == current) {
                        parent.left = current.right;
                    } else {
                        parent.right = current.right;
                    }
                    return node;
                } else if (current.right == null) {
                    if (parent == null) {
                        return current.left;
                    }
                    if (parent.left == current) {
                        parent.left = current.left;
                    } else {
                        parent.right = current.left;
                    }
                    return node;
                } else {
                    BinaryNode<V> successorParent = current;
                    BinaryNode<V> successor = current.right;
                    while (successor.left != null) {
                        successorParent = successor;
                        successor = successor.left;
                    }
                    current.value = successor.value;
                    if (successorParent == current) {
                        current.right = successor.right;
                    } else {
                        successorParent.left = successor.right;
                    }
                    return node;
                }
            }
        }
        return node;
    }

    @Override
    public boolean exists(V value) {
        BinaryNode<V> node = root;
        while (node != null) {
            if (node.isGreaterThan(value)) {
                node = node.left;
            } else if (node.isLowerThan(value)) {
                node = node.right;
            } else {
                return true;
            }
        }

        return false;
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
