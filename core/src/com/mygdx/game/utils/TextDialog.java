package com.mygdx.game.utils;

import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;

/**
 * Created by Shuni on 3/12/17.
 */
public class TextDialog extends Dialog {

    private static final int LABEL_WIDTH = 300;

    public TextDialog(String title, Skin skin, String dialogText) {
        super(title, skin);

        Label label = new Label(dialogText, skin);
        label.setWrap(true);
        label.setAlignment(Align.center);
        getContentTable().add(label).prefWidth(LABEL_WIDTH);

        button("OK");
    }

    @Override
    protected void result(Object object) {
        remove();
    }
}
