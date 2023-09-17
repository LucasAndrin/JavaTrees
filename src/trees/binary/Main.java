package trees.binary;

import trees.binary.modifiers.FilterInterface;

public class Main {
    public static void main(String[] args) {
        BinaryTree tree = BinaryTree.create();

        for (int i = 0; i < 15; i++) {
            tree.add(i);
        }

        System.out.println("Árvore binária: ");
        System.out.println(tree.show());

        System.out.println("3) Escreva um algoritmo que retorna true se uma determinada árvore é uma árvore AVL e false caso contrário? ");
        System.out.println("Resultado: " + tree.isBalanced());

        System.out.println("4) Faça uma função que, dada uma árvore AVL, retorne à quantidade de nós que guardam números primos");
        System.out.println("Resultado: " + tree.filter(BinaryNode::isPrimeNumber).count());

        System.out.println("6) Escreva um método que receba um nível da árvore e mostre todos os nodos nesse nível.");
        System.out.println(tree.filter(node -> node.level == 3).show());

        System.out.println("7) Faça um método para somar os nós presentes nos níveis ímpares de uma árvore AVL.");
        System.out.println("Resultado: " + tree.filter(node -> node.level % 2 != 0).sum());
    }
}