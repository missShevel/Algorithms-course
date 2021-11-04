package lr3;

public class Ant {

    protected int trailSize;
    protected int trail[];
    protected boolean visited[];

    public Ant(int tourLength){
        this.trailSize = tourLength; //length of trail
        this.trail = new int[tourLength]; //list of cities to explore
        this.visited = new boolean[tourLength]; //list to mark which cities were explored
    }

    protected void visitCity(int currentIndex, int city){
        trail[currentIndex + 1] = city;
        visited[city] = true;
    }

    protected boolean isVisited(int i){
        return visited[i];
    }

    protected float trailLength(float graph[][]){
        float length = graph[trail[trailSize - 1]][trail[0]];
        for (int i = 0; i < trailSize - 1; i++){
            length += graph[trail[i]][trail[i+1]];
        }
        return length;
    }

    protected void forget() {
        for (int i = 0; i < trailSize; i++){
            visited[i] = false;
        }
    }
}
