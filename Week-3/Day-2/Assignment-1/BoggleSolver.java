import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import edu.princeton.cs.algs4.TrieSET;

/**
 * Class BoggleSolver.
 * @author V. Pavan Kumar
 */
public class BoggleSolver {

    /**
     * TrieSET Variable.
     */
    private final TrieSET trieSETObj;

    /**
     * No-Argumented Constructor for initializing instance variables.
     */
    private BoggleSolver() {
        trieSETObj = new TrieSET();
    }

    /**
     * Argumented Constructor for initializing instance variables.
     */
    public BoggleSolver(final String[] dictionary) {

        this();

        if (dictionary == null) {
            throw new IllegalArgumentException();
        }
        
        for (final String word : dictionary) {

            if (word.length() < 3) {
                continue;
            }

            trieSETObj.add(word);
        }

    }

    /**
     * @param word word for which score to be calculated
     * @return score for the word based on its length and availability in the dictionary
     */
    public int scoreOf(final String word) {

        if (word == null) {
            throw new IllegalArgumentException();
        }

        if (!trieSETObj.contains(word)) {
            return 0;
        }

        final int wordLen = word.length();

        if (wordLen == 3 || wordLen == 4) {
            return 1;
        } else if (wordLen == 5) {
            return 2;
        } else if (wordLen == 6) {
            return 3;
        } else if (wordLen == 7) {
            return 5;
        } else {
            return 11;
        }
    }


    /**
     * @param args Command Line Arguments
     */
    public static void main(final String[] args) {

        final String path = "G:\\Github\\ADS-2\\Week-2\\Boogle Solver\\boggle\\";
        
        final File folder = new File(path);
        final File[] fileArray = folder.listFiles();

        File fileObj = null;
        Scanner scannerObj = null;

        for (int i = 0; i < fileArray.length; i++) {

            if (fileArray[i].isFile() && fileArray[i].getName().startsWith("dictionary")) {

            try {
                fileObj = new File(path + fileArray[i].getName());
                scannerObj = new Scanner(fileObj);
            } catch (final FileNotFoundException exceptionObj) {
                exceptionObj.printStackTrace();
            }

            final StringBuffer words = new StringBuffer();

            while (scannerObj.hasNextLine()) {
                words.append(scannerObj.nextLine()).append(";");
            }

            final String[] dictionary = words.substring(0).split(";");

            final BoggleSolver boggleSolverObj = new BoggleSolver(dictionary);
            
            System.out.println("Input : " + fileArray[i].getName());
            System.out.println("Total Valid and Unique Words in Dictionary : " + boggleSolverObj.trieSETObj.size());
            System.out.println();
            
            }
        }
        scannerObj.close();
    }
}