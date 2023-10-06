package trees;

public interface Tree<V extends Comparable<V>> {

    void clear();

    void add(V value);

    void remove(V value);

    String show();

}
