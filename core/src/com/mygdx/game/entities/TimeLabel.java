package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mygdx.game.screens.Play;

/**
 * Created by hys1435 on 4/25/17.
 */
public class TimeLabel extends Label{

    public TimeLabel(CharSequence text, Skin skin) {
        super(text, skin);
        this.setFontScale(4);
    }

    public void update(Player player, OrthographicCamera camera) {
        this.setText("Time Left: "+ Play.timeLeft / 60000 + " : " + (Play.timeLeft / 1000) % 60);
        this.setPosition(player.getX()-200,
                player.getY()+camera.viewportHeight/2-50);
    }
}
