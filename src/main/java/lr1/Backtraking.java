package lr1;

import java.util.*;

public class Backtraking {

    public static int numberOfStates = 0;
    public static int numberOfStatesChecked = 0;
    public static int numberOfStatesInMemory = 0;

    private List<String> regions;
    private List<String> colors;

    public Backtraking(List<String> regions, List<String> colors) {
        this.regions = regions;
        this.colors = colors;


    }

    public ArrayList<Region> backtrack(Backtraking problem, ArrayList<Region> coloredRegions) {

        if (problem.regions.size() == coloredRegions.size()) {
            return coloredRegions;
        }

        String nextRegionName = selectNextRegion(problem, coloredRegions);
        for (String color : problem.colors) {
            Region newColoredRegion = new Region(nextRegionName, color);
            coloredRegions.add(newColoredRegion);
            numberOfStates++;

            if (checkAssignment(newColoredRegion, coloredRegions)) {
                numberOfStatesInMemory++;///check in matrixx
                ArrayList<Region> result = backtrack(problem, coloredRegions);
                if (result != null) {
                    return result;
                }

            }
            coloredRegions.remove(newColoredRegion);
            numberOfStatesChecked++;
        }
        return null;

    }

    private String selectNextRegion(Backtraking problem, ArrayList<Region> coloredRegions) { ///mrv

        if (coloredRegions.isEmpty()) {
            return problem.regions.get(new Random().nextInt(problem.regions.size()));
        }

        ArrayList<String> regionsToCheck = new ArrayList<>();
        regionsToCheck.addAll(problem.regions);
        for (String region : problem.regions) {
            for (Region r : coloredRegions) {
                if (r.name.equals(region)) {
                    regionsToCheck.remove(region);
                }
            }
        }

        int minNumOfColors = Integer.MAX_VALUE;
        String result = "";

        for (String s : regionsToCheck) {

            if (calculateMRV(s, problem.colors, coloredRegions) < minNumOfColors) {
                minNumOfColors = calculateMRV(s, problem.colors, coloredRegions);
            }

            if (calculateMRV(s, problem.colors, coloredRegions) == minNumOfColors) {
                result = s;
            }
        }
        return result;
    }

    private boolean checkAssignment(Region newColoredRegion, ArrayList<Region> coloredRegions) {

        //check for conflicts
        ArrayList<String> neighbours = findNeighbours(newColoredRegion.name);
        for (Region r : coloredRegions) {
            for (int i = 0; i < neighbours.size(); i++) {
                if ((neighbours.get(i).equals(r.name))) {
                    if (newColoredRegion.color.equals(r.color)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public static ArrayList<String> findNeighbours(String regionName) {
        Map map = new Map();
        int[][] relations = map.getRegionsRelations();

        ArrayList<String> neighbours = new ArrayList<>();

        HashMap<String, Integer> regionsList = map.getListOfRegions();
        HashMap<Integer, String> numberAndRegion = Map.numberAndRegion();
        int index = regionsList.get(regionName);


        for (int i = 0; i < relations.length; i++) {
            if (relations[index][i] == 1) {
                neighbours.add(numberAndRegion.get(i));
            }
        }
        return neighbours;
    }

    public int calculateMRV(String regionName, List<String> colors, ArrayList<Region> coloredRegions) { //calculate maximum possible number of colors

        ArrayList<String> neighbours = findNeighbours(regionName);

        List<String> remainedColors = new ArrayList<>();
        remainedColors.addAll(colors);
        int counter = colors.size();
        while (counter > 0) {
            for (int i = 0; i < neighbours.size(); i++) {
                for (Region r : coloredRegions) {
                    if (r.name.equals(neighbours.get(i))) {
                        remainedColors.remove(r.color);
                    }

                }
            }
            counter--;
        }
        return remainedColors.size();

    }

    public static void printStatistics(){
        System.out.println("Number of all states : " + numberOfStates);
        System.out.println("Number of states in memory: " + numberOfStatesInMemory);
        System.out.println("Number of checked states: " + numberOfStatesChecked);
    }

}

class Region {
    String name;
    String color;


    public Region(String name, String color) {
        this.name = name;
        this.color = color;
    }

    @Override
    public String toString() {
        return "Region{" +
                "name='" + name + '\'' +
                ", color='" + color +
                "}\n";
    }
}

