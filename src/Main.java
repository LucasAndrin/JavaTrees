import trees.BinarySearchTree;
import trees.Tree;
import trees.btree.BTree;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        BTree<Integer> bTree = new BTree<>();
        for (int i = 1; i <= 53; i++) {
            bTree.add(i);
        }
        int[] numbers = {53, 52, 48, 51, 45, 50, 46};

        for (int number : numbers) {
            bTree.remove(number);
        }
        System.out.println(bTree);
//        test();
    }

    private static void test() {
        int[] limits = { 100000, 1000000 };
        List<Tree<Integer>> trees = new ArrayList<>();
        trees.add(new BTree<>(20));
        trees.add(new BinarySearchTree<>());
//        trees.add(new BinaryTree<>());

        for (int limit : limits) {
            System.out.println("#######################################");
            System.out.println("LIMIT: " + limit);

            System.out.println("RANDOMIZED: ");
            int[] integers = Helper.getRandomizedIntegersByLimit(limit);

            for (Tree<Integer> tree : trees) {
                testTree(tree, integers);
            }

            System.out.println("LINEAR: ");
            integers = Helper.getIntegersByLimit(limit);

            for (Tree<Integer> tree : trees) {
                testTree(tree, integers);
            }
        }
    }

    private static void testTree(Tree<Integer> tree, int[] integers) {
        System.out.println(tree.getClass().getSimpleName());

        double elapsedTime = insertions(tree, integers);
        System.out.println("Insertions: " + elapsedTime + "ms");

        elapsedTime = search(tree, integers);
        System.out.println("Search: " + elapsedTime + "ms");

        elapsedTime = deletes(tree, integers);
        System.out.println("Deletes: " + elapsedTime + "ms");
        tree.clear();
    }

    private static double insertions(Tree<Integer> tree, int[] integers) {
        double time = 0;
        for (int integer : integers) {
            time += Helper.getExecutionTime(() -> tree.add(integer));
        }

        return time;
    }

    private static double search(Tree<Integer> tree, int[] integers) {
        return Helper.getExecutionTime(() -> tree.exists(integers.length));
    }

    private static double deletes(Tree<Integer> tree, int[] integers) {
        double time = 0;
        for (int integer : integers) {
            time += Helper.getExecutionTime(() -> tree.remove(integer));
        }

        return time;
    }
}
