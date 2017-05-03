package com.mygdx.game.data;

import java.util.ArrayList;
import java.util.List;

/**
* Created by Zhaoqi on 5/1/17.
*/
public class ClueText {

    private List<String> clues = new ArrayList<>();

    public ClueText() { }

    public void initClues() {

        //Door 1 at Kirk
        clues.add("Macalester College has renamed its \"Humanities Building\" NEILL HALL" +
                " after Edward Duffield Neill, " +
                "founder and first president of the college, in 2013." +
                "You might find something helpful there!"
        );

        //Door 2 at Neill
        clues.add("After the tunnel was built, the JANET WALLACE BUILDING became a center for experiments with robots.");

        //Door 3 at J-Wall
        clues.add("Wind... How can wind help you destroy these enemies? Go find something on this campus gathers the power of wind.");

        //Door 4 at Wind Turbine
        clues.add("In the development of robots, environmentalists in our team deal with environmental-friendliness. Maybe there are clues in their places.");

        //Door 5 at Olin Rice
        clues.add("A mysterious man is asking you to find clues hidden under 3 \"Macalester\" Signs on campus. " +
                "Hint: the first one is on Grand Ave.");

        //Sign on Grand
        clues.add("Remember the answer (number) to the question: If you break a mirror, how many years of bad luck might you get? " +
                "Go to next sign on Macalester Street and St. Clair Avenue.");

        //Sign on Macalester & St. Clair
        clues.add("What is the loneliest number? Remember the two numbers you have got! Please go and find the last Macalester Sign.");

        //Sign on St. Clair & Snelling
        clues.add("The last question is \"What does a stitch save in time?\" Now you have got all three numbers. Go back to where you met the mysterious man.");

        //Olin Rice
        clues.add("You were trapped by the robots! There are fewer robots around the campus center.");

        //CC
        clues.add("You only have 2'30\" to travel back in time. Remember, you need to convince the administrators, computer scientists and musicians not to complete the project " +
                "which would trigger the self-conciousness of the robots!");

        //Weyerhaeuser
        clues.add("The administrators have given the computer scientists and musicians permission. You need to convince them first.");

        //Olin Rice
        clues.add("Enter the building through the other door. Hint: There is a rear door on the other side of Olin Rice.");

        //Olin Rice Rear Door
        clues.add("Shelley's testing the robot. Go and find her.");

        //Last one
        clues.add("You have go back to 1874 when Macalester was founded.");


    }

    public String getNextClue(String curClue) {
        return clues.get(clues.indexOf(curClue) + 1);
    }
}



