package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.data.Direction;
import com.mygdx.game.screens.Play;

import java.util.Random;

/**
 * Created by Zhaoqi on 2017/3/14.
 */

/**
 * Represents an enemy character.
 */
public class Enemy extends Player {

    private Random rd = new Random();
    private boolean shouldFreeze; // true if the enemy stops moving, false if the enemy is moving
    private Direction prevDir;
    private Direction nextDir;

    public Enemy (Texture texture, Stage stage) {
        super(texture, stage);
        this.shouldFreeze = false;
        prevDir = Direction.IDLE;
        nextDir = Direction.IDLE;
    }

    /**
     * Make movement of the enemy. The movement contains two parts: chasing player and random movement.
     * @param player the player the enemy is chasing at.
     * @param collisionRects the rectangles that a sprite cannot go into.
     */
    public void makeEnemyMove(Player player, Array<Rectangle> collisionRects){
        if (this.shouldFreeze)
            return;

        float oldX = getX();
        float oldY = getY();

        float scale = 0.2f;
        chasePlayer(player, scale);

        if (Play.elapseTime > Play.SECOND) {
            prevDir = nextDir;
            int next = rd.nextInt(4);
            setDirection(next);
        }

        this.makeMove(this.prevDir, (1 - scale) * Math.max((Play.SECOND-Play.elapseTime)/(float) Play.SECOND, 0));
        this.makeMove(this.nextDir, (1 - scale) * Math.min((Play.elapseTime)/(float) Play.SECOND, 1));

        float newX = getX();
        float newY = getY();

        for (Rectangle rect : collisionRects) {
            if (isCollided(rect)){
                updatePosition(oldX, oldY, newX, newY, rect);
            }
        }
    }

    /**
     * Make the chasing part of the enemy movement.
     * @param player The player it is chasing at.
     * @param scale The extent to which it is chasing the player. 0 indicates total random movement, 1 indicates purely chasing.
     */
    public void chasePlayer(Player player, float scale) {
        float enemySpeedX = player.getX() - getX();
        float enemySpeedY = player.getY() - getY();

        float speedMag = (float) Math.sqrt(enemySpeedX * enemySpeedX + enemySpeedY * enemySpeedY);
        enemySpeedX = enemySpeedX / speedMag * SPEED * scale;
        enemySpeedY = enemySpeedY / speedMag * SPEED * scale;
        translateY(enemySpeedY);
        translateX(enemySpeedX);
    }

    /**
     * set the next random direction based on a randomly generated integer.
     * @param nextDir a randomly generated integer from 0 to 3.
     */
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

    /**
     * Update the position of the enemy when it hits a wall or the boundary of the map.
     * @param oldX Previous x-coordinate before the movement.
     * @param oldY Previous y-coordinate before the movement.
     * @param newX New x-coordinate after the movement.
     * @param newY New y-coordinate after the movement.
     * @param rec the rectangle it is collided at.
     */
    private void updatePosition(float oldX, float oldY, float newX, float newY, Rectangle rec) {
        setX(oldX);
        if (isCollided(rec)) {
            setY(oldY);
            setX(newX);
            if (isCollided(rec)) {
                setX(oldX);
            }
        }
    }
    public void setFreeze(boolean freeze) {
        this.shouldFreeze = freeze;
    }
}