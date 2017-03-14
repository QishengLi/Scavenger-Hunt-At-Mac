package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

import java.util.Random;

/**
 * Created by hys1435 on 2017/3/14.
 */
public class Enemy extends Player {

    Random rd = new Random();

    public static final float SPEED = 5f;

    public Enemy (Texture texture) {
        super(texture);
    }


    public void makeMove(TiledMap tiledMap, Array<Rectangle> collisionRects){

        int nextDir = rd.nextInt(4);

        switch (nextDir) {
            case 0:
                translateY(SPEED);
                break;
            case 1:
                translateY(-SPEED);
                break;
            case 2:
                translateX(-SPEED);
                break;
            case 3:
                translateX(SPEED);
                break;
        }

        if (checkOverlap(collisionRects)) {
            setPosition(150, 150);
        }
    }
}
