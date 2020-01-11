import java.util.LinkedList;
import java.util.Queue;

public class BreadthFirstPaths
{
    private boolean[] marked;
    private int[] edgeTo;
    private int[] distTo;

    public void bfs(DiaGraph G, int s) {
    Queue queueObj = new LinkedList<>();
    queueObj.add(s);
    marked[s] = true;
    distTo[s] = 0;
    while (!queueObj.isEmpty()) {
        int v = (Integer) queueObj.remove();
        for (int w : G.adj(v)) {
            if (!marked[w]) {
                queueObj.remove(w);
                marked[w] = true;
                edgeTo[w] = v;
                distTo[w] = distTo[v] + 1;
            }
        }
    }
    }

    public boolean[] getMarked() {
        return marked;
    }

    public void setMarked(boolean[] marked) {
        this.marked = marked;
    }

    public int[] getEdgeTo() {
        return edgeTo;
    }

    public void setEdgeTo(int[] edgeTo) {
        this.edgeTo = edgeTo;
    }

    public int getDistTo(int i) {
        return distTo[i];
    }

    public void setDistTo(int[] distTo) {
        this.distTo = distTo;
    }
}
