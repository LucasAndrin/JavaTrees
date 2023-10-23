package trees.btree;

import trees.util.Key;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.ListIterator;

public class BTreeNode<T extends Comparable<T>> {

    protected LinkedList<Key<T>> keys = new LinkedList<>();

    protected LinkedList<BTreeNode<T>> childs = new LinkedList<>();

    protected int limit;

    protected boolean isRoot = false;

    public BTreeNode(int limit) {
        this.limit = limit;
    }

    private BTreeNode(T value, int limit) {
        keys.addFirst(Key.create(value));
        this.limit = limit;
    }

    private BTreeNode(Key<T> key, int limit) {
        keys.addFirst(key);
        this.limit = limit;
    }

    protected boolean isLeaf() {
        return childs.isEmpty();
    }

    protected boolean isFull() {
        return keys.size() == limit;
    }

    protected void add(T value) {
        int index = getNextIndex(value);
        if (isLeaf()) {
            keys.add(index, Key.create(value));
        } else {
            BTreeNode<T> child = childs.get(index);
            child.add(value);

            if (child.isFull()) {
                splitChild(index, child);
            }
        }
    }

    protected boolean exists(T value) {
        ListIterator<Key<T>> iterator = keys.listIterator();
        while (iterator.hasNext()) {
            Key<T> key = iterator.next();

            if (key.isEqualsToValue(value)) {
                return true;
            } else if (key.isGreaterThanValue(value)) {
                int index = iterator.previousIndex();
                return checkChildIndex(index) && childs.get(index).exists(value);
            } else if (!iterator.hasNext()) {
                int index = iterator.nextIndex();
                return checkChildIndex(index) && childs.get(index).exists(value);
            }
        }

        return false;
    }

    protected void splitChild(int nextIndex, BTreeNode<T> child) {
        int index = child.getMediumIndex();
        Key<T> nextKey = child.keys.get(index);
        LinkedList<BTreeNode<T>> splitedChildren = child.getSplittedChilds(index);

        keys.add(nextIndex, nextKey);
        if (!isLeaf()) {
            childs.remove(nextIndex);
        }
        childs.addAll(nextIndex, splitedChildren);
    }

    protected LinkedList<BTreeNode<T>> getSplittedChilds(int splitIndex) {
        ListIterator<Key<T>> kIterator = keys.listIterator();
        BTreeNode<T> left = BTreeNode.create(limit);
        BTreeNode<T> right = BTreeNode.create(limit);

        if (isLeaf()) {
            while (kIterator.hasNext()) {
                int kIndex = kIterator.nextIndex();
                Key<T> key = kIterator.next();
                if (kIndex < splitIndex) {
                    left.keys.add(key);
                } else if (kIndex > splitIndex) {
                    right.keys.add(key);
                }
            }
        } else {
            ListIterator<BTreeNode<T>> cIterator = childs.listIterator();
            left.childs.add(cIterator.next());
            while (kIterator.hasNext()) {
                int kIndex = kIterator.nextIndex();
                Key<T> key = kIterator.next();
                BTreeNode<T> child = cIterator.next();

                if (kIndex < splitIndex) {
                    left.keys.add(key);
                    left.childs.add(child);
                } else if (kIndex > splitIndex) {
                    right.keys.add(key);
                    right.childs.add(child);
                } else {
                    right.childs.add(child);
                }
            }
        }

        return new LinkedList<>(Arrays.asList(left, right));
    }

    protected int getNextIndex(T value) {
        ListIterator<Key<T>> iterator = keys.listIterator();
        while (iterator.hasNext()) {
            if (iterator.next().isGreaterThanValue(value)) {
                return iterator.previousIndex();
            }
        }
        return iterator.nextIndex();
    }

    protected int getMediumIndex() {
        return keys.size() / 2;
    }

    private boolean checkChildIndex(int index) {
        return index >= 0 && index < childs.size();
    }

    protected static <T extends Comparable<T>> BTreeNode<T> create(int limit) {
        return new BTreeNode<>(limit);
    }

    protected static <T extends Comparable<T>> BTreeNode<T> create(T value, int limit) {
        return new BTreeNode<>(value, limit);
    }

    protected static <T extends Comparable<T>> BTreeNode<T> create(Key<T> key, int limit) {
        return new BTreeNode<>(key, limit);
    }

    protected BTreeNode<T> root() {
        isRoot = true;
        return this;
    }

    protected BTreeNode<T> leaf() {
        isRoot = false;
        return this;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(keys);
        return sb.toString();
    }
}
