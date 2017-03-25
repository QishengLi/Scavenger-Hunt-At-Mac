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
                new Answer("1874", "You are correct! Go to the other door of Old Main"),
                new Answer("1974", "You are wrong!"),
                new Answer("2074", "You are wrong!")));

        Qs.add(
            new MultipleChoice(
                "What is 6 * 9?",
                new Answer("1874", "You are correct! Go to the other door of Old Main"),
                new Answer("1974", "You are wrong!"),
                new Answer("2074", "You are wrong!")));

    }

    public List<MultipleChoice> getQs(){
        return this.Qs;
    }

    // Get the num'th question in the list Qs
    public MultipleChoice getNthQuestion(int num) {
        return getQs().get(num);
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