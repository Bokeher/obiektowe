package KoloFortuny;

public class Questions extends Question implements QuestionsImp {

    @Override
    public boolean isCorrect(String givenAnswer) {
        if(givenAnswer.equals("a")) {
            if(answerA.equals(correctAnswer)) return true;
        } else if(givenAnswer.equals("b")) {
            if (answerB.equals(correctAnswer)) return true;
        } else if(givenAnswer.equals("c")) {
            if(answerC.equals(correctAnswer)) return true;
        } else if(givenAnswer.equals("d")) {
            if(answerD.equals(correctAnswer)) return true;
        }
        return false;
    }

    public Questions(String d) {
        String[] data = d.split(";");

        this.question = data[0];
        this.category = data[1];
        this.answerA = data[2];
        this.answerB = data[3];
        this.answerC = data[4];
        this.answerD = data[5];
        this.correctAnswer = data[6];
        this.difficulty = data[7];
    }
}