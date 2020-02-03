import java.util.Scanner;

public class Snail {
    public static void main(String[] args) {

        // Scanner scannerObj = new Scanner(System.in);

        // while (scannerObj.hasNextLine()) {

            // double h = scannerObj.nextInt();
            // double u = scannerObj.nextInt();
            // double d = scannerObj.nextInt();
            // double f = scannerObj.nextInt();

            double h = Double.parseDouble(args[0]);
            double u = Double.parseDouble(args[1]);
            double d = Double.parseDouble(args[2]);
            double f = Double.parseDouble(args[3]);

            // if (h == 0 && u == 0 && d == 0 && f == 0) {
            //     break;
            // }

            double initialHeight = 0;
            double heightAfterClimbing = u;
            double loss = ((f / 100.0) * u);
            boolean flag = true;
            
            int day = 1;

            while (h >= heightAfterClimbing) {

                initialHeight = heightAfterClimbing - d;

                // System.out.println("A : " + initialHeight + " " + u + " " + heightAfterClimbing);

                if (initialHeight < 0) {
                    flag = false;
                    break;
                }
                
                day++;

                u = u - loss;
                // System.out.println("B : " + initialHeight + " " + u + " " + heightAfterClimbing);

                if (u < 0) {
                    flag = false;
                    break;
                }

                heightAfterClimbing = u + initialHeight;
  
                // System.out.println("C : " + initialHeight + " " + u + " " + heightAfterClimbing);

                if (heightAfterClimbing < 0) {
                    flag = false;
                    break;
                }
                
            }
            if (flag) {
                System.out.println("success on day " + day);
            } else {
                System.out.println("failure on day " + day);
            }
        }    
    // }
}