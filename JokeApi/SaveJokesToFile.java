import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class SaveJokesToFile {
    boolean[] selects;
    ArrayList<Joke> list;

    public SaveJokesToFile(boolean[] selects, JokeList jokeList) {
        list = jokeList.getList();
        this.selects = selects;
    }

    public void save() {
        ClearCmd c = new ClearCmd();
        c.clear();
        
        Scanner sc = new Scanner(System.in);
        System.out.print("Wpisz nazwe pliku: ");
        String fileName = sc.nextLine();

        File f = new File(fileName+".txt");

        try {
            FileWriter fw = new FileWriter(f);
            int i = 0;
            for (Joke joke : list) {
                if(selects[i]) fw.write(joke.getContent()+"\n\n");
                i++;
            }

            fw.close();
        } catch (IOException e) {
            System.out.println("Zapis do pliku sie nie udal");
        }
    }
}
