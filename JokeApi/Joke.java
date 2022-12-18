public class Joke implements JokeImp{
    private String content = "";

    public Joke(String content) {
        content = content.replaceAll("\\\\n", "\n");
        content = content.replace("\\\"", "\"");
        this.content = content;
    }
    
    @Override
    public String getContent() {
        return content;
    }
}
