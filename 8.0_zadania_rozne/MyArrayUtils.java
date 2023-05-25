import java.util.HashMap;
import java.util.Random;

public class MyArrayUtils {
    private int[] arr = new int[1000];
    private String res = "";

    public MyArrayUtils() {
        Random r = new Random();
        for (int i = 0; i < arr.length; i++) {
            // (max - min + 1) - min
            arr[i] = r.nextInt(100-(-100)+1)-100;
        }
    }

    /**
     * Znajduje najwieksza liczbe w tablicy
     * @return zwraca najwieksza liczbe w tablicy
     */
    public int findMax() {
        // liczba mniejsza od minimalnej generowanej przez program
        int max = -101;

        for (int i : arr) {
            // jezeli sprawdzana liczba jest wieksza od obecnego maxa to przypisuje ja do maxa
            if(i > max) max = i;
        }

        return max;
    }

    /**
     * Szuka indeks pierwszego wystapienia liczby w tablicy
     * @param numb - szukana liczba
     * @return indeks pierwszego wystapienia tej liczby (-1 jak nie ma takiej liczby)
     */
    public int findIndex(int numb) {
        for (int i = 0; i < arr.length; i++) {
            if(numb == arr[i]) return i;
        }

        return -1;
    }

    /**
     * Liczy duplikaty
     * @return zwraca mape przerobiona na String (mapa zawiera liczby oraz ilosc ich wystapien w tablicy)
     */
    public String countDuplicate() {
        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
        int key;

        // iteruje poprzez cala mape 
        for (int i = 0; i < arr.length; i++) {
            key = arr[i];
            if(map.get(key) == null) {
                // jezeli nie ma klucza mapy arr[i] to tworzy taki
                map.put(key, 1);
            } else {
                // jezeli jest klucz to inkrementuje jego wartosc
                map.put(key, map.get(key) + 1);
            }
        }

        // dopisuje do zmiennej res wszystkie elementy mapy
        map.forEach((key2, value) -> {
            res += key2 + ":" + value+" razy\n";
        });

        return res;
    }
}
