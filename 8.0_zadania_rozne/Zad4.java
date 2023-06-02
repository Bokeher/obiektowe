import java.util.Random;

public class Zad4 {
    public static void main(String[] args) {
        // tworzy tablice
        int[] arr1 = new int[1000];
        int[] arr2 = new int[1000];
        int[] arr3 = new int[1000];

        // wypelnia tablice
        fillArray(arr1);
        fillArray(arr2);
        fillArray(arr3);

        // klasa do sortowania
        Sorting sort = new Sorting();

        // bubble sort
        System.out.println("Bubble sort: \nTablica przed posortowaniem: "+arrayToString(arr1));
        sort.bubbleSort(arr1);
        System.out.println("Tablica po sortowaniu: "+arrayToString(arr1));

        // quick sort
        System.out.println("\nQuick sort: \nTablica przed posortowaniem: "+arrayToString(arr2));
        sort.quickSort(arr2, 0, arr2.length-1);
        System.out.println("Tablica po sortowaniu: "+arrayToString(arr2));

        // insertion sort
        System.out.println("\nInsertion sort: \nTablica przed posortowaniem: "+arrayToString(arr3));
        sort.insertionSort(arr3);
        System.out.println("Tablica po sortowaniu: "+arrayToString(arr3));
    }

    /**
     * Wyplenia tablice liczbami z zakresu [-100;100]
     * @param arr tablica do wypelnienia
     */
    public static void fillArray(int[] arr) {
        Random r = new Random();
        for (int i = 0; i < arr.length; i++) {
            // (max - min + 1) - min
            arr[i] = r.nextInt(100-(-100)+1)-100;
        }
    }

    /**
     * Zwraca tablice jako elementy odzielone spacja w Stringu
     * @param arr tablica do wyswietlenia
     * @return elementy tablicy odzielona spacjami
     */
    public static String arrayToString(int[] arr) {
        String res = "";

        for (int i = 0; i < arr.length; i++) {
            res += arr[i]+" ";
        }

        return res;
    }
}
