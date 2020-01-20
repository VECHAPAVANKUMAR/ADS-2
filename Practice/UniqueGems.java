import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class UniqueGems {

    public String lrs(String s) {

        String lrs1 = "";

        if (s == null || s.isEmpty()) {
            return "-1";
        }

        int N = s.length();
        String[] suffixes = new String[N];

        for (int i = 0; i < N; i++) {
            suffixes[i] = s.substring(i, N);
        }

        Arrays.sort(suffixes);

        for (int i = 0; i < N - 1; i++) {

            int len = lcp(suffixes[i], suffixes[i + 1]);

            if (len >= 1) {
                lrs1 += suffixes[i].substring(0, len) + ";";
            }
        }

        return lrs1.length() == 0 ? "-1" : lrs1.replaceAll(";$", "");
    }

    private int lcp(String str1, String str2) {

        int min = Math.min(str1.length(), str2.length());

        for (int i = 0; i < min; i++) {

            if (str1.charAt(i) != str2.charAt(i)) {
                return i;
            }
        }

        return min;
    }

    public static void main(String[] args) {

        UniqueGems uniquegemsObj = new UniqueGems();
        
        String path = "G:\\Github\\ADS-2\\UniqueGems\\UniqueGems";
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

                // List<String> input = null;
                int line = 0;
                HashMap<String, Integer> res = null;

                while (scannerObj.hasNext()) {
                    res = new HashMap<>();
                    if (line == 0) {
                        scannerObj.nextLine();
                        line++;
                    }
                    String[] temp = uniquegemsObj.lrs(scannerObj.nextLine()).split(";");
                    for (int j = 0; j < temp.length; j++) {
                        if (res.get(temp[j]) == null) {
                            res.put(temp[j],0);
                        }
                        res.put(temp[j], res.get(temp[j]) + 1);
                    }
                    System.out.println(res);
                }
            }
        }
    }
}