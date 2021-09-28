package lr1;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws CloneNotSupportedException {
//        Map Ukraine = new Map();
//        Backtraking problem = new Backtraking(Ukraine.getRegions(), Ukraine.getColors());
//
//        ArrayList<Region> solution = new ArrayList<>();
//
//        ArrayList<Region> coloredMap= problem.backtrack(problem, solution);
//
//        System.out.println(coloredMap);


        Solution s = new Solution();
//
//        System.out.println("???????" + s.calculatePairsOfConflicts() + "?????????");
//        System.out.println(s);
//
//        System.out.println("///////////////");
//
//        ArrayList<Integer> checked = new ArrayList<>();
//
//        ArrayList<Solution> test = s.generateChildren(checked);
//
//        for (Solution t: test
//             ) {
//            System.out.println(t.calculatePairsOfConflicts());
//            System.out.println(t);
//
//
//        }
        System.out.println(s.calculatePairsOfConflicts());
        Solution result = SA.simaltedAnnealing(s);


        System.out.println(result);
        System.out.println(result.calculatePairsOfConflicts());

    }
}
