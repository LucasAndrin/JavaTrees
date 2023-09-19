public class City {

    public String name;

    public double area;

    public int population;

    public City(String name, double area, int population) {
        this.name = name;
        this.area = area;
        this.population = population;
    }

    public double getDemographicDensity() {
        return population / area;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("City{");
        sb.append("name='").append(name).append('\'');
        sb.append(", area=").append(area);
        sb.append(", population=").append(population);
        sb.append('}');
        return sb.toString();
    }
}
