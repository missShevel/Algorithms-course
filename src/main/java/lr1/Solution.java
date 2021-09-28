package lr1;

import java.util.*;

public class Solution implements Cloneable{

    private HashMap<Integer, String> coloredMap;

    public Solution() {
        HashMap<Integer, String> tempMap = new HashMap<>();
        List<String> colors = Arrays.asList("Red", "Green", "Blue", "Yellow");
        for (int i = 1; i < 25; i++) {
            tempMap.put(i, colors.get(new Random().nextInt(colors.size())));
        }
        this.coloredMap = tempMap;
    }

    public Solution(HashMap<Integer, String> coloredMap) {
        this.coloredMap = coloredMap;
    }


    public int calculatePairsOfConflicts() {
        Map map = new Map();
        HashMap<Integer, String> numberedRegions = Map.numberAndRegion();
        List<String> checkedRegions = new LinkedList<>();
        int conflicts = 0;

        for (int i = 1; i < 25; i++) {
            String colorOfRegion = this.coloredMap.get(i);
            ArrayList<String> neighbours = Backtraking.findNeighbours(numberedRegions.get(i - 1));
            for (String neighbour :
                    neighbours) {
                if (!(checkedRegions.contains(neighbour))) {
                    if (colorOfRegion
                            .equals(this.coloredMap
                                    .get(map.listOfRegions
                                            .get(neighbour)))) {
                        conflicts++;
                    }
                }
                checkedRegions.add(coloredMap.get(i));
            }
        }
        return conflicts;
    }

    HashMap<Integer, String> getColoredMap(){
        return this.coloredMap;
    }


    public ArrayList<Solution> generateChildren(ArrayList<Integer> alreadyChangedRegions) throws CloneNotSupportedException {
        ArrayList<Solution> children = new ArrayList<>();
        List<String> colors = Arrays.asList("Red", "Green", "Blue", "Yellow");
do {
    int numberOfRegion = new Random().nextInt(24);
    if (!(alreadyChangedRegions.contains(numberOfRegion))) {
        for (String color : colors) {
            HashMap<Integer, String> newSolutionMap = new HashMap<>(getColoredMap());
            if (!(newSolutionMap.get(numberOfRegion).equals(color))) {
                Solution child = new Solution(newSolutionMap);
                child.coloredMap.put(numberOfRegion, color);
                children.add(child);
            }
        }
    }
    alreadyChangedRegions.add(numberOfRegion);
} while(children.isEmpty());
        return children;

    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        return "Solution{" +
                "coloredMap=" + coloredMap +
                '}';
    }
}