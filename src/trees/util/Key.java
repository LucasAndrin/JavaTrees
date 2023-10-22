package trees.util;

public class Key<T extends Comparable<T>> {

    public T value;

    private Key(T value) {
        this.value = value;
    }

    public boolean isEqualsToValue(T value) {
        return this.value.compareTo(value) == 0;
    }

    public boolean isGreaterThanValue(T value) {
        return this.value.compareTo(value) > 0;
    }

    public boolean isLowerThanValue(T value) {
        return this.value.compareTo(value) < 0;
    }

    public static <T extends Comparable<T>> Key<T> create(T key) {
        return new Key<>(key);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(value);
        return sb.toString();
    }
}
