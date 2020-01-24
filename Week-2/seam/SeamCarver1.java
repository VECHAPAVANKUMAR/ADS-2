import edu.princeton.cs.algs4.Picture;
import java.io.File;
import java.util.Arrays;

public class SeamCarver1 {

    private final Picture pictureObj;
    private double[][] energyMatrix;
    private double[][] cummlativeErgyMtrx;

    public SeamCarver1(final Picture p) {
        this.pictureObj = p;
        this.calcEnergyMatrix(pictureObj);
    }

    public Picture picture() {
        return this.pictureObj;
    }

    public int width() {
        return this.pictureObj.width();
    }

    public int height() {
        return this.pictureObj.height();
    }

    private int[] rgb(final int x, final int y) {

        final int rgb = pictureObj.getRGB(x, y);

        final int red = this.getRed(rgb);
        final int green = this.getGreen(rgb);
        final int blue = this.getBlue(rgb);

        return new int[] { red, green, blue };
    }

    private int getBlue(final int rgb) {
        return rgb >> 16 & 0xFF;
    }

    private int getGreen(final int rgb) {
        return rgb >> 8 & 0xFF;
    }

    private int getRed(final int rgb) {
        return rgb >> 0 & 0xFF;
    }

    private double deltaX2(final int x, final int y) {

        final int[] arr1 = this.rgb(x - 1, y);
        final int[] arr2 = this.rgb(x + 1, y);

        return Math.pow(arr1[0] - arr2[0], 2) + Math.pow(arr1[1] - arr2[1], 2) + Math.pow(arr1[2] - arr2[2], 2);
    }

    private double deltaY2(final int x, final int y) {

        final int[] arr1 = this.rgb(x, y - 1);
        final int[] arr2 = this.rgb(x, y + 1);

        return Math.pow(arr1[0] - arr2[0], 2) + Math.pow(arr1[1] - arr2[1], 2) + Math.pow(arr1[2] - arr2[2], 2);
    }

    public double energy(final int x, final int y) {

        validate(x, y);
        return energyMatrix[x][y];

    }

    public int[] findVerticalSeam() {
        return findSeam(cummlativeErgyMtrx);
    }

    public int[] findHorizontalSeam() {
        return findSeam(transpose(cummlativeErgyMtrx));
    }

    private double[][] transpose(final double[][] m) {
        final double[][] temp = new double[m[0].length][m.length];
        for (int row = 0; row < m.length; row++)
            for (int col = 0; col < m[0].length; col++)
                temp[col][row] = m[row][col];
        return temp;
    }

    private boolean isBorder(final int x, final int y) {
        return (x == 0 || x == pictureObj.height() - 1 || y == 0 || y == pictureObj.width() - 1) ? true : false;
    }

    private void validate(final int x, final int y) {
        if ((x < 0 || x > pictureObj.width() - 1) || (y < 0 || y > pictureObj.height() - 1)) {
            throw new IllegalArgumentException();
        }
    }

    private void calcEnergyMatrix(final Picture p) {

        this.energyMatrix = new double[p.height()][p.width()];
        this.cummlativeErgyMtrx = new double[p.height()][p.width()];

        for (int col = 0; col < p.height(); col++) {
            for (int row = 0; row < p.width(); row++) {
                if (isBorder(col, row)) {
                    this.energyMatrix[col][row] = 1000;
                } else {
                    this.energyMatrix[col][row] = Math.sqrt(deltaX2(col, row) + deltaY2(col, row));
                }
            }
        }

        // for (int col = 0; col < p.height(); col++) {
        //     for (int row = 0; row < p.width(); row++) {
        //         if (col == 0) {
        //             this.cummlativeErgyMtrx[col][row] = this.energyMatrix[col][row];
        //         } else if (row == 0) {
        //             System.out.println("2 " + row);
        //             this.cummlativeErgyMtrx[col][row] = this.energyMatrix[col][row]
        //                     + Math.min(this.energyMatrix[col - 1][row], this.energyMatrix[col - 1][row + 1]);
        //         } else if (row == p.width() - 1) {
        //             System.out.println("3 " + row);
        //             this.cummlativeErgyMtrx[col][row] = this.energyMatrix[col][row]
        //                     + Math.min(this.energyMatrix[col - 1][row], this.energyMatrix[col - 1][row - 1]);
        //         } else {
        //             System.out.println("4 " + row);
        //             final double minEle = Math.min(this.energyMatrix[col - 1][row - 1],
        //                             Math.min(this.energyMatrix[col - 1][row], this.energyMatrix[col - 1][row + 1]));
        //             this.cummlativeErgyMtrx[col][row] = this.energyMatrix[col][row] + minEle;
        //         }
        //     }
        // }

    }

