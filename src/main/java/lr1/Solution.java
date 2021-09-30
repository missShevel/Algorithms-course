package lr1;

import java.util.*;

public class Solution extends HashMap {

    private HashMap<Integer, String> coloredMap;
    public static Set<Integer> changedRegions = new HashSet<>();

    public Solution() {
        HashMap<Integer, String> tempMap = new HashMap<>();
        List<String> colors = Arrays.asList("Red", "Green", "Blue", "Yellow");
        for (int i = 1; i <= Map.listOfRegions.size(); i++) {
            tempMap.put(i, colors.get(new Random().nextInt(colors.size())));
        }
        this.coloredMap = tempMap;
    }

    public Solution(HashMap<Integer, String> coloredMap) {
        this.coloredMap = coloredMap;
    }


    public int calculatePairsOfConflicts() {
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
                                    .get(Map.listOfRegions
                                            .get(neighbour) + 1))) {
                        conflicts++;
                    }
                }
                checkedRegions.add(Map.numberAndRegion().get(i-1));
            }
        }
        return conflicts ;
    }

    HashMap<Integer, String> getColoredMap() {
        return this.coloredMap;
    }


    public ArrayList<Solution> generateChildren() {
        if(changedRegions.size() == 24){
            changedRegions.clear();
        }
        ArrayList<Solution> children = new ArrayList<>();
        List<String> colors = Arrays.asList("Red", "Green", "Blue", "Yellow");
        int numberOfRegion = 0;

        do {
            if(changedRegions.isEmpty()) {
                numberOfRegion = (int) Math.floor(Math.random() * (24 - 1 + 1) + 1);
            } else {
                do {
                    numberOfRegion = (int) Math.floor(Math.random() * (24 - 1 + 1) + 1);
                } while (changedRegions.contains(numberOfRegion));
            }
            for (String color : colors) {
                HashMap<Integer, String> newSolutionMap = new HashMap<>(getColoredMap());
                if (!(newSolutionMap.get(numberOfRegion).equals(color))) {
                    Solution child = new Solution(newSolutionMap);
                    child.coloredMap.put(numberOfRegion, color);
                    children.add(child);
                }
            }
        } while (children.isEmpty());
        changedRegions.add(numberOfRegion);
        return children;

    }


    @Override
    public String toString() {
        HashMap<Integer, String> numberAndRegion = Map.numberAndRegion();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.coloredMap.size(); i++) {
            sb.append("Region{");
            sb.append("name= '").append(numberAndRegion.get(i)).append("', color= '").append(this.coloredMap.get(i + 1));
            sb.append("'}\n");
        }
        return sb.toString();
    }

}