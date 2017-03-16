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
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;

import java.util.Random;


public class MainGame extends ApplicationAdapter implements InputProcessor {

    TiledMap tiledMap;
    CampusMap mac;
    OrthographicCamera camera;
    TiledMapRenderer tiledMapRenderer;
    Stage stage;
    SpriteBatch sb;
    Texture playerImg;
    Texture enemyImg;
    Random rd;
    Player player;
    Array<Enemy> enemies;
    Enemy enemy;
    Enemy enemy2;
    Music bgm;
    Skin skin;
    Array<QuestionDialog> questions;
    Array<Rectangle> collisionRects;
    Array<Rectangle> doors;

    @Override public void create () {

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        camera = new OrthographicCamera();
        camera.setToOrtho(false,w,h);
        camera.update();

        tiledMap = new TmxMapLoader().load("campus_map.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        Gdx.input.setInputProcessor(this);
        rd = new Random();
        mac = new CampusMap(tiledMap);
        collisionRects = mac.getCollisionBoxes();
        doors = mac.getDoors();

        stage = new Stage();
        skin = new Skin(Gdx.files.internal("skin/comic-ui.json"));

        // Add background music
        bgm = Gdx.audio.newMusic(Gdx.files.internal("Fireflies.mp3"));
        bgm.setLooping(true); // looping music after it's finished
        bgm.play();

        sb = new SpriteBatch();
        playerImg = new Texture(Gdx.files.internal("pik.png"));
        enemyImg = new Texture(Gdx.files.internal("goblinsword.png"));
        player = new Player(playerImg, stage);
        player.setCenter(w/2 + 50,h/2-50); //TODO: change position
        questions = player.generateQuestions(skin);
        enemies = new Array<>();
        initializeEnemies(enemies, 3);
    }

    @Override public void render () {
        Gdx.gl.glClearColor(0, 0, 0, 1); // set background color
        // Don't know what this line does - seems to work without it
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Show the boundary of the map
        camera.update(); // update the position of camera
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render(); // draw the map on canvas combined with the previous line
        player.makeMove(questions, collisionRects, doors, skin);
        ememyMoves(enemies);

        stage.draw();

        camera.position.set(player.getX(),player.getY(),0); // let the camera follow the player
        mac.adjustBoundary(camera);
        sb.setProjectionMatrix(camera.combined); // Combine the character with the camera?
        sb.begin();
        player.draw(sb); // draw the character
        drawEnemies(enemies);
        sb.end();
    }

    public void initializeEnemies(Array<Enemy> enemies, int ct) {
        for (int i = 1; i <= ct; i++) {
            Enemy enemy = new Enemy(enemyImg, stage);
            enemy.setCenter(rd.nextInt(mac.mapWidth), rd.nextInt(mac.mapHeight));
            enemies.add(enemy);
        }
    }

    public void ememyMoves(Array<Enemy> enemies) {
        for (Enemy enemy : enemies) {
            enemy.makeEnemyMove(player, collisionRects);
        }
    }

    public void drawEnemies(Array<Enemy> enemies) {
        for (Enemy enemy : enemies) {
            enemy.draw(sb);
        }
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