import edu.princeton.cs.algs4.Picture;
/**
 * Class SeamCarver.
 * @author V. Pavan kumar
 */
public final class SeamCarver {
    /**
     * Picture variable;
     */
    private Picture pictureObj;
    /**
     * Energy Matrix.
     */
    private double[][] energyMatrix;
    /**
     * Argumented - Constructor For Initializing Instance Variables.
     */
    public SeamCarver(final Picture picture) {
        
        if (picture == null) {
            throw new IllegalArgumentException();
        }

        this.pictureObj = new Picture(picture);
        calcEnergyMatrix();
    }

    /**
     * @return picture
     */
    public Picture picture() {
        return new Picture(pictureObj);
    }

    /**
     * @return width of the picture
     */
    public int width() {
        return pictureObj.width();
    }

    /**
     * @return height of the picture
     */
    public int height() {
        return pictureObj.height();
    }

    /**
     * @param x column value
     * @param y row value
     * @return rbg values of the pixel
     */
    private int[] rgb(final int x, final int y) {

        final int rgb = pictureObj.getRGB(x, y);

        final int red = this.getRed(rgb);
        final int green = this.getGreen(rgb);
        final int blue = this.getBlue(rgb);

        return new int[] { red, green, blue };
    }

    /**
     * @param rgb rbg value
     * @return blue value form that tbg
     */
    private int getBlue(final int rgb) {
        return rgb >> 0 & 0xFF;
    }

    /**
     * @param rgb rbg value
     * @return green value from that rbg
     */
    private int getGreen(final int rgb) {
        return rgb >> 8 & 0xFF;
    }

    /**
     * @param rgb rbg vale
     * @return red value from that rbg
     */
    private int getRed(final int rgb) {
        return rgb >> 16 & 0xFF;
    }

    /**
     * @param x column value
     * @param y row value
     * @return delta x2 value
     */
    private double deltaX2(final int x, final int y) {

        final int[] arr1 = this.rgb(x - 1, y);
        final int[] arr2 = this.rgb(x + 1, y);

        return Math.pow(arr1[0] - arr2[0], 2) + Math.pow(arr1[1] - arr2[1], 2) + Math.pow(arr1[2] - arr2[2], 2);
    }

    /**
     * @param x column value
     * @param y row value
     * @return delta y2 value
     */
    private double deltaY2(final int x, final int y) {

        final int[] arr1 = this.rgb(x, y - 1);
        final int[] arr2 = this.rgb(x, y + 1);

        return Math.pow(arr1[0] - arr2[0], 2) + Math.pow(arr1[1] - arr2[1], 2) + Math.pow(arr1[2] - arr2[2], 2);
    }

    /**
     * @param x column value
     * @param y row value
     * @return enrgy of that pixel
     */
    public double energy(final int x, final int y) {

        validate(x, y);

        if (isBorder(x, y)) {
            return 1000.0;
        }

        return Math.sqrt(deltaX2(x, y) + deltaY2(x, y));
    }

    /**
     * @param x column value
     * @param y row value
     * @return returns true if it is border otherwise false
     */
    private boolean isBorder(final int x, final int y) {
        return (x == 0 || x == pictureObj.width() - 1 || y == 0 || y == pictureObj.height() - 1) ? true : false;
    }

