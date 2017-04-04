package com.mygdx.game.data;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shuni on 3/22/17.
 */
public class QuestionText {

    private List<MultipleChoice> Qs = new ArrayList<>();

    public QuestionText() { }

    public void initQuestions() {

        //Door at Kirk
        Qs.add(
            new MultipleChoice(
                "When was Macalester founded?",
                new Answer("1874",
                        new Pair<>("You are correct! Please look at how long it has passed since the foundation of Macalester College. " +
                                "Go to where the number indicates on an old street, Macalester Street.", 1)),
                new Answer("1917", new Pair<>("You are wrong! Please try again.", 0)),
                new Answer("1974", new Pair<>("You are wrong! Please try again.", 0))
            )
        );

        //Door at Theater.
        Qs.add(
            new MultipleChoice(
                "What is closely related to theater?",
                new Answer("Science", new Pair<>("Not really... Please try again.",0)),
                new Answer("Music", new Pair<>("Wonderful! Please go to Music Building for the next clue.",1)),
                new Answer("idea: Click to get the next clue.", new Pair<>("Please go to the building that is \"closely related\" " +
                        "to the theater building.",0))
            )
        );

        //Door at Music.
        Qs.add(
            new MultipleChoice(
                "Things start to unfold. It seems like there's some music that causes this invasion. " +
                        "Shelley Hanson is one of the musicians here at Mac. " +
                        "Which of the following ensembles is under her direction at Macalester?",
                new Answer("African Music Ensemble", new Pair<>("You are wrong! Please try again.",0)),
                new Answer("Wind Ensemble", new Pair<>("Correct! Wind... It reminds me of some place special on campus... " +
                        "Where is it? It's not a building. Go to the place for the next clue.",1)),
                new Answer("Asian Music Ensemble", new Pair<>("Wrong answer. Please try again.",0)),
                new Answer("Concert Choir", new Pair<>("Not correct. Hint: check out the website!", 0))
            )
        );

        //Door at Wind Turbine.
        //TODO: add sign to tell people about the toxic music...
        Qs.add(
            new MultipleChoice(
                "The wind is so heavy... (blabla...) Which of the following is NOT true of the wind turbine?",
                new Answer("It is installed in April 2003.", new Pair<>("",0)),
                new Answer("It is the second wind turbine of its size installed in the city of St. Paul.", new Pair<>("Your answer is correct! " +
                        "We actually had the FIRST large wind turbine (total height of 103') installed in the city in 2003. " +
                        "Next step: find people who know this well. It might help uncover the secret.",1)),
                new Answer("It provides an educational opportunity for students, staff and faculty learn about wind energy, " +
                        "wind patterns, geography and the practicality of wind turbines in urban areas.", new Pair<>("",0)),
                new Answer("It is used to generate renewable energy to use on campus.", new Pair<>("", 0))
            )
        );

        //Door at Olin Rice.
        Qs.add(
            new MultipleChoice(
                "What are the three numbers? " +
                        "1. Number of years of bad luck fo breaking a mirror. " +
                        "2. The loneliest number. " +
                        "3. What a stitch in time saves.",
                new Answer("917", new Pair<>("Not really... Try again!", 0)),
                new Answer("612", new Pair<>("Not really... Who is your math professor? (Qisheng: I'm just kidding...)", 0)),
                new Answer("719", new Pair<>("Yes! But... wait! You can not go to ES department any more. " +
                        "The only hope is to go to the oldest building at Macalester to find ...", 1))
            )
        );

        Qs.add(
            new MultipleChoice(
                "What do you think is the reason that there are enemies?",
                new Answer("Music", new Pair<>("Good, my boy. 写不动了...", 1)),
                new Answer("The scavenger hunt game is too difficult!", new Pair<>("Keep going.", 0))
            )
        );
    }

    public List<MultipleChoice> getQs(){
        return this.Qs;
    }

    // Get the num'th question in the list Qs
    public MultipleChoice getNthQuestion(int num) {
        return getQs().get(num);
    }

    //Get total number of questions.
    public int getNumQuestions(){
        return Qs.size();
    }
}


/*
[
    {
        "question": "When was Macalester founded?",
        "answers": [
            { "choice": "1874", "response": "You are correct! Go to the other door of Old Main" },
            { "choice": "1974", "response": "You are wrong!" },
            { "choice": "2074", "response": "You are wrong!" }
        ]
    }
]
 */