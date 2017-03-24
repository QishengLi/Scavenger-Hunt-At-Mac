package com.mygdx.game.utils;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.data.Answer;
import com.mygdx.game.data.MultipleChoice;

/**
 * Created by Shuni on 3/12/17.
 */
public class QuestionDialog extends Dialog {

    private static final int LABEL_WIDTH = 300;
    private Skin skin;
    private Stage stage;
    private String title;


    public QuestionDialog(String title, Skin skin, MultipleChoice question, Stage stage) {

        super(title, skin);
        this.skin = skin;
        this.stage = stage;
        this.title = title;

        Label label = new Label(question.getQuestion(), skin);
        label.setWrap(true);
        label.setAlignment(Align.center);
        getContentTable().add(label).prefWidth(LABEL_WIDTH);

        for (Answer t : question.getChoices()) {
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
