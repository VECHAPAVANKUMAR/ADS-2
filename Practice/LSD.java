    import java.util.Arrays;

    public class LSD {
        
        public static void sort(String[] arr, int w) {
            
            int R = 256;
            int N =  arr.length;
            String[] aux = new String[N];

            for (int i = w - 1; i >= 0; i--) {
                int[] frequencyCount = new int[R + 1];
                for (int j = 0; j < N; j++) {
                    frequencyCount[arr[j].charAt(i) + 1]++;
                }
                for (int r = 0; r < R; r++) { // for cumulative sum
                    frequencyCount[r+1] += frequencyCount[r]; // cumulative sum is required to find where a particulat string
                                                            // string is to be placed so that array will be sorted
                }
                for (int j = 0; j < N; j++) {
                    // System.out.println(frequencyCount[arr[j].charAt(i)]);
                    aux[frequencyCount[arr[j].charAt(i)]++] = arr[j];     // This is post increment
                    // System.out.println(frequencyCount[arr[j].charAt(i)]); // post increment is required because the other
                                                                        // with the same end char must be placed after the first
                                                                        // string in arr to maintain stability
                }
                for (int j = 0; j < N; j++) {
                    arr[j] = aux[j];
                }
            }
        }
        public static void main(String[] args) {
            // This sorting requires fixed length strings
            String[] arr = {"abt","abt","abc"};
            LSD.sort(arr, arr[0].length());
            System.out.println(Arrays.toString(arr));
            // System.out.println('c' + 0);
        }
    }





    // System.out.println(frequencyCount[arr[j].charAt(i)]);
    // System.out.println(frequencyCount[100]);
    // System.out.println(frequencyCount[117]);

    // System.out.println(frequencyCount[arr[j].charAt(i)]++);
    // System.out.println(frequencyCount[arr[j].charAt(i)]);
    // System.out.println(aux[1]);
