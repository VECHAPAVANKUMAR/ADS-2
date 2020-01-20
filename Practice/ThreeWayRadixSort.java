    import java.io.File;
    import java.io.FileNotFoundException;
    import java.util.ArrayList;
    import java.util.Arrays;
    import java.util.List;
    import java.util.Scanner;

    public class ThreeWayRadixSort {

    public static void sort(final String[] a) {
        sort(a, 0, a.length - 1, 0);
    }

    private static void sort(final String[] a, final int lo, final int hi, final int d) { // d because if the current
                                                                                            // char is same then
                                                                        // we need to compare next char for sorting

        if (hi <= lo) {
            return;
        }
        int lt = lo;
        int gt = hi;
        final int v = charAt(a[lo], d);
        int i = lo + 1;

        while (i <= gt) {
            final int t = charAt(a[i], d);
            if (t < v) {
                exch(a, lt++, i++);
            } else if (t > v) {
                exch(a, i, gt--);
            } else {
                i++;
            }
        }

        sort(a, lo, lt - 1, d);
        if (v >= 0) {
            sort(a, lt, gt, d + 1);
        }
        sort(a, gt + 1, hi, d);
    }

    private static void exch(final String[] a, final int i, final int j) {
        final String temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    private static int charAt(final String str, final int d) {
        if (d < str.length()) {
            return str.charAt(d);
        }
        return -1;
    }

    public static void main(final String[] args) {

        // String[] arr = { "she", "shells", "seashells" };
        // System.out.println(Arrays.toString(arr));
        // ThreeWayRadixSort.sort(arr);
        // System.out.println(Arrays.toString(arr));

        String path = "G:\\Github\\ADS-2\\Radix Sorts";
        final File folder = new File(path);
        path = path + "\\";
        final File[] listOfFiles = folder.listFiles();

        int inputCount = 0;
        int count = 0;
        List<String> testCasesFailed = new ArrayList<>();

        for (int i = 0; i < listOfFiles.length; i++) {

            if (listOfFiles[i].isFile() && listOfFiles[i].getName().startsWith("input")) {

                inputCount++;

                File fileObj;
                Scanner scannerObj = null;

                try {
                    fileObj = new File(path + listOfFiles[i].getName());
                    scannerObj = new Scanner(fileObj);
                } catch (final FileNotFoundException exceptionObj) {
                    exceptionObj.printStackTrace();
                }

                String[] input = null;
                int line = 0;

                while (scannerObj.hasNext()) {
                    if (line == 0) {
                        input = new String[Integer.parseInt(scannerObj.nextLine())];
                    }
                    input[line++] = scannerObj.nextLine();
                }

                ThreeWayRadixSort.sort(input);
                
                try {
                    fileObj = new File(path + "output" + listOfFiles[i].getName().substring(5));
                    scannerObj = new Scanner(fileObj);
                } catch (final FileNotFoundException exceptionObj) {
                    exceptionObj.printStackTrace();
                }
                
                if (Arrays.toString(input).equals(scannerObj.nextLine())) {
                    count++;
                } else {
                    testCasesFailed.add(listOfFiles[i].getName());
                }
            }
        }
        if (count == inputCount) {
            System.out.println("All Test Cases Passed");
        } else {
            for (String str : testCasesFailed) {
                System.out.println("Test Case Failed : " + str);
            }
            }
        }
    }