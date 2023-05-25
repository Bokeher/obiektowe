import java.util.Random;

public class Zad4 {
    public static void main(String[] args) {
        // tworzy tablice
        int[] arr = new int[1000];

        // wypelnia tablice
        fillArray(arr);

        // TODO: all sorts
        
    }

    public static void fillArray(int[] arr) {
        Random r = new Random();
        for (int i = 0; i < arr.length; i++) {
            // (max - min + 1) - min
            arr[i] = r.nextInt(100-(-100)+1)-100;
        }
    }
}
