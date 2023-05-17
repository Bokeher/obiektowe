import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String[] menuOptions = {
            "1 - Postaw serwer",
            "2 - Połącz z serwerem"
        };

        Menu menu = new Menu(menuOptions);
        int choice = menu.getChoiceFromMenu(true);

        if(choice == 1) {
            // to jest serwer
            try {
                ServerSocket server = new ServerSocket(23459);
                Socket socket = server.accept();
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                DataInputStream in = new DataInputStream(socket.getInputStream());

                Board player1Board = new Board();
                Board player2Board = null;

                player1Board.placeAllShips();

                while (true) {
                    // get board from player 2
                    if(player2Board == null) {
                        String ans = in.readUTF();
                        player2Board = new Board(ans);
                        continue;
                    }

                    
                    String coordinate = "";
                    boolean validMove = true;
                    while(!validMove){
                        System.out.print("Napisz wspolrzedna strzalu: ");
                        coordinate = sc.nextLine();
                        validMove = player1Board.checkIfValidCoordinate(coordinate);
                    }
                    boolean hit = player1Board.shotShip(coordinate);

                    if(hit) {
                        System.out.println("Statek trafiony");
                    }
                    // TODO: tu dalej


                    // sprawdza czy ktos wygral
                    if(player1Board.checkIfLost()) {
                        System.out.println("Wygral gracz 2");
                    } else if(player2Board.checkIfLost()) {
                        System.out.println("Wygral gracz 1");
                    }

                    // String ans = in.readUTF();
                    // System.out.println(ans);
    
                    // out.writeUTF("suma: "+suma+"\nroznica: "+roznica+"\niloczyn: "+iloczyn); 
                  
                }
            } catch (Exception ex) {
                System.out.println(ex.toString());
            }
        } else {
            // laczy sie z serwerem
            try {
                Socket socket = new Socket("127.0.0.16", 23459);
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                DataInputStream in = new DataInputStream(socket.getInputStream());
    
                Board board = new Board();

                board.placeAllShips();
                boolean firstLoop = true;

                while (true) {
                    if(firstLoop) {
                        out.writeUTF(board.exportBoard());
                        firstLoop = false;
                    }
                    
                    String ans = in.readUTF();
                    System.out.println(ans);

                    ans = sc.nextLine();
                    out.writeUTF(ans);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        
            

            // Board board = new Board();
            // System.out.println("d");
            // System.out.println(board.placeShip(2, "a1", "a2"));
            // System.out.println(board.placeShip(1, "b3", "b3"));
            // System.out.println(board.placeShip(4, "c4", "c7"));
            // System.out.println(board.placeShip(2, "e9", "e8"));
            // System.out.println(board.placeShip(2, "e2", "f2"));
            // System.out.println(board.toString());
            
            // board.shotShip("a1");
            // board.shotShip("a2");
            // board.shotShip("b1");
            // board.shotShip("b3");
            
            // System.out.println(board.toString());
            // System.out.println(board.getShootBoard());
            // Scanner sc = new Scanner(System.in);
            // out.writeUTF("Witaj na serwerze. Podaj liczby w foramcie liczba1;liczba2: ");
            
    }
}