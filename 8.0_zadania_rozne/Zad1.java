import java.util.Random;

class Zad1 {
    public static Random random;
    public static void main(String[] args) {
        random = new Random();
        // stworzenie tablicy
        int[] array = new int[100];

        // wypelnienie tablicy
        array = fillArray(array, 0);

        // wypisanie tablicy
        System.out.print("Elementy tablicy: ");
        for (int i : array) {
            System.out.print(i+" ");
        }
    }
    /**
     * Wypelnia tablice poprzez rekurencje
     * @param array - tablica ktora ma zostac wypelniona
     * @param index - poczatkowy indeks od ktorego ma zaczac
     * @return zwraca wypelniona tablice
     */
    public static int[] fillArray(int[] array, int index) {
        if(index < array.length) {
            // generowanie pseudolosowej liczby z zakresu [1;20]
            
            int randomNumb = random.nextInt(20)+1;

            // wstawienie wartosci do tablicy o indeksie 'index'
            array[index] = randomNumb;

            // wywolanie kolejnej funkcji ktora wypelni tablice o indeksie wiekszym o 1
            fillArray(array, index+1);
        }
        return array;
    }
}