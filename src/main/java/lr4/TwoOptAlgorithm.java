package lr4;

public class TwoOptAlgorithm {
    public static Tour run(Tour tour) {
        float dist = tour.getDistance();
        for (int i = 0; i < tour.tourSize() - 2; i++) {
            for (int j = i+1; j < tour.tourSize()-1; j++) {
                tour.swapIndexes(i, j);
                float newDist = tour.getDistance();

                if(newDist < dist){
                    return run(tour);
                } else {
                    tour.swapIndexes(i, j);
                }
            }
        }
        return tour;
    }
}
