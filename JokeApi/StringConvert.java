import java.util.ArrayList;

public class StringConvert {


    public ArrayList<String> jsonToStringList(String json) {
        json = json.substring(1, json.length()-1);

        ArrayList<Integer> startingIndex = new ArrayList<>();
        ArrayList<Integer> endingIndex = new ArrayList<>();

        boolean ignore = false;
        for(int i = 0; i < json.length(); i++) {
            if(json.charAt(i) == '{' && !ignore) {
                startingIndex.add(i);
                ignore = true;
            }
            if(json.charAt(i) == '}' && ignore) {
                endingIndex.add(i);
                ignore = false;
            }
        }

        ArrayList<String> jokes = new ArrayList<>();
        for(int i = 0; i < startingIndex.size(); i++) {
            jokes.add(json.substring(startingIndex.get(i)+1, endingIndex.get(i)-1));
        }
        return jokes;
    }
}
