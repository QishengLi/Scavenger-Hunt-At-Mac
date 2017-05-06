package com.mygdx.game.data;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Shuni on 3/22/17.
 */

/**
 * An object of a multiple choice question. It has a question statement and a list of choices.
 */
public final class MultipleChoice {

    private final String[] question;
    private final List<Answer> choices;

    public MultipleChoice(String[] myQuestion, Answer... myChoices) {
        if (myChoices.length == 0) {
            throw new IllegalArgumentException("Multiple choice questions must have at least one answer");
        }
        this.question = myQuestion;
        this.choices = Arrays.asList(myChoices);
    }

    public List<Answer> getChoices() {
        return Collections.unmodifiableList(choices);
    }

    public String[] getQuestion() {
        return question;
    }
}
