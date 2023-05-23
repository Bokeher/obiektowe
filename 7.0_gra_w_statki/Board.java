import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Board implements GameBoard {
    private Utils utils = new Utils();
    
    public final String HIT_SIGN = "x";
    public final String SHIP_SIGN = "O";
    public final String MISS_SIGN = "■";
    public final String EMPTY_SIGN = "~";

    private String[][] board = new String[10][10];  

    // wypelnia tablice znakami pustego pola
    public Board() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                board[i][j] = EMPTY_SIGN;
            }
        }
    }

    // konstruktor aby stworzyc obiekt na podstawie String pobranego od client'a
    public Board(String boardInString) {
        String[] collumns = boardInString.split("\n");
        for (int i = 0; i < collumns.length; i++) {
            String[] rows = collumns[i].split(" ");
            for (int j = 0; j < collumns.length; j++) {
                board[i][j] = rows[j];
            }
        }
    }

    /**
     * Prosi uzytkownika o rozstawienie wszystich statkow oraz je zapisuje na planszy
     */
    public void placeAllShips() {
        utils.clearCmd();

        Map<String, Integer> remainingShips = new LinkedHashMap<String, Integer>();
        remainingShips.put("Czteromasztowce", 1);
        remainingShips.put("Trzymasztowce", 2);
        remainingShips.put("Dwumasztowce", 3);
        remainingShips.put("Jednomasztowce", 4);

        Scanner sc = new Scanner(System.in);

        List<String> keys = new ArrayList<>(remainingShips.keySet());
        List<Integer> values = new ArrayList<>(remainingShips.values());

        System.out.println("  Rozstawianie statków  ");
        for (int i = 0; i < remainingShips.size(); i++) {
            String key = keys.get(i);
            Integer value = values.get(i);

            // i = 0 => shipLength = |-4|, i = 1 => shipLength = |-3|, itd.
            int shipLength = Math.abs(i-4);

            String shipName = key.toLowerCase().substring(0, key.length()-2)+"y";
            while(value > 0) {
                System.out.println(toString());
                System.out.println("Wybierz, gdzie ma stać statek: "+shipName);
                System.out.print("Podaj pierwszą współrzędną: ");
                String firstCord = sc.nextLine();
                
                String secndCord;
                if(i == 3) {
                    secndCord = firstCord;
                } else {
                    System.out.print("Podaj drugą współrzędną: ");
                    secndCord = sc.nextLine();
                }

                boolean shipLegallyPlaced = placeShip(shipLength, firstCord, secndCord);
                if(shipLegallyPlaced) {
                    value--;
                } else {
                    System.out.println("Niepoprawne dane");
                }
            }
            break;
        }
        System.out.println("Oczekiwanie na rozmieszczenie statków przez przeciwnika...");
    }


    /**
     * Stawia statke na planszy
     * @param shipLength dlugosc statku [1-4]
     * @param firstCoordinate pierwsza wspolrzedna statku
     * @param secondCoordinate druga wspolrzedna statku (ostatnia)
     * @return true, jezeli sie udalo inaczej false
     */
    public boolean placeShip(int shipLength, String firstCoordinate, String secondCoordinate) {
        if(!utils.checkIfValidCoordinate(firstCoordinate)) return false;
        if(!utils.checkIfValidCoordinate(secondCoordinate)) return false;

        boolean lettersChange = false;
        int firstNumb;
        int secondNumb;
        if(firstCoordinate.charAt(0) == secondCoordinate.charAt(0)) {
            // jezeli zmienia sie liczba
            firstNumb = Integer.parseInt(firstCoordinate.substring(1));
            secondNumb = Integer.parseInt(secondCoordinate.substring(1));
        } else {
            // jezeli zmienia sie litera
            lettersChange = true;
            firstNumb = letterToInt(firstCoordinate);
            secondNumb = letterToInt(secondCoordinate);
        }

        if(Math.abs(secondNumb - firstNumb) != shipLength-1) return false;
        String beginCoordinate = firstCoordinate;
        if(secondNumb - firstNumb < 0) {
            beginCoordinate = secondCoordinate;
        }

        // System.out.println(beginCoordinate);
        int x;
        int y;

        // sprawdza czy sa jakies statki w poblizu (nie moga byc zgodnie z zasadami gry)
        for(int i = 0; i < shipLength; i++) {
            if(lettersChange) {
                x = firstNumb-1+i;
                y = Integer.parseInt(beginCoordinate.substring(1))-1;
            } else {
                x = letterToInt(beginCoordinate.substring(0, 1))-1;
                y = firstNumb-1+i;
            }
            
            if(!noNearbyShips(x, y)) return false;
        }

        // stawianie statkow na planszy
        for(int i = 0; i < shipLength; i++) {
            if(lettersChange) {
                x = firstNumb-1+i;
                y = Integer.parseInt(beginCoordinate.substring(1))-1;
            } else {
                x = letterToInt(beginCoordinate.substring(0, 1))-1;
                y = firstNumb-1+i;
            }
            // System.out.println("X: "+x+" Y: "+y);

            board[y][x] = SHIP_SIGN;
        }

        return true;
    }

    /**
     * Sprawdza czy nie ma znaku statku w poblizu, co uniemozliwialoby rozgrywke przez zasady gry
     * @param x pierwsza wspolrzedna
     * @param y druga wsporzedna
     * @return true jezeli nie ma statkow w poblizu, false jezeli sa
     */
    private boolean noNearbyShips(int x, int y) {
        if(y>0 && board[y-1][x] != EMPTY_SIGN) return false;
        if(y<9 && board[y+1][x] != EMPTY_SIGN) return false;
        
        if(x>0 && board[y][x-1] != EMPTY_SIGN) return false;
        if(x<9 && board[y][x+1] != EMPTY_SIGN) return false;

        return true;
    }

    @Override
    public String toString() {
        String res = "   A B C D E F G H I J \n";

        for (int i = 0; i < board.length; i++) {
            res += (i+1)+" ";
            if(i < 9) res += " ";

            for (int j = 0; j < board.length; j++) {
                res += board[i][j]+" ";
            }
            res += "\n";
        }

        return res;
    }

    /**
     * Metoda potrzebna do przekazywania tablicy klientowi w celu wyswietlenia mu jego plansz
     * @return zwraca cala plansze w postaci pol odzielonych spacja oraz linijek odzielonych \n
     */
    public String exportBoard() {
        String res = "";
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                res += board[i][j]+" ";
            }
            res += "\n"; 
        }

        return res;
    }

    /**
     * zmienia litere na indeks tablicy za pomoca kodu ASCII
     * @param letter litera do zmiany w liczbe
     * @return liczbe odpowiadajaca indeksu tablicy [0-9]
     */
    private int letterToInt(String letter) {
        letter = letter.toLowerCase();
        // ASCII to int then -96 (a=97, b=98, etc)
        char ch = letter.charAt(0);
        int index = ch - 96;

        return index;
    }

    /**
    @param coordinate koordynat strzalu
    @return
    1 jezeli jest trafiony statek i zatopiony
    2 jezeli trafiony, ale nie zatopiony
    0 jezli pudlo
    */
    public int shotShip(String coordinate) {
        int x = letterToInt(coordinate.substring(0, 1))-1;
        int y = Integer.parseInt(coordinate.substring(1))-1;

        if(board[y][x].equals(SHIP_SIGN) || board[y][x].equals(HIT_SIGN)) {
            board[y][x] = HIT_SIGN;

            if(y<10) {
                if(!board[y+1][x].equals(EMPTY_SIGN)) return 2;
            }
            if(y>0) {
                if(!board[y-1][x].equals(EMPTY_SIGN)) return 2;
            }
            if(x<10) {
                if(!board[y][x+1].equals(EMPTY_SIGN)) return 2;
            }
            if(x>0) {
                if(!board[y][x-1].equals(EMPTY_SIGN)) return 2;
            }

            return 1;
        } 
        
        board[y][x] = MISS_SIGN;
        return 0;
    }

    /**
     * Przerabia plansze na taka ktora zawiera tylko strzaly i trafienia, aby nie ujawnic pozycje statkow
     * @return zwraca plansze strzalow
     */
    public String getShootBoard() {
        String res = "   A B C D E F G H I J \n";

        for (int i = 0; i < board.length; i++) {
            res += (i+1)+" ";
            if(i < 9) res += " ";

            for (int j = 0; j < board.length; j++) {
                if(!board[i][j].equals(SHIP_SIGN)) res += board[i][j]+" ";
                else res += "~ ";
            }
            res += "\n";
        }

        return res;
    }
    /**
     * Sprawdza czy gracz przegral
     * @return true - jezeli przegral
     *         false - jezeli nie przegral
     */
    public boolean checkIfLost() {
        for (String[] strings : board) {
            for (String string : strings) {
                if(string.equals(SHIP_SIGN)) return false;
            }
        }
        return true;
    }

    public String[][] getBoard() {
        return this.board;
    }

    /**
     * laczy 2 tablice tak aby moc wyswietlic obie naraz obok siebie
     * @param board2 druga tablica ktora jest tablica strzalow
     * @return zwraca poloczone tablice
     */
    public String combine(GameBoard board2) {
        String b1 = toString();
        String b2 = board2.getShootBoard();

        String[] arr1 = b1.split("\n");
        String[] arr2 = b2.split("\n");

        String res = "     Twoja Plansza           Plansza strzałów\n";

        for (int i = 0; i < arr1.length; i++) {
            res += arr1[i]+"   "+arr2[i]+"\n";
        }

        return res;
    }
}