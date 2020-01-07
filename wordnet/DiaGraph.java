import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DiaGraph {
private final int V;
private final List<List<Integer>> adj;

public DiaGraph(int V) {
    this.V = V;
    adj = new ArrayList<>(V);
    for (int v = 0; v < V; v++)
    adj.add(new ArrayList<Integer>());
}

public void addEdge(int v, int w) {
    adj.get(v).add(w);
}

public Iterable<Integer> adj(int v) { 
    return adj.get(v); 
}
public int V() {
    return V;
} 
public static void main(String[] args) throws IOException {
    DiaGraph diaGraphObj;
    WordNet2 wordNet2Obj  = new WordNet2();
    int hySize = wordNet2Obj.getHypernymsHashMapObj().size();
    diaGraphObj = new DiaGraph(hySize); 
    for (int i = 0; i < hySize; i++) {
        List<String> listObj = wordNet2Obj.getHypernymsHashMapObj().get(wordNet2Obj.getHypMapObj().get(i));
        if (listObj != null) {
        for (int j = 0; j < listObj.size(); j++) {
            diaGraphObj.addEdge(wordNet2Obj.getHypMapObj().get(i), Integer.parseInt(listObj.get(j)));
        }
    }
}
    for (int v = 0; v < diaGraphObj.V(); v++) {
        System.out.print(v);
        for (int w : diaGraphObj.adj(v)) {
            System.out.print(" ------> " + w);
        }
        System.out.println();
    }
}
}