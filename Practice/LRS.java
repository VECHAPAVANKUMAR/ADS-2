    import java.io.File;
    import java.io.FileNotFoundException;
    import java.util.ArrayList;
    import java.util.Arrays;
    import java.util.List;
    import java.util.Scanner;

    public class LRS {

    public String lrs(String s, int reqLen) {

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

            if (len >= reqLen) {
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

        LRS lrsObj = new LRS();

        String path = "G:\\Github\\ADS-2\\LRS";
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

                List<String> input = null;
                int line = 0;

                while (scannerObj.hasNext()) {
                    
                    if (line == 0) {
                        input = new ArrayList<>();
                        scannerObj.nextLine();
                        line++;
                    }

                    input.add(scannerObj.nextLine());
                }

                List<String> res = new ArrayList<>();

                for (int j = 0; j < input.size(); j++) {

                    String[] arr = input.get(j).split(",", 2);

                    if (arr.length == 1) {                        
                        res.add("-1");
                        continue;
                    }

                    String[] test = arr[1].split(",");
                    
                    for (int k = 0; k < test.length; k++) {
                        
                            String[] temp = lrsObj.lrs(arr[0], Integer.parseInt(test[k])).split(";");

                            for (int index = 0; index < temp.length; index++) {
                                res.add(temp[index]);
                            }
                        }
                    }

                // System.out.println(res);

                try {
                    fileObj = new File(path + "output" + listOfFiles[i].getName().substring(5));
                    scannerObj = new Scanner(fileObj);
                } catch (final FileNotFoundException exceptionObj) {
                    exceptionObj.printStackTrace();
                }

                boolean flag = true;

                for (int j = 0; j < res.size(); j++) {
                    if (!res.get(j).equals(scannerObj.nextLine())) {
                        flag = false;
                        testCasesFailed.add(listOfFiles[i].getName());
                        break;
                    }
                }

                if (flag) {
                    count++;
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
