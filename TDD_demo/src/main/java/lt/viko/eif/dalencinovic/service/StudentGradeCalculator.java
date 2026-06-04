package lt.viko.eif.dalencinovic.service;

import java.util.List;

public class StudentGradeCalculator {

    public String calculateGrade(List<Integer> scores, boolean hasExtraCredit){
        if (scores== null || scores.isEmpty()){
            throw new IllegalArgumentException("Scores cannot be empty");
        }

        int total=0;

        for (Integer score :scores){

            if (score==null){
                throw new IllegalArgumentException("Score cannot bet null");
            }

            if (score<0|| score>100){
                throw new IllegalArgumentException("Invalid score: "+score);
            }

            total+=score;
        }

        double average = (double) total/ scores.size();

        if (hasExtraCredit){
            average+=5;
        }

        if (average>100){
            average=100;
        }

        if (average>=90){
            return "A";
        } else if (average>=80) {
            return "B";
        } else if (average>=70) {
            return "C";
        } else if (average>=60) {
            return "D";
        }else {
            return "F";
        }
    }
}
