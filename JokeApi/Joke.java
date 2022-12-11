public class Joke implements JokeImp{
    private String content = "";

    public Joke(String content) {
        this.content = content;
        // make \n work
    }
    
    @Override
    public String getContent() {
        return content;
    }
}
