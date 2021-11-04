package lr3;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

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

    public static float greedySolution(float[][] graph) {
        float tourLength = 0;
        int counter = 0;
        int i = 0, j = 0;
        float minDistance = Float.MAX_VALUE;
        Set<Integer> visitedCities = new HashSet<>();

        visitedCities.add(new Random().nextInt(100));
        int[] route = new int[graph.length];

        while (i < graph.length && j < graph[i].length) {
            if (counter >= graph[i].length - 1) {
                break;
            }

            if (j != i && !(visitedCities.contains(j))) {
                if (graph[i][j] < minDistance) {
                    minDistance = graph[i][j];
                    route[counter] = j + 1;
                }
            }
            j++;


            if (j == graph[i].length) {
                tourLength += minDistance;
                minDistance = Float.MAX_VALUE;
                visitedCities.add(route[counter] - 1);
                j = 0;
                i = route[counter] - 1;
                counter++;
            }
        }

        i = route[counter - 1] - 1;

        for (j = 0; j < graph.length; j++) {

            if ((i != j) && graph[i][j] < minDistance) {
                minDistance = graph[i][j];
                route[counter] = j + 1;
            }
        }
        tourLength += minDistance;
        return tourLength;
    }

}
