package com.mygdx.game.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shuni on 3/22/17.
 */
public class QuestionText {

    private List<Object> Qs = new ArrayList<>();

    public QuestionText() { }

    public void initQuestions() {

        //Door at Kirk
        Qs.add(
            new MultipleChoice(
                new String[]{"If you are reading this, it is very likely that Macalester has not survived the robot revolt. " +
                        "The technology you will be using is very dangerous, and it should not fall into the wrong hands. " +
                        "You must prove yourself to be a true Macalester Scot.",
                        "When was Macalester founded?"},
                new Answer("1770", new String[]{"Ouch! An electric shock! I think the answer was wrong. (Hit the door to answer the question again)"},false),
                new Answer("1916", new String[]{"Ouch! An electric shock! I think the answer was wrong. (Hit the door to answer the question again)"},false),
                new Answer("1874", new String[]{"Yes!","The first experiment was conducted somewhere on Macalester Street. " +
                        "Find it! (Hint: it’s 2037. How long has it been since Mac’s founding?)"},true)
            )
        );

//        Qs.add(
//                "hello world"
//        );


        //Door at Theater.
        Qs.add(
            new MultipleChoice(
                new String[]{"What a familiar place… " +
                        "It is winter here more than half of the year. " +
                        "The first robot we developed helped the school build tunnels between buildings, " +
                        "while people could hide indoors and avoid the cold. ",
                        "Which building was connected to Theater in 2017?"},
                new Answer("Olin Rice Science Building", new String[]{"Ouch! An electric shock! I think the answer was wrong. (Hit the door to answer the question again)\n"},false),
                new Answer("Old Main Humanity Building", new String[]{"Ouch! An electric shock! I think the answer was wrong. (Hit the door to answer the question again)\n"},false),
                new Answer("Janet Wallace Art Building", new String[]{"Yes! After the tunnel was built, the Janet Wallace building became a center for experiments with robots",
                        " Think before you take the road. Some roads have more enemies. (Hint: where are all the early robot prototypes?)"},true)
            )
        );

        //Door at Music.
        Qs.add(
            new MultipleChoice(
                new String[]{"I’m glad you found this place. " +
                        "Somewhere in the middle of the 2020s, " +
                        "some Mac musicians collaborated with CS students in an attempt to use robots to recognize different genres of music. " +
                        "These musicians wanted new inspirations for music composition.",
                        "One of these musicians was Shelley Hanson. " +
                                "She was a key member of the team that eventually created the robot that had the capability to recognize music. " +
                                "It was a huge technical breakthrough.",
                        "With the help of the robot, Shelley created her masterpiece, " +
                                "a new composition that won her numerous awards. She became the director of a Mac ensemble.",
                        "Which ensemble is it?"},

                new Answer("African Music Ensemble", new String[]{"Don't give up. People are counting on you! Try again."},false),
                new Answer("Wind Ensemble", new String[]{"Correct! Wind... How can wind help you destroy these enemies? " +
                        "Something on this campus gathers the power of wind. Go find it!"},true),
                new Answer("Asian Music Ensemble", new String[]{"Don't give up. People are counting on you! Try again."},false),
                new Answer("Concert Choir", new String[]{"Don't give up. People are counting on you! Try again."}, false)
            )
        );

        //Door at Wind Turbine.
        //TODO: add sign to tell people about the toxic music...
        Qs.add(
            new MultipleChoice(
                new String[]{"What is the initial use of this wind turbine?"},
                new Answer("Environmental Protection", new String[]{"Try again"},false),
                new Answer("Campus Icon",new String[]{"Try again"},false),
                new Answer("Electricity Generation",new String[]{"Yes!","Why are robots near the wind turbine friendly to me? Hmm… ",
                "In the development of robots, environmentalists in our team deal with environmental-friendliness. Maybe there are clues in their places. "}, true)
            )
        );


        Qs.add(
            new MultipleChoice(
                new String[]{"If you break a mirror, " +
                        "how many years of bad luck might you get? Remember this number!"},
                    new Answer("OK", new String[]{"Go to next Mac sign"}, true)
            )
        );


        //Door at Olin Rice.
        Qs.add(
            new MultipleChoice(
                new String[]{"What are the three numbers? " +
                        "1. Number of years of bad luck fo breaking a mirror. " +
                        "2. The loneliest number. " +
                        "3. What a stitch in time saves."},
                new Answer("917",new String[]{ "Not really... Try again!"}, false),
                new Answer("612", new String[]{"Not really... Who is your math professor? (Qisheng: I'm just kidding...)"}, false),
                new Answer("719", new String[]{"Yes! But... wait! You can not go to ES department any more. " +
                        "The only hope is to go to the oldest building at Macalester to find ..."}, true)
            )
        );

//        Qs.add(
//            new MultipleChoice(
//                new String[]{"What do you think is the reason that there are enemies?"},
//                new Answer("Music", new String[]{"Good, my boy. 写不动了..."}, true),
//                new Answer("The scavenger hunt game is too difficult!",new String[]{ "Keep going."}, false)
//            )
//        );
    }

    public List<Object> getQs(){
        return this.Qs;
    }

    // Get the num'th question in the list Qs
    public Object getNthQuestion(int num) {
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