package trees.util;

public class Key<T extends Comparable<T>> {

    public T value;

    private Key(T value) {
        this.value = value;
    }

    public boolean isGreaterOrEqualsToValue(T value) {
        return this.value.compareTo(value) >= 0;
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

    public boolean isEqualsTo(Key<T> key) {
        return this.value.compareTo(key.value) == 0;
    }

    public boolean isGreaterThan(Key<T> key) {
        return this.value.compareTo(key.value) > 0;
    }

    public boolean isLowerThan(Key<T> key) {
        return this.value.compareTo(key.value) < 0;
    }

    public boolean isGreaterOrEqualsTo(Key<T> key) {
        return isEqualsTo(key) || isGreaterThan(key);
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
