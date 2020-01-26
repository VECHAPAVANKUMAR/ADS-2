import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import programs.Digraph;

public class Solution {
    private final HashMap<Integer, String> emailHashMapObj;
    private final TreeMap<Integer, List<Integer>> mapObj;
    private Digraph g;
    
    public Solution(String emails, String emailLog) throws IOException {
        emailHashMapObj = new HashMap<>();
        mapObj = new TreeMap<>(Collections.reverseOrder());
        int c = buildDataStructure(emails);
        connections(emailLog, c);
    }

    private int buildDataStructure(String fileName) {
        final Path path = Paths.get(fileName);
        List<String> allLines = null;
        try {
            allLines = Files.readAllLines(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < allLines.size(); i++) {
            final String[] arr = allLines.get(i).split(";");
            emailHashMapObj.put(Integer.parseInt(arr[0]), arr[1]);
        }
        return allLines.size();
    }

    private void connections(String fileName, int c) throws IOException {
        final Path path = Paths.get(fileName);
        List<String> allLines = Files.readAllLines(path);
        String[] lis;
        g = new Digraph(c);
        for (int i = 0; i < allLines.size(); i++) {
            if (allLines.get(i).contains(" ")) {
                lis = allLines.get(i).replace(",", "").split(" ");
                g.addEdge(Integer.parseInt(lis[1]), Integer.parseInt(lis[3]));
            }
        }

        
        for (int v = 0; v < g.V(); v++) {
            if (mapObj.get(g.indegree(v)) == null) {
                mapObj.put(g.indegree(v), new ArrayList<>());
            }
            mapObj.get(g.indegree(v)).add(v);
        }
     }
    public static void main(String[] args) throws IOException {
        Solution solutionObj = new Solution("emails.txt", "email-logs.txt");
        System.out.println(solutionObj.emailHashMapObj.size());

        Set set = solutionObj.mapObj.entrySet(); 
        Iterator i = set.iterator(); 
        int count = 0;
        while (i.hasNext() && count < 10) { 
            Map.Entry me = (Map.Entry)i.next(); 
            ArrayList a = (ArrayList) me.getValue();
            for (Object e : a) {
                System.out.println(solutionObj.emailHashMapObj.get((int)e) + " " + me.getKey()); 

            }
            count++;
        } 

        // System.out.println(solutionObj.mapObj.get(solutionObj.mapObj.size()).size());
        
    }

}
// sentEmailsMapObj.put(Integer.parseInt(lis[1]), Integer.parseInt(lis[3]));
