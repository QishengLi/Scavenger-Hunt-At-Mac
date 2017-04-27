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
    private Direction prevDir;
    private Direction nextDir;
    private final float SCALE = 0.2f;

    public Enemy (Texture texture, Stage stage) {
        super(texture, stage);
        this.shouldFreeze = false;
        prevDir = Direction.IDLE;
        nextDir = Direction.IDLE;
    }

    public void makeEnemyMove(Player player, Array<Rectangle> collisionRects){
        if (this.shouldFreeze)
            return;

        float oldX = getX();
        float oldY = getY();

        chasePlayer(player, SCALE);

        if (Play.elapseTime > Play.SECOND) {
            prevDir = nextDir;
            int next = rd.nextInt(4);
            setDirection(next);
        }

        this.makeMove(this.prevDir, (1 - SCALE) * Math.max((1000-Play.elapseTime)/1000.0f, 0));
        this.makeMove(this.nextDir, (1 - SCALE) * Math.min((Play.elapseTime)/1000.0f, 1));

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