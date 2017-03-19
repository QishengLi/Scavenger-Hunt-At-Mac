package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;

import java.util.Random;

/**
 * Created by Zhaoqi on 2017/3/14.
 */
public class Enemy extends Player {

    Random rd = new Random();

    public Enemy (Texture texture, Stage stage) {
        super(texture, stage);
    }

    public void makeEnemyMove(Player player, Array<Rectangle> collisionRects){
        float enemySpeedX = player.getX() - getX();
        float enemySpeedY = player.getY() - getY();
        //System.out.print(enemySpeedX);
        float speedMag = (float) Math.sqrt(enemySpeedX * enemySpeedX + enemySpeedY * enemySpeedY);
        enemySpeedX = enemySpeedX / speedMag * SPEED / 2.0f;
        enemySpeedY = enemySpeedY / speedMag * SPEED / 2.0f;
        translateY(enemySpeedY);
        translateX(enemySpeedX);
        int nextDir = rd.nextInt(4);
        randomMove(nextDir);

        //if (checkOverlap(collisionRects)) {
        //    setPosition(150, 150);
        //}
    }

    public void randomMove(int nextDir) {

        switch (nextDir) {
            case 0:
                translateY(SPEED/2.0f);
                break;
            case 1:
                translateY(-SPEED/2.0f);
                break;
            case 2:
                translateX(-SPEED/2.0f);
                break;
            case 3:
                translateX(SPEED/2.0f);
                break;
        }
    }
}
