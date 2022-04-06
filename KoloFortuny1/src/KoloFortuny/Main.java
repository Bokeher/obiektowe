package KoloFortuny;

import javax.sound.sampled.*;
import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Points p = new Points();

        System.out.println("-=-=-=-=-=-=-=- Kolo Fortuny -=-=-=-=-=-=-=-");
        System.out.print("Wybierz ilosc graczy [2-4]: ");
        int numberOfPlayers = sc.nextInt();
        while(numberOfPlayers < 2 || numberOfPlayers > 4 ) {
            System.out.println("Niepoprawny zakres");
            System.out.print("Wybierz ilosc graczy [2-4]: ");
            numberOfPlayers = sc.nextInt();
        }
        System.out.print("Wybierz ilosc  rund [4-10]: ");
        int numberOfRounds = sc.nextInt();
        while(numberOfRounds < 4 || numberOfRounds > 10 ) {
            System.out.println("Niepoprawny zakres");
            System.out.print("Wybierz ilosc  rund [4-10]: ");
            numberOfRounds = sc.nextInt();
        }

        int questionsAmount = numberOfRounds * numberOfPlayers;

        Random r = new Random();
        Set set = new HashSet();
        for (int i = 0; i < questionsAmount; i++) {
            int setLength = set.size();
            while (setLength == set.size()) {
                int rNumb = r.nextInt(90); //1-90
                set.add(rNumb);
            }
        }

        List<Integer> randomNumbs = new ArrayList<>();
        randomNumbs.addAll(set);

        File f = new File("questions.txt");
        int roundNumb = 1;
        for (int i = 0; i < questionsAmount; i += numberOfPlayers) {
            System.out.println("\n-=-=-=- Runda " + roundNumb + " -=-=-=- ");

            List<String> data = new ArrayList<>();

            try {
                Scanner sc2 = new Scanner(f);
                while (sc2.hasNextLine()) {
                    String line = sc2.nextLine();
                    data.add(line);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            for (int j = 0; j < numberOfPlayers; j++) {
                Scanner sc3 = new Scanner(System.in);
                Questions question = new Questions(data.get(randomNumbs.get(i + j)));

                System.out.println("Pytanie dla gracza numer " + (j + 1));
                System.out.println("Kategoria: " + question.getCategory());
                System.out.println("Trudnosc: " + question.getDifficulty());
                System.out.println("Pytanie: " + question.getQuestion());
                System.out.println("Odpowiedz a: " + question.getAnswerA());
                System.out.println("Odpowiedz b: " + question.getAnswerB());
                System.out.println("Odpowiedz c: " + question.getAnswerC());
                System.out.println("Odpowiedz d: " + question.getAnswerD());
                System.out.print("Odpowiedz: ");
                String answer = sc3.nextLine();

                if(question.isCorrect(answer)) {
                    p.addPoint(j+1, numberOfPlayers);
                    System.out.println("Poprawna odpowiedz!");
                } else System.out.println("Niepoprawna odpowiedz!");

                System.out.println();
            }
            roundNumb++;
        }
        p.addPoint(5, numberOfPlayers);
        System.out.println(p);

//        try{
//            File musicPath = new File("music.wav");
//
//            if(musicPath.exists()) {
//                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
//                Clip clip = AudioSystem.getClip();
//                clip.open(audioInput);
//                clip.start();
//                clip.loop(Clip.LOOP_CONTINUOUSLY);
//
//            }
//        } catch (UnsupportedAudioFileException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (LineUnavailableException e) {
//            e.printStackTrace();
//        }
    }
}