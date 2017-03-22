package com.mygdx.game.data;

import java.util.ArrayList;

/**
 * Created by Shuni on 3/22/17.
 */
public class QuestionText {

    private ArrayList<MultipleChoice> Qs = new ArrayList<>();
    private MultipleChoice Q0;
    private MultipleChoice Q1;

    public QuestionText() {}

    public void initQuestions() {

        ArrayList<ChoiceResponseTuple> tuple1 = new ArrayList<>();
        tuple1.add(new ChoiceResponseTuple("1874", "You are correct!"));
        tuple1.add(new ChoiceResponseTuple("1974", "You are wrong!"));
        tuple1.add(new ChoiceResponseTuple("2074", "You are wrong!"));

        Q0 = new MultipleChoice("When was Macalester founded?", tuple1);
        Qs.add(Q0);

        ArrayList<ChoiceResponseTuple> tuple2 = new ArrayList<>();
        tuple2.add(new ChoiceResponseTuple("Shuni", "You are correct!"));
        tuple2.add(new ChoiceResponseTuple("Zhaoqi", "You are correct!"));
        tuple2.add(new ChoiceResponseTuple("Qisheng", "You are correct!"));

        Q1 = new MultipleChoice("Who is the smartest?", tuple2);
        Qs.add(Q1);


    }

    public ArrayList<MultipleChoice> getQs(){
        return this.Qs;
    }

    public MultipleChoice getQuestion(int num) {
        return getQs().get(num);
    }
}
