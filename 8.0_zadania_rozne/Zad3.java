import java.util.Scanner;

public class Zad3 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        // prosi o tekst do liczenia wystapein
        System.out.print("Podaj tekst do policzenia wystapień: ");
        String text = sc.nextLine();

        // liczy wystapienia
        int[] encounters = countEncounters(text);

        // wyswietla wystapienia
        System.out.println(encounterToString(encounters));
    }   

    /**
     * Liczy wystapienia samoglosek, spoglosek, cyfr oraz innych znakow
     * @param text tekst do sprawdzenia wystapien
     * @return tablica zawierajaca wystapienia: 
     *      samoglosek na indeksie[0]
     *      spolglosek na indeksie[1]
     *      cyfr na indeksie[2]
     *      innych znakow na indeksie[3]
     */
    private static int[] countEncounters(String text) {
        // zmienienia litery na male
        text = text.toLowerCase();

        // slowniki zawierajace znaki dla konkretnych kategori
        final String vowelsDictionary = "aąeęiouóy";
        final String consonantsDictionary = "bcćdfghjklmnprstwyzżź";
        final String numbersDictionary = "0123456789";

        // liczniki dla konkretnych kategori
        int vowelsCounter = 0;
        int consonantsCounter = 0;
        int numbersCounter = 0;
        int otherCounter = 0;

        // liczy wystapienia 
        for (int i = 0; i < text.length(); i++) {
            String character = text.substring(i, i+1);
            if(consonantsDictionary.contains(character)) {
                consonantsCounter++;
            } else if(vowelsDictionary.contains(character)) {
                vowelsCounter++;
            } else if(numbersDictionary.contains(character)) {
                numbersCounter++;
            } else {
                otherCounter++;
            }
        }

        // tworzy tablice zawierajaca wynik funkcji
        int[] res = {vowelsCounter, consonantsCounter, numbersCounter, otherCounter};
        
        return res;
    }    

    /**
     * Zamienia tablice z wystapieniami na tekst do wyswietlenia
     * @param encounters tablica z wystapieniami
     * @return tekst zawierajacy informacje o wystapieniach
     */
    private static String encounterToString(int[] encounters) {
        String res = "Wystąpienia: \n"
            +"Ilość samogłosek: "+encounters[0]
            +"\nIlość spółgłosek: "+encounters[1]
            +"\nIlość cyfr: "+encounters[2]
            +"\nInne znaki: "+encounters[3];

        return res;
    }
}
