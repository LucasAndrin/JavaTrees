import trees.BinarySearchTree;
import trees.Tree;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        test();
    }

    public static void test() {
        int[] limits = { 100000, 1000000 };
        List<Tree<Integer>> trees = new ArrayList<>();
        trees.add(new BinarySearchTree<>());

        for (Tree<Integer> tree : trees) {
            String name = tree.getClass().getSimpleName();
            for (int limit : limits) {
                long elapsedTime = Helper.getExecutionTime(() -> insertions(tree, limit));
                System.out.println(name + "->" + limit + " insertions: " + elapsedTime + "ms");
                tree.clear();
            }
        }
    }

    public static void insertions(Tree<Integer> tree, int limit) {
        for (int i = 0; i < limit; i++) {
            tree.add(i);
        }
    }
}
