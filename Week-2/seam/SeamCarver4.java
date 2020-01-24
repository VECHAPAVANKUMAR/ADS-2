import edu.princeton.cs.algs4.Picture;
// import java.io.File;
// import java.util.Arrays;
// import java.util.Scanner;

public final class SeamCarver4 {

    private Picture pictureObj;
    private double[][] energyMatrix;

    public SeamCarver4(final Picture picture) {
        
        if (picture == null) {
            throw new IllegalArgumentException();
        }

        this.pictureObj = new Picture(picture);
        calcEnergyMatrix();
    }

    public Picture picture() {
        return pictureObj;
    }

    public int width() {
        return pictureObj.width();
    }

    public int height() {
        return pictureObj.height();
    }

    private int[] rgb(final int x, final int y) {

        final int rgb = pictureObj.getRGB(x, y);

        final int red = this.getRed(rgb);
        final int green = this.getGreen(rgb);
        final int blue = this.getBlue(rgb);

        return new int[] { red, green, blue };
    }

    private int getBlue(final int rgb) {
        return rgb >> 0 & 0xFF;
    }

    private int getGreen(final int rgb) {
        return rgb >> 8 & 0xFF;
    }

    private int getRed(final int rgb) {
        return rgb >> 16 & 0xFF;
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

        if (isBorder(x, y)) {
            return 1000.0;
        }

        return Math.sqrt(deltaX2(x, y) + deltaY2(x, y));
    }

    private boolean isBorder(final int x, final int y) {
        return (x == 0 || x == pictureObj.width() - 1 || y == 0 || y == pictureObj.height() - 1) ? true : false;
    }

    private void validate(final int x, final int y) {
        if ((x < 0 || x > pictureObj.width() - 1) || (y < 0 || y > pictureObj.height() - 1)) {
            throw new IllegalArgumentException();
        }
    }

    private void calcEnergyMatrix() {
        energyMatrix = new double[height()][width()];
        for (int row = 0; row < height(); row++) {
            for (int col = 0; col < width(); col++) {
                energyMatrix[row][col] = energy(col, row);
            }
        }
    }

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

    private double[][] transpose(final double[][] m) {
        final double[][] temp = new double[m[0].length][m.length];
        for (int row = 0; row < m.length; row++) {
            for (int col = 0; col < m[0].length; col++) {
                temp[col][row] = m[row][col];
            }
        }
        return temp;
    }

    public int[] findVerticalSeam() {
        
        if (width() == 1) {
            // return null;
            throw new IllegalArgumentException();
        }

        return getSeam(energyMatrix);
    }

    public int[] findHorizontalSeam() {
        
        if (height() == 1) {
            // return null;
            throw new IllegalArgumentException();
        }

        return getSeam(transpose(energyMatrix));
    }

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

    public void removeHorizontalSeam(final int[] seam) {
        
        if (seam == null || seam.length <= 0  || seam.length > width()) {
            throw new IllegalArgumentException();
        }

        // System.out.println("Original Picture");
        // System.out.println(pictureObj);

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
        // System.out.println("Resized Picture");
        // System.out.println(pictureObj);
        calcEnergyMatrix();
    }

    public void removeVerticalSeam(final int[] seam) {

        if (seam == null || seam.length <= 0 || seam.length > height()){
            throw new IllegalArgumentException();
        }

        // System.out.println("Original Picture");
        // System.out.println(pictureObj);

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
        // System.out.println("Resized Picture");
        // System.out.println(pictureObj);

        calcEnergyMatrix();
    }
    
    // private void removeSeam(final int[] seam) {

    //     final Picture pictureObjCopy;

    //     System.out.println("SEAM : " + remove);
    //     int l;
    //     int w;
    //     if (remove == 'v') {
    //         pictureObjCopy = new Picture(pictureObj.width() - 1, pictureObj.height());
    //         l = pictureObjCopy.height();
    //         w = pictureObjCopy.width();
    //     } else {
    //         pictureObjCopy = new Picture(pictureObj.width(), pictureObj.height() - 1);
    //         l = pictureObjCopy.width();
    //         w = pictureObjCopy.height();
    //     }
    //     System.out.println();
    //     System.out.println(pictureObj);
    //     System.out.println();
        
    //     for (int i = 0; i < pictureObjCopy.height(); i++) {
    //         for (int j = 0; j < pictureObjCopy.width();) {
    //             if (remove == 'h') {
    //                 i = i + j - (j = i);
    //             }
    //             if (j >= seam[i]) {
    //                 pictureObjCopy.set(j, i, pictureObj.get(++j, i));
    //             } else {
    //                 pictureObjCopy.set(j, i, pictureObj.get(j++, i));
    //             }
    //         }
    //     }
    //     System.out.println("Modified Picture Height : " + pictureObjCopy.height());
    //     System.out.println("Modified Picture Width : " + pictureObjCopy.width());
    //     System.out.println("Modified Picture RBG : ");
    //     System.out.println();
    //     System.out.println(pictureObjCopy);
    //     calcEnergyMatrix();
    // }

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

