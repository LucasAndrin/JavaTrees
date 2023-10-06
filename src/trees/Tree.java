package trees;

public abstract class Tree<V extends Comparable<V>> {

    public abstract void add(V value);


    abstract void remove(V value);

}
