package trees.btrees;

import trees.Tree;

public class BTree<T extends Comparable<T>> implements Tree<T> {

    private int order = 4;

    private BNode<T> root;

    public BTree() {

    }

    public BTree(int order) {
        this.order = order;
    }

    @Override
    public void clear() {
        root = null;
    }

    @Override
    public void add(T value) {
        if (root == null) {
            root = BNode.create(value, order).root();
        } else {
            root.add(value);
        }
    }

    @Override
    public void remove(T value) {

    }

    @Override
    public String show() {
        return null;
    }
}
