import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Main {
    private static Utils utils = new Utils();
    private static GameTimer timer;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Scanner sc2 = new Scanner(System.in);
        Scanner sc3 = new Scanner(System.in);

        String[] menuOptions = {
            "1 - Postaw serwer",
            "2 - Połącz z serwerem"
        };

        Menu menu = new Menu(menuOptions);
        int choice = menu.getChoiceFromMenu(true);

        if(choice == 1) {
            System.out.println("Napisz ile ma trwac gra: ");
            int secs = sc3.nextInt();            
            timer = new GameTimer(secs);

            // to jest serwer
            System.out.println("Oczekiwanie na połączenie od klienta...");
            try {
                timer.startGameTimer();
                ServerSocket server = new ServerSocket(23459);
                Socket socket = server.accept();
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                DataInputStream in = new DataInputStream(socket.getInputStream());

                //polimorfizm
                GameBoard serverBoard = new Board();
                GameBoard clientBoard = null;

                serverBoard.placeAllShips();

                while (true) {
                    
                    // get board from player 2
                    if(clientBoard == null) {
                        String ans = in.readUTF();
                        clientBoard = new Board(ans);
                        continue;
                    }
                    printBoards(serverBoard, clientBoard, false);

                    String coordinate = utils.getShootCoordinate();
                    int shotResult = clientBoard.shotShip(coordinate);

                    System.out.println(utils.getShotText(shotResult));

                    printBoards(serverBoard, clientBoard, true);
                    
                    String dataForClient = serverBoard.exportBoard()+";"+clientBoard.exportBoard();

                    // sprawdza czy ktos wygral
                    if(serverBoard.checkIfLost()) {
                        printBoards(serverBoard, clientBoard, false);
                        System.out.println("Przegrałeś!");
                        out.writeUTF(".Wygrałeś!");
                        break;
                    } else if(clientBoard.checkIfLost()) {
                        printBoards(serverBoard, clientBoard, false);
                        System.out.println("Wygrałeś!");
                        out.writeUTF(".Przegrałeś!");
                        break;
                    }
                    
                    if(checkIfTimerEnded()) {
                        // TODO: NEXT
                        out.writeUTF("Koniec czasu");
                        endGame();
                        break;
                    }

                    out.writeUTF(dataForClient);
                    
                    String ans = in.readUTF();
                    serverBoard.shotShip(ans);     

                    // sprawdza czy ktos wygral
                    if(serverBoard.checkIfLost()) {
                        printBoards(clientBoard, serverBoard, false);
                        System.out.println("Przegrałeś!");
                        out.writeUTF(".Wygrałeś!");
                        break;
                    } else if(clientBoard.checkIfLost()) {
                        printBoards(clientBoard, serverBoard, false);
                        System.out.println("Wygrałeś!");
                        out.writeUTF(".Przegrałeś!");
                        break;
                    }

                    if(checkIfTimerEnded()) {
                        // TODO: NEXT
                        out.writeUTF("Koniec czasu");
                        endGame();
                        break;
                    }

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
    
                GameBoard clientBoard = new Board();
                GameBoard serverBoard = new Board();

                clientBoard.placeAllShips();
                
                boolean firstLoop = true;
                while (true) {
                    printBoards(clientBoard, serverBoard, true);
                    if(firstLoop) {
                        out.writeUTF(clientBoard.exportBoard());
                        firstLoop = false;
                    }                    

                    // get data from server
                    String ans = in.readUTF();

                    if(ans.charAt(0) == '.') {
                        printBoards(clientBoard, serverBoard, false);
                        System.out.println(ans.substring(1));
                        break;
                    }

                    // data to objects
                    String[] arr = ans.split(";");
                    serverBoard = new Board(arr[0]);
                    clientBoard = new Board(arr[1]);

                    printBoards(clientBoard, serverBoard, false);

                    String coordinate = utils.getShootCoordinate();
                    int shotResult = serverBoard.shotShip(coordinate);

                    System.out.println(utils.getShotText(shotResult));

                    printBoards(clientBoard, serverBoard, true);
                    

                    // send shooting cord to server
                    out.writeUTF(coordinate);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }    
    }    

    private static void printBoards(GameBoard board1, GameBoard board2, boolean additionalText) {
        utils.clearCmd();
        System.out.println(board1.combine(board2));
        if(additionalText) System.out.println("Trwa ruch przeciwnika...");
    }

    private static boolean checkIfTimerEnded() {
        if(!timer.gameState) return true;
        return false;
    }
    private static void endGame() {
        System.out.println("Koniec czasu");

        // liczenie punktow

        
    }
}