import java.io.IOException;
import programs.In;
import programs.StdOut;
public class Outcast {

    WordNet wordNetObj;
    public Outcast(WordNet words) {
        this.wordNetObj = words;
    }

    public String outcast(String[] nouns) {
        int maxDist = 0;
        int dist;
        String outcst = "";
        for (int i = 0; i < nouns.length; i++) {
            dist = 0;
            for (int j = 0; j < nouns.length; j++) {
                System.out.println(nouns[i]);
                dist = dist + wordNetObj.distance(nouns[i], nouns[j]);
            }
            if (maxDist < dist) {
                maxDist = dist;
                outcst = nouns[i];
                System.out.println("IF");
            }
        }
        // for (String eleA : nouns) {
        //     for (String eleB : nouns) {
        //         dist = dist + wordNetObj.distance(eleA, eleB);
        //         System.out.println(dist);
        //     }
            // if (maxDist < dist) {
            //     dist = maxDist;
            //     outcst = eleA;
            //     System.out.println("IF");
        //     }
        // }
        return outcst;
    }
    public static void main(String[] args) throws IOException {
        WordNet wordNetObj = new WordNet(args[0], args[1]);
        Outcast outcastObj = new Outcast(wordNetObj);
        for (int i = 2; i < args.length; i++) {
            In inObj = new In(args[i]);
            String[] nouns = inObj.readAllStrings();
            StdOut.println(args[i] + " --- " + outcastObj.outcast(nouns));
        }
    }
}