package trees.interfaces;

@FunctionalInterface
public interface CompareInterface<N> {
    boolean compare(N value, N treeValue);
}
