package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.data.Direction;
import com.mygdx.game.screens.Play;

import java.util.Random;

/**
 * Created by Zhaoqi on 2017/3/14.
 */
public class Enemy extends Player {

    private Random rd = new Random();
    private boolean shouldFreeze;
    private Direction nextDir = Direction.IDLE;
    private final float SCALE = 0.5f;

    public Enemy (Texture texture, Stage stage) {
    super(texture, stage);
    this.shouldFreeze = false;
    }

    public void makeEnemyMove(Player player, Array<Rectangle> collisionRects){
        if (this.shouldFreeze)
            return;

        float oldX = getX();
        float oldY = getY();

        chasePlayer(player, SCALE);

        if (Play.elapseTime > 2000) {
            int next = rd.nextInt(4);
            setDirection(next);
            Play.elapseTime = 0;
            Play.startTime = TimeUtils.millis();
        }

        makeMove(this.nextDir, (float) Math.sqrt(1 - SCALE * SCALE));

        float newX = getX();
        float newY = getY();

        for (Rectangle rect : collisionRects) {
            if (isOverlapped(rect)){
                updatePosition(oldX, oldY, newX, newY, rect);
            }
        }
    }

    public void chasePlayer(Player player, float scale) {
        float enemySpeedX = player.getX() - getX();
        float enemySpeedY = player.getY() - getY();

        float speedMag = (float) Math.sqrt(enemySpeedX * enemySpeedX + enemySpeedY * enemySpeedY);
        enemySpeedX = enemySpeedX / speedMag * SPEED * scale;
        enemySpeedY = enemySpeedY / speedMag * SPEED * scale;
        translateY(enemySpeedY);
        translateX(enemySpeedX);
    }
    public void setDirection(int nextDir) {

        switch (nextDir) {
            case 0:
                this.nextDir = Direction.UP;
                break;
            case 1:
                this.nextDir = Direction.DOWN;
                break;
            case 2:
                this.nextDir = Direction.LEFT;
                break;
            case 3:
                this.nextDir = Direction.RIGHT;
                break;
        }
    }

    public void updatePosition(float oldX, float oldY, float newX, float newY, Rectangle rec) {
        setX(oldX);
        if (!isOverlapped(rec)) {
            return;
        }
        else {
            setY(oldY);
            setX(newX);
            if (!isOverlapped(rec)) {
                return;
            }
            else {
                setX(oldX);
                return;
            }
        }
    }
    public void setFreeze(boolean freeze) {
        this.shouldFreeze = freeze;
    }
}