import java.util.ArrayList;

class JokeApi{
    public static void main(String[] args) {
        ClearCmd c = new ClearCmd();
        c.clear();

        GetData gd = new GetData();
        String url = gd.getFromUser(false);
        String json = gd.getFromApi(url);
        while(json == null) {
            System.out.println("Ponawiam probe pobrania danych");
            json = gd.getFromApi(url);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            }
        } 
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