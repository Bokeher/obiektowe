import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ServerMain {
    static Board board;
    public static void main(String[] args) {
        try {
            ServerSocket server = new ServerSocket(23459);
            Socket socket = server.accept();
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            DataInputStream in = new DataInputStream(socket.getInputStream());

            Scanner sc = new Scanner(System.in);
            File f = new File("gameHistory.txt");

            while (true) {
                String chose;
                do {
                    out.writeUTF(" --- Menu ---\n"+
                                 "1 - Uruchom gre\n"+
                                 "2 - Historia gier");
                    chose = in.readUTF();
                } while (!chose.equals("1") && !chose.equals("2"));

                if(chose.equals("1")) {
                    board = new Board();
        
                    out.writeUTF("Podaj nazwe gracza 1: ");
                    String name1 = in.readUTF();            
                    out.writeUTF("Podaj nazwe gracza 2: ");
                    String name2 = in.readUTF();         
                    
                    String sign1 = "O";
                    String sign2 = "X";
                    Random rand = new Random();
                    int random = rand.nextInt(1); // 0-1
                    if(random == 0) {
                        sign1 = "X";
                        sign2 = "O";
                    }
    
                    Player player1 = new Player(name1, sign1);
                    Player player2 = new Player(name2, sign2);
        
                    boolean player1Turn = false;
                    if(player1.getSign().equals("X")) player1Turn = true;
        
                    int winner = 0; 
                    boolean playing = true;
                    while(playing) {
                        String mess = board.toString()+"\n"; 
                        if(player1Turn) {
                            mess += "Ruch "+player1.getName()+" ("+player1.getSign()+").";
                        } else {
                            mess += "Ruch "+player2.getName()+" ("+player2.getSign()+").";
                        }
                        mess += "\nPodaj pole do wstawienia twojego znaku";
                        out.writeUTF(mess);
                        String res = in.readUTF();
                        // [a-c][1-3]|[1-3][a-c]
                        Pattern pattern = Pattern.compile("[a-c][1-3]|[1-3][a-c]", Pattern.CASE_INSENSITIVE);
                        Matcher matcher = pattern.matcher(res);
    
                        while(!matcher.find() || res.length() > 2) {
                            out.writeUTF("Niepoprawny ruchu\nPodaj pole do wstawienia");
                            res = in.readUTF();
                            matcher = pattern.matcher(res);
                        }
       
                        pattern = Pattern.compile("[a-c][1-3]", Pattern.CASE_INSENSITIVE);
                        matcher = pattern.matcher(res);
                        if(!matcher.find()) res = ""+res.charAt(1)+res.charAt(0);
    
                        int index = 0;
                        int number = Integer.parseInt(res.substring(1, 2));
                        char letter = res.charAt(0);
    
                        if(letter == 'a') index = 0; 
                        if(letter == 'b') index = 1;
                        else if(letter == 'c') index = 2;

                        if(number == 2) index += 3;
                        else if(number == 3) index += 6;
    
                        System.out.println(index);

                        String sign = player1.getSign();
                        if(!player1Turn) sign = player2.getSign(); 
    
                        System.out.println(index+" "+sign);
    
                        if(board.getSignAt(index).equals(" ")) board.setSignAt(index, sign);
                        else player1Turn = !player1Turn;
    
                        String winnerSign = null;
                        
                        if(board.checkWin()) {
                            winnerSign = board.getWinnerSign();
                            if(winnerSign.equals("-")) winner = 3;
                            else if(player1.getSign().equals(winnerSign)) winner = 1;
                            else if(player2.getSign().equals(winnerSign)) winner = 2;
                            playing = false;
                        } else player1Turn = !player1Turn;
                    }
                    // if(player1Turn)
        
                    FileWriter fw = new FileWriter(f, true);
                    
                    String mess = "Wygrał gracz ";
                    String matchData = player1.getName()+" ("+player1.getSign()+") vs "+player2.getName()+" ("+player2.getSign()+")";
                    if(winner == 3) {
                        mess = "Remis";
                        fw.write(matchData+" | Remis \n");
                    } else if(winner == 1) {
                        mess += player1.getName();
                        fw.write(matchData+" | Zwycięzca: "+player1.getName()+"\n");
                    } else {
                        mess += player2.getName();
                        fw.write(matchData+" | Zwycięzca: "+player2.getName()+"\n");
                    }
                    out.writeUTF(board.toString()+"\n"+mess);
                    in.readUTF();
                    fw.close();
                } else if(chose.equals("2")) {
                    Scanner sc2 = new Scanner(f);
                    String res = "";
                    while (sc2.hasNextLine()) {
                        res += sc2.nextLine()+"\n";
                    }
                    out.writeUTF(res);
                    in.readUTF();
                } 
            } 
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }
}