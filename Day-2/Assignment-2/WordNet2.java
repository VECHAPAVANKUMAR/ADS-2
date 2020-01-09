import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class WordNet2 {
    private HashMap<String, List<Integer>> synsetHashMapObj;
    List<Integer> hypMapObj;
    private HashMap<Integer, List<String>> hypernymsHashMapObj;
    List<String> listObj;
    public WordNet2() {
        synsetHashMapObj = new HashMap<>();
        hypernymsHashMapObj = new HashMap<>();
        hypMapObj = new ArrayList<>();
        listObj = new ArrayList<>();
    }
    private void parseSynsets(String fileName) throws IOException, FileNotFoundException {
        Path path = Paths.get(fileName);
        List<String> allLines = Files.readAllLines(path);
        for (int i = 0; i < allLines.size(); i++) {
            String[] arr = allLines.get(i).split(",");
            String[] temp = arr[1].split(" ");
            for (int j = 0; j < temp.length; j++) {
                String key = temp[j].toLowerCase().trim();
                if (synsetHashMapObj.get(key) == null) {
                    synsetHashMapObj.put(key, new ArrayList<>());
                } 
                if(!synsetHashMapObj.get(key).contains(Integer.parseInt(arr[0])))
                    synsetHashMapObj.get(key).add(Integer.parseInt(arr[0]));
            }
        }
    }

    private void parseHypernyms(String fileName) throws IOException, FileNotFoundException {
        Path path = Paths.get(fileName);
        List<String> allLines = Files.readAllLines(path);
        String[] arr;
        for (int i = 0; i < allLines.size(); i++) {
            if (allLines.get(i).contains(",")) {
                arr = allLines.get(i).split(",",2);
                int key = Integer.parseInt(arr[0]);
                if (hypernymsHashMapObj.get(key) == null) {
                    hypernymsHashMapObj.put(key, new ArrayList<>());
                }
                for (int j = 0; j < arr[1].split(",").length; j++) {
                    if(!hypernymsHashMapObj.get(key).contains(arr[1].split(",")[j])) {
                        hypernymsHashMapObj.get(key).add(arr[1].split(",")[j]);
                    }
                }
                // hypernymsHashMapObj.put(Integer.parseInt(arr[0]), Arrays.asList(arr[1].split(",")));
                // hypernymsHashMapObj.put(Integer.parseInt(arr[0]), listObj);

                hypMapObj.add(Integer.parseInt(arr[0]));
                for (String key1 : arr[1].split(",")) {
                    hypernymsHashMapObj.put(Integer.parseInt(key1), null);
                    hypMapObj.add(Integer.parseInt(key1));
                }
            } else {
                arr = allLines.get(i).split(" ");
                hypernymsHashMapObj.put(Integer.parseInt(arr[0]), null);
                hypMapObj.add(Integer.parseInt(arr[0]));
            }
        }
    }

    public boolean isNoun(String word) {
        String key = word.trim().toLowerCase();
        return synsetHashMapObj.containsKey(key);
    }

    public int distance(String nounA, String nounB) {
        if (nounA == null || nounB == null) {
            throw new IllegalArgumentException();
        }
        if (nounA.equals(nounB)) {
            return 0;
        }
        List<Integer> listObj1 = synsetHashMapObj.get(nounA);
        List<Integer> listObj2 = synsetHashMapObj.get(nounB);
        Collections.sort(listObj1);
        Collections.sort(listObj2);
        int l1 = 0, l2 = 0, minDiff = Integer.MAX_VALUE;
        while (l1 < listObj1.size() && l2 < listObj2.size()) {
            minDiff = Math.min(minDiff, Math.abs(listObj1.get(l1) - listObj2.get(l2)));
            if (listObj1.get(l1) < listObj2.get(l2)) {
                l1++;
            } else {
                l2++;
            }
        }
    return minDiff;
     }
    public static void main(String[] args) throws IOException, FileNotFoundException {
        WordNet2 wordNet2Obj  = new WordNet2();
        File folder = new File("G:\\Github\\ADS-2\\WORDNET");
        File[] listOfFiles = folder.listFiles();
        
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile() && listOfFiles[i].getName().startsWith("synsets")) {
                wordNet2Obj.parseSynsets(listOfFiles[i].getName());
            } else if (listOfFiles[i].isFile() && listOfFiles[i].getName().startsWith("hypernyms")) {
                wordNet2Obj.parseHypernyms(listOfFiles[i].getName());
            }
        }
        System.out.println(wordNet2Obj.synsetHashMapObj.size());  
        System.out.println(wordNet2Obj.synsetHashMapObj.get("bioscope"));  
        // System.out.println("SHD " + wordNet2Obj.distance("Mustelidae", "Mutillidae"));
        int hySize = wordNet2Obj.getSynsetHashMapObj().size();
        DiaGraph diaGraphObj = new DiaGraph(hySize); 
        
        for (int i = 0; i < hySize; i++) {
            List<String> listObj = wordNet2Obj.getHypernymsHashMapObj().get(wordNet2Obj.getHypMapObj().get(i));
            // System.out.println(listObj);
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

    public HashMap<String, List<Integer>> getSynsetHashMapObj() {
        return synsetHashMapObj;
    }

    public void setSynsetHashMapObj(HashMap<String, List<Integer>> synsetHashMapObj) {
        this.synsetHashMapObj = synsetHashMapObj;
    }

    public List<Integer> getHypMapObj() {
        return hypMapObj;
    }

    public void setHypMapObj(List<Integer> hypMapObj) {
        this.hypMapObj = hypMapObj;
    }

    public HashMap<Integer, List<String>> getHypernymsHashMapObj() {
        return hypernymsHashMapObj;
    }

    public void setHypernymsHashMapObj(HashMap<Integer, List<String>> hypernymsHashMapObj) {
        this.hypernymsHashMapObj = hypernymsHashMapObj;
    }
}