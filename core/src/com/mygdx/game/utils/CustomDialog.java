package com.mygdx.game.utils;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.data.MultipleChoice;
import com.mygdx.game.entities.Enemy;
import com.mygdx.game.screens.Play;

/**
 * An abstract class for dialog structures.
 *
 */
public abstract class CustomDialog extends Dialog {

    private static final int LABEL_WIDTH = 400;

    private InputProcessor ip;
    private Play playScreen;
    private CustomDialog responseDialog;

    protected CustomDialog(String title, Skin skin, CustomDialog responseDialog) {
        super(title, skin);
        this.ip = Gdx.input.getInputProcessor();
        this.responseDialog = responseDialog;

        Screen curScreen = ((Game) Gdx.app.getApplicationListener()).getScreen();
        if (curScreen instanceof Play) {
            this.playScreen = (Play) curScreen;
        }
    }

    /**
     * This method is used to update the content of a dialog if first initiated.
     * @param object
     */
    public abstract void renderContent(Object object);

    /**
     * This method should return the content of the dialog.
     * @return
     */
    public abstract Object getContent();

    protected void addLabel(String str, Skin skin) {
        Label label = new Label(str, skin);
        label.setWrap(true); //wrapping text to multiple lines
        label.setAlignment(Align.center);
        getContentTable().clearChildren();
        getContentTable().add(label).prefWidth(LABEL_WIDTH); //prefWidth is width of the actual text box
        getButtonTable().clearChildren();
    }

    private boolean checkScreen() {
        return this.playScreen != null;
    }

    /**
     * Freeze time clock and enemy movement
     * @param freeze
     */
    private void freezeTime(boolean freeze) {
        if (!checkScreen()) return;
        Array<Enemy> enemies = this.playScreen.getEnemies();
        for (Enemy e : enemies) {
            e.setFreeze(freeze);
        }
        this.playScreen.getGameStatsGroup().setFrozen(freeze);
    }

    public CustomDialog getResponseDialog() {
        return responseDialog;
    }

    public void setResponseDialog(CustomDialog responseDialog) {
        this.responseDialog = responseDialog;
    }

    @Override public Dialog show(Stage stage) {
        playScreen.getPlayer().resetDirection();
        Gdx.input.setInputProcessor(stage);  // Set input processor to dialog
        freezeTime(true);
        return super.show(stage);
    }

    @Override protected void result(Object object) {
        // Reset input processor to screen
        Gdx.input.setInputProcessor(this.ip);
        freezeTime(false);
        if (this.responseDialog != null) {
            if (object instanceof MultipleChoice) {
                responseDialog.renderContent(object);
                responseDialog.show(getStage());
            }
            // if the object is passed as a TextDialog
            if (object instanceof String[]) {
                String[] strs = (String[]) object;
                if (!(strs.length == 0)) {
                    responseDialog.renderContent(strs);
                    responseDialog.show(getStage());
                }
            }
        }
        remove();
    }

    @Override
    public Dialog button (Button button, Object object) {
        Dialog result = super.button(button, object);
        getButtonTable().row(); //align choices vertically
        return result;
    }
}

