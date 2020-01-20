import java.util.ArrayList;
import java.util.List;
import edu.princeton.cs.algs4.Queue;

public class TST<Value> {
    
    private Node root;
    private int keyCount;
    private List<String> keysListObj;

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

    //     if (key == null || key.isEmpty()) {
    //         throw new IllegalArgumentException();
    //     }

    //     // System.out.println("BEFORE : " + keyCount + " root : " + root);

    //     if (!contains(key)) {
    //         keyCount++;
    //     }

    //     // System.out.println("AFTER : " + keyCount + " root : " + root);
        
    //     put(root, key, val);
    // }

    // private void put(Node node, String key, Value val) {

    //     // System.out.println("Root : " + node);

    //     if (node == null) {
    //         node = new Node();
    //         node.ch = key.charAt(0);
    //         root = node;
    //     }

    //     // System.out.println("Root : " + node);

    //     int pos = 0;

    //     while (pos < key.length() - 1) {

    //         int diff = key.charAt(pos) - node.ch;

    //         if (diff < 0) {

    //             System.out.println("Left");

    //             // while (node.left !=  null) {
    //             //     node = node.left;
    //             // }
                
    //             node.left = new Node();
    //             node.left.ch = key.charAt(pos);
    //             node = node.left;

    //         } else if (diff == 0) {

    //             System.out.println("Middle");

    //             // while (node.middle != null) {
    //             //     node = node.middle;
    //             // }

    //             pos++;
                
    //             node.middle = new Node();
    //             node.middle.ch = key.charAt(pos);
    //             node = node.middle;

    //             if (pos == key.length() - 1) {
    //                 System.out.println("value : " + val);
    //                 node.val = val;
    //                 System.out.println("Value : " + node);
    //             }   

    //         } else if (diff > 0) {  

    //             System.out.println("Right");

    //             // while (node.right != null) {
    //             //     node = node.right;
    //             // }

    //             node.right = new Node();
    //             node.right.ch = key.charAt(pos);
    //             node = node.right;

    //         }
    //     }
    // }

    public void put(String key, Value val) {
    
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

    private Node put(Node node, String key, Value val, int d) {

        char c = key.charAt(d);

        if (node == null) {
            node = new Node();
            node.ch = c;
        }

        int diff = c - node.ch;

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

    public Value get(String key) {
        
        Node current = root;
        
        if (current == null || key == null || key.isEmpty()) {
            throw new IllegalArgumentException();
        }

        if (!contains(key)) {
            throw new IllegalArgumentException();
        }

        int pos = 0;

        while (current != null && pos < key.length() - 1) {

            int diff = key.charAt(pos) - (current.ch);

            if (diff < 0) {
                current = current.left;
            } else if (diff == 0) {
                current = current.middle;
                pos++;
            } else if (diff > 0) {
                current = current.right;
            }
        }

        return pos == key.length() - 1 ?  current.val : null;
    }

    private boolean contains(String key) {

        Node current = root;

        if (key == null || key.isEmpty()) {
            throw new IllegalArgumentException();
        }

        int pos = 0;

        while (current != null && pos < key.length() - 1) {

            int diff = key.charAt(pos) - (current.ch);

            if (diff < 0) {
                current = current.left;
            } else if (diff == 0) {
                current = current.middle;
                pos++;
            } else if (diff > 0) {
                current = current.right;
            }
        }

        return pos == key.length() - 1 ?  true : false;
    }

    public int size() {
        return keyCount;
    }

    public Iterable<String> keys() {

        Queue<String> queueObj = new Queue<>();

        for (String key : keysListObj) {
            queueObj.enqueue(key);
        }

        return queueObj;
    }

    public Iterable<String> keysWithPrefix(String prefix) {

        Queue<String> queueObj = new Queue<>();

        for (String key : keysListObj) {
            
            if (key.startsWith(prefix)) {
                queueObj.enqueue(key);
            }
        }

        return queueObj;
    }

    public Iterable<String> keysThatMatch(String pattern) {

        Queue<String> queueObj = new Queue<>();

        for (String key : keysListObj) {
            if (key.matches(pattern)) {
                queueObj.enqueue(key);
            }
        }

        return queueObj;
    }

    public String longestPrefixOf(String str) {

        if (str == null) {
            throw new IllegalArgumentException();
        }

        if(str.isEmpty()) {
            return null;
        }

        return null;
    }
    
    public static void main(String[] args) {

        TST<Integer> tstObj = new TST<>();

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

        System.out.println("Keys That Match : " + tstObj.keysThatMatch("s%"));

    }
}







































