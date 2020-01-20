public class Digraph {

    /**
     * number of vertices in this digraph.
     */
    private final int verticesCount; 

    /**
     * number of edges in this digraph.
     */
    private int edgesCount;  
                       
    /**
     * LinkedList Array Adjacency List.
     */
    private final LinkedList<Integer>[] adjacencyList;

    /**
     * Integer Array Indegree.
     */
    private final int[] indegree;

    /**
     * @param V the number of vertices
     * @throws IllegalArgumentException throws illegalArgumentException if v < 0
     */
    public Digraph(final int V) {
        if (V < 0)
            throw new IllegalArgumentException("Number of vertices in a Digraph must be nonnegative");
        this.verticesCount = V;
        this.edgesCount = 0;
        indegree = new int[V];
        adjacencyList = (LinkedList<Integer>[]) new LinkedList[V];
        for (int v = 0; v < V; v++) {
            adjacencyList[v] = new LinkedList<Integer>();
        }
    }

    /**
     * @return returns the number of vertices in this digraph
     */
    public int V() {
        return verticesCount;
    }

    /**
     * @return returns the number of edges in this digraph
     */
    public int E() {
        return edgesCount;
    }

    /**
     * throws an IllegalArgumentException if vertex v is not in between 0 and v-1
     */
    private void validateVertex(final int v) {
        if (v < 0 || v >= verticesCount)
            throw new IllegalArgumentException();
    }

    /**
     * @param v the tail vertex
     * @param w the head vertex
     * @throws IllegalArgumentException if any one of the vertex does not lies between 0 and v-1
     */
    public void addEdge(final int v, final int w) {
        validateVertex(v);
        validateVertex(w);
        adjacencyList[v].add(w);
        indegree[w]++;
        edgesCount++;
    }

    /**
     * @param v the vertex
     * @return the vertices adjacent from vertex v in this digraph, as an iterable
     * @throws IllegalArgumentException if vertex does not lies between 0 and v-1
     */
    public Iterable<Integer> adj(final int v) {
        validateVertex(v);
        return adjacencyList[v];
    }

    /**
     * @param v the vertex
     * @return the outdegree of vertex v
     * @throws IllegalArgumentException if vertex v does not lies between 0 and v-1
     */
    public int outdegree(final int v) {
        validateVertex(v);
        return adjacencyList[v].size();
    }

    /**
     * @param v the vertex
     * @return the indegree of vertex v
     * @throws IllegalArgumentException if vertex v does not lies between 0 and v-1
     */
    public int indegree(final int v) {
        validateVertex(v);
        return indegree[v];
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("vertices : " + verticesCount + " | edges : " + edgesCount + "\n");
        for (int v = 0; v < verticesCount; v++) {
            s.append(String.format("%d -> ", v));
            for (int w : adjacencyList[v]) {
                s.append(String.format("%d ", w));
            }
            s.append("\n");
        }
        return s.toString();
    }

    /**
     * @param args Command Line Arguments
     */
    public static void main(final String[] args) {
        final Digraph digraphObj = new Digraph(10);
        System.out.println(digraphObj.V());
        digraphObj.addEdge(2, 5);
        digraphObj.addEdge(3, 5);
        digraphObj.addEdge(3, 6);
        digraphObj.addEdge(3, 9);

        for (int v = 0; v < digraphObj.V(); v++) {
            for (Integer w : digraphObj.adj(v)) {
                System.out.println(v + " ----> " + w);
            }
        }
        System.out.println(digraphObj);
    }
}