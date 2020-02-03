import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

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
     * @param board Boggle Board
     * @return Valid words that are possible using the letters on the board
     */
    public Iterable<String> getAllValidWords(final BoggleBoard board) {

        final Set<String> words = new HashSet<>();

        for (int row = 0; row < board.rows(); row++) {
            for (int col = 0; col < board.cols(); col++) {
                getWords(row, col, board, words, new boolean[board.rows()][board.cols()], "");
            }
        }
        return words;
    }

    /**
     * @param board Boggle Board
     * @param words Valid words that are possible using the letters on the board
     * @param visited boolean array to ensure that re-visiting a letter again is not possible
     * @param row row index
     * @param col col index
     * @param str word 
     */
    private void getWords(final int row, final int col, final BoggleBoard board, final Set<String> words, 
        final boolean[][] visited, final String str) {

        if (visited[row][col]) {
            return;
        }

        final char letter = board.getLetter(row, col);

        String word = str + letter;

        if (letter == 'Q') {
            word = word + 'U';
        }

        if (!trieSETObj.hasWordWithPrefix(word)) {
            return;
        }

        if (word.length() >= 3 && trieSETObj.contains(word)) {
            words.add(word);
        }

        visited[row][col] = true;

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {

                if (i == 0 && j == 0) {
                    continue;
                }

                if (validate(row + i, col + j, board)) {
                    getWords(row + i, col + j, board, words, visited, word);
                }
            }
        }

        visited[row][col] = false;
    }

    /**
     * @param row row index
     * @param col col index
     * @param board Boggle Board
     * @return boolean value true if the row and col are within the board dimensions otherwise false
     */
    private boolean validate(final int row, final int col, final BoggleBoard board) {
        return (row >= 0 && row < board.rows()) && (col >= 0 && col < board.cols());
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

        // if (wordLen < 3) {
        //     return 0;
        // }

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
            final BoggleBoard boggleBoardObj = new BoggleBoard(4, 4);

            System.out.println("Input File : " + fileArray[i].getName());
            System.out.println("");    
            System.out.println(boggleSolverObj.getAllValidWords(boggleBoardObj));
            System.out.println("");    
           
            }
        }
        scannerObj.close();
    }
}