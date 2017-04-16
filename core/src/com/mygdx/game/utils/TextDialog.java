package com.mygdx.game.utils;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.entities.Player;

import java.util.Arrays;

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
        if (object instanceof String[]) {
            String[] strs = (String[]) object;
            String str1 = strs[0];
            Label label = new Label(str1, skin);
            label.setWrap(true);
            label.setAlignment(Align.center);
            getContentTable().clearChildren();
            getContentTable().add(label).prefWidth(LABEL_WIDTH);
            getButtonTable().clearChildren();
            String[] yourArray = Arrays.copyOfRange(strs, 1, strs.length);
            button("OK", yourArray);
        }
    }


    @Override
    protected void result(Object object) {
        super.result(object);
    }
}
