package trees;

public interface Tree<T extends Comparable<T>> {

    void clear();

    void add(T value);

    void remove(T value);

    boolean exists(T value);

    String show();

}
