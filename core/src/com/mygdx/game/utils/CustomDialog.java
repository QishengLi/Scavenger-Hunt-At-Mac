package com.mygdx.game.utils;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.entities.Enemy;
import com.mygdx.game.screens.Play;

/**
 * Created by Shuni on 3/24/17.
 */

public class CustomDialog extends Dialog {
    public static final int LABEL_WIDTH = 300;

    private InputProcessor ip;
    private Play playScreen;

    public CustomDialog(String title, Skin skin) {
        super(title, skin);
        this.ip = Gdx.input.getInputProcessor();

        // Zhaoqi: I didn't understand these lines below... Start
        Screen curScreen = ((Game) Gdx.app.getApplicationListener()).getScreen();
        if (curScreen instanceof Play) {
            this.playScreen = (Play) curScreen;
        }
        // End
    }

    @Override public Dialog show(Stage stage) {
        // Set input processor to dialog
        Gdx.input.setInputProcessor(stage);
        setEnemiesFreeze(true);
        return super.show(stage);
    }

    @Override protected void result(Object object) {
        // Reset input processor to screen
        Gdx.input.setInputProcessor(this.ip);
        setEnemiesFreeze(false);
    }

    private boolean checkScreen() {
        return this.playScreen != null;
    }

    private void setEnemiesFreeze(boolean freeze) {
        if (!checkScreen()) return;
        Array<Enemy> enemies = this.playScreen.getEnemies();
        for (Enemy e : enemies) {
            e.setFreeze(freeze);
        }
    }
}

