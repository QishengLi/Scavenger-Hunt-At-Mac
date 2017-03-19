package com.mygdx.game.utils;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mygdx.game.data.ChoiceResponseTuple;

import java.util.ArrayList;

/**
 * Created by Shuni on 3/12/17.
 */
public class QuestionDialog extends Dialog {

    private Skin skin;
    private Stage stage;
    private String title;

    public QuestionDialog(String title, Skin skin, String question,
                          ArrayList<ChoiceResponseTuple> tuples, Stage stage) {
        super(title, skin);
        this.skin = skin;
        this.stage = stage;
        this.title = title;

        text(question);
        for (ChoiceResponseTuple t : tuples) {
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
