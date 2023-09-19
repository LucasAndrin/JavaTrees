package integer.trees;

@FunctionalInterface
    public interface FilterInterface<N> {
        boolean apply(N node);
    }