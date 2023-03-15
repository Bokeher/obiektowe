public class Board {
    private String[] board = {" ", " ", " ", " ", " ", " ", " ", " ", " "}; 

    @Override
    public String toString() {
        String res = board[0]+" | "+board[1]+" | "+board[2]+"\n"+
                     "----------\n"+
                     board[3]+" | "+board[4]+" | "+board[5]+"\n"+
                     "----------\n"+
                     board[6]+" | "+board[7]+" | "+board[8]+"\n";
        return res;
    } 

    /**
     * Puts sign in index
     * @param index - index of board - 0-2 first row | 3-5 second row | 6-8 third row
     * @param sign - X or O 
     * @return if incorrect sign or index of out bound <0;8> return false | else true
     */
    public boolean setSignAt(int index, String sign) {
        if(!sign.equals("X") && !sign.equals("O")) return false;
        if(index < 0 || index > 8) return false;

        board[index] = sign;

        return true;
    }
}
