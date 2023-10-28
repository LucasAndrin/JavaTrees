package trees.btree;

import trees.Tree;
import trees.util.Key;

public class BTree<T extends Comparable<T>> implements Tree<T> {

    private int max = 5;

    private BTreeNode<T> root;

    public BTree() {

    }

    public BTree(int max) {
        setMax(max);
    }

    private void setMax(int max) {
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
            root.add(Key.create(value));

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
            Key<T> key = Key.create(value);
            root.remove(key);

            if (root.keys.isEmpty()) {
                root = root.getChild(0);
            } else if (!root.isLeaf() && root.ownKeyWasRemoved()) {
                int leftIndex = root.getNextIndex(key);
                BTreeNode<T> leftChild = root.childs.get(leftIndex);
                int rightIndex = leftIndex + 1;
                BTreeNode<T> rightChild = root.childs.get(rightIndex);

                root.mergeRightChild(rightIndex, leftChild, rightChild);
            }
        }
    }

    @Override
    public String show() {
        return null;
    }

}
