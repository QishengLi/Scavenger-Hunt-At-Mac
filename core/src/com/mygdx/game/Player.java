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

/**
 * Created by Shuni on 2/25/17.
 */
public class Player extends Sprite {

    public static final float SPEED = 3f;

    private Direction movingDir;

//    Skin skin;
//
//    Dialog dialog = new Dialog("Warning", skin) {
//        public void result(Object obj) {
//            System.out.println("result "+obj);
//        }
//    };

    public Player(Texture texture) {
        super(texture);
        this.movingDir = Direction.IDLE;
    }

    public void setDirection(Direction dir) {
        this.movingDir = dir;
    }

    public Direction getDirection() {
        return this.movingDir;
    }

    public void makeMove(TiledMap tiledMap){
        MapLayer layer = tiledMap.getLayers().get("CollisionBoxes");
        MapObjects boxes = layer.getObjects();
        Array<Rectangle> collisionRects = new Array<>();
        for (MapObject box : boxes) {
            if (box instanceof RectangleMapObject) {
                Rectangle rect = ((RectangleMapObject) box).getRectangle();
                collisionRects.add(rect);
            }
        }

        float oldXPos = getX();
        float oldYPos = getY();

        switch (this.movingDir) {
            case UP:
                translateY(SPEED);
                break;
            case DOWN:
                translateY(-SPEED);
                break;
            case LEFT:
                translateX(-SPEED);
                break;
            case RIGHT:
                translateX(SPEED);
                break;
        }

        if (checkOverlap(collisionRects)) {
            popUpMessage(tiledMap);
            setPosition(oldXPos, oldYPos);
        }
    }


    private boolean checkOverlap(Array<Rectangle> rects) {
        for (Rectangle rect : rects) {
            if (checkTileOverlap(rect)){
                return true;
            }
        }
        return false;
    }

    private boolean checkTileOverlap(Rectangle rec2) {
        float p1x = getX();
        float p1y = getY() + getHeight();
        float p2x = getX() + getWidth();
        float p2y = getY();

        float p3x = rec2.getX();
        float p3y = rec2.getY() + rec2.getHeight();
        float p4x = rec2.getX() + rec2.getWidth();
        float p4y = rec2.getY();
        return (! ( (p2x < p3x) || (p1y < p4y) || (p1x > p4x) || (p2y > p3y)));
    }

    private void popUpMessage(TiledMap tiledMap) {
        MapLayer layer = tiledMap.getLayers().get("Doors");
        MapObjects boxes = layer.getObjects();
        Array<Rectangle> doorRects = new Array<Rectangle>();
        for (MapObject box : boxes) {
            if (box instanceof RectangleMapObject) {
                Rectangle rect = ((RectangleMapObject) box).getRectangle();
                doorRects.add(rect);
            }
        }
        if(checkOverlap(doorRects)) {
//            dialog.text("Are you sure you want to quit?");
//            dialog.button("Yes", true); //sends "true" as the result
//            dialog.button("No", false);  //sends "false" as the result
//            dialog.show();
            System.out.println("Overlap!");
        }
    }
}