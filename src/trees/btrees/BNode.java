package trees.btrees;

import trees.util.Key;

import java.util.*;

public class BNode<T extends Comparable<T>> {

    protected LinkedList<Key<T>> keys = new LinkedList<>();

    protected List<BNode<T>> children = new ArrayList<>();

    protected boolean isRoot = false;

    private int order;

    private BNode(T value, int order) {
        keys.add(Key.create(value));
        this.order = order;
    }

    private BNode(Key<T> key, int order) {
        keys.add(key);
        this.order = order;
    }

    protected boolean isLeaf() {
        return children.isEmpty();
    }

    protected boolean isExceeded() {
        return keys.size() == order;
    }

    protected boolean isFull() {
        return keys.size() == order - 1;
    }

    protected void add(T value) {
        int index = nextKeyIndexByValue(value);
        if (isLeaf()) {
            keys.add(index, Key.create(value));
            if (isExceeded()) {
                Key<T> splitKey = split();
                keys.clear();
                keys.add(splitKey);
            }
        } else {
            BNode<T> child = children.get(index);
            child.add(this, value);
        }
    }

    protected void add(BNode<T> parent, T value) {
        int index = nextKeyIndexByValue(value);
        if (isLeaf()) {
            keys.add(index, Key.create(value));
            if (isExceeded()) {
                int splitIndex = getMediumIndex();
                Key<T> splitKey = keys.get(splitIndex);
                List<BNode<T>> splitedChildren = getSplitedChildrenByIndex(splitIndex);
                parent.keys.add(splitKey);
                parent.children.remove(parent.nextKeyIndexByValue(splitKey.value) - 1);
                parent.children.addAll(splitedChildren);
            }
        } else {
            BNode<T> child = children.get(index);
            child.add(this, value);
        }
    }

    private Key<T> split() {
        int splitIndex = getMediumIndex();

        children = getSplitedChildrenByIndex(splitIndex);

        return keys.get(splitIndex);
    }

    private List<BNode<T>> getSplitedChildrenByIndex(int splitIndex) {
        ListIterator<Key<T>> iterator = keys.listIterator();

        BNode<T> left = null;
        BNode<T> right = null;
        while (iterator.hasNext()) {
            int index = iterator.nextIndex();
            Key<T> key = iterator.next();

            if (index < splitIndex) {
                if (left == null) {
                    left = BNode.create(key, order);
                } else {
                    left.keys.add(key);
                }
            } else if (index > splitIndex) {
                if (right == null) {
                    right = BNode.create(key, order);
                } else {
                    right.keys.add(key);
                }
            }
        }
        return new ArrayList<>(Arrays.asList(left, right));
    }

    private int nextKeyIndexByValue(T value) {
        ListIterator<Key<T>> iterator = keys.listIterator();
        while (iterator.hasNext()) {
            if (iterator.next().isGreaterThanValue(value)) {
                return iterator.previousIndex();
            }
        }
        return iterator.nextIndex();
    }

    private int getMediumIndex() {
        return keys.size() / 2;
    }

    protected static <T extends Comparable<T>> BNode<T> create(T key, int order) {
        return new BNode<>(key, order);
    }

    protected static <T extends Comparable<T>> BNode<T> create(Key<T> key, int order) {
        return new BNode<>(key, order);
    }

    protected BNode<T> root() {
        isRoot = false;
        return this;
    }
}
