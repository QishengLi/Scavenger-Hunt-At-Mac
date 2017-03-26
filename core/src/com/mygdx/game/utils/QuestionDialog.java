package com.mygdx.game.utils;

import com.badlogic.gdx.scenes.scene2d.Stage;
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

    private Skin skin;
    private String title;
    private boolean correctAnswer = false;
    private TextDialog responseDialog;

    public QuestionDialog(String title, Skin skin, MultipleChoice question) {

        super(title, skin);
        this.skin = skin;
        this.title = title;

        Label label = new Label(question.getQuestion(), skin);
        label.setWrap(true); //Zhaoqi: seems to work without it
        label.setAlignment(Align.center);
        getContentTable().add(label).prefWidth(LABEL_WIDTH); // Zhaoqi: what is getContentTable and prefWidth?

        for (Answer t : question.getChoices()) {
            button(t.getChoice(), t.getResponse());
        }
    }

    public boolean getCorrectAnswer() {
        return this.correctAnswer;
    }

    public TextDialog getResponseDialog() {
        return responseDialog;
    }

    @Override
    protected void result(final Object object) {
        super.result(object);
        if(object.toString().contains("correct")){
            correctAnswer = true;
        }
        else {
            Player.health--;
        }
        responseDialog = new TextDialog(this.title, this.skin, object.toString());
        responseDialog.show(getStage());
        remove();
    }
}
