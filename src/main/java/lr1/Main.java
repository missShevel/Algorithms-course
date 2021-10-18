package lr1;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        Map Ukraine = new Map();
        Backtraking problem = new Backtraking(Ukraine.getRegions(), Ukraine.getColors());
        ArrayList<Region> emptyRegions = new ArrayList<>();

        ArrayList<Region> coloredMap = problem.backtrack(problem, emptyRegions);
        Solution backtrackingResult = new Solution(coloredMap);
        System.out.println("Backtracking + MRV");
        System.out.println(backtrackingResult);
        System.out.println("Backtracking result number of conflicts");
        System.out.println(backtrackingResult.calculatePairsOfConflicts());
        System.out.println("Backtracking statistic");
        Backtraking.printStatistics();

        Solution randomState = new Solution();
        Solution annealingResult = SA.simaltedAnnealing(randomState);
        System.out.println("Simulated annealing result");
        System.out.println(annealingResult);
        System.out.println("Start state number of conflicts");
        System.out.println(randomState.calculatePairsOfConflicts());
        System.out.println("Anneal result number of conflicts");
        System.out.println(annealingResult.calculatePairsOfConflicts());
        System.out.println("Simulated annealing statistic");
        SA.printStatistics();
    }
}
