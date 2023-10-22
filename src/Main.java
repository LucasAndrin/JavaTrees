import trees.BinarySearchTree;
import trees.BinaryTree;
import trees.Tree;
import trees.btree.BTree;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        test();
    }

    private static void test() {
        int[] factors = { 1, 5, 10, 25, 50, 100 };
        int[] limits = { 100000, 1000000 };
        List<Tree<Integer>> trees = new ArrayList<>();
        trees.add(new BinarySearchTree<>());
        trees.add(new BTree<>(25));

        for (Tree<Integer> tree : trees) {
            String name = tree.getClass().getSimpleName();
            for (int limit : limits) {
                System.out.println(name + "->" + limit + " insertions: ");
                for (int factor : factors) {
                    long elapsedTime = insertions(tree, limit, factor);
                    System.out.println("factor: " + factor + "->" + elapsedTime + "ms");

                    tree.clear();
                }
            }
        }
    }

    private static long insertions(Tree<Integer> tree, int limit, int factor) {
        if (factor <= 0) {
            throw new IllegalArgumentException("The 'factor' argument must be greater than 0!");
        }
        long time = 0;
        int halfLimit = limit / 2;
        for (int i = 0; i < halfLimit; i+= factor) {
            for (int j = i; j < Math.min(i + factor, halfLimit); j++) {
                int finalJ = j;
                time += Helper.getExecutionTime(() -> tree.add(finalJ));
            }
            for (int j = limit - 1 - i; j >= Math.max(limit - factor - 1, halfLimit); j--) {
                int finalJ = j;
                time += Helper.getExecutionTime(() -> tree.add(finalJ));
            }
        }

        return time;
    }
}
