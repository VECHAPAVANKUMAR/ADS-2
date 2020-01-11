/**
 *@author V. Pavan Kumar
 */
import java.io.FileNotFoundException;
import java.io.IOException;
import edu.princeton.cs.algs4.In;

/**
 * Class Outcast.
 */
public class Outcast {
    private final WordNet wordNetObj;

    /**
     *
     * @param words words
     */
    public Outcast(final WordNet words) {
        this.wordNetObj = words;
    }

    /**
     *
     * @param nouns words
     * @return odd one out
     */
    public String outcast(final String[] nouns) {
        int maxDist = 0;
        int dist;
        String res = "";
        for (final String eleA : nouns) {
            dist = 0;
            for (final String eleB : nouns) {
                dist = dist + wordNetObj.distance(eleA, eleB);
            }
            if (maxDist < dist) {
                maxDist = dist;
                res = eleA;
            }
        }
        return res;
    }

    /**
     *
     * @param args arguments
     */
    public static void main(final String[] args) {
        try {
        final WordNet obj = new WordNet(args[0], args[1]);
        final Outcast outcastObj = new Outcast(obj);
        for (int i = 2; i < args.length; i++) {
            final In inObj = new In(args[i]);
            final String[] nouns = inObj.readAllStrings();
            System.out.println(outcastObj.outcast(nouns));
        }
        } catch(final IllegalArgumentException e) {
            System.out.println(e);
        }
    }
}
