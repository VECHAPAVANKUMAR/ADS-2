import java.io.IOException;
import programs.In;
import programs.StdOut;

public class Outcast {

    private WordNet wordNetObj;

    public Outcast(WordNet words) {
        this.wordNetObj = words;
    }

    public String outcast(String[] nouns) {
        int maxDist = 0;
        int dist;
        String outcst = "";
        
        for (String eleA : nouns) {
            dist = 0;
            for (String eleB : nouns) {
                dist = dist + wordNetObj.distance(eleA, eleB);
            }
            if (maxDist < dist) {
                dist = maxDist;
                outcst = eleA;
            }
        }
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