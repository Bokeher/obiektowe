import java.util.ArrayList;

class JokeApi{
    public static void main(String[] args) {
        ClearCmd c = new ClearCmd();
        c.clear();

        GetData gd = new GetData();
        String url = gd.getFromUser(false);
        String json = gd.getFromApi(url);
        ArrayList<String> jokes = new StringConvert().jsonToStringList(json);
        JokeList jokeList = new JokeList(jokes);
        
        // System.out.println(url);
        c.clear();
        System.out.println("Wylosowane żarty: ");
        System.out.println(jokeList.toString());
        
        boolean[] saveData = gd.getSaveData(jokeList);
        SaveJokesToFile saveToFile = new SaveJokesToFile(saveData, jokeList);
        saveToFile.save();
        
    }
}