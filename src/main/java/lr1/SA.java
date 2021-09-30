package lr1;

import java.util.ArrayList;
import java.util.Random;

public class SA {

    public static Solution simaltedAnnealing(Solution problem) {
        ArrayList<Solution> children;
        Solution current = problem;
        int t = 1;
        while (true) {
            children = current.generateChildren();
            int T = schedule(t, 7);
            if (T <= 0) {
                return current;
            }
            Solution next = children.get(new Random().nextInt(children.size()));
                if (acceptByProbability(current, next, T)) {
                    current = next;
                }
            t++;
        }
    }

    private static boolean acceptByProbability(Solution current, Solution next, int T) {
        if (next.calculatePairsOfConflicts() < current.calculatePairsOfConflicts()) {
            return true;
        } else {
            double deltaE = next.calculatePairsOfConflicts() - current.calculatePairsOfConflicts();
            double probability = Math.exp(deltaE/-T);
            return probability > Math.random();
        }
    }
    /*
     * t - number of iteration
     * k - linear koef (try different values)
     */

    private static int schedule(int t, int k) {
        return 1000 - (k*t);
    }

}
