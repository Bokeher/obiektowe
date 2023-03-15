import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ServerMain {
    public static void main(String[] args) {
        try {
            ServerSocket server = new ServerSocket(23459);
            Socket socket = server.accept();
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            DataInputStream in = new DataInputStream(socket.getInputStream());

            Scanner sc = new Scanner(System.in);

            Board board = new Board();
            board.setSignAt(2, "O");
            board.setSignAt(4, "X");
            
            System.out.println(board.toString());
            while (true) {
                String ans = in.readUTF();
                System.out.println(ans);

                
                
                // out.writeUTF("suma: "+suma+"\nroznica: "+roznica+"\niloczyn: "+iloczyn);                
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }
}