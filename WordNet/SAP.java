    import java.util.Iterator;
    import edu.princeton.cs.algs4.Digraph;
    import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
    import edu.princeton.cs.algs4.In;

    /**
     * Class SAP.
     * @author V. Pavan Kumar
     */
    public final class SAP {

        /**
         * Digraph variable.
         */
    private final Digraph digraphObj;

        /**
         * Argumented Constructor for intializing instance variables.
         * @param g digraph
         */
        public SAP(final Digraph g) {
            this.digraphObj = g;
        }

        /**
         * Gives the shortest distance between v and w if ancestor exists otherwise it 
         * returns -1
         * @param v head vertex
         * @param w tail vertex
         * @return distance
         */
        public int length(final int v, final int w) {

        validateVertex(v);
        validateVertex(w);

        final BreadthFirstDirectedPaths bfdvObj = new BreadthFirstDirectedPaths(digraphObj, v);
        final BreadthFirstDirectedPaths bfdwObj = new BreadthFirstDirectedPaths(digraphObj, w);

        int infinity = Integer.MAX_VALUE;
        
        for (int vertex = 0; vertex < digraphObj.V(); vertex++) {
            if (bfdvObj.hasPathTo(vertex) && bfdwObj.hasPathTo(vertex)) {
                final int dist = bfdvObj.distTo(vertex) + bfdwObj.distTo(vertex);
                if (dist <= infinity) {
                    infinity = dist;
                }
            }
        }

        return (infinity == Integer.MAX_VALUE) ? -1 : infinity;
    }

    /**
     * This method gives the ancestor of vertices v and w
     * @param v head vertex
     * @param w tail vertex
     * @return ancestor
     */
    public int ancestor(final int v, final int w) {

        validateVertex(v);
        validateVertex(w);

        if (v == w) {
            return v;
        }

        final BreadthFirstDirectedPaths bfdvObj = new BreadthFirstDirectedPaths(digraphObj, v);
        final BreadthFirstDirectedPaths bfdwObj = new BreadthFirstDirectedPaths(digraphObj, w);
        
        int infinity = Integer.MAX_VALUE;
        int ances = -1;

        for (int vertex = 0; vertex < digraphObj.V(); vertex++) {
            if (bfdvObj.hasPathTo(vertex) && bfdwObj.hasPathTo(vertex)) {
                final int dist = bfdvObj.distTo(vertex) + bfdwObj.distTo(vertex);
                if (dist <= infinity) {
                    infinity = dist;
                    ances = vertex;
                }
            }
        }

        return ances;
    }

    /**
     * Gives the shortest distance between v and w
     * @param v one vertex
     * @param w other vertex
     * @return distance
     */
    public int length(final Iterable<Integer> v, final Iterable<Integer> w) {

        validateIterable(v);
        validateIterable(w);

        int infinity = Integer.MAX_VALUE;

        for (final Integer eleV : v) {
            for (final Integer eleW : w) {
                final int sap = length(eleV, eleW);
                if (sap <= infinity) {
                    infinity = sap;
                }
            }
        }

        return (infinity == Integer.MAX_VALUE) ? -1 : infinity;
    }

    /**
     * Gives the common ancestor if exists otherwise it returns -1
     * @param v first vertex ids
     * @param w second vertex ids
     * @return ancestor id
     */
    public int ancestor(final Iterable<Integer> v, final Iterable<Integer> w) {

        validateIterable(v);
        validateIterable(w);

        int infinity = Integer.MAX_VALUE;
        int ances = -1;

        for (final Integer eleV : v) {
            for (final Integer eleW : w) {
                final int anc = ancestor(eleV, eleW);
                final int dist = length(eleV, eleW);
                if (dist <= infinity) {
                    infinity = dist;
                    ances = anc;
                }
            }
        }

        return ances;
    }

    /**
     * @param v id list
     */
    private void validateIterable(final Iterable<Integer> v) {

        if (v == null) {
            throw new IllegalArgumentException();
        }
        
        final Iterator<Integer> iterableObj = v.iterator();

        while (iterableObj.hasNext()) {
            if (iterableObj.next() == null) {
                throw new IllegalArgumentException();
            }
        }
    }

    /**
     * @param v nounA vertex
     * @param w nounB vertex
     */
    private void validateVertex(final int v) {
        if(v < 0 || v >= digraphObj.V()) {
            throw new IllegalArgumentException();  
        }
    }

        /**
         * @param args command line arguments
         */
        public static void main(final String[] args) {

            final In inObj = new In("G:\\Github\\ADS-2\\wordnet files\\digraph25.txt");
            final Digraph diObj = new Digraph(inObj);
            final SAP sapObj = new SAP(diObj);
            System.out.println(sapObj.length(22, 24));
            System.out.println(sapObj.ancestor(22, 24));
        }
    }
