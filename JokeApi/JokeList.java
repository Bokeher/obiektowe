import java.util.ArrayList;

public class JokeList implements JokeListImp {
    private ArrayList<Joke> jokeList = new ArrayList<Joke>();

    public JokeList(ArrayList<String> jokes) {
        for (String joke : jokes) {
            String searchingString1 = "\"joke\": ";
            String searchingString2 = "\"flags\":";
            int start = joke.indexOf(searchingString1)+searchingString1.length()+1;
            int end = joke.indexOf(searchingString2) - 15;
            
            jokeList.add(new Joke(joke.substring(start, end)));
        }
    }

    @Override
    public ArrayList<Joke> getList() {
        return jokeList;
    }

    @Override
    public String toString() {
        String res = "";
        for (Joke joke : jokeList) {
            res += joke.getContent()+"\n";
        }
        return res;
    }

}
