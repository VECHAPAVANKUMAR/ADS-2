import programs.BreadthFirstDirectedPaths;
import programs.Digraph;
import programs.In;
import programs.Bag;

public class SAP {

    private Digraph digraphObj;

    public SAP(final Digraph g) {
        this.digraphObj = g;
    }

    public int length(final int v, final int w) {
        if (v < 0 || v >= digraphObj.V() || w < 0 || w >= digraphObj.V()) {
            throw new IllegalArgumentException();
        }
        if (v == w) {
            return 0;
        }

        final BreadthFirstDirectedPaths bfdsObj1 = new BreadthFirstDirectedPaths(digraphObj, v);
        final BreadthFirstDirectedPaths bfdsObj2 = new BreadthFirstDirectedPaths(digraphObj, w);

        int distance = Integer.MAX_VALUE;

        for (int vertex = 0; vertex < digraphObj.V(); vertex++) {
            if (bfdsObj1.hasPathTo(vertex) && bfdsObj2.hasPathTo(vertex)) {
                final int newDist = bfdsObj1.distTo(vertex) + bfdsObj2.distTo(vertex);
                if (newDist < distance) {
                    distance = newDist;
                }
            }
        }
        return distance == Integer.MAX_VALUE ? -1 : distance;
    }

    public int ancesstor(final int v, final int w) {
        if (v < 0 || w < 0) {
            throw new IllegalArgumentException();
        }
        if (v == w) {
            return v;
        }

        int ancstor = -1;
        final BreadthFirstDirectedPaths bfdsObj1 = new BreadthFirstDirectedPaths(digraphObj, v);
        final BreadthFirstDirectedPaths bfdsObj2 = new BreadthFirstDirectedPaths(digraphObj, w);

        int distance = Integer.MAX_VALUE;

        for (int vertex = 0; vertex < digraphObj.V(); vertex++) {
            if (bfdsObj1.hasPathTo(vertex) && bfdsObj2.hasPathTo(vertex)) {
                final int tempDist = bfdsObj1.distTo(vertex) + bfdsObj2.distTo(vertex);
                if (tempDist < distance) {
                    distance = tempDist;
                    ancstor = vertex;
                }
            }
        }
        return ancstor;
    }

    public int length(final Iterable<Integer> v, final Iterable<Integer> w) {
        if (v == null || w == null) {
            throw new IllegalArgumentException();
        }
        int distance = Integer.MAX_VALUE;
        for (final Integer eleV : v) {
            for (final Integer eleW : w) {
                final int tempDist = length(eleV, eleW);
                if (tempDist < distance) {
                    distance = tempDist;
                }
            }
        }
        return distance == Integer.MAX_VALUE ? -1 : distance;
    }

    public int ancesstor(final Iterable<Integer> v, final Iterable<Integer> w) {
        if (v == null || w == null) {
            throw new IllegalArgumentException();
        }
        int shortestDist = Integer.MAX_VALUE;
        int ancstor = -1;
        for (final Integer eleV : v) {
            for (final Integer eleW : w) {
                final int tempAncstor = ancesstor(eleV, eleW);
                if (length(eleV, eleW) < shortestDist) {
                    shortestDist = length(eleV, eleW);
                    ancstor = tempAncstor;
                }
            }

        }
        return ancstor;
    }

    public static void main(final String[] args) {
        final In inObj = new In("Digraph25.txt");
        final Digraph digphObj = new Digraph(inObj);
        final SAP sapObj = new SAP(digphObj);
        System.out.println("Enter the Vertices v and w");
        final int v = Integer.parseInt(args[0]);
        final int w = Integer.parseInt(args[1]);
        System.out.println("Len : " + sapObj.length(v, w));
        System.out.println("Anc : " + sapObj.ancesstor(v, w));
        final Bag<Integer> A = new Bag<>();
        final Bag<Integer> B = new Bag<>();
            A.add(13);
            A.add(23);
            A.add(24);
            B.add(6);
            B.add(16);
            B.add(17);
            System.out.println("A " + sapObj.length(A, B));
        }
    }