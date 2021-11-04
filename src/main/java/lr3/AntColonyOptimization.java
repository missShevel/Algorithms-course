package lr3;

import java.util.*;
import java.util.stream.IntStream;

import static java.lang.Math.abs;

public class AntColonyOptimization {

    private double alpha = 2;
    private double beta = 4;
    private double evaporation = 0.4; // ro
    private int numberOfAnts = 30;
    private double randomFactor = 0.01;

    private int maxIterations = 1000;

    private int numberOfCities;
    private double Lmin;
    private float [][] graph;
    private float [][] sightMatrix;
    private float [][] trails;
    private List<Ant> ants = new ArrayList<>();
    private Random  random = new Random();
    private double probabilities[];

    private int currentIndex;

    private int [] bestTourOrder;
    private float bestTourLength;

    public AntColonyOptimization(float [][] generatedMatrix){
        this.graph = generatedMatrix;
        this.trails = new float[generatedMatrix.length][generatedMatrix.length];
        this.sightMatrix = new float[generatedMatrix.length][generatedMatrix.length];
        this.probabilities = new double[generatedMatrix.length];
        this.numberOfCities = generatedMatrix.length;
        this.Lmin = TSP.greedySolution();
        IntStream.range(0, numberOfAnts)
                .forEach(i -> ants.add(new Ant(numberOfCities)));
    }

    public int[] solve() {
        putAntsOnPositions();
        setStartTrails();
        setSightMatrix();
        IntStream.range(0, maxIterations)
                .forEach(i -> {
                    moveAnts();
                    updateTrails();
                    updateBestSolution();
                });
        System.out.println("Length of the best path founded: " + (bestTourLength - numberOfCities));
        System.out.println("Order of best solution: " + Arrays.toString(bestTourOrder));
        return bestTourOrder.clone();
    }

    private void setStartTrails() {
        IntStream.range(0, numberOfCities)
                .forEach(i -> {
                    IntStream.range(0,numberOfCities)
                            .forEach(j -> trails[i][j] = (abs(new Random().nextInt()))%3 + 1);
                });
    }

    private void setSightMatrix() {
        IntStream.range(0, numberOfCities)
                .forEach(i -> {
                    IntStream.range(0,numberOfCities)
                            .forEach(j -> this.sightMatrix[i][j] = 1.0f / graph[i][j]);
                });
    }

    private void putAntsOnPositions() {
        IntStream.range(0, numberOfAnts)
                .forEach(i -> {
                    ants.forEach(ant -> {
                        ant.forget();
                        ant.visitCity(-1, random.nextInt(numberOfCities));
                    });
                });
        this.currentIndex = 0;
    }

    private void moveAnts() {
    IntStream.range(currentIndex, numberOfCities - 1)
        .forEach(i -> {
            ants.forEach(ant -> ant.visitCity(currentIndex, selectNextCity(ant)));
            currentIndex++;
        });
    }

    private int selectNextCity(Ant ant) {
        int t = random.nextInt(numberOfCities - currentIndex);
        if(random.nextDouble() < randomFactor) {
            OptionalInt cityIndex = IntStream.range(0, numberOfCities)
                    .filter(i -> i == t && !ant.isVisited(i))
                    .findFirst();
            if(cityIndex.isPresent()) {
                return cityIndex.getAsInt();
            }
        }
        calculateProbabilities(ant);
        double r = random.nextDouble();
        double total = 0;
        for (int i = 0; i < numberOfCities; i++){
            total += probabilities[i];
            if (total >= r){
                return i;
            }
        }
        throw new RuntimeException("No cities left");
    }

    private void calculateProbabilities(Ant ant) {
        int i = ant.trail[currentIndex];
        double pheromone = 0.0;
        for (int l = 0; l < numberOfCities; l++) {
            if (!ant.isVisited(l)) {
                pheromone += Math.pow(trails[i][l], alpha) * Math.pow(sightMatrix[i][l], beta);
            }
        }
        for (int j = 0; j < numberOfCities; j++) {
            if (ant.isVisited(j)) {
                probabilities[j] = 0.0;
            } else {
                double numerator = Math.pow(trails[i][j], alpha) * Math.pow(sightMatrix[i][j], beta);
                probabilities[j] = numerator / pheromone;
            }
        }

    }

    private void updateTrails() {
        for (int i = 0; i < numberOfCities; i++) {
            for (int j = 0; j < numberOfCities; j++) {
                trails[i][j] *= (1.0 - evaporation);
            }
        }
        for(Ant a : ants){
            float contribution = (float) (Lmin / a.trailLength(graph));
            for (int i = 0; i < numberOfCities - 1; i++) {
                trails[a.trail[i]][a.trail[i+1]] += contribution;
            }
            trails[a.trail[numberOfCities - 1]][a.trail[0]] += contribution;
        }
    }

    private void updateBestSolution() {
        if (bestTourOrder == null) {
            bestTourOrder = ants.get(0).trail;
            bestTourLength = ants.get(0).trailLength(graph);
        }
        for(Ant a : ants) {
            if (a.trailLength(graph) < bestTourLength) {
                bestTourLength = a.trailLength(graph);
                bestTourOrder = a.trail.clone();
            }
        }
    }






}
