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
            List<Integer> gradeList = new ArrayList<>();
            List<Integer> weigthList = new ArrayList<>();
            out.writeUTF("Podaj liczby w formacie ocena;waga: ");
            while (true) {
                String ans = in.readUTF();
                System.out.println(ans);

                if (ans.equals("0:wynik")) {
                    Calculations calc = new Calculations(gradeList, weigthList);
                    double avg = calc.getWeightenedAverage();
                    double weightenedDeviation = calc.getWightenedDeviation();
                    double deviation = calc.getDeviation();

                    String mess = "Wszystkie oceny: ";

                    for (int i = 0; i < gradeList.size(); i++) {
                        mess += gradeList.get(i)+";"+weigthList.get(i)+", ";
                    }

                    out.writeUTF(mess+"\nŚrednia ważona: "+avg+"\nOdchylenie standardowe ważone: "+weightenedDeviation+"\nOdchylenie standardowe: "+deviation);
                } else {
                    String[] numbs = ans.split(";");
                    int grade = Integer.parseInt(numbs[0]);
                    int weight = Integer.parseInt(numbs[1]);

                    boolean gradeErr = true;
                    boolean weightErr = true;
                    if(grade >= 1 && grade <= 6) gradeErr = false;
                    if(weight >= 1 && weight <= 5) weightErr = false;

                    if(gradeErr && weightErr) out.writeUTF("Ocena i waga nieprawidłowe");
                    else {
                        if(gradeErr) out.writeUTF("Nieprawidłowa ocena");
                        if(weightErr) out.writeUTF("Waga oceny nieprawidłowa");
                    }

                    if(!gradeErr && !weightErr) {
                        gradeList.add(grade);
                        weigthList.add(weight);

                        out.writeUTF("Dodano ocene \nNapisz kolejna ocene w formacie ocena;waga lub napisz '0:wynik', aby wyswietlic obliczenia ");
                    }
                }
                // out.writeUTF("suma: "+suma+"\nroznica: "+roznica+"\niloczyn: "+iloczyn);                
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }
}