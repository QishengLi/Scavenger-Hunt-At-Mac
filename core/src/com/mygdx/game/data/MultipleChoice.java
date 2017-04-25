package com.mygdx.game.data;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Shuni on 3/22/17.
 */
public final class MultipleChoice {

    private final String[] question;
    private final List<Answer> choices;
   // private final String[] clue;

    public MultipleChoice(String[] myQuestion, Answer... myChoices) {
        if (myChoices.length == 0) {
            throw new IllegalArgumentException("Multiple choice questions must have at least one answer");
        }
        this.question = myQuestion;
        this.choices = Arrays.asList(myChoices);
        //this.clue = myClue;
    }

    public List<Answer> getChoices() {
        return Collections.unmodifiableList(choices);
    }

    public String[] getQuestion() {
        return question;
    }

//    public String[] getClue() {
//        return clue;
//    }

    public String getCorretResponse() {

        String result = "";
        if (choices.size() != 0) {
            for (Answer answer : choices) {
                if (answer.isCorrect()) {
                    result = ("" + Arrays.asList(answer.getResponse())).replaceAll("(^.|.$)", "").replace(", ", "  " );
                }
            }
        }
        return result;
    }
}
