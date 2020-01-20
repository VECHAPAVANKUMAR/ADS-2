import java.util.Arrays;

public class Practice {
    public static void main(String[] args) {
        String str = "From 0, to 668";
        String[] arr = str.replace(",", "").split(" ");
        System.out.println(Arrays.toString(arr));
        System.out.println(arr[1] + "  " + arr[3]) ;
    }
}