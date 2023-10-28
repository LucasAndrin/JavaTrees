package trees.btree;

import trees.util.Key;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.ListIterator;

public class BTreeNode<T extends Comparable<T>> {

    protected int max;

    protected LinkedList<Key<T>> keys = new LinkedList<>();

    protected LinkedList<BTreeNode<T>> childs = new LinkedList<>();

    private BTreeNode(int max) {
        this.max = max;
    }

    private BTreeNode(T value, int max) {
        keys.addFirst(Key.create(value));
        this.max = max;
    }

    private BTreeNode(Key<T> key, int max) {
        keys.addFirst(key);
        this.max = max;
    }

    private int getMaximumKeysSize() {
        return max - 1;
    }

    private int getMinimumKeysSize() {
        return max / 2;
    }

    protected boolean isLeaf() {
        return childs.isEmpty();
    }

    protected boolean keysHasSurplus() {
        return keys.size() > getMaximumKeysSize();
    }

    protected boolean keysHasLack() {
        return keys.size() < getMinimumKeysSize();
    }

    protected boolean keysReachMaximumSize() {
        return keys.size() == getMaximumKeysSize();
    }

    protected boolean keysHasMoreThanMinimumSize() {
        return keys.size() > getMinimumKeysSize();
    }

    protected boolean ownKeyWasRemoved() {
        return keys.size() < childs.size() - 1;
    }

    protected void add(Key<T> key) {
        if (isLeaf()) {
            addKey(key);
        } else {
            int index = getNextIndex(key);
            BTreeNode<T> child = childs.get(index);
            child.add(key);

            if (child.keysHasSurplus()) {
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

    protected void remove(Key<T> key) {
        ListIterator<Key<T>> kIterator = keys.listIterator();
        int index = 0;
        while (kIterator.hasNext()) {
            Key<T> next = kIterator.next();
            if (next.isEqualsTo(key)) {
                kIterator.remove();
                return;
            } else if (next.isLowerThan(key)) {
                index = kIterator.nextIndex();
            } else {
                index = kIterator.previousIndex();
                break;
            }
        }

        BTreeNode<T> child = childs.get(index);
        child.remove(key);

        if (!child.isLeaf() && child.ownKeyWasRemoved()) {
            int leftIndex = child.getNextIndex(key);
            BTreeNode<T> leftChild = child.childs.get(leftIndex);

            if (leftChild.keysHasMoreThanMinimumSize()) {
                child.keys.add(leftIndex, leftChild.removeGreaterKey());
                return;
            }

            int rightIndex = leftIndex + 1;
            BTreeNode<T> rightChild = child.childs.get(rightIndex);
            if (rightChild.keysHasMoreThanMinimumSize()) {
                child.keys.add(leftIndex, rightChild.removeLowerKey());
                return;
            }

            child.mergeChilds(rightIndex, leftChild, rightChild);
        }

        if (child.keysHasLack()) {
            // Check if left brother can borrow a key
            int leftIndex = index - 1;
            BTreeNode<T> leftBrother = getChild(leftIndex);
            if (leftBrother != null && leftBrother.keysHasMoreThanMinimumSize()) {
                Key<T> newChildKey = keys.set(leftIndex, leftBrother.removeGreaterKey());
                child.addKey(newChildKey);
                return;
            }

            // Check if right brother can borrow a key
            BTreeNode<T> rightBrother = getChild(index + 1);
            if (rightBrother != null && rightBrother.keysHasMoreThanMinimumSize()) {
                Key<T> newChildKey = keys.set(index, rightBrother.removeLowerKey());
                child.addKey(newChildKey);
                return;
            }

            childs.remove(index);
            if (leftBrother != null) {
                leftBrother.addKey(keys.remove(leftIndex));
                leftBrother.keys.addAll(child.keys);
                leftBrother.childs.addAll(child.childs);
            } else if (rightBrother != null) {
                rightBrother.addKey(keys.remove(index));
                rightBrother.keys.addAll(0, child.keys);
                rightBrother.childs.addAll(0, child.childs);
            }
        }
    }

    protected Key<T> removeGreaterKey() {
        if (isLeaf()) {
            return keys.removeLast();
        }
        return childs.getLast().removeGreaterKey();
    }

    protected Key<T> removeLowerKey() {
        if (isLeaf()) {
            return keys.removeFirst();
        }
        return childs.getFirst().removeLowerKey();
    }

    protected void addKey(Key<T> key) {
        ListIterator<Key<T>> iterator = keys.listIterator();
        while (iterator.hasNext()) {
            if (iterator.next().isGreaterThan(key)) {
                iterator.previous();
                break;
            }
        }
        iterator.add(key);
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

    protected void mergeChilds(int rightIndex, BTreeNode<T> leftChild, BTreeNode<T> rightChild) {
        childs.remove(rightIndex);

        leftChild.childs.addAll(rightChild.childs);
        leftChild.keys.addAll(rightChild.keys);
    }

    protected LinkedList<BTreeNode<T>> getSplittedChilds(int splitIndex) {
        ListIterator<Key<T>> kIterator = keys.listIterator();
        BTreeNode<T> left = BTreeNode.create(max);
        BTreeNode<T> right = BTreeNode.create(max);

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

    protected BTreeNode<T> getChild(int index) {
        if (checkChildIndex(index)) {
            return childs.get(index);
        }
        return null;
    }

    protected int getNextIndex(Key<T> key) {
        ListIterator<Key<T>> iterator = keys.listIterator();
        while (iterator.hasNext()) {
            if (iterator.next().isGreaterOrEqualsTo(key)) {
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

    protected static <T extends Comparable<T>> BTreeNode<T> create(int max) {
        return new BTreeNode<>(max);
    }

    protected static <T extends Comparable<T>> BTreeNode<T> create(T value, int max) {
        return new BTreeNode<>(value, max);
    }

    protected static <T extends Comparable<T>> BTreeNode<T> create(Key<T> key, int max) {
        return new BTreeNode<>(key, max);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(keys);
        return sb.toString();
    }
}
