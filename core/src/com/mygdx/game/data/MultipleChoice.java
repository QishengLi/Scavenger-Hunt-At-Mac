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

    public MultipleChoice(String[] question, Answer... choices) {
        if(choices.length == 0) {
            throw new IllegalArgumentException("Multiple choice questions must have at least one answer");
        }
        this.question = question;
        this.choices = Arrays.asList(choices);
    }

    public List<Answer> getChoices() {
        return Collections.unmodifiableList(choices);
    }

    public String[] getQuestion() {
        return question;
    }
}
