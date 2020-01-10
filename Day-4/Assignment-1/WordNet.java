import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import programs.Digraph;

public class WordNet {
    
    private HashMap<String, List<Integer>> synsetHashMapObj;
    List<String> synListObj;
    private HashMap<Integer, List<String>> hypernymsHashMapObj;
    Digraph g;
    
    private WordNet() {
        synsetHashMapObj = new HashMap<>();
        hypernymsHashMapObj = new HashMap<>();
        synListObj = new ArrayList<>();
    }

    public WordNet(final String synsets, final String hypernyms) throws IOException {
        this();
        parseSynsets(synsets);
        parseHypernyms(hypernyms);
    }

    public Iterable<String> nouns() {
     return synsetHashMapObj.keySet();   
    }
    
    private void parseSynsets(final String fileName) throws IOException, FileNotFoundException {
        final Path path = Paths.get(fileName);
        final List<String> allLines = Files.readAllLines(path);
        for (int i = 0; i < allLines.size(); i++) {
            final String[] arr = allLines.get(i).split(",");
            final String[] temp = arr[1].split(" ");
            // synListObj.add(Integer.parseInt(arr[0]), arr[1].trim().toLowerCase());
            for (int j = 0; j < temp.length; j++) {
                final String key = temp[j].toLowerCase().trim();
                if (synsetHashMapObj.get(key) == null) {
                    synsetHashMapObj.put(key, new ArrayList<>());
                }
                if (!synsetHashMapObj.get(key).contains(Integer.parseInt(arr[0])))
                    synsetHashMapObj.get(key).add(Integer.parseInt(arr[0]));
                synListObj.add(Integer.parseInt(arr[0]), key);
            }
        }
    }

    private void parseHypernyms(final String fileName) throws IOException, FileNotFoundException {
        final Path path = Paths.get(fileName);
        final List<String> allLines = Files.readAllLines(path);
        String[] arr;
        for (int i = 0; i < allLines.size(); i++) {
            if (allLines.get(i).contains(",")) {
                arr = allLines.get(i).split(",", 2);
                final int key = Integer.parseInt(arr[0]);
                if (hypernymsHashMapObj.get(key) == null) {
                    hypernymsHashMapObj.put(key, new ArrayList<>());
                }
                for (int j = 0; j < arr[1].split(",").length; j++) {
                    if (!hypernymsHashMapObj.get(key).contains(arr[1].split(",")[j])) {
                        hypernymsHashMapObj.get(key).add(arr[1].split(",")[j]);
                    }
                }
                for (final String key1 : arr[1].split(",")) {
                    hypernymsHashMapObj.put(Integer.parseInt(key1), null);
                }
            } else {
                arr = allLines.get(i).split(" ");
                hypernymsHashMapObj.put(Integer.parseInt(arr[0]), null);
            }
        }
        final int size = synsetHashMapObj.size();
        g = new Digraph(size);
        for (int i = 0; i < size; i++) {
            final List<String> listObj = hypernymsHashMapObj.get(i);
            if (listObj != null) {
                for (int j = 0; j < listObj.size(); j++) {
                    g.addEdge(i, Integer.parseInt(listObj.get(j)));
                }
            }
        }
        // for (int i = 0; i < g.V(); i++) {
        // for (Integer ele : g.adj(i)) {
        // System.out.println(i + " ---- >" + ele);
        // }
        // }
    }

    public boolean isNoun(final String word) {
        if (word == null) {
            throw new IllegalArgumentException();
        }
        return synsetHashMapObj.containsKey(word);
    }

    public int distance(final String nounA, final String nounB) {
        if (nounA == null || nounB == null) {
            throw new IllegalArgumentException();
        }
        final List<Integer> nounAIdList = synsetHashMapObj.get(nounA.trim().toLowerCase());
        final List<Integer> nounBIdList = synsetHashMapObj.get(nounB.trim().toLowerCase());
        final SAP sapObj = new SAP(g);
        return sapObj.length(nounAIdList, nounBIdList);
    }

    public String sap(final String nounA, final String nounB) {
        if (nounA == null || nounB == null) {
            throw new IllegalArgumentException();
        }
        final List<Integer> nounAIdList = synsetHashMapObj.get(nounA.trim().toLowerCase());
        final List<Integer> nounBIdList = synsetHashMapObj.get(nounB.trim().toLowerCase());
        final SAP sapObj = new SAP(g);
        final int ancestor = sapObj.ancesstor(nounAIdList, nounBIdList);
        return (ancestor == -1) ? null : synListObj.get(ancestor);
    }

    public static void main(final String[] args) throws IOException, FileNotFoundException {
        final String folderPath = "G:\\Github\\ADS-2\\WORDNET";
        final WordNet wordNetObj = new WordNet(folderPath + "\\" + "synsets.txt", folderPath + "\\" + "hypernyms.txt");

        // File folder = new File(folderPath);
        // File[] listOfFiles = folder.listFiles();

        // for (int i = 0; i < listOfFiles.length; i++) {
        // if (listOfFiles[i].isFile() &&
        // listOfFiles[i].getName().startsWith("synsets")) {
        // wordNetObj.parseSynsets(folderPath + "\\" + listOfFiles[i].getName());
        // } else if (listOfFiles[i].isFile() &&
        // listOfFiles[i].getName().startsWith("hypernyms")) {
        // wordNetObj.parseHypernyms(folderPath + "\\" + listOfFiles[i].getName());
        // }
        // }

        wordNetObj.parseSynsets(folderPath + "\\" + "synsets.txt");
        System.out.println(wordNetObj.synsetHashMapObj.size());
        System.out.println(wordNetObj.synsetHashMapObj.get("bioscope"));
        System.out.println(wordNetObj.synListObj.get(24278));
        System.out.println(wordNetObj.synListObj.get(24277));
        System.out.println(wordNetObj.synListObj.get(13745));
        System.out.println(wordNetObj.synListObj.get(13746));
        System.out.println("IS NOUN " + wordNetObj.isNoun("bioscope"));
        System.out.println("DISTANCE " + wordNetObj.distance("1750s", "1760s"));
        System.out.println("ANCSTR " + wordNetObj.sap("1750s", "1760s"));
        System.out.println("DISTANCE " + wordNetObj.distance("AIDS", "acquired_immune_deficiency_syndrome"));
        System.out.println("ANCSTR " + wordNetObj.sap("AIDS", "acquired_immune_deficiency_syndrome"));

    }

    public HashMap<String, List<Integer>> getSynsetHashMapObj() {
        return synsetHashMapObj;
    }

    public void setSynsetHashMapObj(final HashMap<String, List<Integer>> synsetHashMapObj) {
        this.synsetHashMapObj = synsetHashMapObj;
    }

    public List<String> getHypMapObj() {
        return synListObj;
    }

    public void setHypMapObj(final List<String> hypMapObj) {
        this.synListObj = hypMapObj;
    }

    public HashMap<Integer, List<String>> getHypernymsHashMapObj() {
        return hypernymsHashMapObj;
    }

    public void setHypernymsHashMapObj(final HashMap<Integer, List<String>> hypernymsHashMapObj) {
        this.hypernymsHashMapObj = hypernymsHashMapObj;
    }
}