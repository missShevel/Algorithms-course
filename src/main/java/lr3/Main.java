package lr3;

public class Main {
    public static void main(String[] args) {
        TSP m = new TSP(100);
        //m.printDistanceMatrix();
        AntColonyOptimization ac = new AntColonyOptimization(m.getDistanceMatrix());
        for (int i = 0; i < 3; i++) {
            ac.solve();
            System.out.println("//////");
        }

        System.out.print("Greedy solution: ");
        System.out.println(TSP.greedySolution(m.getDistanceMatrix()));


    }
}
