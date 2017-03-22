package com.mygdx.game.data;

import java.util.ArrayList;

/**
 * Created by Shuni on 3/22/17.
 */
public class MultipleChoice {

    private String question;
    private ArrayList<ChoiceResponseTuple> choices;

    public MultipleChoice(String question, ArrayList<ChoiceResponseTuple> choices) {
        this.question = question;
        this.choices = choices;
    }

    public ArrayList<ChoiceResponseTuple> getChoices() {
        return choices;
    }

    public String getQuestion() {
        return question;
    }

    public void setChoices(ArrayList<ChoiceResponseTuple> choices) {
        this.choices = choices;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}
