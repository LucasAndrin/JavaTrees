package trees.interfaces;

@FunctionalInterface
public interface FilterInterface<N> {
    boolean apply(N node);
}