import java.io.*;
import java.util.*;
import java.util.regex.*;

public class Solution {

    public static void main(String[] args) {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT. Your class should be named Solution. */
        Scanner scannerObj = new Scanner(System.in);
        
        while(scannerObj.hasNext()) {
            String input = scannerObj.nextLine();
            Pattern patternObj = Pattern.compile("\"");
            Matcher matcherObj = patternObj.matcher(input);
            StringBuffer sb = new StringBuffer(input.length());
            int i = 0;
            while (matcherObj.find()) {
                if (i % 2 == 0) {
                    matcherObj.appendReplacement(sb, "``");
                }
                else {
                    matcherObj.appendReplacement(sb, "''");
                }
                ++i;
            }
            matcherObj.appendTail(sb);
            System.out.println(sb.toString());

        }
    }
}