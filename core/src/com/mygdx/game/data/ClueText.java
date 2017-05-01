package com.mygdx.game.data;

import java.util.ArrayList;
import java.util.List;

/**
* Created by hys1435 on 5/1/17.
*/
public class ClueText {

    private List<String> clues = new ArrayList<>();

    public ClueText() { }

    public void initClues() {

        clues.add("You could click the button to show the current clue.");

        //Door 1 at Kirk
        clues.add("Great!" +
                "Actually, Macalester College has renamed its \"Humanities Building\" NEILL HALL" +
                " after Edward Duffield Neill, " +
                "founder and first president of the college, in 2013." +
                "You might find something helpful there!"

        );

        //TODO: manually type clues

        clues.add("Clue 2: J-Wall");

        clues.add("Clue 3: Wind Turbine");

        clues.add("Clue 4: Olin-Rice");

        clues.add("Clue 5: ");


    }

    public List<String> getClues(){
        return this.clues;
    }

    public String getNextClue(String curClue) {
        return clues.get(clues.indexOf(curClue) + 1);
    }
    //Get total number of clues.
    public int getNumClues(){
        return clues.size();
    }
}



