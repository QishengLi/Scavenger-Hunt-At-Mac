package com.mygdx.game.data;

import javafx.util.Pair;

/**
 * Created by Shuni on 3/13/17.
 */
public final class Answer {

    private final String choice;
    private final Pair<String, Integer> response;

    public Answer(String choice, Pair<String, Integer> response) {
        this.choice = choice;
        this.response = response;
    }

    public String getChoice(){
        return this.choice;
    }

    public Pair<String, Integer> getResponse(){
        return this.response;
    }
}