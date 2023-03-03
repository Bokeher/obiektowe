import java.util.List;

public class Calculations {
    private List<Integer> gradeList;
    private List<Integer> weightList;

    public Calculations(List<Integer> gradeList, List<Integer> weightList) {
        this.gradeList = gradeList;
        this.weightList = weightList;
    }

    public double getWeightenedAverage() {
        double gradeValue = 0;
        double weightSum = 0;
        for (int i = 0; i < gradeList.size(); i++) {
            int grade = gradeList.get(i);
            int weight = weightList.get(i);

            gradeValue += grade*weight;
            weightSum += weight;
        }
        double average = gradeValue/weightSum;
        return average;
    }

    public double getWightenedDeviation() {
        double weightSum = 0;        
        double score = 0;
        for (int i = 0; i < gradeList.size(); i++) {
            int grade = gradeList.get(i);
            int weight = weightList.get(i);

            score += (grade*grade)*weight;
            weightSum += weight;
        }
        double average = getWeightenedAverage();
        System.out.println(score+"/"+weightSum+"-"+average);
        double res = Math.sqrt(score/weightSum - average); 
        return res;
        //https://zadaniacke.pl/teoria/odchylenie-standardowe-wazone/
    }

    public double getDeviation() {
        double avg = getArythmeticAverage();
        double score = 0;
        int i;
        for (i = 0; i < gradeList.size(); i++) {
            int grade = gradeList.get(i);

            score += (grade*grade);
        }

        return Math.sqrt((score/i)-(avg*avg));
        // todo - test
    }
    
    public double getArythmeticAverage() {
        double sum = 0;
        int i = 0;
        for (i = 0; i < gradeList.size(); i++) {
            int grade = gradeList.get(i);

            sum += grade;
        }
        return sum / i;
    }
}