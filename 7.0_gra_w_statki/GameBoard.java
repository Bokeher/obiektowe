public interface GameBoard {
    String HIT_SIGN = null;
    void placeAllShips();
    boolean placeShip(int shipLength, String firstCoordinate, String secondCoordinate);
    int shotShip(String coordinate);
    boolean checkIfLost();
    String getShootBoard();
    String combine(GameBoard board2);
    String exportBoard();
    String[][] getBoard();
}