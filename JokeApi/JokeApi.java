package JokeApi;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class JokeApi{
    public static void main(String[] args) {
        Scanner scInt = new Scanner(System.in);
        final String allLanguagues = "cs|de|en|es|fr|pt";

        System.out.print("Podaj ilosc kawałów: ");
        int jokeAmount = scInt.nextInt();

        Scanner scString = new Scanner(System.in);
        String language = "none";
        do{
            System.out.print("Wybierz język: \ncs - czeski, \nde - niemiecki, \nen - angielski, \nes - hiszpański, \nfr - francuski, \npt - portugalski \nWybór: ");
            language = scString.nextLine();
        } while(!allLanguagues.contains(language));

        System.out.print("\nCzy chcesz wybrać kategorie [y - tak, n - nie]: ");
        String choseCategories = scString.nextLine();
        
        List<String> categoriesList = new ArrayList<>();
        // String []category = null;
        boolean cont = true;
        if(choseCategories.equals("y")) {
            final String[] allCategories = {"Programming", "Miscellaneous", "Dark", "Pun", "Spooky", "Christams"};
            
            System.out.print("\nWybierz kategorie: \n1 - programowanie, \n2 - różne, \n3 - ciemny humor, \n4 - gra słów, \n5 - straszny, \n6 - boże narodzenie \nWybór: ");
            int categoryIndex = scInt.nextInt();

            categoriesList.add(allCategories[categoryIndex-1]);
            
            String d = String.join(",", categoriesList);
            System.out.println(d);
            // System.out.print("Czy chcesz wybrać jeszcze jedną kategorie [y - tak, n - nie]: ");
            // if(sc2.nextLine().equals("y")) cont = false;
        }

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("https://v2.jokeapi.dev/joke/Any?type=single&amount="+jokeAmount+"&lang="+language))
            .build();

        try {
            HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String res = response.body().toString();
            System.out.println(res);

            String d = res.substring(res.indexOf("\"joke\": \"")+9);
        
            String joke = d.substring(0, d.indexOf(".\",")+1);
            System.out.println("Żart: "+joke);

        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}