package lr4;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Tour {
    float[][] distanceMatrix = new MatrixManager().getDistanceMatrix();
    // Holds our tour of cities
    private ArrayList tour = new ArrayList<Integer>();
    // Cache
    private double fitness = 0;
    private float distance = 0;

    // Constructs a blank tour
    public Tour() throws IOException {
        for (int i = 0; i < TourManager.numberOfCities(); i++) {
            tour.add(-1);
        }

    }

    public Tour(ArrayList tour) throws IOException {
        this.tour = tour;
    }

    // Creates a random chromosome
    public void generateChromosome() {
        // Loop through all our destination cities and add them to our tour
        for (int cityIndex = 0; cityIndex < TourManager.numberOfCities(); cityIndex++) {
            setCity(cityIndex, TourManager.getCity(cityIndex));
        }
        // Randomly reorder the tour
        Collections.shuffle(tour);
    }

    // Gets a city from the tour
    public int getCity(int tourPosition) {
        return (int)tour.get(tourPosition);
    }

    // Sets a city in a certain position within a tour
    public void setCity(int tourPosition, int city) {
        tour.set(tourPosition, city);
        // If the tours been altered we need to reset the fitness and distance
        fitness = 0;
        distance = 0;
    }

    // Gets the tours fitness
    public double getFitness() {
        if (fitness == 0) {
            fitness = 1/(double)getDistance();
        }
        return fitness;
    }

    // Gets the total distance of the tour
    public float getDistance(){
        if (distance == 0) {
            float tourDistance = 0;
            // Loop through our tour's cities
            for (int cityIndex=0; cityIndex < tourSize(); cityIndex++) {
                // Get city we're travelling from
                int fromCity = getCity(cityIndex);
                // City we're travelling to
                int destinationCity;
                // Check we're not on our tour's last city, if we are set our
                // tour's final destination city to our starting city
                if(cityIndex+1 < tourSize()){
                    destinationCity = getCity(cityIndex+1);
                }
                else{
                    destinationCity = getCity(0);
                }
                // Get the distance between the two cities
                tourDistance += this.distanceMatrix[fromCity][destinationCity];
            }
            distance = tourDistance;
        }
        return distance;
    }

    // Get number of cities on our tour
    public int tourSize() {
        return tour.size();
    }

    // Check if the tour contains a city
    public boolean containsCity(int city){
        return tour.contains(city);
    }

    @Override
    public String toString() {
        String geneString = "";
        for (int i = 0; i < tourSize(); i++) {
            geneString += getCity(i)+" ";
        }
        return geneString;
    }
}
