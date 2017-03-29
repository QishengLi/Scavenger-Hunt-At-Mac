package com.mygdx.game.utils;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.data.Answer;
import com.mygdx.game.data.MultipleChoice;
import com.mygdx.game.entities.Player;

/**
 * Created by Shuni on 3/12/17.
 */
public class QuestionDialog extends CustomDialog {

    private boolean correctAnswer = false;
    private Skin skin;

    public QuestionDialog(String title, Skin skin, CustomDialog responseDialog) {
        super(title, skin, responseDialog);
        this.skin = skin;
    }

    public boolean isAnsweredCorrectly() {
        return this.correctAnswer;
    }

    public void renderContent(Object object) {
        if (object instanceof MultipleChoice) {
            MultipleChoice question = (MultipleChoice) object;
            Label label = new Label(question.getQuestion(), skin);
            label.setWrap(true); //Zhaoqi: seems to work without it. Shuni: this is for wrapping text to multiple lines
            label.setAlignment(Align.center);
            getContentTable().add(label).prefWidth(LABEL_WIDTH); // Zhaoqi: what is getContentTable and prefWidth?
            // Shuni: getContentTable gets the shape/ellipse of the dialog box
            // prefWidth is width of the actual text box.

            for (Answer t : question.getChoices()) {
                button(t.getChoice(), t.getResponse());
            }
        }
    }

    // Called when clicking on button
    @Override
    protected void result(final Object object) {
        if(object.toString().contains("correct")){ //hhhhhhhh What the heck.....
            //add a boolean instance variable to multiple choice
            correctAnswer = true;
        }
        else {
            Player.health--;
        }
        super.result(object);
    }
}
