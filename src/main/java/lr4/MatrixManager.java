package lr4;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;
import java.io.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class MatrixManager {
    static String fileName = "Matrix.txt";
    private int numberOfCities;
    private float[][] distanceMatrix;


    public MatrixManager() throws IOException {
        this.numberOfCities = getNumberOfLines();
        this.distanceMatrix = new float[numberOfCities][numberOfCities];
        readMatrix();
    }

    public MatrixManager(int numberOfCities){
        this.numberOfCities = numberOfCities;
        this.distanceMatrix = generateRandomMatrix();
    }
    private int getNumberOfLines(){
        int lines = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            while (reader.readLine() != null) lines++;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    public float[][] generateRandomMatrix(){
       float[][] randomMatrix = new float[numberOfCities][numberOfCities];
        Random r = new Random();
        for (int i = 0; i < numberOfCities; i++) {
            for (int j = 0; j < numberOfCities; j++) {
                if (i == j) {
                    randomMatrix[i][j] = 0;
                } else if (randomMatrix[i][j] != 0){
                    continue;
                } else {
                    randomMatrix[i][j] = randomMatrix[j][i] = r.nextFloat() * (50 - 5) + 5;
                }
            }
        }
        return randomMatrix;
    }

    public void saveInFile(){
        File matrix = new File("./", "Matrix.txt");
        try (FileWriter fstream = new FileWriter(matrix);
             BufferedWriter info = new BufferedWriter(fstream)) {
            for (int i = 0; i < numberOfCities; i++) {
                StringBuilder record = new StringBuilder();
                for (int j = 0; j < numberOfCities; j++) {
                    record.append(this.distanceMatrix[i][j]);
                    if(j != numberOfCities - 1) {
                        record.append(" ");
                    }
                }
                record.append("\n");
                info.write(String.valueOf(record));
            }
        } catch (IOException e) {
            e.printStackTrace();
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

    public void setDistanceMatrix(float[][] distanceMatrix) {
        this.distanceMatrix = distanceMatrix;
    }

    public  float[][] getDistanceMatrix() {
        float [][] result = distanceMatrix;
        return result;
    }

    public int getNumberOfCities() {
        return numberOfCities;
    }

    public void readMatrix() throws IOException {
        AtomicInteger rowNumber = new AtomicInteger();
        rowNumber.set(0);
        try (Stream<String> stream = Files.lines(Path.of(fileName))) {
            stream.forEach(line -> {
                float value = Float.parseFloat(line.substring(0, line.indexOf(" ")));
                this.distanceMatrix[rowNumber.get()][0] = value;
                for (int i = 1; i < numberOfCities - 1; i++) {
                    String cutted = line.substring(line.indexOf(" ")+1);
                    value = Float.parseFloat(cutted.substring(0, cutted.indexOf(" ")));
                    this.distanceMatrix[rowNumber.get()][i] = value;
                    line = cutted;
                }
                rowNumber.getAndIncrement();
            }
            );
        }

    }
}

