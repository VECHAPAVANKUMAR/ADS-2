import edu.princeton.cs.algs4.Picture;
import java.awt.Color;
import java.io.File;

public class SeamCarver {

    private Picture pictureObj;

    public SeamCarver(final Picture p) {
        this.pictureObj = p;
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

        final Color colorObj = pictureObj.get(x, y);

        final int red = colorObj.getRed();
        final int green = colorObj.getGreen();
        final int blue = colorObj.getBlue();

        return new int[] { red, green, blue };
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
            return 1000;
        } 
            
        return Math.sqrt(deltaX2(x, y) + deltaY2(x, y));
    }

    private boolean isBorder(int x, int y) {
        return (x == 0 || x == pictureObj.width() - 1 || y == 0 || y == pictureObj.height() - 1) ? true : false;
    }

    private void validate(final int x, final int y) {
        if ((x < 0 || x > pictureObj.width() - 1) || (y < 0 || y > pictureObj.height() - 1)) {
            throw new IllegalArgumentException();
        }
    }

    public static void main(final String[] args) {

        String path = "G:\\Github\\ADS-2\\Week-2\\Day-1\\Assignment-1\\seam\\";
        final File folder = new File(path);
        final File[] listOfFiles = folder.listFiles();
        
        for (int i = 0; i < listOfFiles.length; i++) {
            
            if (listOfFiles[i].isFile() && listOfFiles[i].getName().endsWith("png")) {

                System.out.println("Input Picture : " + listOfFiles[i].getName());

                final Picture p = new Picture(path + listOfFiles[i].getName());
                final SeamCarver seamCarverObj = new SeamCarver(p);

                System.out.println("Height : " + seamCarverObj.height());
                System.out.println("Width : " + seamCarverObj.width());

                for (int col = 0; col < seamCarverObj.width(); col++) {
                    for (int row = 0; row < seamCarverObj.height(); row++) {
                        System.out.println("Pixel : " + col + " " + row + " " + seamCarverObj.energy(col, row));
                    }
                }
            }
        }
    }
}

