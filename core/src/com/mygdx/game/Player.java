package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

/**
 * Created by Shuni on 2/25/17.
 */
public class Player extends Sprite {

    private TiledMapTileLayer collisionLayer;

    public Player(Texture texture, TiledMapTileLayer collisionLayer) {
        super(texture);
        this.collisionLayer = collisionLayer;
    }


    




}
