package com.mygdx.game.data;

/**
 * Created by Shuni on 3/13/17.
 */

/**
 * An Answer object is a choice of a multiple choice question.
 */
public final class Answer {

    private final String choice;
    private final String[] response;
    private boolean correct;

    Answer(String choice, String[] response, boolean correct) {
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