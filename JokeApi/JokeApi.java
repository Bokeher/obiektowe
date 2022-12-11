import java.util.ArrayList;

class JokeApi{
    public static void main(String[] args) {
        GetData gd = new GetData();
        String url = gd.getFromUser(true);
        System.out.println(url);
        String json = gd.getFromApi(url);
        ArrayList<String> jokes = new StringConvert().jsonToStringList(json);
        JokeList jokeList = new JokeList(jokes);
        
        System.out.println(jokeList.toString());
    }
}