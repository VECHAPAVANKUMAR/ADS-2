static class Edge implements Comparable<Edge> {
    int v;
    int w;
    int weight;
    public Edge(int v, int w, int wgh) {
        this.v = v;
        this.w = w;
        this.weight = wgh;
    }
    
public int either()
    { return v; }
    
public int other(int vertex)
{
    if (vertex == v) return w;
    else return v;
}
    public int compareTo(Edge that) {
        
        if (this.weight < that.weight) {
            return -1;
        }
        if (this.weight > that.weight) {
            return 1;
        }
        return 0;
    }
}

//     private class EdgeWeightedGraph {
    
//         int v;
//         LinkedList[] adj;    // adj[v] = adjacency list for vertex v

//         EdgeWeightedGraph(int V) {
//             this.v = V;
//         adj = (LinkedList[]) new LinkedList[V];
//         for (int v = 0; v < V; v++)
//             adj[v] = new LinkedList();

//         }
//         public void addEdge(int v, int w) {
//             adj.add(v) = w;
//         }
//     }


// private class LazyPrimMST
//     {
//     private boolean[] marked; // MST vertices
//     private Queue<Edge> mst; // MST edges
//     private PriorityQueue<Edge> pq; // PQ of edges

//     public LazyPrimMST(EdgeWeightedGraph G)
//     {
//     pq = new MinPQ<Edge>();
//     mst = new Queue<Edge>();
//     marked = new boolean[G.V()];
//     visit(G, 0);

//     while (!pq.isEmpty() && mst.size() < G.V() - 1)
//     {
//     Edge e = pq.delMin();
//     int v = e.either(), w = e.other(v);
//     if (marked[v] && marked[w]) continue;
//     mst.enqueue(e);
//     if (!marked[v]) visit(G, v);
//     if (!marked[w]) visit(G, w);
//     }
//     }
//     }

//     private void visit(EdgeWeightedGraph G, int v)
//     {
//     marked[v] = true;
//     for (Edge e : G.adj(v))
//     if (!marked[e.other(v)])
//     pq.insert(e);
// }

public static void main(String[] args) {
    /* Enter your code here. Read input from STDIN. Print output to STDOUT. Your class should be named Solution. */
    
    Scanner scannerObj = new Scanner(System.in);
    int vertices = scannerObj.nextInt();
    // LazyPrimMST primObj;
    while (vertices != 0) {
        
        LinkedList<Edge> adj = (LinkedList<Edge>[]) new LinkedList[vertices + 1];
        
        for(int i = 1; i <= vertices; i++) {    
            int connections = scannerObj.nextInt();
            if (connections != 0) 
                    adj[i] = new LinkedList<Edge>();
                for(int j = connections; j > 0; j--) {
                    int w = scannerObj.nextInt();
                    adj[i].add(new Edge(i, w, scannerObj.nextInt()));
                }
            
        }
        int from = scannerObj.nextInt();
        int to = scannerObj.nextInt();
        // primObj = new LazyPrimMST(ewg);
        vertices = scannerObj.nextInt();

        
}
}
