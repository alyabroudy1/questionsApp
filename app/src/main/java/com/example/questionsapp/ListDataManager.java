package com.example.questionsapp;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ListDataManager implements DataManagerControllable{

    static ArrayList<Scenario> scenarios;



    public ListDataManager() {
        scenarios = new ArrayList<>();
        this.loadFixtures();
    }

    public List<Scenario> getScenarios(){
        return ListDataManager.scenarios;
    }

    public void saveScenarios(Scenario scenario){
        if (!ListDataManager.scenarios.contains(scenario)){
            ListDataManager.scenarios.add(scenario);
        }
    }

    public void deleteScenarios(Scenario scenario){
        ListDataManager.scenarios.remove(scenario);
    }

    public void updateScenarios(Scenario scenario){
        for (Scenario s : ListDataManager.scenarios){
            if (s == scenario){
                ListDataManager.scenarios.remove(s);
                ListDataManager.scenarios.add(scenario);
            }
        }
    }



    public void loadFixtures() {
        Scenario s1 = new Scenario();
        ArrayList<Question> s1Quiz = new ArrayList<>();

        s1.setId(1);
        s1.setTitle("first Scenario");
        s1.setPoints(20);
        s1.setSubject("WUG");
        s1.setDescription("WUG dec");

        Question q1 = new Question();
        q1.setId(1);
        q1.setTitle("title q1");
        q1.setDescription("description q1");
        q1.setPoints(8);
        q1.setMultipleChoice(true);
        q1.setSubject(Scenario.SCENARIO_SUBJECT_WUG);

        Choice c1 = new Choice();
        c1.setId(1);
        c1.setTitle("A is correct?");
        c1.setDescription("A des");
        c1.setCorrect(false);
        q1.addChoice(c1);

        Choice c2 = new Choice();
        c2.setId(2);
        c2.setTitle("B is correct?");
        c2.setDescription("B des");
        c2.setCorrect(true);
        q1.addChoice(c2);

        Choice c3 = new Choice();
        c3.setId(3);
        c3.setTitle("C is correct?");
        c3.setDescription("C des");
        c3.setCorrect(false);
        q1.addChoice(c3);

        Choice c4 = new Choice();
        c4.setId(4);
        c4.setTitle("D is correct?");
        c4.setDescription("D des");
        c4.setCorrect(true);
        q1.addChoice(c4);

        Choice c5 = new Choice();
        c5.setId(5);
        c5.setTitle("E is correct?");
        c5.setDescription("E des");
        c5.setCorrect(false);
        q1.addChoice(c5);

        Choice c6 = new Choice();
        c6.setId(6);
        c6.setTitle("F is correct?");
        c6.setDescription("F des");
        c6.setCorrect(false);
        q1.addChoice(c6);


        Question q2 = new Question();
        q2.setId(2);
        q2.setTitle("title q2");
        q2.setDescription("description q2");
        q2.setPoints(5);
        q2.setMultipleChoice(true);
        q2.setSubject(Scenario.SCENARIO_SUBJECT_ITK);

        Choice cc1 = new Choice();
        cc1.setId(7);
        cc1.setTitle("AA is correct?");
        cc1.setDescription("A des");
        cc1.setCorrect(false);
        q2.addChoice(cc1);

        Choice cc2 = new Choice();
        cc2.setId(8);
        cc2.setTitle("BB is correct?");
        cc2.setDescription("B des");
        cc2.setCorrect(true);
        q2.addChoice(cc2);

        Choice cc3 = new Choice();
        cc3.setId(9);
        cc3.setTitle("CC is correct?");
        cc3.setDescription("C des");
        cc3.setCorrect(false);
        q2.addChoice(cc3);

        Choice cc4 = new Choice();
        cc4.setId(10);
        cc4.setTitle("DD is correct?");
        cc4.setDescription("D des");
        cc4.setCorrect(true);
        q2.addChoice(cc4);


        s1.addQuestion(q1);
        s1.addQuestion(q2);
       /* s1Quiz.add(q1);
        s1Quiz.add(q2);
        s1.setQuestions(s1Quiz);

        */

        ListDataManager.scenarios.add(s1);

    }

}
