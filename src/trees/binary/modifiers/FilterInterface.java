package trees.binary.modifiers;

@FunctionalInterface
    public interface FilterInterface<N> {
        boolean apply(N node);
    }