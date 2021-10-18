package lr2;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;

public class FileManager {

    String fileName;
    int numberOfElements;

    public void generateFile(int size) {
        File database = new File("./", "Data.csv");
        Random rng = new Random();
        Set<Integer> generatedSet = new LinkedHashSet<>();
        ArrayList<Integer> keys = new ArrayList<>();
        while (generatedSet.size() < size) {
            Integer next = rng.nextInt(10000) + 1;
            generatedSet.add(next);
        }
        keys.addAll(generatedSet);
        try (FileWriter fstream = new FileWriter(database);
             BufferedWriter info = new BufferedWriter(fstream)) {
            for (int i = 0; i < size; i++) {
                StringBuilder record = new StringBuilder();
                record.append(keys.get(i));
                record.append(",");
                record.append(getRandomWord(50));
                record.append("\n");
                info.write(String.valueOf(record));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getRandomWord(int length) {
        String r = "";
        for (int i = 0; i < length; i++) {
            r += (char) (Math.random() * 26 + 97);
        }
        return r;
    }



}