    /**
     * @param x column value
     * @param y row value
     */
    private void validate(final int x, final int y) {
        if ((x < 0 || x > pictureObj.width() - 1) || (y < 0 || y > pictureObj.height() - 1)) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Calculates the energy matrix.
     */
    private void calcEnergyMatrix() {
        energyMatrix = new double[height()][width()];
        for (int row = 0; row < height(); row++) {
            for (int col = 0; col < width(); col++) {
                energyMatrix[row][col] = energy(col, row);
            }
        }
    }

    /**
     * @param engMatrix energy matrix
     * @return cummulative energy matrix
     */
    private double[][] getCummErgyMtrx(final double[][] engMatrix) {

        final double[][] cummErgyMtrx = new double[engMatrix.length][engMatrix[0].length];

        for (int i = 0; i < engMatrix[0].length; i++) {
            cummErgyMtrx[0][i] = 1000.0;
        }

        for (int row = 1; row < engMatrix.length; row++) {
            for (int col = 0; col < engMatrix[0].length; col++) {
                if (col == 0) {
                    if (cummErgyMtrx[row - 1][col] < cummErgyMtrx[row - 1][col + 1]) {
                        cummErgyMtrx[row][col] = engMatrix[row][col] + cummErgyMtrx[row - 1][col];
                    } else {
                        cummErgyMtrx[row][col] = engMatrix[row][col] + cummErgyMtrx[row - 1][col + 1];
                    }
                } else if (col == engMatrix[0].length - 1) {
                    if (cummErgyMtrx[row - 1][col] < cummErgyMtrx[row - 1][col - 1]) {
                        cummErgyMtrx[row][col] = engMatrix[row][col] + cummErgyMtrx[row - 1][col];
                    } else {
                        cummErgyMtrx[row][col] = engMatrix[row][col] + cummErgyMtrx[row - 1][col - 1];
                    }
                } else {
                    if (cummErgyMtrx[row - 1][col - 1] <= cummErgyMtrx[row - 1][col]
                            && cummErgyMtrx[row - 1][col - 1] <= cummErgyMtrx[row - 1][col + 1]) {
                        cummErgyMtrx[row][col] = engMatrix[row][col] + cummErgyMtrx[row - 1][col - 1];
                    } else if (cummErgyMtrx[row - 1][col] <= cummErgyMtrx[row - 1][col + 1]) {
                        cummErgyMtrx[row][col] = engMatrix[row][col] + cummErgyMtrx[row - 1][col];
                    } else {
                        cummErgyMtrx[row][col] = engMatrix[row][col] + cummErgyMtrx[row - 1][col + 1];
                    }
                }
            }
        }
        return cummErgyMtrx;
    }

    /**
     * @param m matrix
     * @return transpose of that matrix
     */
    private double[][] transpose(final double[][] m) {
        final double[][] temp = new double[m[0].length][m.length];
        for (int row = 0; row < m.length; row++) {
            for (int col = 0; col < m[0].length; col++) {
                temp[col][row] = m[row][col];
            }
        }
        return temp;
    }

    /**
     * @return vertical seam indices position which have low energy
     */
    public int[] findVerticalSeam() {
        
        if (width() == 1) {
            return new int[height()];
        }

        return getSeam(energyMatrix);
    }

    /**
     * @return horizontal seam indices positions which are to be removed
     */
    public int[] findHorizontalSeam() {
        
        if (height() == 1) {
            return new int[width()];
        }

        return getSeam(transpose(energyMatrix));
    }

    /**
     * @param m matrix
     * @return seam indices
     */
    private int[] getSeam(final double[][] m) {

        final int[] seamIndices = new int[m.length];
        final double[][] cummErgyMtrx = getCummErgyMtrx(m);
        double min = Double.MAX_VALUE;
        int pos = -1;
        final int lastRow = cummErgyMtrx.length - 1;

        for (int i = 0; i < cummErgyMtrx[0].length; i++) {
            if (min > cummErgyMtrx[lastRow][i]) {
                min = cummErgyMtrx[lastRow][i];
                pos = i;
            }
        }

        for (int row = lastRow; row >= 0; row--) {
            
            seamIndices[row] = pos;

            if (row == 0) {
                break;
            }
            
            if (pos == 0) {
                if (cummErgyMtrx[row - 1][pos] > cummErgyMtrx[row - 1][pos + 1]) {
                    pos += 1;
                }
            } else if (pos == m[0].length - 1) {
                if (cummErgyMtrx[row - 1][pos] > cummErgyMtrx[row - 1][pos - 1]) {
                    pos -= 1;
                }
            } else {
                if (cummErgyMtrx[row - 1][pos - 1] <= cummErgyMtrx[row - 1][pos]
                        && cummErgyMtrx[row - 1][pos - 1] <= cummErgyMtrx[row - 1][pos + 1]) {
                    pos -= 1;
                } else if (cummErgyMtrx[row - 1][pos] <= cummErgyMtrx[row - 1][pos + 1]) {

                } else {
                    pos += 1;
                }
            }
        }
        return seamIndices;
    }

    /**
     * @param seam seam indices that are to be removed
     */
    public void removeHorizontalSeam(final int[] seam) {
        
        if (seam == null || height() == 1 || seam.length != width()) {
            throw new IllegalArgumentException();
        }

        for (int i = 0; i < seam.length; i++) {
            if (seam[i] < 0 || seam[i] >= height()) {
                throw new IllegalArgumentException();
            }
        }

        for (int i = 0; i < seam.length - 1; i++) {
            if (seam[i] < 0 || Math.abs(seam[i] - seam[i + 1]) > 1) {
                throw new IllegalArgumentException();
            }
        }

        Picture resizedPicture;
        resizedPicture = new Picture(pictureObj.width(), pictureObj.height() - 1);
        
        for (int i = 0; i < resizedPicture.width(); i++) {
            for (int j = 0; j < resizedPicture.height();) {
                if (j >= seam[i]) {
                    resizedPicture.set(i, j, pictureObj.get(i, ++j));
                } else {
                    resizedPicture.set(i, j, pictureObj.get(i, j++));
                }
            }
        }

        pictureObj = resizedPicture;
        resizedPicture = null;
        calcEnergyMatrix();
    }

    /**
     * @param seam seam indices that are to be removed
     */
    public void removeVerticalSeam(final int[] seam) {

        if (seam == null || width() == 1 || seam.length != height()){
            throw new IllegalArgumentException();
        }

        for (int i = 0; i < seam.length; i++) {
            if (seam[i] < 0 || seam[i] >= width()) {
                throw new IllegalArgumentException();
            }
        }
        for (int i = 0; i < seam.length - 1; i++) {
            if (seam[i] < 0 || Math.abs(seam[i] - seam[i + 1]) > 1) {
                throw new IllegalArgumentException();
            }
        }

        Picture resizedPicture;
        resizedPicture = new Picture(pictureObj.width() - 1, pictureObj.height());

        for (int i = 0; i < resizedPicture.height(); i++) {
            for (int j = 0; j < resizedPicture.width();) {
                if (j >= seam[i]) {
                    resizedPicture.set(j, i, pictureObj.get(++j, i));
                } else {
                    resizedPicture.set(j, i, pictureObj.get(j++, i));
                }
            }
        }
        pictureObj = resizedPicture;
        resizedPicture = null;
        calcEnergyMatrix();
    }
    
    /**
     * @param args Command Line Arguments
     */
    public static void main(final String[] args) {

        // final String path = "G:\\Github\\ADS-2\\Week-2\\seam\\";
        
        // final File folder = new File(path);
        // final File[] ar = folder.listFiles();

        // Picture pictureObj;
        // SeamCarver2 seamCarver2Obj;

        // int count = 0;
        // int inCount = 0;

        // for (int i = 0; i < ar.length; i++) {

        //     if (ar[i].isFile() && ar[i].getName().endsWith("6x5.png")) {
                
        //         inCount++;
                
        //         pictureObj = new Picture(path + ar[i].getName());
        //         seamCarver2Obj = new SeamCarver2(pictureObj);

        //         int[] se = seamCarver2Obj.findVerticalSeam();
        //         String seam = "Vertical";

        //         File f = null;
        //         Scanner scObj = null;

        //         try {
        //             f = new File(path + ar[i].getName().replace(".png", "") + ".printseams.txt");
        //             scObj = new Scanner(f);
        //         } catch (final Exception e) {
        //             count++;
        //             continue;
        //         }

        //         boolean flag = false;
        //         String str = "";

        //         while (scObj.hasNext() && !flag) {
        //             while ((str = scObj.nextLine()).startsWith(seam)) {
        //                 flag = true;
        //                 break;
        //             }
        //         }

        //         String res = Arrays.toString(se);
        //         res = res.replace(",", "").replace("[", "{ ").replace("]", " }");
        //         final String act = str.replace(seam + " seam: ", "");

        //         boolean resflag1 = false;

        //         if (res.equals(act)) {
        //             resflag1 = true;
        //         } else {
        //             System.out.println();
        //             System.out.println("Input : " + ar[i].getName());
        //             System.out.println("Actual : " + seam + " seam " + act);
        //             System.out.println("Obtained : " + seam + " seam " + res);
        //         }

        //         se = seamCarver2Obj.findHorizontalSeam();
        //         seam = "Horizontal";

        //         flag = false;
        //         str = "";

        //         while (scObj.hasNext() && !flag) {
        //             while ((str = scObj.nextLine()).startsWith(seam)) {
        //                 flag = true;
        //                 break;
        //             }
        //         }

        //         String res1 = Arrays.toString(se);
        //         res1 = res1.replace(",", "").replace("[", "{ ").replace("]", " }");
        //         final String act1 = str.replace(seam + " seam: ", "");

        //         boolean resflag2 = false;

        //         if (res1.equals(act1)) {
        //             resflag2 = true;
        //         } else {
        //             System.out.println();
        //             System.out.println("Input : " + ar[i].getName());
        //             System.out.println("Actual : " + seam + " seam " + act1);
        //             System.out.println("Obtained : " + seam + " seam " + res1);
        //         }    
                
        //         if(resflag1 && resflag2) {
        //             count++;
        //         }

        //         scObj.close();

        //         int[] result = seamCarver2Obj.findVerticalSeam();
        //         System.out.println(Arrays.toString(result));
        //         seamCarver2Obj.removeVerticalSeam(result);

        //         result = seamCarver2Obj.findHorizontalSeam();
        //         System.out.println(Arrays.toString(result));
        //         seamCarver2Obj.removeHorizontalSeam(result);
        //     }
        // }

        // if (count != inCount) {
        //     System.out.println("Test cases passed : " + count + " out of " + inCount);
        // } else {
        //     System.out.println("All Test Cases Passed");
        // }
    }
}

