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
 * Created by Shuni on 3/12/17.
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

    public boolean isAnswered() {
        return this.isAnswered;
    }

    public boolean isCorrect() { return this.isCorrect; }

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

    public Object getContent() {
        return this.content;
    }
    // Called when clicking on button
    @Override
    protected void result(final Object object) {
        if (object instanceof Answer) {
            this.isAnswered = true;
            Answer answer = (Answer) object;

            // Zhaoqi: This part is for setClues
            if (answer.isCorrect()) {
                this.isCorrect = true;
                Screen curScreen = ((Game) Gdx.app.getApplicationListener()).getScreen();
                Play play = (Play) curScreen;
                //MultipleChoice mc = (MultipleChoice) this.getContent();
                play.setCurClue(play.clueText.getNextClue(play.getCurClue()));
            }
            else {
                Player.health--;
                GameStats.remainingFlashingTime = 2.0f;
            }
            super.result(answer.getResponse());
        }
    }
}
