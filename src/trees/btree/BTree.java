package trees.btree;

import trees.Tree;

public class BTree<T extends Comparable<T>> implements Tree<T> {

    private int limit = 5;

    private BTreeNode<T> root;

    public BTree() {

    }

    public BTree(int limit) {
        setLimit(limit);
    }

    private void setLimit(int limit) {
        if (limit < 3) {
            throw new IllegalArgumentException("limit must be an integer bigger than 2");
        }
        this.limit = limit;
    }

    @Override
    public void clear() {
        root = null;
    }

    @Override
    public void add(T value) {
        if (root == null) {
            root = BTreeNode.create(value, limit).root();
        } else {
            root.add(value);

            if (root.isFull()) {
                splitRoot();
            }
        }
    }

    public boolean exists(T value) {
        return root != null && root.exists(value);
    }


    @Override
    public void remove(T value) {

    }

    @Override
    public String show() {
        return null;
    }

    protected void splitRoot() {
        BTreeNode<T> newRoot = BTreeNode.create(limit);
        newRoot.childs.add(root.leaf());
        newRoot.root().splitChild(0, root);

        root = newRoot;
    }

}
