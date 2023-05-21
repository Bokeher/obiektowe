import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Board implements GameBoard {
    private Utils utils = new Utils();
    
    private final String hitSign = "x";
    private final String shipSign = "O";
    private final String missSign = "o";
    private final String emptySign = "~";

    private String[][] board = new String[10][10];  

    public Board() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                board[i][j] = emptySign;
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
        }
        System.out.println("Oczekiwanie na rozmieszczenie statków przez przeciwnika...");
    }

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

            board[y][x] = shipSign;
        }

        return true;
    }

    private boolean noNearbyShips(int x, int y) {
        if(y>0 && board[y-1][x] != emptySign) return false;
        if(y<9 && board[y+1][x] != emptySign) return false;
        
        if(x>0 && board[y][x-1] != emptySign) return false;
        if(x<9 && board[y][x+1] != emptySign) return false;

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

    private int letterToInt(String letter) {
        // ASCII to int then -96 (a=97, b=98, etc)
        char ch = letter.charAt(0);
        int index = ch - 96;

        return index;
    }

    /**
    @param coordinate - The coordinate to shoot at.
    @return
    1 if the shot hits and sinks the ship.
    2 if the shot hits but the ship is not yet sunk.
    0 if the shot misses the ship.
    */
    public int shotShip(String coordinate) {
        int x = letterToInt(coordinate.substring(0, 1))-1;
        int y = Integer.parseInt(coordinate.substring(1))-1;

        if(board[y][x].equals(shipSign) || board[y][x].equals(hitSign)) {
            board[y][x] = hitSign;

            if(y<10) {
                if(!board[y+1][x].equals(emptySign)) return 2;
            }
            if(y>0) {
                if(!board[y-1][x].equals(emptySign)) return 2;
            }
            if(x<10) {
                if(!board[y][x+1].equals(emptySign)) return 2;
            }
            if(x>0) {
                if(!board[y][x-1].equals(emptySign)) return 2;
            }

            return 1;
        } 
        
        board[y][x] = missSign;
        return 0;
    }

    public String getShootBoard() {
        String res = "   A B C D E F G H I J \n";

        for (int i = 0; i < board.length; i++) {
            res += (i+1)+" ";
            if(i < 9) res += " ";

            for (int j = 0; j < board.length; j++) {
                if(!board[i][j].equals(shipSign)) res += board[i][j]+" ";
                else res += "~ ";
            }
            res += "\n";
        }

        return res;
    }

    public boolean checkIfLost() {
        for (String[] strings : board) {
            for (String string : strings) {
                if(string.equals(shipSign)) return false;
            }
        }
        return true;
    }

    public String[][] getBoard() {
        return this.board;
    }

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