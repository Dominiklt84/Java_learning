package lt.viko.eif.dalencinovic.service;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StudentGradeCalculatorTest {

    private StudentGradeCalculator calculator= new StudentGradeCalculator();

    @Test
    void shouldThrowExceptionForEmptyScores(){

        assertThrows(
                IllegalArgumentException.class,
                ()-> calculator.calculateGrade(List.of(), false)
        );
    }

    @Test
    void shouldThrowExceptionIfScoreIsNull(){
        List<Integer> scores= new ArrayList<>();
        scores.add(null);
        assertThrows(
                IllegalArgumentException.class,
                ()-> calculator.calculateGrade(scores, false)
        );
    }

    @Test
    void shouldThrowExceptionIfScoreIsInvalid(){
        List<Integer> scores = new ArrayList<>();
        scores.add(120);
        assertThrows(
                IllegalArgumentException.class,
                ()-> calculator.calculateGrade(scores, false)
        );
    }

//    @Test
//    void shouldAddTotal(){
//        List<Integer> scores = new ArrayList<>();
//        scores.add(66);
//        scores.add(77);
//        assertThrows(
//                IllegalArgumentException.class,
//                ()-> calculator.calculateGrade(scores, false)
//        );
//    }


}