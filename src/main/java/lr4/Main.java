package lr4;


import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {
//        MatrixManager m = new MatrixManager(300);
//        m.saveInFile();

        MatrixManager matrix = new MatrixManager();
        System.out.println("Greedy solution: ");
        TSP.solve(matrix.getDistanceMatrix());
        for (int i = 0; i < matrix.getNumberOfCities(); i++) {
            TourManager.addCity(i);
        }

        Population pop = new Population(50, true);
        System.out.println("Initial distance: " + pop.getFittest().getDistance());

        pop = GA.evolvePopulation(pop);
        for (int i = 0; i < 1000; i++) {
            pop = GA.evolvePopulation(pop);
            if(i%20 == 0){
                System.out.println("Best found solution on iteration " + (i + 20) + " : " + pop.getFittest().getDistance());
            }
        }
        System.out.println("Finished");
        System.out.println("Final distance: " + pop.getFittest().getDistance());

}

    }
