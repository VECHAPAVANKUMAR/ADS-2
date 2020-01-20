import java.io.FileNotFoundException;
import java.io.File;
import java.util.ArrayList; 
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;

/**
 * Class WordNet.
 * @author V. Pavan Kumar
 */
public class WordNet {

    /**
     * Digraph variable.
     */
    private Digraph g;

    /**
     * Hashmap synsets variable
     */
    private final HashMap<String, List<Integer>> synsetHashMapObj;

    /**
     * Hashmap hypernyms variable.
     */
    private final HashMap<Integer, List<Integer>> hypernymHashMapObj;

    /**
     * list nouns variable
     */
    private final List<String> nounsListObj;

    /**
     * No - Arg Constructor for initializing instance variables.
     */
    private WordNet() {
        synsetHashMapObj = new HashMap<>();
        hypernymHashMapObj = new HashMap<>();
        nounsListObj = new ArrayList<>();
    }

    /**
     * Argumented Constructor with parameters to p;ace nouns into synset hashmap
     * and hypernyms hashmap.
     */
    public WordNet(final String synsets, final String hypernyms) {
        
        this();
        final int c = parseSynsets(synsets);
        parseHypernyms(hypernyms, c);

        // To check wheter is a rooted dag

        int count = 0;
        for (int i = 0; i < g.V(); i++) {
            if (g.outdegree(i) == 0)
                count = count + 1;
        }
        if (count != 1) {
            throw new IllegalArgumentException();
        }
        final DirectedCycle obj = new DirectedCycle(g);
        if (obj.hasCycle()) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * This method will load the given files
     * 
     * @return synset array
     */
    private ArrayList<String> loadFile(final String filename) {
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

    /**
     * This method is used to place nouns in synset file into synset hashmap
     * @param filename synsets file
     * @return number of lines in the given file
     */
    private int parseSynsets(final String filename) {
        final ArrayList<String> lines = loadFile(filename);
        for (int i = 0; i < lines.size(); i++) {
            final String[] line = lines.get(i).split(",");
            final String[] nouns = line[1].split(" ");
            nounsListObj.add(Integer.parseInt(line[0]), line[1]);
            for (int j = 0; j < nouns.length; j++) {
                if (synsetHashMapObj.get(nouns[j]) == null) {
                    synsetHashMapObj.put(nouns[j], new ArrayList<>());
                }
                synsetHashMapObj.get(nouns[j]).add(Integer.parseInt(line[0]));
            }
        }
        return lines.size();
    }

    /**
     * This method is used to load data into hypernyms hashmap from hypernyms file
     * @param filename hypernyms file
     * @param c numer of lines in synset file
     */
    private void parseHypernyms(final String filename, final int c) {

        final ArrayList<String> lines = loadFile(filename);
        String[] lis;
        for (int i = 0; i < lines.size(); i++) {
            if (lines.get(i).contains(",")) {
                lis = lines.get(i).split(",", 2);
                final List<Integer> listObj = new ArrayList<>();
                for (final String ele : lis[1].split(",")) {
                    listObj.add(Integer.parseInt(ele));
                }
                hypernymHashMapObj.put(Integer.parseInt(lis[0]), listObj);
            } else {
                lis = lines.get(i).split(" ");
                hypernymHashMapObj.put(Integer.parseInt(lis[0]), null);
            }

        }

        g = new Digraph(c);
        for (int i = 0; i < hypernymHashMapObj.size(); i++) {
            final List<Integer> obj = hypernymHashMapObj.get(i);
            if (obj != null) {
                for (int j = 0; j < obj.size(); j++) {
                        g.addEdge(i, obj.get(j));
                    }
                }
            }
        }

    /**
     * @return wordnet nouns iterable
     */
    public Iterable<String> nouns() {
        return synsetHashMapObj.keySet();
    }

    /**
     * @param word word to be found whether it is a noun
     * @return boolean value true if word is a noun otherwise it returns false
     */
    public boolean isNoun(final String word) {
        if (word == null) {
            throw new IllegalArgumentException();
        }
        return synsetHashMapObj.containsKey(word);
    }

    /**
     * This method is used to find the shortest distance between nounA and nounB
     *@param nounA first noun
     *@param nounB second noun
     */
    public int distance(final String nounA, final String nounB) {
        if (nounA == null || nounB == null) {
            throw new IllegalArgumentException();
        }
        final List<Integer> nounAIdList = synsetHashMapObj.get(nounA);
        final List<Integer> nounBIdList = synsetHashMapObj.get(nounB);
        final SAP sapObj = new SAP(g);
        return sapObj.length(nounAIdList, nounBIdList);
    }

    /**
     * This method is used to find the shortest common ancestor between nounA and nounB
     *@param nounA first noun
     *@param nounB second noun
     */
    public String sap(final String nounA, final String nounB) {
        if (nounA == null || nounB == null) {
            throw new IllegalArgumentException();
        }
        final List<Integer> nounAIdList = synsetHashMapObj.get(nounA);
        final List<Integer> nounBIdList = synsetHashMapObj.get(nounB);
        final SAP sapObj = new SAP(g);
        final int ancestor = sapObj.ancestor(nounAIdList, nounBIdList);
        return (ancestor == -1) ? null : nounsListObj.get(ancestor);
    }

    /**
     * @param args command line arguments
     */
    public static void main(final String[] args) {
        final WordNet words = new WordNet();
        final String folder = "G:\\Github\\ADS-2\\wordnet files" + "\\";
        words.loadFile(folder + "synsets.txt");
        final int c = words.parseSynsets(folder + "synsets.txt");
        words.parseHypernyms(folder + "hypernyms.txt", c);
        System.out.println(words.hypernymHashMapObj.size());
        System.out.println(words.synsetHashMapObj.size());
        final Digraph obj = new Digraph(10);
        System.out.println(obj.V());
        System.out.println(words.nounsListObj.get(36));
    }
}
