package com.mygdx.game;


import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class CampusMap extends TiledMap {
    private final TiledMap map;
    MapProperties prop;
    int mapWidth;
    int mapHeight;

	public CampusMap(TiledMap map) {
	    this.map = map;
	    this.prop = map.getProperties();
	    this.mapWidth = prop.get("width", Integer.class) * prop.get("tilewidth", Integer.class);
	    this.mapHeight = prop.get("height", Integer.class) * prop.get("tileheight", Integer.class);
	}

    public void adjustBoundary(OrthographicCamera cam) {

        float mapLeft = 0;
        float mapBottom = 0;

        // The camera dimensions, halved
        float cameraHalfWidth = cam.viewportWidth * .5f;
        float cameraHalfHeight = cam.viewportHeight * .5f;

        float cameraLeft = cam.position.x - cameraHalfWidth;
        float cameraRight = cam.position.x + cameraHalfWidth;
        float cameraBottom = cam.position.y - cameraHalfHeight;
        float cameraTop = cam.position.y + cameraHalfHeight;

        if(cameraLeft + Player.SPEED <= mapLeft) {
            cam.position.x = mapLeft + cameraHalfWidth;
        }
        else if(cameraRight - Player.SPEED >= mapWidth) {
            cam.position.x = mapWidth - cameraHalfWidth;
        }

        if(cameraBottom + Player.SPEED <= mapBottom) {
            cam.position.y = mapBottom + cameraHalfHeight;
        }
        else if(cameraTop - Player.SPEED >= mapHeight) {
            cam.position.y = mapHeight - cameraHalfHeight;
        }
    }

    public Array<Rectangle> getCollisionBoxes() {
        MapLayer layer = map.getLayers().get("CollisionBoxes");
        return getRects(layer);
    }

    public Array<Rectangle> getDoors() {
        MapLayer layer = map.getLayers().get("Doors");
        return getRects(layer);
    }

    public Array<Rectangle> getRects(MapLayer layer) {
        MapObjects boxes = layer.getObjects();
        Array<Rectangle> rects = new Array<>();
        for (MapObject box : boxes) {
            if (box instanceof RectangleMapObject) {
                Rectangle rect = ((RectangleMapObject) box).getRectangle();
                rects.add(rect);
            }
        }
        return rects;
    }
}
