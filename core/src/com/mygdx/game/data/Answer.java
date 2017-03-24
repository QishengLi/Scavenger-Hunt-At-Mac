package com.mygdx.game.data;

/**
 * Created by Shuni on 3/13/17.
 */
public final class Answer {

    private final String choice;
    private final String response;

    public Answer(String choice, String response) {
        this.choice = choice;
        this.response = response;
    }

    public String getChoice(){
        return this.choice;
    }

    public String getResponse(){
        return this.response;
    }
}