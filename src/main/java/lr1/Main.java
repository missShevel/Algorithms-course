package lr1;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        Map Ukraine = new Map();
        Backtraking problem = new Backtraking(Ukraine.getRegions(), Ukraine.getColors());

        ArrayList<Region> solution = new ArrayList<>();

        ArrayList<Region> coloredMap= problem.backtrack(problem, solution);

        System.out.println(coloredMap);

    }
}
