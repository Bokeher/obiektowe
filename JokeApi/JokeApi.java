package JokeApi;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

class JokeApi{
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        final String allLanguagues = "cs|de|en|es|fr|pt";

        System.out.print("Podaj ilosc kawałów: ");
        int jokeAmount = sc.nextInt();

        Scanner sc2 = new Scanner(System.in);
        String language = "none";
        do{
            System.out.print("Wybierz język [cs - czeski, de - niemiecki, en - angielski, es - hiszpański, fr - francuski, pt - portugalski]: ");
            language = sc2.nextLine();
        } while(!allLanguagues.contains(language));

        System.out.println("Czy chcesz wybrać kategorie [y - tak, n - nie]: ");
        String choseCategories = sc2.nextLine();
        
        String category = "";
        boolean continue = true;
        if(choseCategories == "y") {
            
            System.out.print("Wybierz kategorie [Programming - programowanie, Miscellaneous - różne, Dark - ciemny humor, Pun - gra słów, Spooky - straszny, Christmas - boże narodzenie]: ");
            category += sc2.nextLine();

            System.out.print("Czy chcesz wybrać jeszcze jedną kategorie [y - tak, n - nie]: ");
            if(sc2.nextLine().equals("y")) continue = false;
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