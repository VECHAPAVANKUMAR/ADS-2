import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import edu.princeton.cs.algs4.Queue;

public class TST<Value> {
    
    private Node root;
    private int keyCount;
    private final List<String> keysListObj;

    private class Node {

        char ch;
        Value val;
        Node left, middle, right;

        public String toString() {
            return ch + " : " + val;
        }

    }

    public TST() {
        keysListObj = new ArrayList<>();
    }

    // public void put(String key, Value val) {

    // if (key == null || key.isEmpty()) {
    // throw new IllegalArgumentException();
    // }

    // // System.out.println("BEFORE : " + keyCount + " root : " + root);

    // if (!contains(key)) {
    // keyCount++;
    // }

    // // System.out.println("AFTER : " + keyCount + " root : " + root);

    // put(root, key, val);
    // }

    // private void put(Node node, String key, Value val) {

    // // System.out.println("Root : " + node);

    // if (node == null) {
    // node = new Node();
    // node.ch = key.charAt(0);
    // root = node;
    // }

    // // System.out.println("Root : " + node);

    // int pos = 0;

    // while (pos < key.length() - 1) {

    // int diff = key.charAt(pos) - node.ch;

    // if (diff < 0) {

    // System.out.println("Left");

    // // while (node.left != null) {
    // // node = node.left;
    // // }

    // node.left = new Node();
    // node.left.ch = key.charAt(pos);
    // node = node.left;

    // } else if (diff == 0) {

    // System.out.println("Middle");

    // // while (node.middle != null) {
    // // node = node.middle;
    // // }

    // pos++;

    // node.middle = new Node();
    // node.middle.ch = key.charAt(pos);
    // node = node.middle;

    // if (pos == key.length() - 1) {
    // System.out.println("value : " + val);
    // node.val = val;
    // System.out.println("Value : " + node);
    // }

    // } else if (diff > 0) {

    // System.out.println("Right");

    // // while (node.right != null) {
    // // node = node.right;
    // // }

    // node.right = new Node();
    // node.right.ch = key.charAt(pos);
    // node = node.right;

    // }
    // }
    // }

    public void put(final String key, final Value val) {

        if (key == null) {
            throw new IllegalArgumentException("calls put() with null key");
        }

        if (!contains(key)) {
            keyCount++;
            keysListObj.add(key);
        } else if (val == null) {
            keyCount--;
            keysListObj.remove(key); // delete existing key
            return;
        }

        root = put(root, key, val, 0);
    }

    private Node put(Node node, final String key, final Value val, final int d) {

        final char c = key.charAt(d);

        if (node == null) {
            node = new Node();
            node.ch = c;
        }

        final int diff = c - node.ch;

        if (diff < 0) {
            node.left = put(node.left, key, val, d);
        } else if (diff > 0) {
            node.right = put(node.right, key, val, d);
        } else if (d < key.length() - 1) {
            node.middle = put(node.middle, key, val, d + 1);
        } else {
            node.val = val;
        }
        return node;
    }

    public Value get(final String key) {

        Node current = root;

        if (current == null || key == null || key.isEmpty()) {
            throw new IllegalArgumentException();
        }

        if (!contains(key)) {
            throw new IllegalArgumentException();
        }

        int pos = 0;

        while (current != null && pos < key.length() - 1) {

            final int diff = key.charAt(pos) - (current.ch);

            if (diff < 0) {
                current = current.left;
            } else if (diff == 0) {
                current = current.middle;
                pos++;
            } else if (diff > 0) {
                current = current.right;
            }
        }

        return pos == key.length() - 1 ? current.val : null;
    }

    private boolean contains(final String key) {

        Node current = root;

        if (key == null || key.isEmpty()) {
            throw new IllegalArgumentException();
        }

        int pos = 0;

        while (current != null && pos < key.length() - 1) {

            final int diff = key.charAt(pos) - (current.ch);

            if (diff < 0) {
                current = current.left;
            } else if (diff == 0) {
                current = current.middle;
                pos++;
            } else if (diff > 0) {
                current = current.right;
            }
        }

        return pos == key.length() - 1 ? true : false;
    }

    public int size() {
        return keyCount;
    }

    public Iterable<String> keys() {

        final Queue<String> queueObj = new Queue<>();

        for (final String key : keysListObj) {
            queueObj.enqueue(key);
        }

        return queueObj;
    }

    public Iterable<String> keysWithPrefix(final String prefix) {

        final Queue<String> queueObj = new Queue<>();

        for (final String key : keysListObj) {

            if (key.startsWith(prefix)) {
                queueObj.enqueue(key);
            }
        }

        return queueObj;
    }

    public Iterable<String> keysThatMatch(String pattern) {

        pattern = pattern.replace("%", "+");
        final Pattern patternObj = Pattern.compile(pattern);
        Matcher matcherObj;
        final Queue<String> queueObj = new Queue<>();

        for (final String key : keysListObj) {
            matcherObj = patternObj.matcher(key);
            if (matcherObj.find()) {
                queueObj.enqueue(key);
            }
        }

        return queueObj.isEmpty() ? null : queueObj;
    }

