package lr4;

import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

public class Crossover {

    public  Tour runOrderedCrossover(Tour parent1, Tour parent2) throws IOException {
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

        public  Tour runSCXcrossover(Tour parent1, Tour parent2) throws IOException {
        Tour child = new Tour();
        child.setCity(0, parent1.getCity(0));

        for (int i = 1; i < parent1.tourSize(); i++) {
            int currentCity = child.getCity(i-1);
            int nextCityInParent1 = getNextCity(currentCity, parent1, child);
            int nextCityInParent2 = getNextCity(currentCity, parent2, child);

            float p1Length = parent1.getCost(currentCity, nextCityInParent1);
            float p2Length = parent2.getCost(currentCity, nextCityInParent2);

            int nextCity = p1Length < p2Length ? nextCityInParent1 : nextCityInParent2;
            child.setCity(i, nextCity);

        }
        ThreeOptAlgorithm.run(child);
        return child;
    }

    private  int getNextCity(int current, Tour parent, Tour child){
        int pos = parent.getPosition(current);
        int next;
        if (pos == parent.tourSize() - 1) {
            next = parent.getCity(0);
        } else {
            next = parent.getCity(pos + 1);
        }
        // check if next already used in offspring => take first free city
        if (child.containsCity(next)) {
            next = getFirstUnused(parent, child);
        }
        return next;
    }

    private int getFirstUnused(Tour parent, Tour child) {
        for (int city : parent.getCities()) {
            if (!child.containsCity(city)) {
                return city;
            }
        }
        return 0;
    }

    public Tour runPMcrossover(Tour parent1, Tour parent2) throws IOException {
        Tour child = new Tour();
MatchingSectionBounds matchingSectionBounds = generateRandomMatchingSectionBounds(parent1);

for(int i = matchingSectionBounds.lowerBound; i <= matchingSectionBounds.upperBound; i++){
    child.setCity(i,parent1.getCity(i));
}

for(int i = matchingSectionBounds.lowerBound; i <= matchingSectionBounds.upperBound; i++){
    int cityToCheck = parent2.getCity(i);
    boolean cityAlreadyInChild = false;

    for(int j = matchingSectionBounds.lowerBound; j <= matchingSectionBounds.upperBound
                                                       && !cityAlreadyInChild; j++){
        if (child.getCity(j) == cityToCheck){
            cityAlreadyInChild = true;
        }
    }

    if (!cityAlreadyInChild){
        int foundPosition = findPositionForValueInSecondParent(matchingSectionBounds, parent1, parent2,
                i);
        
        child.setCity(foundPosition, cityToCheck);
    }
}

copyRemainingCities(parent1, parent2, child, matchingSectionBounds);

return child;

    }

    private void copyRemainingCities(Tour parent1, Tour parent2, Tour child, MatchingSectionBounds matchingSectionBounds) {
            for (int i = 0; i < matchingSectionBounds.lowerBound; i++) {
                if (child.getCity(i) == -1) {
                    child.setCity(i, parent2.getCity(i));
                }
            }

            for (int i = matchingSectionBounds.upperBound; i < parent1.tourSize(); i++) {
                if (child.getCity(i) == -1) {
                    child.setCity(i, parent2.getCity(i));
                }
            }
    }

    private  int findPositionForValueInSecondParent(MatchingSectionBounds matchingSectionBounds, Tour parent1, Tour parent2, int currentPositionInSecondParent) {
        int correspondingValueInFirstParent = parent1.getCity(currentPositionInSecondParent);

        // find value in second parent
        boolean valueFound = false;
        int foundIndex = -1;
        for (int j = 0; j <= parent2.tourSize() - 1 && !valueFound; j++) {
            if (parent2.getCity(j) == correspondingValueInFirstParent) {
                valueFound = true;
                foundIndex = j;
            }
        }

        if (matchingSectionBounds.lowerBound <= foundIndex && matchingSectionBounds.upperBound >= foundIndex) {
            return findPositionForValueInSecondParent(matchingSectionBounds, parent1, parent2, foundIndex);
        } else {
            return foundIndex;
        }
    }

    private  MatchingSectionBounds generateRandomMatchingSectionBounds(Tour anyParent) {
        MatchingSectionBounds matchingSectionBounds = new MatchingSectionBounds();
        matchingSectionBounds.lowerBound = ThreadLocalRandom.current().nextInt(1, anyParent.tourSize() - 2);

        if (matchingSectionBounds.lowerBound == 1) {
            // prevent full section selection
            matchingSectionBounds.upperBound = ThreadLocalRandom.current().nextInt(matchingSectionBounds.lowerBound, anyParent.tourSize() - 3);

        } else {
            matchingSectionBounds.upperBound = ThreadLocalRandom.current().nextInt(matchingSectionBounds.lowerBound, anyParent.tourSize() - 2);
        }

        return matchingSectionBounds;
    }

     class MatchingSectionBounds {
        int upperBound;
        int lowerBound;
    }


}

