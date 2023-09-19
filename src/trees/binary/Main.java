package trees.binary;

public class Main {
    public static void main(String[] args) {
        BinaryTree<Integer> tree = new BinaryTree<>(
                (value, treeValue) -> value < treeValue,
                (value, treeValue) -> value > treeValue
        );

        for (int i = 0; i < 15; i++) {
            tree.add(i);
        }

        System.out.println("Árvore binária: ");
        System.out.println(tree.show());

        System.out.println("2) Escreva o método de exclusão de um elemento da árvore AVL.");
        tree.remove(10);
        System.out.println(tree.show());

    }
}
