public class Zad2 {
    public static void main(String[] args) {
        MyArrayUtils arrayUtils = new MyArrayUtils();

        System.out.println("Najwieksza liczba w tablicy: "+arrayUtils.findMax());

        System.out.println("Indeks pierwszego wystapienia liczby 10: "+arrayUtils.findIndex(10));

        System.out.println("Ilosc wystapien liczb w formacie [liczba:iloscWystapien]: "+arrayUtils.countDuplicate());
    }
}
