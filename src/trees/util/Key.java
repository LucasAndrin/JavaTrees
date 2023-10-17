package trees.util;

public class Key<T extends Comparable<T>> {

    public T value;

    private Key(T value) {
        this.value = value;
    }

    public boolean isGreaterThanValue(T key) {
        return this.value.compareTo(key) > 0;
    }

    public boolean isLowerThanValue(T key) {
        return this.value.compareTo(key) < 0;
    }

    public static <T extends Comparable<T>> Key<T> create(T key) {
        return new Key<>(key);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Key{");
        sb.append(value);
        sb.append('}');
        return sb.toString();
    }
}
