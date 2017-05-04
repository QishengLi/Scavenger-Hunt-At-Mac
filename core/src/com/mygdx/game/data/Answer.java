package com.mygdx.game.data;

/**
 * Created by Shuni on 3/13/17.
 */

/**
 * An object of an answer. It has a choice and a response.
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