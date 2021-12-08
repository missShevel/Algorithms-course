package lr4;

import java.util.ArrayList;

public class TSP {
    public static ArrayList<Integer> solve(float [][] graph) {
        //Enter the number of cities
        int n = graph.length;
        final int MAX = 1000000000;

        ArrayList<Integer> tour = new ArrayList<>();
        //For storage distance
        float[] sq = new float[n];
        //Used to indicate the distance that sq has been stored
        int top = 0;
        //Used to indicate whether the city has passed through (1 means it has gone, 0 means not)
        int[] flag = new int[n];
        //Assign value to sq
        for (int i = 0; i < n; i++) {
            sq[i] = MAX;
        }

        //Enter the starting point of the city (the city is calculated from 1, but the array is constructed from 0, so START-1 is also required)
        int start = 0;
        tour.add(start);
        //Record the starting city to avoid being unable to find the starting point at the end
        int firstCity = start;
        //Record a city
        int nextCity = -1;
        //Record whether the city has been visited. If it has been visited, it is 1; if it is visited, it is 0
        flag[start] = 1;
        //Among the five cities, there are only four ways to go without considering returning to the starting point
        //When sq has saved the distance of the Fourth Road (i.e. when sq subscripts n-2), exit the loop and have traveled all cities
        while (top + 1< n - 1) {//Top < n-1 can also be written here
            //Traverse the distance between the city and other cities
            for (int j = 0; j < n; j++) {
                //Find the nearest city with the smallest distance from the city, and the city has not been there (if the input is 0, the two roads are not connected)
                if (sq[top] > graph[start][j] && flag[j] == 0 && graph[start][j]!=0) {
                    sq[top] = graph[start][j];
                    //Used to record adjacent cities
                    nextCity = j;
                }
            }
            //When you jump out of the loop, it means that the adjacent city has been found
            //Mark the adjacent city as already arrived
            flag[nextCity] = 1;

            tour.add(nextCity);
            //Export the adjacent city
            //Take the neighboring city as the starting city and cycle again until all the cities have been traversed
            start = nextCity;
            //top + +, which means that the next space of sq is used to store the next segment
            top++;
        }

        float sum = 0;
        //The fifth space of sq is used to store the distance between the last location and the starting point
        //Get the sum of the distances that have been passed
        for(int i = 0;i < n - 2; i++){
            sum += graph[tour.get(i)][tour.get(i+1)];
        }
        sum += graph[tour.get(n-2)][tour.get(0)];
        System.out.println(sum);
        return tour;
    }

}



