package lr4;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
//        MatrixManager m = new MatrixManager(20);
//        m.saveInFile();
        MatrixManager matrix = new MatrixManager();

        for (int i = 0; i < matrix.getNumberOfCities(); i++) {
            TourManager.addCity(i);
        }

        Population pop = new Population(50, true);
        System.out.println("Initial distance: " + pop.getFittest().getDistance());

        pop = GA.evolvePopulation(pop);
        for (int i = 0; i < 100; i++) {
            pop = GA.evolvePopulation(pop);
        }
        System.out.println("Finished");
        System.out.println("Final distance: " + pop.getFittest().getDistance());
        System.out.println("Solution:");
        System.out.println(pop.getFittest());
}

    }
