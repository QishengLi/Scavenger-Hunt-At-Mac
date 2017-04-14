package com.mygdx.game.data;

import javafx.util.Pair;

import java.util.List;

/**
 * Created by Shuni on 3/13/17.
 */
public final class Answer {

    private final String choice;
    private final String[] response;
    private boolean correct;

    public Answer(String choice, String[] response, boolean correct) {
        this.choice = choice;
        this.response = response;
        this.correct = correct;
    }

    public String getChoice(){
        return this.choice;
    }

    public String[] getResponse(){
        return this.response;
    }

    public boolean isCorrect() {
        return correct;
    }
}