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
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.data.Direction;
import com.mygdx.game.data.TextGenerator;
import com.mygdx.game.entities.*;
import com.mygdx.game.utils.CustomDialog;
import com.mygdx.game.utils.QuestionDialog;
import com.mygdx.game.utils.TextDialog;

import java.util.ArrayList;
import java.util.List;
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
    private Array<Rectangle> existingDoors;
    private Map<Rectangle, CustomDialog> spots;

    private Music bgm;
    public static Sound enemyHit;
    public static Sound hitCorrectDoor;
    public static Sound hitWrongDoor;

    private Texture playerImg;
    private Texture enemyImg;

    private GameStats gameStatsGroup;

    public TextGenerator textGenerator;
    public String clue;
    public TextDialog curClueDialog;

    private int initialWidth;
    private int initialHeight;

    public static long startTime;
    public static long elapseTime;
    public long timeLimitStart;
    public static long timeLeft;
    public static final long SECOND = 1000;

    public DialogGenerator dialogGenerator;

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

        OrthographicCamera stageCam = new OrthographicCamera();
        stageCam.setToOrtho(false, w, h);
        stageCam.zoom -= 0.5;
        stageCam.update();
        Viewport v = new FitViewport(this.initialWidth, this.initialHeight, stageCam);
        stage = new Stage(v);
        skin = new Skin(Gdx.files.internal("ui/skin/uiskin-edit.json"));

        tiledMap = new TmxMapLoader().load("map/full_map.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        Gdx.input.setInputProcessor(this);
        rd = new Random();
        mac = new CampusMap(tiledMap);
        collisionRects = mac.getCollisionBoxes();
        doors = mac.getDoors();

        // Add background music
        bgm = Gdx.audio.newMusic(Gdx.files.internal("soundEffects/bgm.mp3"));
        bgm.setLooping(true); // looping music after it's finished
        bgm.play();

        textGenerator = new TextGenerator();
        textGenerator.initClues();
        dialogGenerator = new DialogGenerator();
        questions = dialogGenerator.generateQuestions(skin);

        sb = new SpriteBatch();
        playerImg = new Texture(Gdx.files.internal("sprite/scot_small.png"));
        enemyImg = new Texture(Gdx.files.internal("sprite/robot.png"));
        player = new Player(playerImg, stage);
        player.setCenter(mac.mapWidth/2+1520,mac.mapHeight/2); //TODO: change position

        enemies = new Array<>();
        initializeEnemies(enemies, 20);
        enemyHit = Gdx.audio.newSound(Gdx.files.internal("soundEffects/enemyHit.wav"));
        hitCorrectDoor = Gdx.audio.newSound(Gdx.files.internal("soundEffects/doorOpen.mp3"));
        hitWrongDoor = Gdx.audio.newSound(Gdx.files.internal("soundEffects/doorPunch.mp3"));

        SpotCollection spotCollection = new SpotCollection();
        spotCollection.initSpots(doors, questions);
        spots = spotCollection.getSpots();

        gameStatsGroup = new GameStats(skin);
        gameStatsGroup.init(stage);

        player.setCollisionRects(collisionRects);
        player.setDoorRects(doors);
        player.setSpots(spots);
        player.setQuestions(questions);

        List<CustomDialog> bgs = dialogGenerator.generateTextDialog(skin, 8, "Background");

        bgs.get(0).renderContent(new String[]{"Today is May 4th, 2037.",
                "1600 Grand Ave, Saint Paul. Macalester College Campus.",
                "The robots... they revolted.",
                "\"Zhaoqi! Zhaoqi!...\" Richard is severely injured.",
                "\"There's something I haven't told you. I have designed, a way, to turn the clock back...\"",
                "Turn the clock back? Wait! -- \"Richard! You mean, we could go back to, to 20 years ago?\"",
                "Richard: \"I, I don't have much time left. Go... Go to Kirk Section 9!\"",
                "Richard drew his last breath."
        });

        curClueDialog = new TextDialog("Current Clue", skin, null);
        clue = "You could click the button to show the current clue. Go to Kirk Section 9 for your first clue.";

        TextButton curClue = new TextButton("Current Clue", skin, "default");
        curClue.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                curClueDialog.renderContent(new String[]{clue});
                curClueDialog.show(stage);
            }
        });
        curClue.pad(10);

        Table table = new Table();
        table.pad(250, 330, 250, 330);
        table.setFillParent(true);
        table.bottom();
        table.left();

        table.add(curClue);
        stage.addActor(table);

        bgs.get(0).show(this.stage);

        startTime = TimeUtils.millis();
        elapseTime = 0;
        timeLimitStart = 0;
        timeLeft = 150000; //2 min 30s
    }

    @Override public void render (float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1); // set background color
        // Don't know what this line does - seems to work without it
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Show the boundary of the map
        camera.update(); // update the position of camera
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render(); // draw the map on canvas combined with the previous line

        setTimeStart(player);
        if (timeLimitStart != 0 && !(gameStatsGroup.isFrozen())) {
            timeLeft -= 1000 * Gdx.graphics.getDeltaTime();
        }

        existingDoors = getExistingDoors();
        player.makePlayerMove(nextDoor(), existingDoors);
        elapseTime = TimeUtils.timeSinceMillis(startTime);
        enemyMoves(enemies);
        player.hitEnemy(enemies);
        GameStats.remainingFlashingTime -= Gdx.graphics.getDeltaTime();

        if(!player.isAlive(Player.health) || timeLeft < 0) {
            ((Game) Gdx.app.getApplicationListener()).setScreen(new Exit(initialWidth / 2,initialHeight / 2, false));
        }
        if (isFinished(getExistingDoors())) {
            ((Game) Gdx.app.getApplicationListener()).setScreen(new Exit(initialWidth / 2,initialHeight / 2, true));
        }

        camera.position.set(player.getX(),player.getY(),0);
        mac.adjustBoundary(camera);
        sb.setProjectionMatrix(camera.combined);
        sb.begin();
        player.draw(sb);
        drawEnemies(enemies);
        drawExplosion(delta);

        if (timeLimitStart != 0) {
            gameStatsGroup.startCountdown();
        }
        gameStatsGroup.update();

        sb.end();

        stage.act();
        stage.draw();
    }

    public void setCurClue(String clue) {
        this.clue = clue;
    }

    public String getCurClue() {
        return this.clue;
    }

    public Skin getSkin() {
        return this.skin;
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

    // Initialize ct number of enemies in an array of enemies
    private void initializeEnemies(Array<Enemy> enemies, int ct) {
        for (int i = 1; i <= ct; i++) {
            Enemy enemy = new Enemy(enemyImg, stage);
            enemy.setCenter(rd.nextInt(mac.mapWidth), rd.nextInt(mac.mapHeight));
            while (enemy.isCollidedWithAll(collisionRects)) {
                enemy.setCenter(rd.nextInt(mac.mapWidth), rd.nextInt(mac.mapHeight));
            }
            enemies.add(enemy);
        }
    }

    public Array<Enemy> getEnemies() {
        return this.enemies;
    }

    public Player getPlayer() {
        return player;
    }

    // Makes move for each enemy, and loops over timer for each second
    private void enemyMoves(Array<Enemy> enemies) {
        for (Enemy enemy : enemies) {
            enemy.makeEnemyMove(player, collisionRects);
        }
        if (elapseTime > SECOND) {
            elapseTime = 0;
            startTime = TimeUtils.millis();
        }
    }

    public GameStats getGameStatsGroup() {
        return gameStatsGroup;
    }

    private void drawEnemies(Array<Enemy> enemies) {
        for (Enemy enemy : enemies) {
            enemy.draw(sb);
        }
    }

    /**
     * Draw the animation when player hits enemy.
     * */
    private void drawExplosion(float delta){
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

    // triggers the start of time limit when player hits the door of CC
    private void setTimeStart(Player player) {
        if (timeLimitStart == 0 && getExistingDoors().size >= 10) {
            timeLimitStart = TimeUtils.millis();
        }
    }

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

    // get the array of doors where questions have been answered
    private Array<Rectangle> getExistingDoors() {
        //TODO: Refactor this part. Confusing.
        Array<Rectangle> existingDoors = new Array<>();
        for (CustomDialog questionDialog : questions) {
            int i = 0;
            if (questionDialog instanceof TextDialog) {
                CustomDialog customDialog = questionDialog;
                while (customDialog instanceof TextDialog && i < 20) {
                    customDialog = customDialog.getResponseDialog();
                    i++;
                }
                if (customDialog instanceof QuestionDialog) {
                    QuestionDialog question = (QuestionDialog) customDialog;
                    if (question.isCorrect()) {
                        existingDoors.add(SpotCollection.getKeyByValue(spots, questionDialog));
                    }
                }
            }
            if (questionDialog instanceof QuestionDialog) {
                QuestionDialog question = (QuestionDialog) questionDialog;
                if (question.isCorrect()) {
                    existingDoors.add(SpotCollection.getKeyByValue(spots, question));
                }
            }
        }
        return existingDoors;
    }

    // get the next door where the player should go
    private Rectangle nextDoor() {
        if (isFinished(existingDoors)) {
            return doors.get(existingDoors.size - 1);
        }
        return (doors.get(existingDoors.size));
    }

    // check if the player has finished answering all questions
    private boolean isFinished(Array<Rectangle> existingDoors) {
        return (existingDoors.size == doors.size);
    }
}