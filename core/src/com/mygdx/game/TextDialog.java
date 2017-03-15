package com.mygdx.game;

import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * Created by Shuni on 3/12/17.
 */
public class TextDialog extends Dialog {

    public TextDialog(String title, Skin skin, String dialogText) {
        super(title, skin);
        text(dialogText);
        button("OK");
    }

    @Override
    protected void result(Object object) {
        System.out.println(object);
    }
}