    public String longestPrefixOf(final String query) {

        if (query == null) {
            throw new IllegalArgumentException();
        }

        if (query.isEmpty()) {
            return null;
        }

        String longestPrefix = "";
        int max = Integer.MIN_VALUE;
        int count;

        for (final String key : keysListObj) {
            count = 0;
            for (int i = 0; i < key.length(); i++) {
                if (key.charAt(i) == query.charAt(i)) {
                    count++;
                }
            }

            if (count > max) {
                max = count;
                longestPrefix = key;
            }
        }

        return longestPrefix;
    }

    public static void main(final String[] args) {

        final TST<Integer> tstObj = new TST<>();

        System.out.println("Contains : " + tstObj.contains("shells"));

        tstObj.put("shells", 3);
        tstObj.put("the", 5);
        tstObj.put("by", 4);
        tstObj.put("shore", 3);


        // System.out.println("Get Root  : " + tstObj.get("shells"));

        System.out.println("Contains : " + tstObj.contains("shells"));

        // System.out.println("ROOT : " + tstObj.root.middle.middle.middle.middle.middle.val);

        System.out.println("Root  : " + tstObj.root.ch);
        System.out.println("Left  : " + tstObj.root.left.ch);
        System.out.println("Right : " + tstObj.root.right.ch);
        System.out.println("Root Middle Right Right : " + tstObj.root.middle.middle.right.ch);

        System.out.println("Total Keys : " + tstObj.size());
        
        System.out.println("Get Root  : " + tstObj.get("shells"));
        System.out.println("Get Left  : " + tstObj.get("by"));
        System.out.println("Get Right : " + tstObj.get("the"));

        System.out.println("KEYS : " + tstObj.keys());

        tstObj.put("shells", null);

        System.out.println("KEYS : " + tstObj.keys());

        System.out.println("Keys With Prefix : " + tstObj.keysWithPrefix("sh"));

        System.out.println("Keys That Match : " + tstObj.keysThatMatch(".he.l"));

        System.out.println("Keys Longest Prefix : " + tstObj.longestPrefixOf("shores"));

    }
}

        // Queue<String> queueObj = new Queue<>();

        // for (String key : keysListObj) {
        //     if (key.matches(pattern.replace("%", "+"))) {
        //         queueObj.enqueue(key);
        //     }
        // }







































        // import java.io.*;
        // import java.util.*;
        // import java.text.*;
        // import java.math.*;
        // import java.util.regex.*;
        
        // public class Solution {
        
        //     public static void main(String[] args) {
        //         /* Enter your code here. Read input from STDIN. Print output to STDOUT. Your class should be named Solution. */
                
        //         BufferedReader bufferedReaderObj = new BufferedReader(new InputStreamReader(System.in));
                
        //         String temp;
                
        //         int pre = 0;
        //         int preMax = 0;
        //         int post = 0;
        //         int postMax = 0;
                
        //         HashMap<String, int[]> values = new HashMap<String, int[]>();
        //         boolean flag = true;
                
                
        //         try {
                    
        //             while ((temp = bufferedReaderObj.readLine()) != null) {
                        
        //                 if (flag == true) {
                            
        //                     preMax = Integer.parseInt(temp.split(" ")[0]);
        //                     postMax = Integer.parseInt(temp.split(" ")[1]);
        //                     flag = false;
                        
        //                 } else {
                            
        //                     if(pre < preMax){
                                
        //                         int[] arr;
        //                         int lower = 0;
        //                         int upper = 0;
                                
        //                         String[] input = temp.split(" ");
        //                         int dimensions = Integer.parseInt(input[3]) + 1;
                                
        //                         arr = new int[dimensions]; // To find all the coefficients
        //                         dimensions--;
        //                         arr[dimensions] = Integer.parseInt(input[2]);
        //                         dimensions--;
                                
        //                         for(int i = input.length - 1; i > 5; i = i-2) {
                                    
        //                             upper = Integer.parseInt(input[i]);
        //                             lower = Integer.parseInt(input[i - 1]);
        //                             arr[dimensions] = arr[dimensions + 1] * (upper - lower +1); // To find c1 to cd
        //                             dimensions--;
                                
        //                         }
                                
        //                         int maxDim = 1;
        //                         int tSub = 0;
                                
        //                         for(int i = 4; i < input.length; i = i + 2){
                                    
        //                             tSub = tSub + Integer.parseInt(input[i]) * arr[maxDim]; // Co
        //                             maxDim++;
        //                         }
                                
        //                         arr[dimensions] = Integer.parseInt(input[1]) - tSub;
        //                         values.put(input[0], arr);
        //                         pre++;
                                
        //                     } else if (pre == preMax && post < postMax){     
                                
        //                         String[] postcoefficients = temp.split(" ");
        //                         int[] coefficients = values.get(postcoefficients[0]);
        //                         int sum = coefficients[0];
        //                         String result = postcoefficients[0] + "[";
                                
        //                         for(int i = 1; i < coefficients.length;i++){
                                    
        //                             result = result + postcoefficients[i] + "," + " ";
        //                             sum = sum + (Integer.parseInt(postcoefficients[i]) * coefficients[i]);
        //                         }
                                
        //                         result = result.substring(0, result.length() - 2);
        //                         result = result +  "]" + " " + "=" + " " + sum;
        //                         System.out.println(result);
        //                         post++;
        //                     }
        //                 }
        //             }  
        //         } catch (Exception exceptionObj){
                   
        //         }
        // }
        // }
        