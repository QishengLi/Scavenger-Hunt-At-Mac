package com.mygdx.game.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shuni on 3/22/17.
 */
public class QuestionText {

    private List<MultipleChoice> Qs = new ArrayList<>();

    public QuestionText() { }

    public void initQuestions() {

        Qs.add(
            new MultipleChoice(
                "When was Macalester founded?",
                new Answer("1874", "You are correct! Please go to library!"),
                new Answer("1974", "You are wrong! Please try again."),
                new Answer("2074", "You are wrong! Please try again.")));

        Qs.add(
            new MultipleChoice(
                "How many librarians are here to help students? (Hint: listed on Macalester Website)",
                new Answer("24", "You are wrong! Please try again."),
                new Answer("25", "You are correct! Go to the building where Department of Philosophy is located."),
                new Answer("26", "You are wrong! Please try again.")));

        Qs.add(
            new MultipleChoice(
                "Which of the following department is not in this building?",
                new Answer("Classics", "You are wrong! Please try again."),
                new Answer("History", "You are wrong! Please try again."),
                new Answer("Linguistics", "You are correct! Please go to CC for your next clue.")));

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