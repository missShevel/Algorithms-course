package lr4;

import java.io.IOException;
import java.util.ArrayList;

public class GA {
    /**
     * TODO:
     * add 2 more crossover operators:
     * - Ordered crossover (is implemented)
     * - sequential constructive crossover.(https://github.com/tkutz/genetic-algorithms/blob/master/plugins/com.tkutz.ai.genetic.tsp/src/com/tkutz/ai/genetic/tsp/operators/SCX.java)
     * - Partially mapped crossover (https://github.com/PLT875/Solving-the-TSP-using-Genetic-Algorithms/blob/master/src/Crossover/PMX.java)
     * <p>
     * add 1 more mutation
     * - Edge swaps (is implemented)
     * - Edge inversion (is implemented)
     * <p>
     * <p>
     * add 2 local adjustments
     * - 2Opt (https://github.com/tkutz/genetic-algorithms/blob/master/plugins/com.tkutz.ai.genetic.tsp/src/com/tkutz/ai/genetic/tsp/operators/TwoOptAlgorithm.java)- done
     * - 3Opt (https://github.com/adamcumiskey/TSPComparison/blob/master/Greedy3OptTSP.java) - done
     */
    /* GA parameters */
    private static final double mutationRate = 0.015;
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
    public static Tour crossover(Tour parent1, Tour parent2) throws IOException {
        // Create new child tour
        Tour child = new Tour();

        // Get start and end sub tour positions for parent1's tour
        int startPos = (int) (Math.random() * parent1.tourSize());
        int endPos = (int) (Math.random() * parent1.tourSize());

        // Loop and add the sub tour from parent1 to our child
        for (int i = 0; i < child.tourSize(); i++) {
            // If our start position is less than the end position
            if (startPos < endPos && i > startPos && i < endPos) {
                child.setCity(i, parent1.getCity(i));
            } // If our start position is larger
            else if (startPos > endPos) {
                if (!(i < startPos && i > endPos)) {
                    child.setCity(i, parent1.getCity(i));
                }
            }
        }

        // Loop through parent2's city tour
        for (int i = 0; i < parent2.tourSize(); i++) {
            // If child doesn't have the city add it
            if (!child.containsCity(parent2.getCity(i))) {
                // Loop to find a spare position in the child's tour
                for (int ii = 0; ii < child.tourSize(); ii++) {
                    // Spare position found, add city
                    if (child.getCity(ii) == -1) {
                        child.setCity(ii, parent2.getCity(i));
                        break;
                    }
                }
            }
        }


        ThreeOptAlgorithm.run(child);
        return child;
    }

//    public static Tour SCXcrossover(Tour parent1, Tour parent2) throws IOException {
//        Tour child = new Tour();
//        child.setCity(0, parent1.getCity(0));
//
//        for (int i = 1; i < parent1.tourSize(); i++) {
//            int currentCity = child.getCity(i-1);
//            int nextCityInParent1 = getNextCity(currentCity, parent1, child);
//            int nextCityInParent2 = getNextCity(currentCity, parent2, child);
//
//            float p1Length = Tour.getCost(currentCity, nextCityInParent1);
//            float p2Length = Tour.getCost(currentCity, nextCityInParent2);
//
//            int nextCity = p1Length < p2Length ? nextCityInParent1 : nextCityInParent2;
//            child.setCity(i, nextCity);
//
//        }
//        return child;
//    }

//    private static int getNextCity(int current, Tour parent, Tour child){
//        int pos = parent.getPosition(current);
//        int next;
//        if (pos == parent.tourSize() - 1) {
//            next = parent.getCity(0);
//        } else {
//            next = parent.getCity(pos + 1);
//        }
//        // check if next already used in offspring => take first free city
//        if (child.containsCity(next)) {
//            next = getFirstUnused(parent, child);
//        }
//        return next;
//    }

//    private static int getFirstUnused(Tour parent, Tour child) {
//        for (int city : parent.getCities()) {
//            if (!child.containsCity(city)) {
//                return city;
//            }
//        }
//        return 0;
//    }

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

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////// Local optimisations ///////////////////////////////////////////////////////////////////////


/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Selects candidate tour for crossover
    private static Tour tournamentSelection(Population pop) throws IOException {
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
