    import edu.princeton.cs.algs4.In;

    /**
     * Class Outcast.
     * @author V. Pavan Kumar
     */
    public class Outcast {
        
        /**
         * wordnet variable.
         */
        private final WordNet wordNetObj;

        /**
         * Argumented constructor for initialising instance variables.
         * @param words wordnetObj
         */
        public Outcast(final WordNet words) {
            this.wordNetObj = words;
        }

        /**
         * @param nouns noun array
         * @return odd one from the given noun array
         */
        public String outcast(final String[] nouns) {

            int maxDist = 0;
            int dist;
            String result = "";

            for (final String eleA : nouns) {
                dist = 0;
                for (final String eleB : nouns) {
                    dist = dist + wordNetObj.distance(eleA, eleB);
                }
                if (maxDist < dist) {
                    maxDist = dist;
                    result = eleA;
            }
        }
        
        return result;
        }

        /**
         * @param args command line arguments
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
            } catch(final IllegalArgumentException exceptionObj) {
                System.out.println(exceptionObj);
            }
        }
    }
