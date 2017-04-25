package com.mygdx.game.data;

import com.mygdx.game.screens.Play;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shuni on 3/22/17.
 */
public class QuestionText {

    private List<Object> Qs = new ArrayList<>();

    public QuestionText() { }

    public void initQuestions() {

        //Door 1 at Kirk
        Qs.add(
            new MultipleChoice(
                new String[]{"An LCD screen.",
                            "I don't think I have seen this since 2018. It's all aerial imagery these days.",
                            "\"If you are reading this, it is very likely that Macalester has not survived the robot revolt. ",
                            "The technology you will be using is very dangerous, and it should not fall into the wrong hands.",
                            "You must prove yourself to be a true Macalester Scot.\"",
                            "When was Macalester founded?"
                },

                new Answer("1770", new String[]{"Ouch! An electric shock! I think the answer was wrong. Hit the door to answer the question again.\n" +
                        "Keep an eye on your life bar. Each wrong answer will cost you a life. "},false),
                new Answer("1916", new String[]{"Ugh! You are wrong. Hit the door to answer the question again.\n" +
                        "Keep an eye on your life bar. Each wrong answer will cost you a life. "},false),
                new Answer("1874", new String[]{"Great!",
                        "The first experiment was conducted somewhere on Macalester Street. Find it!",
                        "Hint: It has been 163 years since Mac was founded. Art Building is situated at 130 Macalester St."},true)
            )
        );

//        Qs.add(
//                "hello world"
//        );


        //Door 2 at Theater.
        Qs.add(
            new MultipleChoice(
                new String[]{"What a familiar place... ",
                        "It is winter here more than half of the year. The first robot we developed helped the school build tunnels between buildings, while people could hide indoors and avoid the cold.",
                        "Which building was connected to Theater in 2017?"
                },
                new Answer("Olin Rice Science Building", new String[]{"Ouch! An electric shock! I think the answer was wrong. (Hit the door to answer the question again)\n"},false),
                new Answer("Janet Wallace Art Building", new String[]{"Yes! After the tunnel was built, the Janet Wallace building became a center for experiments with robots.",
                        "You might find something helpful there."},true),
                    new Answer("Old Main Humanity Building", new String[]{"Ouch! An electric shock! I think the answer was wrong. (Hit the door to answer the question again)\n"},false)
                    )
        );

        //Door 3 at Music.
        Qs.add(
            new MultipleChoice(
                new String[]{"Another LCD screen.",
                        "I'm glad you found this place. ",
                        "Somewhere in the middle of the 2020s, " +
                            "some Mac musicians collaborated with computer science students in an attempt to use robots to recognize different genres of music. " +
                            "These musicians wanted new inspirations for music composition.",
                        "One of these musicians was Shelley Hanson. " +
                                "She was a key member of the team that eventually created the robot that had the capability of recognizing music. " +
                                "It was a huge technical breakthrough.",
                        "With the help of the robot, Shelley created her masterpiece, " +
                                "a new composition that won her numerous awards. She became the director of a Mac ensemble.",
                        "Which ensemble is it?"},

                new Answer("African Music Ensemble", new String[]{"Don't give up. People are counting on you! Try again."},false),
                new Answer("Wind Ensemble", new String[]{"Correct! Wind... How can wind help you destroy these enemies? ",
                        "Something on this campus gathers the power of wind. Go find it!"}, true),
                new Answer("Asian Music Ensemble", new String[]{"Don't give up. People are counting on you! Try again."},false),
                new Answer("Concert Choir", new String[]{"Don't give up. People are counting on you! Try again."}, false)
            )
        );

        //Door 4 at Wind Turbine.
        Qs.add(
            new MultipleChoice(
                new String[]{"A robot jumps out of the grass.",
                            "\"I do not mean to hurt you!\"",
                            "You received a black box...",
                             "\"Wait, what is this for? Why did you give me this box?\"",
                            "The robot self-destructed.",
                            "Another LCD screen.",
                            "What is the initial use of this wind turbine?"},
                new Answer("Environmental Protection", new String[]{"Yes!",
                        "Why are robots near the wind turbine friendly to me? Hmm... ",
                        "In the development of robots, environmentalists in our team deal with environmental-friendliness. Maybe there are clues in their places."},true),
                new Answer("Campus Icon",new String[]{"Ouch! An electric shock! I think the answer was wrong. (Hit the door to answer the question again)\n"},false),
                new Answer("Decoration",new String[]{"Ouch! An electric shock! I think the answer was wrong. (Hit the door to answer the question again)\n"}, false)
            )
        );

        //Door 5 at Olin Rice
        Qs.add(
            new MultipleChoice(
                new String[]{"A man is standing at the front gate... he is unharmed!",
                        "\"Who are you? Why are you here? Why aren't you being attacked by the robots?\"",
                        "\"I have been waiting for you. I have an important clue for you.",
                        "But to test your determination to save this campus, you must find three clues hidden under three \"Macalester\" signs. Come back to me after you have the answer.\"",
                        "\"Wait... but who are you?\""
                },
                new Answer("OK", new String[]{"(No Response...) Please go to the sign on Grand Avenue first."}, true)
            )
        );

        //Door 6 at Grand Macalester Sign
        Qs.add(
            new MultipleChoice(
                new String[]{"Great to see you here."},
                new Answer("OK", new String[]{"If you break a mirror, how many years of bad luck might you get? Remember this number!",
                        "Please go to the next sign on Macalester Street and St. Clair Avenue."}, true)
            )
        );

        //Door 7 at St. Clair Sign
        Qs.add(
           new MultipleChoice(
               new String[]{"This is the right sign."},
               new Answer("OK", new String[]{"What is the loneliest number? Remember this number! Please go and find the last Macalester Sign. ",
                       "Again, remember the two numbers you have got!"}, true)
           )
        );

        //Door 8 at St. Clair/Snelling Sign
        Qs.add(
            new MultipleChoice(
                new String[]{"Still remembering the previous two numbers?"},
                new Answer("Yes!", new String[]{"What does a stitch save in time?",
                        "Now you have got all three numbers. Go back to where you met the mysterious man."}, true)
                )
        );

        //Door 9 at Olin Rice again
        Qs.add(
            new MultipleChoice(
                new String[]{"\"Hey, I'm back with the clues you asked for. Could you tell me who you are now?\"",
                        "\"What are the three numbers?\""},
                new Answer("9, 1, 7", new String[]{"Not correct. Please try again."}, false),
                new Answer("7, 0, 9", new String[]{"Not correct. Please try again."}, false),
                new Answer("9, 0, 7", new String[]{"Not correct. Please try again."}, false),
                new Answer("7, 1, 9", new String[]{"\"No, it's not right.\", ",
                    "\"How is it possible? I solved all the clues! These are the right answers!\"",
                    "Wait, his smile looks weird...",
                    "Robots! They are coming from all directions! It's a trap!",
                    "There are fewer of them around the campus center. I should go there!"}, true)
                )
        );


        //Door 10 at CC.
        Qs.add(
            new MultipleChoice(
                new String[]{
                "Wait, is that an old man is sitting in Cafe Mac? What's he doing?",
                "\"Young man, show me your box.\"",
                "Box? Oh right, the one given to me from the robot by the wind turbine.",
                "\"This... this really is it! I've been waiting for the person with this old time machine for long!\"",
                "\"When the group of Mac faculties created the robot that can recognize music, they left the robot in Olin Rice 254, for students to play with it freely. " +
                        "One day, someone played Shelley's award-winning music composition, the robot gained self-consciousness in the midst of recognizing that musical piece.\"",
                " \"That was the first robot that became self-conscious. It broke into Professor Paul Cantrell's office and stole the master code for all robots. And then, the robot revolt happened.\"",
                "\"But how do you know all this? Who are you?\"",
                "\"I'm a robot, too.\"",
                "No! That sent a chill down my spine. I jumped back away from the robot.",
                "\"Richard was in the team that created the robot that could recognize music. He was always aware of the danger that the capability to recognize music could turn into " +
                        "self-consciousness if the robot is exposed to truly exceptional music pieces. He created me, and placed me in Olin Rice 254.\"",
                " \"I'm of a much older model, and I'm not connected to Professor Paul Cantrell's central control.\"", "\"What? That's against federal law. Richard can be jailed for doing this.\""
                },
                new Answer("OK", new String[]{
                        "\"And yet, he chose to take the risk. And you, you must not fail him. You have to go back in time and prevent their project from happening. " +
                                "You need to convince the administrators, computer scientists and musicians not to finish their project.\"",
                        "\"administrators, computer scientists and musicians... OK! I will do it!\"",
                        "\"Unfortunately the time machine is too old. It is broken now. I could only make it work for 5 minutes. After that, my energy will run out and I will be destructed. And you,\"",
                        "\"You will be stuck in 2017 forever.\""}, true)
            )
        );

        //Door 11 at Weyerhaeuser
        Qs.add(
            new MultipleChoice(
                new String[]{"\"Please do not do this project! It will hurt people!\""},
                new Answer("OK", new String[]{"Somehow, they are convinced."}, true)
            )
        );

        //Door 12 at Olin Rice again
        Qs.add(
            new MultipleChoice(
                new String[]{"\"Please do not do this project! It will hurt people!\""},
                new Answer("OK", new String[]{"Somehow, they are convinced."}, true)
            )
        );

        //Door 13 at Art
        Qs.add(
                new MultipleChoice(
                        new String[]{"\"Please do not do this project! It will hurt people!\"",
                        "\"But I need this robot! I need it to create my dream music piece. I cannot do it without a robot " +
                                "that can recognize and analyze music. I don’t want to give up at this stage.\"",
                        "\"Artificial Intelligence may help people, but if we develop robots without any caution, they will destroy everything! " +
                                "I've seen them do it. Please stop!\" I showed Shelley the time machine.",
                        "......",
                        "\"I don't believe you.\"",
                        "\"...alright.\"",
                        "I look at the time machine.",
                        "20 seconds left",
                        "I sighed.",
                        "I entered the years \"1874\", and pushed the button on the time machine again.",
                        "Congratulations. You win the game!"},
                        new Answer("OK", new String[]{"Congratulations. You win the game!"}, true)
                )
        );
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