package trees.btree;

import trees.Tree;

public class BTree<T extends Comparable<T>> implements Tree<T> {

    private int max = 5;

    private BTreeNode<T> root;

    public BTree() {

    }

    public BTree(int max) {
        setLimit(max);
    }

    private void setLimit(int max) {
        if (max < 3) {
            throw new IllegalArgumentException("max must be an integer bigger than 2");
        }
        this.max = max;
    }

    @Override
    public void clear() {
        root = null;
    }

    @Override
    public void add(T value) {
        if (root == null) {
            root = BTreeNode.create(value, max);
        } else {
            root.add(value);

            if (root.keysHasSurplus()) {
                BTreeNode<T> newRoot = BTreeNode.create(max);
                newRoot.childs.add(root);
                newRoot.splitChild(0, root);
                root = newRoot;
            }
        }
    }

    public boolean exists(T value) {
        return root != null && root.exists(value);
    }


    @Override
    public void remove(T value) {
        if (root != null) {
            root.remove(value);
        }
    }

    @Override
    public String show() {
        return null;
    }

}
