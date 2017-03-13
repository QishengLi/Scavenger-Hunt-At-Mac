package com.mygdx.game;

/**
 * Created by qisheng on 2/9/2017.
 */

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;


public class MainGame extends ApplicationAdapter implements InputProcessor {

    TiledMap tiledMap;
    CampusMap mac;
    OrthographicCamera camera;
    TiledMapRenderer tiledMapRenderer;
    Stage stage;
    SpriteBatch sb;
    Texture texture;
    Player player;
    Music bgm;

    @Override public void create () {

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        camera = new OrthographicCamera();
        camera.setToOrtho(false,w,h);
        camera.update();

        tiledMap = new TmxMapLoader().load("campus_map.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        Gdx.input.setInputProcessor(this);
        mac = new CampusMap(tiledMap);

        stage = new Stage();

        // Add background music
        bgm = Gdx.audio.newMusic(Gdx.files.internal("Fireflies.mp3"));
        bgm.setLooping(true); // looping music after it's finished
        bgm.play();

        sb = new SpriteBatch();
        texture = new Texture(Gdx.files.internal("pik.png"));
        player = new Player(texture, stage);
        player.setCenter(w/2 + 50,h/2-50); //TODO: change position
    }

    @Override public void render () {
        Gdx.gl.glClearColor(0, 0, 0, 1); // set background color
        // Don't know what this line does - seems to work without it
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Show the boundary of the map
        camera.update(); // update the position of camera
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render(); // draw the map on canvas combined with the previous line

        stage.draw();

        player.makeMove(tiledMap);
        camera.position.set(player.getX(),player.getY(),0); // let the camera follow the player
        mac.adjustBoundary(camera, player);
        sb.setProjectionMatrix(camera.combined); // Combine the character with the camera?
        sb.begin();
        player.draw(sb); // draw the character
        sb.end();
    }

    // Called when a key was pressed
    @Override public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.UP) {
            player.setDirection(Direction.UP);
        } else if (keycode == Input.Keys.DOWN) {
            player.setDirection(Direction.DOWN);
        } else if (keycode == Input.Keys.LEFT) {
            player.setDirection(Direction.LEFT);
        } else if (keycode == Input.Keys.RIGHT) {
            player.setDirection(Direction.RIGHT);
        }
        return false;
    }

    // Called when a key was released
    @Override public boolean keyUp(int keycode) {
        if ((keycode == Input.Keys.UP && player.getDirection() == Direction.UP) ||
            (keycode == Input.Keys.DOWN && player.getDirection() == Direction.DOWN) ||
            (keycode == Input.Keys.LEFT && player.getDirection() == Direction.LEFT) ||
            (keycode == Input.Keys.RIGHT && player.getDirection() == Direction.RIGHT)) {
            player.setDirection(Direction.IDLE);
        }

        return false;
    }

    @Override public boolean keyTyped(char character) {
        return false;
    }

    @Override public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return stage.touchDown(screenX, screenY, pointer, button);
    }

    @Override public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return stage.touchUp(screenX, screenY, pointer, button);
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