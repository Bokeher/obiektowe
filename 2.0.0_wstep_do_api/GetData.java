import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class GetData implements GetDataImp{
    @Override
    public Person getData(String imie) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("https://api.agify.io?name="+imie))
            .build();

        try {
            HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String res = response.body().toString();

            res = res.substring(1, res.length()-1);
            
            String[] arr = res.split(",");

            for(int i = 0; i < arr.length; i++) {
                String elem = arr[i];
                String[] temp = elem.split(":");
                String value = temp[1];
                
                //removing "" from String values
                if(value.charAt(0) == '"') value = value.substring(1, value.length()-1);
                arr[i] = value;
            }

            int wiek = Integer.parseInt(arr[0]);
            int numer = Integer.parseInt(arr[1]);
            String name = arr[2];

            Person p = new Person(name, wiek, numer);

            return p;
        } catch (Exception e) {
            // TODO: handle exception
        }
        return null;
    }
}
