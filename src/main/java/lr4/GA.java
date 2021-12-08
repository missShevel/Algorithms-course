package lr4;

import java.io.IOException;
import java.util.ArrayList;

public class GA {
    /**
     * TODO:
     * add 2 more crossover operators:
     * - Ordered crossover (is implemented)
     * - Sequential constructive crossover - done
     * - Partially mapped crossover - done
     * <p>
     * add 1 more mutation
     * - Edge swaps (is implemented)
     * - Edge inversion (is implemented)
     * <p>
     * <p>
     * add 2 local adjustments
     * - 2Opt - done
     * - 3Opt - done
     */
    /* GA parameters */
     static final double mutationRate = 0.015;
    private static final int tournamentSize = 5;
    private static final boolean elitism = true;

    // Evolves a population over one generation
    public static Population evolvePopulation(Population population) throws IOException {
        Population newPopulation = new Population(population.populationSize(), false);

        // Keep our best individual if elitism is enabled
        int elitismOffset = 0;
        if (elitism) {
            newPopulation.saveTour(0, population.getFittest());
            elitismOffset = 1;
        }
        // Crossover population
        // Loop over the new population's size and create individuals from
        // Current population
        for (int i = elitismOffset; i < newPopulation.populationSize(); i++) {
            // Select parents
            Tour parent1 = tournamentSelection(population);
            Tour parent2 = tournamentSelection(population);
            // Crossover parents
            Tour child = crossover(parent1, parent2);
            // Add child to new population
            newPopulation.saveTour(i, child);
        }

        // Mutate the new population a bit to add some new genetic material
        for (int i = elitismOffset; i < newPopulation.populationSize(); i++) {
            mutate(newPopulation.getTour(i));
        }
        return newPopulation;
    }

///////////////////////////////////   Crossovers ///////////////////////////////////////////////////////////////////////
    // Applies crossover to a set of parents and creates offspring (Ordered crossover)
    public static Tour crossover(Tour parent1, Tour parent2) {
        return new Crossover().runOrderedCrossover(parent1, parent2);
       // return new Crossover().runSCXcrossover(parent1, parent2);
       // return new Crossover().runPMcrossover(parent1, parent2);
    }


    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    /////////////////////////////////////  Mutations ///////////////////////////////////////////////////////////////////////
    // Mutate a tour using swap 2 genes mutation
    private static void mutate(Tour tour) {
        // Loop through tour cities
        for (int tourPos1 = 0; tourPos1 < tour.tourSize(); tourPos1++) {
            // Apply mutation rate
            if (Math.random() < mutationRate) {
                // Get a second random position in the tour
                int tourPos2 = (int) (tour.tourSize() * Math.random());

                // Get the cities at target position in tour
                int city1 = tour.getCity(tourPos1);
                int city2 = tour.getCity(tourPos2);

                // Swap them around
                tour.setCity(tourPos2, city1);
                tour.setCity(tourPos1, city2);
            }
        }
    }

    //Edge inversion mutation
    private static void inversionMutation(Tour tour) {
        if (Math.random() < mutationRate) {
            int pos1 = (int) (tour.tourSize() * Math.random());
            int pos2 = pos1;
            while (pos2 == pos1) {
                pos2 = (int) (tour.tourSize() * Math.random());
            }
            reverseEdges(tour, pos1, pos2);
        }
    }

    static void reverseEdges(Tour tour, int startIndex, int endIndex) {
        int size = tour.tourSize();
        ArrayList<Integer> subTour = getSubtour(tour, startIndex, (endIndex + 1) % size);
        for (int k = 0; k < subTour.size(); k++) {
            int setIndex = (size + endIndex + 1 - (k + 1)) % size;
            tour.setCity(setIndex, subTour.get(k));
        }
    }

    private static ArrayList<Integer> getSubtour(Tour tour, int startIndex, int endIndex) {
        ArrayList<Integer> subTour = new ArrayList<Integer>();
        int index = startIndex;
        while (index != endIndex) {
            subTour.add(tour.getCity(index));
            index++;
            index = index % tour.tourSize();
        }
        return subTour;
    }

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Selects candidate tour for crossover
    private static Tour tournamentSelection(Population pop) {
        // Create a tournament population
        Population tournament = new Population(tournamentSize, false);
        // For each place in the tournament get a random candidate tour and
        // add it
        for (int i = 0; i < tournamentSize; i++) {
            int randomId = (int) (Math.random() * pop.populationSize());
            tournament.saveTour(i, pop.getTour(randomId));
        }
        // Get the fittest tour
        Tour fittest = tournament.getFittest();
        return fittest;
    }
}
