import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Board {
    // board[x][y] x == abcdefghij | y = 123456789
    private String[][] board = new String[10][10];  
    //4 33 222 1111

    public Board() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                board[i][j] = "~";
            }
        }
    }

    public boolean placeShip(int shipLength, String firstCoordinate, String secondCoordinate) {
        if(!checkIfValidCoordinate(firstCoordinate)) return false;
        if(!checkIfValidCoordinate(secondCoordinate)) return false;

        boolean lettersChange = false;
        int firstNumb;
        int secondNumb;
        if(firstCoordinate.charAt(0) == secondCoordinate.charAt(0)) {
            // in case of changing number
            firstNumb = Integer.parseInt(firstCoordinate.substring(1));
            secondNumb = Integer.parseInt(secondCoordinate.substring(1));
        } else {
            // in case of changing letter
            lettersChange = true;
            firstNumb = letterToInt(firstCoordinate);
            secondNumb = letterToInt(secondCoordinate);
        }

        if(Math.abs(secondNumb - firstNumb) != shipLength-1) return false;
        String beginCoordinate = firstCoordinate;
        if(secondNumb - firstNumb < 0) {
            beginCoordinate = secondCoordinate;
        }
        //e9 e6
        System.out.println(beginCoordinate);
        int x;
        int y;

        // check if there are any nearby ships that cant exist due to game rules 
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

        // place ship on board
        for(int i = 0; i < shipLength; i++) {
            if(lettersChange) {
                x = firstNumb-1+i;
                y = Integer.parseInt(beginCoordinate.substring(1))-1;
            } else {
                x = letterToInt(beginCoordinate.substring(0, 1))-1;
                y = firstNumb-1+i;
            }
            System.out.println("X: "+x+" Y: "+y);

            board[y][x] = "O";
        }

        return true;
    }

    private boolean noNearbyShips(int x, int y) {
        if(y>0 && board[y-1][x] != "~") return false;
        if(y<9 && board[y+1][x] != "~") return false;
        
        if(x>0 && board[y][x-1] != "~") return false;
        if(x<9 && board[y][x+1] != "~") return false;

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

    private int letterToInt(String letter) {
        // ASCII to int then -96 (a=97, b=98, etc)
        char ch = letter.charAt(0);
        int index = ch - 96;

        return index;
    }

    public boolean checkIfValidCoordinate(String coordinate) {
        Pattern pattern = Pattern.compile("[a-i]", Pattern.CASE_INSENSITIVE);
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

    public boolean shotShip(String coordinate) {
        int x = letterToInt(coordinate.substring(0, 1))-1;
        int y = Integer.parseInt(coordinate.substring(1))-1;

        if(board[y][x].equals("O")) {
            board[y][x] = "x";
            return true;
        } 
        
        board[y][x] = "o";
        return false;
    }

    public String getShootBoard() {
        String res = "   A B C D E F G H I J \n";

        for (int i = 0; i < board.length; i++) {
            res += (i+1)+" ";
            if(i < 9) res += " ";

            for (int j = 0; j < board.length; j++) {
                if(!board[i][j].equals("O")) res += board[i][j]+" ";
                else res += "~ ";
            }
            res += "\n";
        }

        return res;
    }
}