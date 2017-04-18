package com.mygdx.game.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.data.Direction;
import com.mygdx.game.entities.*;
import com.mygdx.game.utils.CustomDialog;
import com.mygdx.game.utils.TextDialog;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

/**
 * Created by Qisheng on 2/9/2017.
 */


public class Play implements Screen, InputProcessor {

    private TiledMap tiledMap;
    private CampusMap mac;
    private OrthographicCamera camera;
    private TiledMapRenderer tiledMapRenderer;
    private Stage stage;
    private SpriteBatch sb;

    private Random rd;
    private Player player;
    private Array<Enemy> enemies;

    private Skin skin;
    private Array<CustomDialog> questions;
    private Array<Rectangle> collisionRects;
    private Array<Rectangle> doors;
    private Map<Rectangle, CustomDialog> spots;

    private Music bgm;
    public static Sound punch;
    public static Sound hitCorrectDoor;
    public static Sound hitWrongDoor;

    private Texture playerImg;
    private Texture enemyImg;
    private Texture healthBar;
    private Texture bar;
    private Texture mac_logo;

    private HealthBar barGroup;
    private Label lifeLabel;

    private Label curClue;
    private SelectBox<Object> clueBox;
    private Table table;

    private int initialWidth;
    private int initialHeight;

    public static long startTime;
    public static long elapseTime;
    public static final long SECOND = 1000;

    public Play() {
        initialWidth = 0;
        initialHeight = 0;
    }

    public Play(int w, int h) {
        initialWidth = w * 2;
        initialHeight = h * 2;
    }


    @Override public void show () {

        float w = ((this.initialWidth == 0) ? Gdx.graphics.getWidth() : this.initialWidth) * 2;
        float h = ((this.initialHeight== 0) ? Gdx.graphics.getHeight() : this.initialHeight) * 2;

        camera = new OrthographicCamera();
        camera.setToOrtho(false,w,h);
        camera.update();

        tiledMap = new TmxMapLoader().load("map/full_map.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        Gdx.input.setInputProcessor(this);
        rd = new Random();
        mac = new CampusMap(tiledMap);
        collisionRects = mac.getCollisionBoxes();
        doors = mac.getDoors();

        stage = new Stage();
        skin = new Skin(Gdx.files.internal("skin/comic-ui.json"));

        // Add background music
        bgm = Gdx.audio.newMusic(Gdx.files.internal("soundEffects/Winds Of Stories.mp3"));
        bgm.setLooping(true); // looping music after it's finished
        bgm.play();

        sb = new SpriteBatch();
        playerImg = new Texture(Gdx.files.internal("pik.png"));
        enemyImg = new Texture(Gdx.files.internal("sprite/robot8.png"));
        player = new Player(playerImg, stage);
        // if set position: mac.mapWidth + 400, cannot hit door.
        player.setCenter(mac.mapWidth/2+1520,mac.mapHeight/2); //TODO: change position
        questions = player.generateQuestions(skin);

        enemies = new Array<>();
        initializeEnemies(enemies, 10);
        punch = Gdx.audio.newSound(Gdx.files.internal("punch.wav"));
        hitCorrectDoor = Gdx.audio.newSound(Gdx.files.internal("soundEffects/doorOpen.mp3"));
        hitWrongDoor = Gdx.audio.newSound(Gdx.files.internal("soundEffects/doorPunch.mp3"));


        Chapter chapters = new Chapter();
        chapters.initSpots(doors, questions);
        spots = chapters.getSpots();

        healthBar = new Texture(Gdx.files.internal("healthbar.png"));
        bar = new Texture(Gdx.files.internal("bar.png"));
        mac_logo = new Texture(Gdx.files.internal("mac_logo2.jpg"));

        lifeLabel = new Label("Life: "+ Player.health, skin);
        barGroup = new HealthBar(bar, healthBar, lifeLabel, mac);
        barGroup.initBar();

        player.setCollisionRects(collisionRects);
        player.setDoorRects(doors);
        player.setSpots(spots);
        player.setQuestions(questions);


        TextDialog bg3 = new TextDialog("Background", skin, null);
        TextDialog bg2 = new TextDialog("Background", skin, bg3);
        TextDialog bg1 = new TextDialog("Background", skin, bg2);
        //bg1.renderContent(new String[]{});
        TextDialog bg = new TextDialog("Background", skin, bg1);
        bg.renderContent(new String[]{"Today is May 4th, 2037. Our pretty Macalester campus has been invaded by unknown creatures. " +
                "You, the only survivor, must solve all the puzzles and gather all information to destroy the energy source of enemies.",
                "Fortunately, you don’t have to face it alone. People left you with pieces of clues around the campus. " +
                "Go to Kirk Section 9 to start your adventure."});
        bg.show(this.stage);

        Label title = new Label("Current clue", skin);
        curClue = new Label("kijgf",skin);
        Object[] sample = new Object[2];
        sample[0] = title;
        sample[1] = curClue;
        clueBox = new SelectBox<Object>(skin);
        clueBox.setItems(sample);
        table = new Table();
        table.setFillParent(true);
        table.top();

        table.add(clueBox);
        stage.addActor(table);

        clueBox.addListener(new ChangeListener() {

            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.out.println(((Label) clueBox.getSelected()).getText());
            }
        });



        startTime = TimeUtils.millis();
        elapseTime = 0;
    }

