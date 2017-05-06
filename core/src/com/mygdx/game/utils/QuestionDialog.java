package com.mygdx.game.utils;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mygdx.game.data.Answer;
import com.mygdx.game.data.MultipleChoice;
import com.mygdx.game.entities.GameStats;
import com.mygdx.game.entities.Player;
import com.mygdx.game.screens.Play;

/**
 * QuestionDialog shows two CONNECTED parts.
 * 1)An array of messages
 * 2)A multiple choice question with choices
 */
public class QuestionDialog extends CustomDialog {

    private boolean isAnswered;
    private boolean isCorrect;
    private Object content;
    private Skin skin;

    public QuestionDialog(String title, Skin skin, CustomDialog responseDialog) {
        super(title, skin, responseDialog);
        this.skin = skin;
        this.isAnswered = false;
        this.isCorrect = false;
    }

    /**
     * Show MultipleChoice (a question statement and a list of choices) in the dialog
     * @param object
     */
    public void renderContent(Object object) {
        content = object;
        if (object instanceof MultipleChoice) {
            MultipleChoice question = (MultipleChoice) object;
            addLabel(question.getQuestion()[question.getQuestion().length-1],skin);
            for (Answer t : question.getChoices()) {
                button(t.getChoice(), t);
            }
        }
    }

    public boolean isAnswered() {
        return this.isAnswered;
    }

    public boolean isCorrect() { return this.isCorrect; }

    public Object getContent() {
        return this.content;
    }

    @Override
    protected void result(final Object object) {
        if (object instanceof Answer) {
            this.isAnswered = true;
            Answer answer = (Answer) object;
            if (answer.isCorrect()) {
                this.isCorrect = true;
                Screen curScreen = ((Game) Gdx.app.getApplicationListener()).getScreen();
                Play play = (Play) curScreen;
                play.setCurClue(play.textGenerator.getNextClue(play.getCurClue())); //update current clue
            }
            else {
                Player.health--;
                GameStats.remainingFlashingTime = 2.0f; //set flashing time
            }
            super.result(answer.getResponse());
        }
    }
}
