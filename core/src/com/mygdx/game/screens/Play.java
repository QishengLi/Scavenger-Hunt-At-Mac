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
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.data.ClueText;
import com.mygdx.game.data.Direction;
import com.mygdx.game.entities.*;
import com.mygdx.game.utils.CustomDialog;
import com.mygdx.game.utils.TextDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by Qisheng on 2/9/2017.
 *
 * The order in show method MATTERS!!!!!
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
    public static Sound enemyHit;
    public static Sound hitCorrectDoor;
    public static Sound hitWrongDoor;

    private Texture playerImg;
    private Texture enemyImg;
    private Texture healthBar;
    private Texture bar;

    private GameStats barGroup;
    private Label lifeLabel;
    private Label timeLabel;

    //不要删
    public String clue;
    //public Object[] sampleSelectBox;
    public TextDialog curClueDialog;
    //private SelectBox<Object> clueBox;
    //private Table table;
    public ClueText clueText;

    private int initialWidth;
    private int initialHeight;

    public static long startTime;
    public static long elapseTime;
    public long timeLimitStart;
    public static long timeLeft;
    public static final long SECOND = 1000;

    public DialogGenerator dialogGenerator;
    //public QuestionText qt;

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

        OrthographicCamera stageCam = new OrthographicCamera();
        stageCam.setToOrtho(false, w, h);
        stageCam.zoom -= 0.5;
        stageCam.update();
        Viewport v = new FitViewport(this.initialWidth, this.initialHeight, stageCam);
        stage = new Stage(v);
        skin = new Skin(Gdx.files.internal("ui/skin/uiskin-edit.json"));

        // Add background music
        bgm = Gdx.audio.newMusic(Gdx.files.internal("soundEffects/bgm.mp3"));
        bgm.setLooping(true); // looping music after it's finished
        bgm.play();

        dialogGenerator = new DialogGenerator();

        sb = new SpriteBatch();
        playerImg = new Texture(Gdx.files.internal("sprite/scot_small.png"));
        enemyImg = new Texture(Gdx.files.internal("sprite/robot.png"));
        player = new Player(playerImg, stage);
        player.setCenter(mac.mapWidth/2+1520,mac.mapHeight/2); //TODO: change position

        questions = dialogGenerator.generateQuestions(skin);

        clueText = new ClueText();
        clueText.initClues();

        enemies = new Array<>();
        initializeEnemies(enemies, 20);
        enemyHit = Gdx.audio.newSound(Gdx.files.internal("soundEffects/enemyHit.wav"));
        hitCorrectDoor = Gdx.audio.newSound(Gdx.files.internal("soundEffects/doorOpen.mp3"));
        hitWrongDoor = Gdx.audio.newSound(Gdx.files.internal("soundEffects/doorPunch.mp3"));


        Chapter chapters = new Chapter();
        chapters.initSpots(doors, questions);
        spots = chapters.getSpots();

        healthBar = new Texture(Gdx.files.internal("interfaceComponents/healthbar.png"));
        bar = new Texture(Gdx.files.internal("interfaceComponents/bar.png"));

        lifeLabel = new Label("Life: "+ Player.health, skin);
        barGroup = new GameStats(player, bar, healthBar, lifeLabel, mac);
        barGroup.initBar();

        timeLabel = new Label("Time:" + timeLeft, skin);

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

          //不要删，list of clues
//        sampleSelectBox = new Object[2];
//        sampleSelectBox[0] = "Current Clue List";
//        sampleSelectBox[1] = "Current clue 1, 他妈的逗我呢，为啥不work？";
//        clueBox = new SelectBox<Object>(skin);
//        clueBox.setItems(sampleSelectBox);
//        Table table1 = new Table();
//        table1.setFillParent(true);
//        table1.top();
//
//        table1.add(clueBox);
//        stage.addActor(table1);


        curClueDialog = new TextDialog("Current Clue", skin, null);
        clue = "You could click the button to show the current clue. Go to Kirk Section 9 for your first clue.";

        TextButton curClue = new TextButton("Current Clue", skin, "default");
        curClue.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                curClueDialog.renderContent(new String[]{clue}); // Zhaoqi: change this if needs changing clues
                    //System.out.println("show message begins");
                curClueDialog.show(stage);
                    //System.out.println("show message ends");
            }
        });
        curClue.pad(10);

        Table table2 = new Table();
        table2.pad(250, 330, 250, 330);
        table2.setFillParent(true);
        table2.bottom();
        table2.left();

        table2.add(curClue);
        stage.addActor(table2);

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
        if (timeLimitStart != 0 && !(barGroup.isFreezed())) {
            timeLeft -= 1000 * Gdx.graphics.getDeltaTime();
        }

        player.makePlayerMove();
        elapseTime = TimeUtils.timeSinceMillis(startTime);
        ememyMoves(enemies);
        player.hitEnemy(enemies);
        GameStats.remainingFlashingTime -= Gdx.graphics.getDeltaTime();
        if(!player.isAlive(Player.health) || timeLeft < 0) { // time > 5s
            //bgm.stop();
            ((Game) Gdx.app.getApplicationListener()).setScreen(new Exit(initialWidth,initialHeight, false));
        }
        if (player.isFinished(player.getExistingDoors())) {
            //bgm.stop();
            ((Game) Gdx.app.getApplicationListener()).setScreen(new Exit(initialWidth,initialHeight, true));
        }

        camera.position.set(player.getX(),player.getY(),0); // let the camera follow the player
        mac.adjustBoundary(camera);
        sb.setProjectionMatrix(camera.combined); // Combine the character with the camera?
        sb.begin();
        player.draw(sb); // draw the character
        drawEnemies(enemies);
        drawExplosion(delta);

        if (timeLimitStart != 0) {
            barGroup.initTimeLabel(timeLabel);
        }

        if (barGroup.isTimeLabelSet()) {
            barGroup.updateTimeLabel();
        }

        barGroup.updateBar(camera);
        barGroup.adjustBoundary(camera);
        barGroup.draw(sb, 1);
        sb.end();

        //不要删
       // clueBox.setItems(sampleSelectBox);

        stage.act();
        stage.draw();
    }

    //不要删
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
    public void initializeEnemies(Array<Enemy> enemies, int ct) {
        for (int i = 1; i <= ct; i++) {
            Enemy enemy = new Enemy(enemyImg, stage);
            enemy.setCenter(rd.nextInt(mac.mapWidth), rd.nextInt(mac.mapHeight));
            while (enemy.isOverlappedArray(collisionRects)) {
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

    // Makes move for each enemy, and change direction for each second
    public void ememyMoves(Array<Enemy> enemies) {
        for (Enemy enemy : enemies) {
            enemy.makeEnemyMove(player, collisionRects);
        }
        if (elapseTime > SECOND) {
            elapseTime = 0;
            startTime = TimeUtils.millis();
        }
    }

    public GameStats getBarGroup() {
        return barGroup;
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

    public void setTimeStart(Player player) {
        //Qisheng: it should be 10 if it is still CC.
        if (timeLimitStart == 0 && player.getExistingDoors().size >= 10) { //TODO: change parameter: the door that triggers time limit: correct: 10
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
}