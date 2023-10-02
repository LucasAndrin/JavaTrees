import trees.binary.BinaryTree;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class Main {
    public static void main(String[] args) {
        BinaryTree<City> tree = new BinaryTree<>(
                (value, treeValue) -> Objects.hashCode(value.name) < Objects.hashCode(treeValue.name),
                (value, treeValue) -> Objects.hashCode(value.name) > Objects.hashCode(treeValue.name)
        );

        tree.add(new City("Rio do Sul", 261 , 72006));
        tree.add(new City("Blumenau", 518.619, 361855));
        tree.add(new City("Rio do Oeste", 245 , 7520));

        System.out.println("a. Contar o número de municípios, percorrendo os nós cadastrados na árvore.");
        System.out.println("Resultado:" + tree.count());

        System.out.println("b. Mostrar apenas os nomes dos municípios com mais de X habitantes. Por exemplo, X\npode ser 100.000 pessoas.");
        System.out.println(tree.filter(node -> node.value.population >= 100).pluck(node -> node.value.name));

        System.out.println("c. Mostrar a densidade demográfica de cada cidade. A densidade demográfica é a\nrelação entre a população e a área.");
        System.out.println(tree.pluck(node -> node.value.getDemographicDensity()));

        System.out.println("d. Mostrar o somatório de área em km2 de todas as cidades juntas em relação ao\nterritório nacional (em porcentagem).");
        List<Double> areas = tree.pluck(node -> node.value.area);
        double total = 0;
        for (double area : areas) {
            total += area;
        }
        System.out.println("Resultado: " + total * 100 / 8510000 + "%");

        System.out.println("e. Mostrar o nome da cidade com a maior população.");
        Comparator<City> comparadorPorPreco = Comparator.comparingInt(city -> city.population);
        List<City> cities = tree.getArrayList();
        Collections.sort(tree.getArrayList(), comparadorPorPreco);
        System.out.println("Resultado: " + cities.get(0).name);
    }
}
