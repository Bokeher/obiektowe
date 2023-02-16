package zad2;
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
            out.writeUTF("Podaj wyraz do zapisu: ");
            List<String> list = new ArrayList<>();
            while (true) {
                String ans = in.readUTF().toLowerCase();
                System.out.println(ans);

                if(ans.equals("0:q")) break;

                if(ans.equals("1:wszystkie")) {
                    out.writeUTF("Lista: "+list.toString()+"\nPodaj wyraz do zapisu lub napisz 0:q aby zakończyć program: ");
                } else {
                    if(list.contains(ans)) out.writeUTF("Podane słowo już nam podaj inne");
                    else {
                        out.writeUTF("Dodano nowe słowo, jeśli chcesz wyświetlić listę słów wpisz 1:wszystkie lub podaj inne słowo: ");
                        list.add(ans);
                    }
                }
                System.out.println(list);
            }
        } catch (Exception ex) {
            // System.out.println(ex.toString());
        }
    }
}