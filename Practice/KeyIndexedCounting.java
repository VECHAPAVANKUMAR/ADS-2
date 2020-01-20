import java.util.Arrays;

public class KeyIndexedCounting {

    public static void CountingSort(int[] arr) {
        
        int max = arr[0];

        for (int i = 0; i < arr.length; i++) {
            if (arr[i] > max) {
                max = arr[i];
            }
        }
        // System.out.println(max);
        int[] frequencyCount = new int[max + 1];
        int[] aux = new int[arr.length];

        for (int i = 0; i < arr.length; i++) {
            frequencyCount[arr[i]]++;
        }
        // System.out.println(Arrays.toString(frequencyCount));
        for (int i = 1; i <= max; i++) {
            frequencyCount[i] += frequencyCount[i-1];
        }
        // System.out.println(Arrays.toString(frequencyCount));
        for (int i = arr.length - 1; i >= 0; i--) {
            aux[frequencyCount[arr[i]] - 1] = arr[i];
            frequencyCount[arr[i]]--;
        }
        // System.out.println(Arrays.toString(aux));
        for (int i = 0; i < arr.length; i++) {
            arr[i] = aux[i];
        }
    }

    public static void CountingSort(char[] arr) {
        
        int R = 256; // Radix
        int[] frequencyCount = new int[R + 1];
        char[] aux = new char[arr.length];

        for (int i = 0; i < arr.length; i++) {
            frequencyCount[arr[i]]++;
        }
        // System.out.println(Arrays.toString(frequencyCount));
        for (int i = 1; i <= R; i++) {
            frequencyCount[i] += frequencyCount[i-1];
        }
        // System.out.println(Arrays.toString(frequencyCount));
        for (int i = arr.length - 1; i >= 0; i--) {
            aux[frequencyCount[arr[i]] - 1] = arr[i];
            frequencyCount[arr[i]]--;
        }
        // System.out.println(Arrays.toString(aux));
        for (int i = 0; i < arr.length; i++) {
            arr[i] = aux[i];
        }
    }

    public static void main(String[] args) {

        // This Counting sort is applicable only foe byte, short and integer array
        // that to for only positive numbers
        // Used for small arrays
        // stable sort which is evident from the fourth loop of Counting sort method

        int[] arr = {3,2,4,10,1,2,3,1,1,8,9,6,2,4};
        System.out.println(Arrays.toString(arr));
        KeyIndexedCounting.CountingSort(arr);
        System.out.println(Arrays.toString(arr));

        char[] a = {'d','a','b','a'};
        System.out.println(Arrays.toString(a));
        KeyIndexedCounting.CountingSort(a);
        System.out.println(Arrays.toString(a));
    }
}