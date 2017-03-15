package com.mygdx.game;

/**
 * Created by Shuni on 3/13/17.
 */
public class ChoiceResponseTuple {

    private String choice;
    private String response;

    public ChoiceResponseTuple(String choice, String response) {
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