import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import edu.princeton.cs.algs4.Digraph;

public class WordNet {
    private Digraph g;
    private final HashMap<String, List<Integer>> synsetHashMapObj;
    private final HashMap<Integer, List<Integer>> hypernymsHashMapObj;
    private final List<String> nounsList;

    public WordNet() throws IOException {
        synsetHashMapObj = new HashMap<>();
        hypernymsHashMapObj = new HashMap<>();
        nounsList = new ArrayList<>();
    }

    public WordNet(final String synsets, final String hypernyms) throws IOException {
        this();
        parseSynsets(synsets);
        parseHypernyms(hypernyms);
    }

    private void parseSynsets(final String filename) throws IOException {
        final Path path = Paths.get(filename);
        final List<String> allLines = Files.readAllLines(path);
        for (int i = 0; i < allLines.size(); i++) {
            final String[] line = allLines.get(i).split(",");
            final String[] nouns = line[1].split(" ");
            for (int j = 0; j < nouns.length; j++) {
                if (synsetHashMapObj.get(nouns[j]) == null) {
                    synsetHashMapObj.put(nouns[j], new ArrayList<>());
                }
                synsetHashMapObj.get(nouns[j]).add(Integer.parseInt(line[0]));
                nounsList.add(Integer.parseInt(line[0]), nouns[j]);
            }
        }
    }

    private void parseHypernyms(final String filename) throws IOException {
        final Path path = Paths.get(filename);
        final List<String> allLines = Files.readAllLines(path);
        String[] lis;
        for (int i = 0; i < allLines.size(); i++) {
            if (allLines.get(i).contains(",")) {
                lis = allLines.get(i).split(",", 2);
                final List<Integer> listObj = new ArrayList<>();
                for (final String ele : lis[1].split(",")) {
                    listObj.add(Integer.parseInt(ele));
                }
                hypernymsHashMapObj.put(Integer.parseInt(lis[0]), listObj);
            } else {
                lis = allLines.get(i).split(" ");
                hypernymsHashMapObj.put(Integer.parseInt(lis[0]), null);
            }

        }
        final int size = synsetHashMapObj.size();
        g = new Digraph(size);
        for (int i = 0; i < hypernymsHashMapObj.size(); i++) {
            final List<Integer> obj = hypernymsHashMapObj.get(i);
            if (obj != null) {
                for (int j = 0; j < obj.size(); j++) {
                    if (obj != null) {
                        g.addEdge(i, obj.get(j));
                    }
                }
            }
        }
    }

    public Iterable<String> nouns() {
        return synsetHashMapObj.keySet();
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
        return (ancestor == -1) ? null : nounsList.get(ancestor);
    }

    public static void main(final String[] args) throws IOException {
        // WordNet words = new WordNet();
        // String folder = "E:\\ADS 2\\wordnet" + "\\";
        // words.loadingFile(folder + "synsets.txt");
        // words.parseSynsets(folder + "synsets.txt");
        // words.parseHypernyms(folder + "hypernyms.txt");
        // System.out.println(words.hypernymsLis.size());
        // System.out.println(words.data.size());
        // Digraph obj = new Digraph(10);
        // System.out.println(obj.V());
        //System.out.println(words.nounsList.get(36));
    }
}

