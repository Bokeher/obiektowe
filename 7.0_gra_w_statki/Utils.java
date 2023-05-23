import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    /**
     * Sprawdza czy podana wspolrzedna moze istniec na planszy
     * @param coordinate wspolrzedna do sprawdzenia
     * @return true, jezeli moze istniec inaczej false
     */
    public boolean checkIfValidCoordinate(String coordinate) {
        if(coordinate.equals("")) return false;

        Pattern pattern = Pattern.compile("[a-j]", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(coordinate.charAt(0)+"");
        if(!matcher.find()) return false;

        if(coordinate.length() == 2) {
            pattern = Pattern.compile("[1-9]", Pattern.CASE_INSENSITIVE);
            matcher = pattern.matcher(coordinate.charAt(1)+"");
            if(!matcher.find()) return false;
        } else if(coordinate.length() == 3) {
            if(coordinate.charAt(2) != '0') return false;
        } else return false;

        return true;
    }

    /**
     * Czysci konsole
     */
    public void clearCmd() {
        // for vsc console (might work on linux)
        try {
            System.out.print("\033[H\033[2J"); 
            System.out.flush();

            // for Windows cmd
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (Exception ex) {} 

    }

    /**
     * Prosi uzutkownika o podanie wspolrzednej az do skutku
     * @return wspolrzedna pobrana od uzytkownika
     */
    public String getShootCoordinate() {
        Scanner sc = new Scanner(System.in);
        String coordinate = "";

        while(!checkIfValidCoordinate(coordinate)) {
            System.out.print("Napisz współrzedną strzału: ");
            coordinate = sc.nextLine();
        }

        return coordinate;
    }

    /**
     * zamienia liczbe na rezultat strzalu
     * @param shotResult rezultat strzalu [0-2]
     * @return 0-pudlo, 1-zatopienie, 2-trafienie
     */
    public String getShotText(int shotResult) {
        if(shotResult == 1) {
            // trafinie z zatopieniem
            return "Trafiono statek i zatopiono";
        } else if(shotResult == 2) {
            // trafienie bez zatopienia
            return "Trafiono statek, ale nie zatopiono";
        } else {
            return "Pudło!";
        }
    }

    /**
     * Liczy ilość punktów na podstawie ilości trafień
     * @param board - plansza strzałów
     * @return ilość punktów
     */
    public int countPoints(GameBoard board) {
        String[][] arr = board.getBoard();
        int points = 0;

        for (String[] strings : arr) {
            for (String string : strings) {
                if(string.equals("x")) points++;
            }
        }

        return points;
    }
}
