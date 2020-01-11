
/**
 *@author V. Pavan Kumar
 */
import edu.princeton.cs.algs4.Digraph;

import java.util.Iterator;

import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.In;

/**
 * Class SAP.
 */
public final class SAP {
    /**
     *digraph variable
     */
  private final Digraph dirg;

    /**
     * constructor
     * 
     * @param g digraph
     */
    public SAP(final Digraph g) {
        this.dirg = g;
    }

    /**
     * gives the shortest distance between v and w
     * 
     * @param v one vertex
     * @param w other vertex
     * @return distance
     */
    public int length(final int v, final int w) {
        validateVertices(v, w);
        // if (v == w) {
        //     return 0;
        // }
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
     * this method gives the ancestor of vertices v and w
     * 
     * @param v one vertex
     * @param w other vertex
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
     * gives the shortest distance between v and w
     * 
     * @param v one vertex
     * @param w other vertex
     * @return length
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
     * gives the common ancestor
     * 
     * @param v one vertex
     * @param w other vertex
     * @return ancestor
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

    private void validateIterable(Iterable<Integer> v) {
        if (v == null) {
            throw new IllegalArgumentException();
        }
        Iterator<Integer> obj = v.iterator();
        while (obj.hasNext()) {
            if (obj.next() == null) {
                throw new IllegalArgumentException();
            }
        }
    }

    private void validateVertices(final int v, final int w) {
        if (v < 0 || v >= dirg.V() || w < 0 || w >= dirg.V()) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * main method
     * 
     * @param args arguments
     */
    public static void main(final String[] args) {
        // final In inObj = new In("E:\\ADS 2\\wordnet\\digraph25.txt");
        // final Digraph diObj = new Digraph(inObj);
        // final SAP sapObj = new SAP(diObj);
        // System.out.println(sapObj.length(22, 24));
        // System.out.println(sapObj.ancestor(22, 24));
    }
}
