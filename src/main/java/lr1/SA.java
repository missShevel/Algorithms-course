package lr1;

import java.util.ArrayList;
import java.util.Random;

public class SA {

    public static Solution simaltedAnnealing(Solution problem) throws CloneNotSupportedException {

        ArrayList<Solution> children = new ArrayList<>();
        ArrayList<Integer> alreadyChangedRegions = new ArrayList<>();

        Solution current = problem;
        int t = 1;
        while (true) {
            children = current.generateChildren(alreadyChangedRegions);
            int T = schedule(t, 20);
            if(T == 0){
                return current;
            }
            Solution next = children.get(new Random().nextInt(children.size()));
            int deltaE = next.calculatePairsOfConflicts() - current.calculatePairsOfConflicts();
            if(deltaE < 0) {
                current = next;
            } else if (Math.exp(deltaE/T) > Math.random()){
                current = next; ///c вероятностью???
            }
            t++;
        }

    }

//    public boolean acceptByProbability(double currentFitness, double newFitness)
//    {
//        if(newFitness < currentFitness)
//            return true;
//        else
//        {
//            double delta = currentFitness - newFitness;
//            double probability = Math.exp(delta/this.temperature);
//            return probability > Math.random();
//        }
//    }
//
//    private static boolean acceptByProbability (int cuurentValue, int nextValue){
//
//    }

    /*
     * t - number of iteration
     * k - linear koef (try different values)
    */
    private static int schedule(int t, int k){
        return 1000 - (k*t);
    }

}
