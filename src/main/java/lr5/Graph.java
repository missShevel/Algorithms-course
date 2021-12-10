package lr5;
import java.util.*;

public class Graph {
    
    //Class arrays for Graph
    private static int [][] B;               // 0 = no edge; 1 = red edge; -1 = blue edge
    private static boolean [] visited;
    
    public Graph(int N){                            // a constructor for a instance of the class with N vertices 
        B = new int[N][N];
        visited = new boolean[B.length];
    }
    
    public void addEdge(int u, int v, int w){       // add an edge from vertex u to v with value w (which in this hw will be  only 0, -1, or 1)
        this.B[u][v] = w;
        this.B[v][u] = w;
    }
    
    public void removeEdge(int u, int v){         // remove the edge from u to v and the (duplicate) edge from v to u
        this.B[u][v] = 0;
        this.B[v][u] = 0;
    }
    
    public int getEdge(int u, int v){               // return the value (-1, 0, or 1) of the edge that goes from u to v
        return this.B[u][v];
    }
    
    public boolean isEdge(int u, int v){            // return true or false depending on whether there is an edge (of either color) from u to v
        if(this.B[u][v] != 0){
            return true;
        } else {
            return false;
        }
    }
    
    public int degree(int v){                       // return the number of edges of either color connected to vertex v
        int counter = 0;
        for(int i = 0; i < this.B[v].length; ++i){
            if(this.B[v][i] != 0){
                counter++;
            }
        }
        return counter;
    }
    
    public int degree(int v, int w){                // return the number of edges of color w connected to vertex v
        int counter = 0;
        for(int i = 0; i < this.B[v].length; ++i){
            if(this.B[v][i] == w){
                counter++;
            }
        }
        return counter;
    }
    
    public int sizeOfGraph(){
        return B.length;
    }
    
    public boolean isFull(){
        for(int i = 0; i < B.length; i++){
            if(degree(i) < B.length - 1)
                return false;
        }
        return true;                
    }
    
    public void printEdges(){                       // print out the edge matrix, as shown above; this is only for debugging
        System.out.print("     ");
        for(int i = 1; i < this.B.length +1; ++i){
            System.out.print(i+"   ");
        }
        System.out.println();
        System.out.print("     ");
        for(int i = 0; i < this.B.length; ++i){
            System.out.print("-   ");
        }
        System.out.println();
        for(int i = 0; i < this.B.length; ++i){
            System.out.print((i+1)+" |  ");
            for(int n = 0; n < this.B.length; ++n){
                if(n<5 && this.B[i][n+1] == -1){
                    System.out.print(this.B[i][n]+"  ");
                }else{
                System.out.print(this.B[i][n]+"   ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }
    
    public boolean isCycleOfLength(int n, int w){  // return true if there is a cycle of length n among edges of color w
        for(int i = 0; i < B.length; i++){
            visited[i] = true;
            for(int j = 0; j < B.length; j++){
                visited[j] = true;
                if(getEdge(i, j) == w){
                    if(isCycleHelper(j, i, n, w))
                        return true;
                }
                visited[j] = false;
            }
            visited[i] = false;
        }
        return false;
    }
    
    private boolean isCycleHelper(int u, int v, int n, int w){
        if(n == 2){
            if(getEdge(u, v) == w)
                return true;
            else
                return false;
        }else{
            for(int i = 0; i < B.length; i++){
                if(getEdge(u, i) == w && !visited[i]){
                    visited[i] = true;
                    boolean temp = isCycleHelper(i, v, --n, w);
                    visited[i] = false;
                    return temp;
                }
            }
        }
        return false;
    }
}