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
                "When was Macalester founded??",
                new Answer("1770",
                        new Pair<>("Ugh! You are wrong. Hit the door to answer the question again.\n" +
                                "Keep an eye on your life bar. Each wrong answer will cost you a life", 0)),
                new Answer("1874", new Pair<>("Great! Indeed, it has been 163 years since Mac was founded. \n" +
                        "Does this number, 163, ring a bell? Someone hides a clue for you on 163 Macalester Street.", 1)),
                new Answer("1980", new Pair<>("Ugh! You are wrong. Hit the door to answer the question again.\n" +
                        "Keep an eye on your life bar. Each wrong answer will cost you a life", 0))
            )
        );

        //Door at Theater.
        Qs.add(
            new MultipleChoice(
                "You made it! Winter is coming. Our smart ancestors once built tunnels between buildings to avoid the coldness. Which building is connected to Theater?",
                new Answer("Olin Rice Science Building", new Pair<>("Not really... Please try again.",0)),
                new Answer("Old Main Humanity Building", new Pair<>("Not really... Please try again.",0)),
                new Answer("Janet Wallace Art Building", new Pair<>("Wonderful! Go to J-Wall for the next clue. " +
                        "Think before you take the road. Some roads have more enemies.",1))
            )
        );

        //Door at Music.
        Qs.add(
            new MultipleChoice(
                "Things start to unfold. Years ago, many music majors figured out that some weird music is the weapon of aliens. They can control and mess up people’s minds. \n" +
                        "Shelley Hanson, one of the musicians at Mac, came up with an idea to destroy this music. She hides this valuable clue in the name of her ensemble. " +
                        "Which ensemble is under her direction at Macalester?",
                new Answer("African Music Ensemble", new Pair<>("Don't give up. People are counting on you! Try again.",0)),
                new Answer("Wind Ensemble", new Pair<>("Correct! Wind... How can wind help you destroy these enemies? " +
                        "Something on this campus gathers the power of wind. Go find it!",1)),
                new Answer("Asian Music Ensemble", new Pair<>("Don't give up. People are counting on you! Try again.",0)),
                new Answer("Concert Choir", new Pair<>("Don't give up. People are counting on you! Try again.", 0))
            )
        );

        //Door at Wind Turbine.
        //TODO: add sign to tell people about the toxic music...
        Qs.add(
            new MultipleChoice(
                "Shelley notices that grass, flowers and other organisms near the wind turbine are not affected by this invasion! Why?",
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