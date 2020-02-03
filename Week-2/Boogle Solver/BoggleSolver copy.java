import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class BoggleSolver {

    private final TrieST<String> trieSTObj;

    private BoggleSolver() {
        trieSTObj = new TrieST<>();
    }

    public BoggleSolver(final String[] dict) {

        this();

        Set<String> uniqueWords = new HashSet<>(Arrays.asList(dict));

        for (final String word : uniqueWords) {

            if (word.length() < 3) {
                continue;
            }

            trieSTObj.put(word, scoreOf(word) + "");
        }

    }

    public Iterable<String> getAllValidWords(final BoggleBoard board) {

        final int M = board.rows();
        final int N = board.cols();

        final Set<String> words = new HashSet<>();

        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                getValidWords(board, words, new boolean[M][N], i, j, "");
            }
        }

        return words;
    }

    private void getValidWords(final BoggleBoard board, final Set<String> words, final boolean[][] visited,
            final int row, final int col, final String str) {

        if (visited[row][col]) {
            return;
        }

        final char letter = board.getLetter(row, col);

        String word = str + letter;

        if (letter == 'Q') {
            word = word + 'U';
        }

        if (!trieSTObj.hasWordWithPrefix(word)) {
            return;
        }

        if (word.length() > 2 && trieSTObj.contains(word)) {
            words.add(word);
        }

        visited[row][col] = true;

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {

                if (i == 0 && j == 0) {
                    continue;
                }

                if (validate(row + i, col + j, board)) {
                    getValidWords(board, words, visited, row + i, col + j, word);
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

        if (!trieSTObj.contains(word)) {
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