package com.mygdx.game.utils;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mygdx.game.data.ChoiceResponseTuple;
import com.mygdx.game.data.MultipleChoice;

/**
 * Created by Shuni on 3/12/17.
 */
public class QuestionDialog extends Dialog {

    private Skin skin;
    private Stage stage;
    private String title;
    private MultipleChoice question;

    public QuestionDialog(String title, Skin skin, MultipleChoice question, Stage stage) {
        super(title, skin);
        this.skin = skin;
        this.stage = stage;
        this.title = title;
        this.question = question;

        text(question.getQuestion());
        for (ChoiceResponseTuple t : question.getChoices()) {
            button(t.getChoice(), t.getResponse());
        }
    }

    @Override
    protected void result(final Object object) {
        TextDialog responseDialog = new TextDialog(this.title, this.skin, object.toString());
        remove(); // bug, why???
        responseDialog.show(this.stage);
    }
}
