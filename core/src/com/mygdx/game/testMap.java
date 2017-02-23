package com.mygdx.game;

/**
 * Created by qisheng on 2/9/2017.
 */

//package com.gamefromscratch;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;

public class testMap extends ApplicationAdapter implements InputProcessor {
    Texture img;
    TiledMap tiledMap;
    OrthographicCamera camera;
    TiledMapRenderer tiledMapRenderer;
    SpriteBatch sb;
    Texture texture;
    Sprite player;

    @Override public void create () {
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        camera = new OrthographicCamera();
        camera.setToOrtho(false,w,h);
        camera.update();
        tiledMap = new TmxMapLoader().load("myMap.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        Gdx.input.setInputProcessor(this);
        sb = new SpriteBatch();
        texture = new Texture(Gdx.files.internal("pik.png"));
        player = new Sprite(texture);
    }

    @Override public void render () {
        Gdx.gl.glClearColor(0, 0, 0, 1); // set background color
        // Don't know what this line does - seems to work without it
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Show the boundary of the map
        camera.update(); // update the position of camera
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render(); // draw the map on canvas combined with the previous line

        // Let pikachu move smoothly without implementing InputProcessor
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            player.translateX(-1f);
            camera.translate(-1f, 0);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            player.translateX(1f);
            camera.translate(1f, 0);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.UP)){
            player.translateY(1f);
            camera.translate(0, 1f);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            player.translateY(-1f);
            camera.translate(0, -1f);
        }
        sb.setProjectionMatrix(camera.combined); // Combine the character with the camera?
        sb.begin();
        player.draw(sb); // draw the character
        sb.end();
    }

    // Called when a key was pressed
    @Override public boolean keyDown(int keycode) {
        /* if(keycode == Input.Keys.LEFT) {
            player.translateX(-32);
            camera.translate(-32, 0);
        }
        if(keycode == Input.Keys.RIGHT) {
            player.translateX(32);
            camera.translate(32, 0);
        }
        if(keycode == Input.Keys.UP) {
            player.translateY(32);
            camera.translate(0, 32);
        }
        if(keycode == Input.Keys.DOWN) {
            player.translateY(-32);
            camera.translate(0, -32);
        } */
        return false;
    }

    // Called when a key was released
    @Override public boolean keyUp(int keycode) {
        if(keycode == Input.Keys.NUM_1)
            tiledMap.getLayers().get(0).setVisible(!tiledMap.getLayers().get(0).isVisible());
        if(keycode == Input.Keys.NUM_2)
            tiledMap.getLayers().get(1).setVisible(!tiledMap.getLayers().get(1).isVisible());
        return false;
    }

    @Override public boolean keyTyped(char character) {
        return false;
    }

    // called when mouse was clicked
    @Override public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Vector3 clickCoordinates = new Vector3(screenX,screenY,0);
        Vector3 position = camera.unproject(clickCoordinates);
        player.setPosition(position.x, position.y);
        return true;
    }

    @Override public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override public boolean scrolled(int amount) {
        return false;
    }
}