    // private void findSeam(int col, int row, List<Double> minErgy) {
    //     if (row == 0) {
    //         minErgy.add(row, energyMatrix[col][row]);
    //     } else {
    //         if (energyMatrix[col][row] <= minErgy.get(minErgy.size())) {
    //             minErgy.add(row, energyMatrix[col][row]);
    //         } else {
    //             minErgy.remove(minErgy.size());
    //         }
    //     }        
    // }

    private int[] findSeam(double[][] cEM) {

        System.out.println("cem ");
                
        double[][] m = cEM;

        for (int j = 0; j < m[0].length; j++) {
            System.out.println(Arrays.toString(m[j]));
        }

        int[] seamIdxs = new int[pictureObj.height()];
        int idx = -1;

        System.out.println("M 0 L " + m[0].length);
        System.out.println("M L " + m.length);

        // for (int i = 0; i < m[0].length; i++) {
        //     double minErgy = m[i][0];            
        //     for (int j = 0; j < m.length; j++) {
        //         if (m[i][j] < minErgy) {
        //             minErgy = m[i][j];
        //             idx = j;
        //         }
        //     }
        //     if (idx == -1) {
        //         seamIdxs[i] = 0;
        //     } 
        //     if (i == 0) {
        //         System.out.println("if " + i);
        //         seamIdxs[i] =  1;
        //     } else {
        //         System.out.println("else " + i);
        //         seamIdxs[i] =  idx;
        //     }
            // seamIdxs[i] = idx;
            
        int[] minVer = new int[pictureObj.height()];
        int jref = 0;
        for (int i = pictureObj.height() - 1; i >= 0; i--) {
            double min_energy = Double.MAX_VALUE;
            if (i == pictureObj.height() - 1) {
                for (int j = 0; j < pictureObj.width(); j++) {
                    if (m[j][i] < min_energy) {
                        min_energy = m[j][i];
                        jref = j;
                    }
                }
            } else {
                if (jref == 0) {
                    if (m[jref][i] > m[jref + 1][i]) {
                        jref = jref + 1;
                    }
                } else if (jref == pictureObj.width() - 1) {
                    if (m[jref][i] > m[jref - 1][i]) {
                        jref = jref - 1;
                    }
                } else {
                    if ((m[jref][i] > m[jref - 1][i])
                            && (m[jref + 1][i] > m[jref - 1][i])) {
                        jref = jref - 1;
                    } else if ((m[jref][i] > m[jref + 1][i])
                            && (m[jref - 1][i] > m[jref + 1][i])) {
                        jref = jref + 1;
                    }
                }
            }
            minVer[i] = jref;

        }
        minVer[0]--;
        return minVer;
        }

    //     return seamIdxs;
    // }

    public static void main(final String[] args) {

        final String path = "G:\\Github\\ADS-2\\Week-2\\seam\\";
        final File folder = new File(path);
        final File[] listOfFiles = folder.listFiles();
        
        // for (int i = 0; i < listOfFiles.length; i++) {
            
        //     if (listOfFiles[i].isFile() && listOfFiles[i].getName().endsWith("png")) {

        //         System.out.println("Input Picture : " + listOfFiles[i].getName());

        //         // final Picture p = new Picture(path + listOfFiles[i].getName());
        //         final Picture p = new Picture(path + "3x4.png");

        //         final SeamCarver seamCarverObj = new SeamCarver(p);

        //         System.out.println("Height : " + seamCarverObj.height());
        //         System.out.println("Width : " + seamCarverObj.width());

        //         for (int col = 0; col < seamCarverObj.width(); col++) {
        //             for (int row = 0; row < seamCarverObj.height(); row++) {
        //                 System.out.println("Energy of Pixel : " + col + " " + row + " : " + seamCarverObj.energy(col, row));
        //             }
        //         }
        //         System.out.println("Vertical Seam Idxs : " + Arrays.toString(seamCarverObj.findVerticalSeam()));
        //     }
        // }

        final Picture p = new Picture(path + "4x6.png");

        final SeamCarver1 seamCarverObj = new SeamCarver1(p);
        System.out.println();
        for (int i = 0; i < seamCarverObj.energyMatrix.length; i++) {
            System.out.println(Arrays.toString(seamCarverObj.energyMatrix[i]));
        }
        System.out.println("A ");
        System.out.println("Width : " + seamCarverObj.width());
        System.out.println("Height : " + seamCarverObj.height());
        System.out.println("Vertical Seam Idxs : " + Arrays.toString(seamCarverObj.findVerticalSeam()));
        System.out.println("Energy : " + seamCarverObj.energy(1, 3));
        System.out.println("Vertical Seam Idxs : " + Arrays.toString(seamCarverObj.findHorizontalSeam()));


    }
}

