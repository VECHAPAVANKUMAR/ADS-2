
public class SAP {
    
    DiaGraph G;
    BreadthFirstPaths breadthFirstPathsObj1;
    BreadthFirstPaths breadthFirstPathsObj2;
    int ancesstor1 = -1;
    int distance;
    // int ancesstor2 = -1;

    public SAP(DiaGraph diaGraph) {
        this.G = diaGraph;
        this.breadthFirstPathsObj1 = new BreadthFirstPaths();
    }
    public int length(int v, int w) {
        if (v < 0 || w < 0) {
            throw new IllegalArgumentException();
        }
        if (v == w) {
            return 0;
        }
        breadthFirstPathsObj1.bfs(G, v);
        return breadthFirstPathsObj1.getDistTo(w);
    }
    public int ancesstor(int v, int w) {
        breadthFirstPathsObj1.bfs(G, v);
        breadthFirstPathsObj2.bfs(G, w);
        distance = Integer.MAX_VALUE;
        for (Integer ele : G.adj(v)) {
            if()
        }
        return ancesstor1;
    }
}