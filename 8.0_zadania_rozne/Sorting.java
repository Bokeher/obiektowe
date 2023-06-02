public class Sorting {
    /**
     * Metoda sortująca tablicę przy użyciu algorytmu Bubble Sort.
     * @param array tablica do posortowania
     */
    public void bubbleSort(int[] array) {
        int n = array.length;

        // Pętla zewnętrzna - przechodzi przez całą tablicę
        for (int i = 0; i < n - 1; i++) {

            // Pętla wewnętrzna - porównuje sąsiednie elementy i zamienia je miejscami, jeśli są w niewłaściwej kolejności
            for (int j = 0; j < n - i - 1; j++) {

                // Sprawdza, czy elementy są w niewłaściwej kolejności
                if (array[j] > array[j + 1]) {
                    
                    // Zamień elementy miejscami
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
        }
    }
    /**
     * Metoda sortująca tablicę przy użyciu algorytmu Quick Sort.
     * @param array tablica do posortowania
     * @param low   indeks początkowy
     * @param high  indeks końcowy
     */
    public void quickSort(int[] array, int low, int high) {
        if (low < high) {
            // Dzielimy tablicę i otrzymujemy indeks podziału
            int pivot = partition(array, low, high);

            // Wywołujemy rekurencyjnie quickSort dla lewej i prawej części podziału
            quickSort(array, low, pivot - 1);
            quickSort(array, pivot + 1, high);
        }
    }

    /**
     * Metoda dzieląca tablicę na podstawie wybranego elementu (pivot) i zwracająca indeks podziału.
     * @param array tablica do podziału
     * @param low   indeks początkowy
     * @param high  indeks końcowy
     * @return indeks podziału
     */
    public int partition(int[] array, int low, int high) {
        // Wybieramy ostatni element jako pivot
        int pivot = array[high];

        // Indeks elementu mniejszego od pivot
        int i = low - 1;

        for (int j = low; j < high; j++) {
            // Jeśli aktualny element jest mniejszy lub równy pivot
            if (array[j] <= pivot) {
                i++;

                // Zamieniamy elementy o indeksach i oraz j
                swap(array, i, j);
            }
        }

        // Zamieniamy pivot z elementem o indeksie i+1
        swap(array, i + 1, high);

        return i + 1;
    }

    /**
     * Metoda pomocnicza do zamiany miejscami dwóch elementów w tablicy.
     * @param array tablica
     * @param i     indeks pierwszego elementu
     * @param j     indeks drugiego elementu
     */
    public static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
    
    /**
     * Sortuje tablicę liczb przy użyciu algorytmu sortowania przez wstawianie.
     * @param array tablica liczb do posortowania
     */
    public void insertionSort(int[] array) {
        int n = array.length;
        for (int i = 1; i < n; i++) {
            int key = array[i]; // bieżący element do wstawienia
            int j = i - 1;

            // Przesuń elementy większe od key o jedną pozycję w prawo
            while (j >= 0 && array[j] > key) {
                array[j + 1] = array[j];
                j--;
            }

            array[j + 1] = key; // Wstaw bieżący element na właściwe miejsce
        }
    }
}
