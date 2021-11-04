package lr3;

public class Main {
    public static void main(String[] args) {
        TSP m = new TSP(100);

        AntColonyOptimization ac = new AntColonyOptimization(m.getDistanceMatrix());

            ac.solve();

        System.out.print("Greedy solution: ");
        System.out.println(TSP.greedySolution(m.getDistanceMatrix()));


    }
}
