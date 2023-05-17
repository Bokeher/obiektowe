import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class ServerMain {
    public static void main(String[] args) {

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



                while (true) {
                    
                    // String ans = in.readUTF();
                    // System.out.println(ans);
    
                    // out.writeUTF("suma: "+suma+"\nroznica: "+roznica+"\niloczyn: "+iloczyn); 
                        
                    Board player1Board = new Board();
                    Board player2Board = new Board();

                    Map<String, Integer> remainingShips = new LinkedHashMap<String, Integer>();
                    remainingShips.put("Czteromasztowce", 1);
                    remainingShips.put("Trzymasztowce", 2);
                    remainingShips.put("Dwumasztowce", 3);
                    remainingShips.put("Jednomasztowce", 4);
    
                    Scanner sc = new Scanner(System.in);

                    // forEach => for i
                    remainingShips.forEach((String key, Integer value) -> {
                        System.out.println(key+" "+value);

                        int shipLength = 0;
                        if(key.equals("Czteromasztowce")) {
                            shipLength = 4;
                        } else if(key.equals("Trzymasztowce")) {
                            shipLength = 3;
                        } else if(key.equals("Dwumasztowce")) {
                            shipLength = 2;
                        } else if(key.equals("Jednomasztowce")) {
                            shipLength = 1;                           
                        }
                        String shipName = key.substring(0, key.length()-2)+"y";
                        while(value > 0) {
                            System.out.println(player1Board);
                            System.out.println("Wybierz gdzie ma stac statek "+shipName);
                            System.out.print("Pierwsza wspolrzedna: ");
                            String firstCord = sc.nextLine();
                            System.out.print("Druga wspolrzedna: ");
                            String secndCord = sc.nextLine();

                            

                            boolean gitGud = player1Board.placeShip(shipLength, firstCord, secndCord);
                            if(gitGud) {
                                value--;
                            } else {
                                System.out.println("Niepoprawne dane");
                            }
                        // 

                        }
                        // TODO: tu ma byc to co na dole -> sprawdzanie czy jest 0 statkow tego typu
                    });
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
    
                Scanner sc = new Scanner(System.in);
                while (true) {
                    String ans = in.readUTF();
                    System.out.println(ans);
    
                    ans = sc.nextLine();
                    out.writeUTF(ans);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        
            

            Board board = new Board();
            System.out.println("d");
            System.out.println(board.placeShip(2, "a1", "a2"));
            System.out.println(board.placeShip(1, "b3", "b3"));
            System.out.println(board.placeShip(4, "c4", "c7"));
            System.out.println(board.placeShip(2, "e9", "e8"));
            System.out.println(board.placeShip(2, "e2", "f2"));
            System.out.println(board.toString());
            
            board.shotShip("a1");
            board.shotShip("a2");
            board.shotShip("b1");
            board.shotShip("b3");
            
            System.out.println(board.toString());
            System.out.println(board.getShootBoard());
            Scanner sc = new Scanner(System.in);
            // out.writeUTF("Witaj na serwerze. Podaj liczby w foramcie liczba1;liczba2: ");
            
    }
}