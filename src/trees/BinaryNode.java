package trees;

public class BinaryNode<V extends Comparable<V>> extends Level {

    protected V value;

    protected BinaryNode<V> left, right;

    protected BinaryNode(int level, V value) {
        super(level);
        this.value = value;
    }

    protected boolean isEqualsTo(V value) {
        return value.compareTo(this.value) == 0;
    }

    protected boolean isGreaterThan(V value) {
        return value.compareTo(this.value) > 0;
    }

    protected boolean isLowerThan(V value) {
        return value.compareTo(this.value) < 0;
    }

    protected BinaryNode<V> getLower() {
        BinaryNode<V> aux = this;
        while (aux.left != null) {
            aux = aux.left;
        }
        return aux;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append('{').append(level).append('}');
        sb.append("->").append(value);
        return sb.toString();
    }
}
