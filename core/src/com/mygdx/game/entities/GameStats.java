package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.mygdx.game.screens.Play;


/**
 * Created by Zhaoqi on 2017/4/3.
 */
public class GameStats extends Group {

    private Image barImg;
    private Image healthBarImg;
    private Label lifeLabel;
    private Label timeLabel;
    private CampusMap map;
    private final float HORIZONTAL_MARGIN = 250;
    private final float VERTICAL_MARGIN = 70;
    public static float remainingFlashingTime = 0f;
    private boolean timeLabelSet;
    private boolean freezed;

    public GameStats(Texture bar, Texture healthBar, Label lifeLabel, CampusMap map) {
        this.barImg = new Image(bar);
        this.healthBarImg = new Image(healthBar);
        this.lifeLabel = lifeLabel;
        this.map = map;
        this.timeLabelSet = false;
        this.freezed = false;
    }

    public void initBar() {
        lifeLabel.setFontScale(3);
        barImg.setPosition(0, 0);
        healthBarImg.setPosition(19, 6);
        lifeLabel.setPosition(50, -15);
        this.addActor(lifeLabel);
        this.addActor(barImg);
        this.addActor(healthBarImg);
    }

    public void updateBar(Player player, OrthographicCamera camera) {
        lifeLabel.setText("Life: "+Player.health);
        healthBarImg.setWidth(177*Player.health/Player.TOTALHEALTH);
        healthBarImg.setHeight(21);
        this.setPosition(player.getX()+camera.viewportWidth/2-HORIZONTAL_MARGIN,
                player.getY()+camera.viewportHeight/2-VERTICAL_MARGIN);
        this.setOrigin(barImg.getWidth()/2,barImg.getHeight()/2);
    }

    public void updateTimeLabel() {
        if (timeLabel != null) {
            timeLabel.setText("Time Left: "+ Play.timeLeft / 60000 + " : " + (Play.timeLeft / 1000) % 60);
        }
    }

    //TODO: Refactor
    public void adjustBoundary(Player player, OrthographicCamera camera) {

        boolean outOfBound = false;

        float mapLeft = 0;
        float mapBottom = 0;
        float mapWidth = map.mapWidth;
        float mapHeight = map.mapHeight;

        // The camera dimensions, halved
        float cameraHalfWidth = camera.viewportWidth * .5f;
        float cameraHalfHeight = camera.viewportHeight * .5f;

        float cameraLeft = camera.position.x - cameraHalfWidth;
        float cameraRight = camera.position.x + cameraHalfWidth;
        float cameraBottom = camera.position.y - cameraHalfHeight;
        float cameraTop = camera.position.y + cameraHalfHeight;

        if (cameraRight + Player.SPEED >= mapWidth) {
            this.setX(mapWidth - HORIZONTAL_MARGIN);
            outOfBound = true;
        }
        if (cameraTop + Player.SPEED >= mapHeight) {
            this.setY(mapHeight - VERTICAL_MARGIN);
            outOfBound = true;
        }
        if (cameraLeft - Player.SPEED <= mapLeft) {
            this.setX(cameraHalfWidth * 2 - HORIZONTAL_MARGIN);
            outOfBound = true;
        }
        if (cameraBottom - Player.SPEED <= mapBottom) {
            this.setY(cameraHalfHeight * 2 - VERTICAL_MARGIN);
            outOfBound = true;
        }
        if (!outOfBound) {
            this.setPosition(player.getX()+camera.viewportWidth/2-HORIZONTAL_MARGIN,
                    player.getY()+camera.viewportHeight/2-VERTICAL_MARGIN);
        }
    }

    public void initTimeLabel(Label timeLabel) {
        this.timeLabel = timeLabel;
        this.timeLabel.setFontScale(4);
        this.timeLabel.setPosition(-1200,0);
        this.addActor(this.timeLabel);
        timeLabelSet = true;
    }

    public boolean isTimeLabelSet() {
        return timeLabelSet;
    }

    @Override
    public void draw(Batch sb, float parentAlpha) {
        if (remainingFlashingTime > 0 && System.currentTimeMillis() % 400 < 150){
            return;
        }
        if (isTransform()) applyTransform(sb, computeTransform());
        drawChildren(sb, parentAlpha);
        if (isTransform()) resetTransform(sb);
    }

    public boolean isFreezed() {
        return freezed;
    }

    public void setFreezed(boolean freezed) {
        this.freezed = freezed;
    }
}
