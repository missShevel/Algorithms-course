package lr5;

public class Player {
    //D - This is our class variable to hold the depth that the method will search to
    private int D = 5;
    Move bestFound = new Move(0, 0);

    //chooseMove - This method will utilize eval and minMax to decide what move
    // would be the best decision by the computer. It assesses different values for
    // every positibility of that the game could go to, and keep track of the best decision.
    public Move chooseMove(Graph G) {
        minMax(G, 0, 1, -1000000000, 1000000000);
        return bestFound;

    }

    //eval - This method will assign values to each possibility within the tree and
    // dictate which move would be the best for the computer to make.
    private int eval(Graph G) {
        int humanPossibleMoves = 0;

        //check if human lost
        if (G.isCycleOfLength(3, 1)) {
            return 1000000000;

            //check if computer lost
        } else if (G.isCycleOfLength(3, -1)) {
            return -1000000000;
        }
        //count possible moves of human, the less - the better
        for (int i = 0; i < G.sizeOfGraph(); i++) {
            for (int j = 0; j < G.sizeOfGraph(); j++) {
                if (!G.isEdge(i, j)) {
                    boolean canHumanMove = true;
                    for (int k = 0; k < G.sizeOfGraph(); k++) {
                        if (k != i && k != j) {
                            if (((i < k) ? G.getEdge(i, k) : G.getEdge(k, i)) == ((j < k) ? G.getEdge(j, k) : G.getEdge(k, j))) {
                                if (((i < k) ? G.getEdge(i, k) : G.getEdge(k, i)) == 1) {
                                    canHumanMove = false;
                                }

                            }

                        }
                        if(canHumanMove){
                          humanPossibleMoves++;
                        }

                    }

                }

            }

        }

        return -humanPossibleMoves;
    }

    //minMax - This method will take in a couple of different parameters and construct
    // a tree with the different possibilities. This method will also preform alpha beta
    // pruning to make the tree traversing more efficient.
    int minMax(Graph G, int depth, int isAITurn, int alpha, int beta) {
      if (G.isCycleOfLength(3, 1)) {
        return 1000000000;

        //check if computer lost
      } else if (G.isCycleOfLength(3, -1)) {
        return -1000000000;
      }

        if (depth >= D || G.isFull())
            return eval(G);
        // stop searching and return eval

        int val;
        Move bestMove = new Move(0, 0);
        int MiniMax = (isAITurn == 1) ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        for (int i = 0; i < G.sizeOfGraph(); i++) {

            for (int j = i + 1; j < G.sizeOfGraph(); j++) {

                if (!G.isEdge(i, j)) {

                    G.addEdge(i, j, (isAITurn == 1) ? -1 : 1);
                    val = minMax(G, depth + 1, (isAITurn == 1) ? 0 : 1, alpha, beta);
                    G.removeEdge(i, j);


                    if ((val > MiniMax && isAITurn == 1) || (val <= MiniMax && isAITurn == 0)) {

                        MiniMax = val;
                        System.out.println("New best value is " + MiniMax);
                        bestMove = new Move(i, j);
                        System.out.println("New best move is " + bestMove);
                    }

                    if (isAITurn == 1) {
                        alpha = Math.max(alpha, val);
                    } else {
                        beta = Math.min(beta, val);
                    }
                    if (alpha > beta) break;

                }
            }
            if (depth == 0 && !new Move(0, 0).equals(bestMove)) {
                this.bestFound = bestMove;
            }
        }
        return MiniMax;
    }
}


