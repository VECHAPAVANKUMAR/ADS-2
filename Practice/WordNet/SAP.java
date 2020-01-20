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
  private final Digraph dirg;

    /**
     * Argumented Constructor for intializing instance variables.
     * @param g digraph
     */
    public SAP(final Digraph g) {
        this.dirg = g;
    }

    /**
     * Gives the shortest distance between v and w if ancestor exists otherwise it 
     * returns -1
     * @param v first vertex
     * @param w second vertex
     * @return distance
     */
    public int length(final int v, final int w) {

        validateVertices(v, w);
        final BreadthFirstDirectedPaths bfdv = new BreadthFirstDirectedPaths(dirg, v);
        final BreadthFirstDirectedPaths bfdw = new BreadthFirstDirectedPaths(dirg, w);
        int infy = Integer.MAX_VALUE;
        for (int i = 0; i < dirg.V(); i++) {
            if (bfdv.hasPathTo(i) && bfdw.hasPathTo(i)) {
                final int dist = bfdv.distTo(i) + bfdw.distTo(i);
                if (dist <= infy) {
                    infy = dist;
                }
            }
        }
        if (infy == Integer.MAX_VALUE)
            return -1;
        return infy;
    }

    /**
     * This method gives the ancestor of vertices v and w
     * @param v first vertex
     * @param w second vertex
     * @return ancestor
     */
    public int ancestor(final int v, final int w) {
        
        validateVertices(v, w);
        if (v == w) {
            return v;
        }
        final BreadthFirstDirectedPaths bfdv = new BreadthFirstDirectedPaths(dirg, v);
        final BreadthFirstDirectedPaths bfdw = new BreadthFirstDirectedPaths(dirg, w);
        int infy = Integer.MAX_VALUE;
        int ances = -1;
        for (int i = 0; i < dirg.V(); i++) {
            if (bfdv.hasPathTo(i) && bfdw.hasPathTo(i)) {
                final int dist = bfdv.distTo(i) + bfdw.distTo(i);
                if (dist <= infy) {
                    infy = dist;
                    ances = i;
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
        if (v == null || w == null) {
            throw new IllegalArgumentException();
        }
        validateIterable(v);
        validateIterable(w);

        int infy = Integer.MAX_VALUE;
        for (final Integer eleV : v) {
            for (final Integer eleW : w) {
                if (eleV == null || eleW == null) {
                    throw new IllegalArgumentException();
                }
                final int sap = length(eleV, eleW);
                if (sap <= infy) {
                    infy = sap;
                }
            }
        }
        return infy == Integer.MAX_VALUE ? -1 : infy;
    }

    /**
     * Gives the common ancestor if exists otherwise it returns -1
     * @param v first vertex ids
     * @param w second vertex ids
     * @return ancestor id
     */
    public int ancestor(final Iterable<Integer> v, final Iterable<Integer> w) {
        if (v == null || w == null) {
            throw new IllegalArgumentException();
        }
        validateIterable(v);
        validateIterable(w);
        int infy = Integer.MAX_VALUE;
        int ances = -1;
        for (final Integer eleV : v) {
            for (final Integer eleW : w) {
                if (eleV == null || eleW == null) {
                    throw new IllegalArgumentException();
                }
                final int anc = ancestor(eleV, eleW);
                final int var = length(eleV, eleW);
                if (var <= infy) {
                    infy = var;
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
        final Iterator<Integer> obj = v.iterator();
        while (obj.hasNext()) {
            if (obj.next() == null) {
                throw new IllegalArgumentException();
            }
        }
    }

    /**
     * @param v nounA vertex
     * @param w nounB vertex
     */
    private void validateVertices(final int v, final int w) {
        if (v < 0 || v >= dirg.V() || w < 0 || w >= dirg.V()) {
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
