package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.mygdx.game.screens.MainMenu;

/**
 * Created by Shuni on 3/19/17.
 */
public class MainGame extends Game {

    public static final String TITLE = "Scavenger Hunt @ Mac", VERSION = "0.0.0.0 Early";

    @Override
    public void create() {
        setScreen(new MainMenu(Gdx.graphics.getWidth(),Gdx.graphics.getHeight()));
    }

    @Override
    public void dispose() { super.dispose();
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void pause() {
        super.pause();
    }

    @Override
    public void resume() {
        super.resume();
    }
}
