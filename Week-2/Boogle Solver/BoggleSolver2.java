import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class BoggleSolver2 {

    private TrieST<String> trieSTObj;

    private BoggleSolver2() {
        trieSTObj = new TrieST<>();
    }

    public BoggleSolver2(final String[] dict) {
        
        this();

        for (final String word : dict) {
            
            if (word.length() < 3) {
                continue;
            }
            
            trieSTObj.put(word, scoreOf(word) + "");
        }
        
    }

    public Iterable<String> getAllValidWords(final BoggleBoard board) {

        int M = board.rows();
        int N = board.cols();

		Set<String> words = new HashSet<>();

		for (int i = 0; i < M; i++) {
			for (int j = 0; j < N; j++) {
				getValidWords(board, words, new boolean[M][N], i, j, "");
			}
		}

        return words;
    }

    private void getValidWords(BoggleBoard board, Set<String> words,
        boolean[][] visited, int row, int col, String str) {
        
        if (visited[row][col]) {
            return;
        }
        
        char letter = board.getLetter(row, col);
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


    public boolean validate(int i, int j, BoggleBoard board) {
        return (i >= 0 && i < board.rows()) && (j >= 0 && j < board.cols());
    }

    public int scoreOf(final String word) {

        if (word == null) {
            throw new IllegalArgumentException();
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

        File fileObj = null;
        Scanner scannerObj = null;

        try {
            fileObj = new File("G:\\Github\\ADS-2\\Week-2\\Boogle Solver\\dictionary-algs4.txt");
            scannerObj = new Scanner(fileObj);
        } catch (final FileNotFoundException e) {
            e.printStackTrace();
        }

        final StringBuffer words = new StringBuffer();

        while (scannerObj.hasNextLine()) {
            words.append(scannerObj.nextLine()).append(";");
        }

        final String[] dictionary = words.substring(0).split(";");

        final BoggleSolver2 boggleSolverObj = new BoggleSolver2(dictionary);
        final BoggleBoard boggleBoardObj = new BoggleBoard(4, 3);
        
       System.out.println(boggleSolverObj.getAllValidWords(boggleBoardObj));
    }
}