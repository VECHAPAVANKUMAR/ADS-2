import java.util.Arrays;

public class MSD {

    public static void sort(String[] a) {
        
        String[] aux = new String[a.length];
        sort(a, aux, 0, a.length - 1, 0);

    }

    private static void sort(String[] a, String[] aux, int lo, int hi, int d) { // d because if the current char is same 
                                                                                // then we need to compare next char for sorting
        if (hi <= lo) {
            return;
        }
        int R = 256;
        int[] freqCount = new int[R + 2];
        for (int i = lo; i <= hi; i++) {
            freqCount[charAt(a[i], d) + 2]++;
        }
        for (int r = 0; r <= R; r++) {
            freqCount[r+1] += freqCount[r];
        }
        for (int i = lo; i <= hi; i++) {
            aux[freqCount[charAt(a[i], d) + 1]++] = a[i];
        }
        for (int i = lo; i <= hi; i++) {
            a[i] = aux[i - lo];
        }

        for (int r = 0; r < R; r++) {
            sort(a, aux, lo + freqCount[r], lo + freqCount[r+1] - 1, d+1);
        }
    }

    private static int charAt(String str, int d) {
        if (d < str.length()) {
            return str.charAt(d);
        }
        return -1;
    }
    public static void main(String[] args) {
        String[] arr = {"she","shells","seashells"};
        System.out.println(Arrays.toString(arr));
        MSD.sort(arr);
        System.out.println(Arrays.toString(arr));
    }
}