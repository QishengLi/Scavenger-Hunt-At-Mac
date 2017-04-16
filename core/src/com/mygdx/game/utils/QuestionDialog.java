package com.mygdx.game.utils;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.data.Answer;
import com.mygdx.game.data.MultipleChoice;
import com.mygdx.game.entities.Player;
import javafx.util.Pair;

/**
 * Created by Shuni on 3/12/17.
 */
public class QuestionDialog extends CustomDialog {

    private boolean answered = false;
    private Skin skin;

    public QuestionDialog(String title, Skin skin, CustomDialog responseDialog) {
        super(title, skin, responseDialog);
        this.skin = skin;
    }

    public boolean isAnswered() {
        return this.answered;
    }

    public void renderContent(Object object) {
        if (object instanceof MultipleChoice) {
            MultipleChoice question = (MultipleChoice) object;
            Label label = new Label(question.getQuestion()[question.getQuestion().length-1], skin);
            label.setWrap(true); //Zhaoqi: seems to work without it. Shuni: this is for wrapping text to multiple lines
            label.setAlignment(Align.center);
            getContentTable().add(label).prefWidth(LABEL_WIDTH); // Zhaoqi: what is getContentTable and prefWidth?
            // Shuni: getContentTable gets the shape/ellipse of the dialog box
            // prefWidth is width of the actual text box.

            for (Answer t : question.getChoices()) {
                button(t.getChoice(), t);
            }
        }
    }

    // Called when clicking on button
    @Override
    protected void result(final Object object) {
        if (object instanceof Answer) {
            Answer answer = (Answer) object;

            if (answer.isCorrect()) {
                answered = true;
            }
            else {
                Player.health--;
            }
            super.result(answer.getResponse());
        }
    }
}
