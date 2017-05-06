package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.screens.Play;

/**
 * Created by Shuni on 5/4/17.
 * GameStats includes Health bar and Time Label
 */
public class GameStats {

    private static final int TABLE_HORIZONTAL_PADDING = 250;
    private static final int TABLE_VERTICAL_PADDING = 330;

    private static final int BAR_WIDTH = 100;
    private static final int BAR_HEIGHT = 10;
    private static final int HEALTHBAR_HORIZONTAL_OFFSET = 8;
    private static final int HEALTHBAR_VERTICAL_OFFSET = 2;

    private Table healthTable;
    private Table timeTable;

    private Group health;
    private Image barImg;
    private Image healthBarImg;
    private Label healthLabel;
    private Label timeLabel;

    public static float remainingFlashingTime = 0f;
    private boolean shouldStartCountdown;
    private boolean frozen;

    public GameStats(Skin skin) {
        this.healthTable = new Table(skin);
        this.timeTable = new Table(skin);

        this.health = new Group() {

            // This method makes the player flash for some amount of seconds.
            @Override
            public void draw(Batch sb, float parentAlpha) {
                if (remainingFlashingTime > 0 && System.currentTimeMillis() % 400 < 150){
                    return;
                }
                super.draw(sb, parentAlpha);
            }
    };

        this.healthLabel = new Label("Life: "+ Player.health, skin);
        this.barImg = new Image(new Texture(Gdx.files.internal("interfaceComponents/bar.png")));
        this.healthBarImg = new Image(new Texture(Gdx.files.internal("interfaceComponents/healthbar.png")));
        this.timeLabel = new Label("", skin);

        this.shouldStartCountdown = false;
        this.frozen = false;
    }

    public void init(Stage stage) {
        // Group bar and health bar
        health.setSize(BAR_WIDTH, BAR_HEIGHT);
        barImg.setWidth(BAR_WIDTH);
        barImg.setHeight(BAR_HEIGHT);
        healthBarImg.setWidth(BAR_WIDTH - 2 * HEALTHBAR_HORIZONTAL_OFFSET);
        healthBarImg.setHeight(BAR_HEIGHT - 2 * HEALTHBAR_VERTICAL_OFFSET);
        barImg.setPosition(0, 0);
        healthBarImg.setPosition(HEALTHBAR_HORIZONTAL_OFFSET, HEALTHBAR_VERTICAL_OFFSET);
        health.addActor(barImg);
        health.addActor(healthBarImg);

        // Construct health table
        healthTable.pad(TABLE_HORIZONTAL_PADDING, 0, 0, TABLE_VERTICAL_PADDING);
        healthTable.setFillParent(true);
        healthTable.top();
        healthTable.right();

        healthTable.add(health).width(BAR_WIDTH).spaceBottom(5).row();
        healthTable.add(healthLabel).align(Align.center).row();

        // Construct time table
        timeTable.pad(TABLE_HORIZONTAL_PADDING, 0, 0, 0);
        timeTable.setFillParent(true);
        timeTable.top();

        timeTable.add(timeLabel).expandX().align(Align.center).row();
        timeTable.setVisible(false);

        stage.addActor(healthTable);
        stage.addActor(timeTable);
    }

    public void update() {
        // Update health
        healthLabel.setText("Life: " + Player.health);
        updateHealthBarWidth();

        // Update time if necessary
        if (shouldStartCountdown) {
            timeLabel.setText("Time Left: "+ Play.timeLeft / 60000 + " : " + (Play.timeLeft / 1000) % 60);
        }
    }

    public void startCountdown() {
        timeTable.setVisible(true);
        this.shouldStartCountdown = true;
    }

    public boolean isFrozen() {
        return frozen;
    }

    public void setFrozen(boolean frozen) {
        this.frozen = frozen;
    }

    private void updateHealthBarWidth() {
        int rawWidth = BAR_WIDTH - 2 * HEALTHBAR_HORIZONTAL_OFFSET;
        healthBarImg.setWidth(rawWidth * Player.health / Player.TOTAL_HEALTH);
    }
}
