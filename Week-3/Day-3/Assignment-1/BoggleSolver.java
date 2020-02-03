import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import edu.princeton.cs.algs4.TrieST;

public class BoggleSolver {

    private TrieST<String> trieSTObj;

    private static int[] row = { -1, -1, -1, 0, 1, 0, 1, 1 };
	private static int[] col = { -1, 1, 0, -1, -1, 1, 0, 1 };

    private BoggleSolver() {
        trieSTObj = new TrieST<>();
    }

    public BoggleSolver(final String[] dict) {
        
        this();

        for (final String word : dict) {
            
            if (word.length() < 3) {
                continue;
            }
            
            trieSTObj.put(word, scoreOf(word) + "");
        }
        
        System.out.println(trieSTObj.size());
    }

    public Iterable<String> getAllValidWords(final BoggleBoard board) {

        int M = board.rows();
        int N = board.cols();

		boolean[][] processed = new boolean[M][N];

		Set<String> words = new HashSet<>();

		for (int i = 0; i < M; i++) {
			for (int j = 0; j < N; j++) {
				getValidWords(board, words, processed, i, j, "");
			}
		}

		for (String word: trieSTObj.keys()) {

			if (words.contains(word)) {
				System.out.println(word);
			}
        }
        
        return words;
    }

    private static void getValidWords(BoggleBoard board, Set<String> words,
        boolean[][] visited, int i, int j, String str) {
        
        if (visited[i][j]) {
            return;
        }

        char letter = board.getLetter(i, j); 
        
        str = str + letter;

        visited[i][j] = true;

        words.add(str);

        for (int k = 0; k < 8; k++) {
            if (validate(i + row[k], j + col[k], board)) {
                getValidWords(board, words, visited, i + row[k], j + col[k], str);
            }
        }

        visited[i][j] = false;
    }


    public static boolean validate(int i, int j, BoggleBoard board) {
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
            System.out.println(boggleBoardObj);
            System.out.println();  
            System.out.println(boggleSolverObj.getAllValidWords(boggleBoardObj));
            System.out.println("");    
            }
        }
        scannerObj.close();
    }
}