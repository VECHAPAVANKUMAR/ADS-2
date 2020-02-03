// import java.io.File;
// import java.io.FileNotFoundException;
// import java.util.Arrays;
import java.util.HashSet;
// import java.util.Scanner;
import java.util.Set;

public class BoggleSolver {

    private final TrieSET trieSETObj;

    private BoggleSolver() {
        trieSETObj = new TrieSET();
    }

    public BoggleSolver(final String[] dict) {

        this();

        for (final String word : dict) {

            if (word.length() < 3) {
                continue;
            }

            trieSETObj.add(word);
        }

    }

    public Iterable<String> getAllValidWords(final BoggleBoard board) {

        final Set<String> words = new HashSet<>();

        for (int i = 0; i < board.rows(); i++) {
            for (int j = 0; j < board.cols(); j++) {
                getWords(board, words, new boolean[board.rows()][board.cols()], i, j, "");
            }
        }
        return words;
    }

    private void getWords(final BoggleBoard board, final Set<String> words, final boolean[][] visited,
            final int row, final int col, final String str) {

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
                    getWords(board, words, visited, row + i, col + j, word);
                }
            }

        }
        visited[row][col] = false;
    }

    private boolean validate(final int i, final int j, final BoggleBoard board) {
        return (i >= 0 && i < board.rows()) && (j >= 0 && j < board.cols());
    }

    public int scoreOf(final String word) {

        if (word == null) {
            throw new IllegalArgumentException();
        }

        if (!trieSETObj.contains(word)) {
            return 0;
        }

        final int wordLen = word.length();

        if (wordLen < 3) {
            return 0;
        }

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

    public static void main(final String[] args) {

    //     File fileObj = null;
    //     Scanner scannerObj = null;

    //     try {
    //         fileObj = new File("G:\\Github\\ADS-2\\Week-2\\Boogle Solver\\dictionary-algs4.txt");
    //         scannerObj = new Scanner(fileObj);
    //     } catch (final FileNotFoundException e) {
    //         e.printStackTrace();
    //     }

    //     final StringBuffer words = new StringBuffer();

    //     while (scannerObj.hasNextLine()) {
    //         words.append(scannerObj.nextLine()).append(";");
    //     }

    //     final String[] dictionary = words.substring(0).split(";");

    //     final BoggleSolver boggleSolverObj = new BoggleSolver(dictionary);
    //     final BoggleBoard boggleBoardObj = new BoggleBoard(4, 3);
        
    //    System.out.println(boggleSolverObj.getAllValidWords(boggleBoardObj));
       
    }
}