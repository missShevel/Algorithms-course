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
            int deltaE = next.calculatePairsOfConflicts() - current.calculatePairsOfConflicts();
            if (deltaE < 0) {
                current = next;
            } else if (Math.exp(deltaE / T) > Math.random()) {
                current = next;
            }
            t++;
        }
    }

    /*
     * t - number of iteration
     * k - linear koef (try different values)
     */
    private static int schedule(int t, int k) {
        return 1000 - (k * t);
    }

}
