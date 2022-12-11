import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Scanner;

public class GetData {
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

        String language = "none";
        do{
            System.out.print("Wybierz język: \ncs - czeski, \nde - niemiecki, \nen - angielski, \nes - hiszpański, \nfr - francuski, \npt - portugalski \nWybór: ");
            language = scString.nextLine();
        } while(!allLanguagues.contains(language));

        System.out.print("\nCzy chcesz wybrać kategorie [y - tak, n - nie]: ");
        String choseCategories = scString.nextLine();
        
        boolean specifiedCategories = false;
        ArrayList<String> categoriesList = new ArrayList<>();
        while(choseCategories.equals("y")) {
            final String[] allCategories = {"Programming", "Miscellaneous", "Dark", "Pun", "Spooky", "Christmas"};
            specifiedCategories = true;
            
            System.out.print("\nWybierz kategorie: \n1 - programowanie, \n2 - różne, \n3 - ciemny humor, \n4 - gra słów, \n5 - straszny, \n6 - boże narodzenie \nWybór: ");
            int categoryIndex = scInt.nextInt();

            categoriesList.add(allCategories[categoryIndex - 1]);
            
            System.out.print("Czy chcesz wybrać jeszcze jedną kategorie [y - tak, n - nie]: ");
            choseCategories = scString.nextLine();
        } ;
        String categories = "Any";
        if(specifiedCategories) categories = String.join(",", categoriesList);

        return "https://v2.jokeapi.dev/joke/"+categories+"?type=single&amount="+jokeAmount+"&lang="+language;
    }
}
