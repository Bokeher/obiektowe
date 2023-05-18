import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Scanner sc2 = new Scanner(System.in);

        String[] menuOptions = {
            "1 - Postaw serwer",
            "2 - Połącz z serwerem"
        };

        // Board test = new Board();
        // test.placeShip(4, "a1", "a4");
        
        // Board test2 = new Board();
        // test2.placeShip(3, "a1", "a3");

        // System.out.println(test.combineBoards(test2));

        // System.out.println(test.shotShip("a9"));

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
                    clearCmd();
                    // get board from player 2
                    if(player2Board == null) {
                        String ans = in.readUTF();
                        player2Board = new Board(ans);
                        continue;
                    }
                    System.out.println(player1Board.combineBoards(player2Board));

                    String coordinate = "";
                    boolean validMove = false;
                    while(!validMove) {
                        System.out.print("Napisz wspolrzedna strzalu: ");
                        coordinate = sc2.nextLine();
                        validMove = player1Board.checkIfValidCoordinate(coordinate);
                    }
                    // System.out.println(coordinate);
                    // int hit = player2Board.shotShip(coordinate);
                    

                    // if(hit == 1) {
                    //     System.out.println("Statek trafiony i zatopiony");
                    // } else if(hit == 2) {
                    //     System.out.println("Statek trafiony i niezatopiony");
                    // } else {
                    //     System.out.println("Pudło");
                    // }

                    String mess = player2Board.combineBoards(player1Board)+"\n"+
                    "Podaj wspolrzedna twojego strzalu: ";
                    // TODO: tu dalej

                    out.writeUTF(mess);


                    // sprawdza czy ktos wygral
                    if(player1Board.checkIfLost()) {
                        System.out.println("Wygral gracz 2");
                    } else if(player2Board.checkIfLost()) {
                        System.out.println("Wygral gracz 1");
                    }

                    String ans = in.readUTF();
                    player1Board.shotShip(ans);
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

                    while(!board.checkIfValidCoordinate(ans)) {
                        System.out.println("Nieprawidlowy format \nPodaj wspolrzedna strzalu: ");
                        ans = sc.nextLine();
                    }

                    out.writeUTF(ans);
                    // clearCmd();
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

    private static void clearCmd() throws InterruptedException, IOException {
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
    }
}