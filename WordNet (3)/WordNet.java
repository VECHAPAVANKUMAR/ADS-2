/**
 *@author V. Pavan Kumar
 */
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;
import java.util.ArrayList; 
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;

/**
 * Class WordNet
 */
public class WordNet {
    private Digraph g;
    private final HashMap<String, List<Integer>> data;
    private final HashMap<Integer, List<Integer>> hypernymsLis;
    private final List<String> nounsList;

    /**
     * constructor
     * 
     * @throws IOException exception
     */
    private WordNet() {
        data = new HashMap<>();
        hypernymsLis = new HashMap<>();
        nounsList = new ArrayList<>();
    }

    /**
     * constructor with parameters
     */
    public WordNet(final String synsets, final String hypernyms) {
        this();
        int c = parseSynsets(synsets);
        parseHypernyms(hypernyms,c);
        int count = 0;
        for (int i = 0; i < g.V(); i++) {
            if (g.outdegree(i) == 0)
                count = count + 1;
        }
        if (count != 1) {
            throw new IllegalArgumentException();
        }
        DirectedCycle obj = new DirectedCycle(g);
        if (obj.hasCycle()) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * this method will load the given files
     * 
     * @return synset array
     */
    private ArrayList<String> loadingFile(final String filename) {
        ArrayList<String> synsetArray;
        Scanner scannerObj = null;
        File f;
        try {
            f = new File(filename);
            scannerObj = new Scanner(f);
        } catch (final FileNotFoundException ex) {
            System.out.println(ex);
    }
    synsetArray = new ArrayList<>();
    while (scannerObj.hasNext()) {
        synsetArray.add(scannerObj.nextLine());
    }
    scannerObj.close();
    return synsetArray;
    }

    private int parseSynsets(final String filename) {
        final ArrayList<String> lines = loadingFile(filename);
        for (int i = 0; i < lines.size(); i++) {
            final String[] line = lines.get(i).split(",");
            final String[] nouns = line[1].split(" ");
            nounsList.add(Integer.parseInt(line[0]), line[1]);
            for (int j = 0; j < nouns.length; j++) {
                if (data.get(nouns[j]) == null) {
                    data.put(nouns[j], new ArrayList<>());
                }
                data.get(nouns[j]).add(Integer.parseInt(line[0]));
                // nounsList.add(Integer.parseInt(line[0]), nouns[j]);
            }
        }
        return lines.size();
    }

    /**
     *
     * @param filename file
     */
    private void parseHypernyms(final String filename, int c) {

        final ArrayList<String> lines = loadingFile(filename);
        String[] lis;
        for (int i = 0; i < lines.size(); i++) {
            if (lines.get(i).contains(",")) {
                lis = lines.get(i).split(",", 2);
                final List<Integer> listObj = new ArrayList<>();
                for (final String ele : lis[1].split(",")) {
                    listObj.add(Integer.parseInt(ele));
                }
                hypernymsLis.put(Integer.parseInt(lis[0]), listObj);
            } else {
                lis = lines.get(i).split(" ");
                hypernymsLis.put(Integer.parseInt(lis[0]), null);
            }

        }
        // final int size = data.size();
        // System.out.println("SIZE : " + size);
        g = new Digraph(c);
        for (int i = 0; i < hypernymsLis.size(); i++) {
            final List<Integer> obj = hypernymsLis.get(i);
            if (obj != null) {
                for (int j = 0; j < obj.size(); j++) {
                    // if (obj != null) {
                        g.addEdge(i, obj.get(j));
                    }
                }
            }
        // }
    }

    /**
     *
     * @return wordnet nouns
     */
    public Iterable<String> nouns() {
        return data.keySet();
    }

    /**
     *
     * @param word word
     * @return boolean
     */
    public boolean isNoun(final String word) {
        if (word == null) {
            throw new IllegalArgumentException();
        }
        return data.containsKey(word);
    }

    /**
     *@param nounA first noun
     *@param nounB second noun
     */
    public int distance(final String nounA, final String nounB) {
        if (nounA == null || nounB == null) {
            throw new IllegalArgumentException();
        }
        final List<Integer> nounAIdList = data.get(nounA);
        final List<Integer> nounBIdList = data.get(nounB);
        final SAP sapObj = new SAP(g);
        return sapObj.length(nounAIdList, nounBIdList);
    }

    /**
     *@param nounA first noun
     *@param nounB second noun
     */
    public String sap(final String nounA, final String nounB) {
        if (nounA == null || nounB == null) {
            throw new IllegalArgumentException();
        }
        final List<Integer> nounAIdList = data.get(nounA);
        final List<Integer> nounBIdList = data.get(nounB);
        final SAP sapObj = new SAP(g);
        final int ancestor = sapObj.ancestor(nounAIdList, nounBIdList);
        return (ancestor == -1) ? null : nounsList.get(ancestor);
    }

    /**
     *Main method
     * @param args arguments
     */
    public static void main(final String[] args) {
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
