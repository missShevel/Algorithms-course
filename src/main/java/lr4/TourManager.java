package lr4;

import java.util.ArrayList;

public class TourManager {

    // Holds our cities
    private static ArrayList destinationCities = new ArrayList<Integer>();

    // Get a city
    public static int getCity(int index){
        return (int)destinationCities.get(index);
    }

    // Get the number of destination cities
    public static int numberOfCities(){
        return destinationCities.size();
    }

    public static void addCity(int city){
        destinationCities.add(city);
    }
}