    @Override public void render (float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1); // set background color
        // Don't know what this line does - seems to work without it
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Show the boundary of the map
        camera.update(); // update the position of camera
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render(); // draw the map on canvas combined with the previous line

        player.makePlayerMove();
        elapseTime = TimeUtils.timeSinceMillis(startTime);
        ememyMoves(enemies);
        player.hitEnemy(enemies);
        if(!player.isAlive(Player.health)) {
            ((Game) Gdx.app.getApplicationListener()).setScreen(new Exit(initialWidth,initialHeight));
        }

        camera.position.set(player.getX(),player.getY(),0); // let the camera follow the player
        mac.adjustBoundary(camera);
        sb.setProjectionMatrix(camera.combined); // Combine the character with the camera?
        sb.begin();
        player.draw(sb); // draw the character
        drawEnemies(enemies);
        drawExplosion(delta);

        sb.draw(mac_logo, player.getX()+camera.viewportWidth/2-240, player.getY()-camera.viewportHeight/2+10);

        barGroup.updateBar(player, camera);
        barGroup.adjustBoundary(player, camera);
        barGroup.draw(sb, 1);
        sb.end();

        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    public void initializeEnemies(Array<Enemy> enemies, int ct) {
        for (int i = 1; i <= ct; i++) {
            Enemy enemy = new Enemy(enemyImg, stage);
            enemy.setCenter(rd.nextInt(mac.mapWidth), rd.nextInt(mac.mapHeight));
            enemies.add(enemy);
        }
    }

    public Array<Enemy> getEnemies() {
        return this.enemies;
    }
    public void ememyMoves(Array<Enemy> enemies) {
        for (Enemy enemy : enemies) {
            enemy.makeEnemyMove(player, collisionRects);
        }
        if (elapseTime > SECOND) {
            elapseTime = 0;
            startTime = TimeUtils.millis();
        }
    }

    public void drawEnemies(Array<Enemy> enemies) {
        for (Enemy enemy : enemies) {
            enemy.draw(sb);
        }
    }

    /**
     * Draw the animation when player hits enemy.
     * */
    public void drawExplosion(float delta){
        ArrayList<Explosion> explosionsToRemove= new ArrayList<>();
        for (Explosion explosion: player.explosions){
            explosion.update(delta);
            if (explosion.remove){
                explosionsToRemove.add(explosion);
            }
        }
        player.explosions.removeAll(explosionsToRemove);

        for (Explosion e:player.explosions){
            e.render(sb);
        }
    }

//    public void drawHealthBar() {
//        life.getData().setScale(2, 2);
//        //TODO: change to CONSTANT; adjust boundary
//                if (Player.health > 10) {
//            sb.setColor(Color.GREEN);
//        }
//        else if(Player.health > 5) {
//            sb.setColor(Color.YELLOW);
//        }
//        else {
//            sb.setColor(Color.RED);
//        }
//        sb.draw(bar, player.getX()+camera.viewportWidth/2-250, player.getY()+camera.viewportHeight/2-70);
//        sb.draw(healthBar, player.getX()+camera.viewportWidth/2-231,player.getY()+camera.viewportHeight/2-64,
//                177*player.health/player.TOTALHEALTH, 21);
//        //TODO: Change Position
//        life.draw(sb,"Life: "+Player.health, player.getX()+camera.viewportWidth/2-200,
//                player.getY()+camera.viewportHeight/2-80);
//    }


    // Called when a key was pressed
    @Override public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.UP) {
            player.addNewDirection(Direction.UP);
        } else if (keycode == Input.Keys.DOWN) {
            player.addNewDirection(Direction.DOWN);
        } else if (keycode == Input.Keys.LEFT) {
            player.addNewDirection(Direction.LEFT);
        } else if (keycode == Input.Keys.RIGHT) {
            player.addNewDirection(Direction.RIGHT);
        }
        return false;
    }

    // Called when a key was released
    @Override public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.UP) {
            player.removeDirection(Direction.UP);
        } else if (keycode == Input.Keys.DOWN) {
            player.removeDirection(Direction.DOWN);
        } else if (keycode == Input.Keys.LEFT) {
            player.removeDirection(Direction.LEFT);
        } else if (keycode == Input.Keys.RIGHT) {
            player.removeDirection(Direction.RIGHT);
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