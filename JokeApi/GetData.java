import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Scanner;

public class GetData {
    ClearCmd c = new ClearCmd();
    
    public String getFromApi(String url) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .build();

        try {
            HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body().toString();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getFromUser(boolean debug) {
        if(debug) return "https://v2.jokeapi.dev/joke/Any?type=single&amount=4&lang=en";

        Scanner scInt = new Scanner(System.in);
        Scanner scString = new Scanner(System.in);
        
        final String allLanguagues = "cs|de|en|es|fr|pt";

        System.out.print("Podaj ilosc kawałów: ");
        int jokeAmount = scInt.nextInt();
        c.clear();

        String language = "none";
        do{
            System.out.print("Wybierz język: \ncs - czeski, \nde - niemiecki, \nen - angielski, \nes - hiszpański, \nfr - francuski, \npt - portugalski \nWybór: ");
            language = scString.nextLine();
        } while(!allLanguagues.contains(language));
        c.clear();

        System.out.print("\nCzy chcesz wybrać kategorie [y - tak, n - nie]: ");
        String choseCategories = scString.nextLine();
        
        boolean specifiedCategories = false;
        ArrayList<String> categoriesList = new ArrayList<>();
        while(choseCategories.equals("y")) {
            final String[] allCategories = {"Programming", "Miscellaneous", "Dark", "Pun", "Spooky", "Christmas"};
            specifiedCategories = true;
            
            c.clear();
            System.out.print("\nWybierz kategorie: \n1 - programowanie, \n2 - różne, \n3 - ciemny humor, \n4 - gra słów, \n5 - straszny, \n6 - boże narodzenie \nWybór: ");
            int categoryIndex = scInt.nextInt();

            categoriesList.add(allCategories[categoryIndex - 1]);
            
            c.clear();
            System.out.print("Czy chcesz wybrać jeszcze jedną kategorie [y - tak, n - nie]: ");
            choseCategories = scString.nextLine();
        };
        String categories = "Any";
        if(specifiedCategories) categories = String.join(",", categoriesList);

        scInt.close();
        scString.close();
        return "https://v2.jokeapi.dev/joke/"+categories+"?type=single&amount="+jokeAmount+"&lang="+language;
    }

    public boolean[] getSaveData(JokeList jokeList) {
        Scanner sc = new Scanner(System.in);

        System.out.print("\nCzy chcesz zapisać jakis żart do pliku [y - tak, n - nie]: ");
        String answer = sc.nextLine();

        if(answer == "n") {
            sc.close();
            return null;
        }
        ArrayList<Joke> list = jokeList.getList();
        boolean[] selects = new boolean[list.size()];
        for (boolean b : selects) b = false;

        boolean cont = true;
        while(cont) {
            c.clear();

            System.out.println("Wybierz żart, który chcesz zapisać");
            for (int i = 0; i < list.size(); i++) {
                String elem = list.get(i).getContent();

                int cut = 50;
                String sentence = "";
                if(elem.length() > 65) {
                    String temp = elem.substring(50, cut+15);
                    String cutSentence = elem.substring(0, 50+temp.indexOf(" "));
                    sentence  = cutSentence+"...";
                } else sentence = elem;

                sentence = sentence.replace("\n", " ");
                String sign = getSign(selects[i]);
                System.out.println((i+1)+"["+sign+"]: "+sentence);
            }
            System.out.println("0 - Wyjście");

            System.out.print("Wybor: ");
            answer = sc.nextLine();

            int index = -2;
            try {
                index = Integer.parseInt(answer) - 1;
            } catch (Exception e) {
                // TODO: handle exception 
            }

            if(index > -1 && index < list.size()) {
                selects[index] = !selects[index];
            } else if(index == -1) cont = false; 
        }
        
        sc.close();
        return selects;
    }
    
    public String getSign(boolean bool) {
        if(bool) return "+";
        return "-";
    }
}
