package com.mygdx.game;


import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

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

    public void adjustBoundary(OrthographicCamera cam, Player player) {

        float mapLeft = 0;
        float mapBottom = 0;

        // The camera dimensions, halved
        float cameraHalfWidth = cam.viewportWidth * .5f;
        float cameraHalfHeight = cam.viewportHeight * .5f;

        float cameraLeft = cam.position.x - cameraHalfWidth;
        float cameraRight = cam.position.x + cameraHalfWidth;
        float cameraBottom = cam.position.y - cameraHalfHeight;
        float cameraTop = cam.position.y + cameraHalfHeight;

        if(cameraLeft + player.getSpeed() <= mapLeft) {
            cam.position.x = mapLeft + cameraHalfWidth;
        }
        else if(cameraRight - player.getSpeed() >= mapWidth) {
            cam.position.x = mapWidth - cameraHalfWidth;
        }

        if(cameraBottom + player.getSpeed() <= mapBottom) {
            cam.position.y = mapBottom + cameraHalfHeight;
        }
        else if(cameraTop - player.getSpeed() >= mapHeight) {
            cam.position.y = mapHeight - cameraHalfHeight;
        }
    }
}
