package lr3;

import java.util.Random;

public class TSP {

    float[][] distanceMatrix;
    int numberOfCities;

    public TSP(int numberOfCities) {
        this.numberOfCities = numberOfCities;
        this.distanceMatrix = new float[numberOfCities][numberOfCities];
        Random r = new Random();
        for (int i = 0; i < numberOfCities; i++) {
            for (int j = 0; j < numberOfCities; j++) {
                if (i == j) {
                    distanceMatrix[i][j] = 0;
                } else if (distanceMatrix[i][j] != 0){
                    continue;
                } else {
                    distanceMatrix[i][j] = distanceMatrix[j][i] = r.nextFloat() * (50 - 5) + 5;
                }
            }
        }
    }

    public void printDistanceMatrix(){
        for (int i = 0; i < this.numberOfCities; i++) {
            for (int j = 0; j < this.numberOfCities; j++) {
                System.out.print(this.distanceMatrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    public float[][] getDistanceMatrix() {
        return distanceMatrix;
    }

    public int getNumberOfCities() {
        return numberOfCities;
    }

    public static int greedySolution(){
        return 500;
    }
}
