package com.mygdx.game.utils;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;

/**
 * Created by Shuni on 3/12/17.
 */
public class TextDialog extends CustomDialog {
    private Skin skin;

    public TextDialog(String title, Skin skin, CustomDialog responseDialog) {
        super(title, skin, responseDialog);
        this.skin = skin;
    }

    public void renderContent(Object object) {
        if (object instanceof String) {
            Label label = new Label((String) object, skin);
            label.setWrap(true);
            label.setAlignment(Align.center);
            getContentTable().clearChildren();
            getContentTable().add(label).prefWidth(LABEL_WIDTH);
            getButtonTable().clearChildren();
            button("OK");
        }
    }

    @Override
    protected void result(Object object) {
        super.result(object);
    }
}
