package com.mygdx.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

/**
 * Created by Shuni on 4/3/17.
 */
public class Instruction implements Screen {

    private Stage stage;
    private Skin skin;
    private Table table;

    private int initialWidth;
    private int initialHeight;

    public Instruction() {
        initialWidth = 0;
        initialHeight = 0;
    }

    public Instruction(int w, int h) {
        initialWidth = w;
        initialHeight = h;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

       // stage.setDebugAll(true);
        stage.act(delta);
        stage.draw();

    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
        table.invalidateHierarchy();
    }

    @Override
    public void show() {

        final int initialWidth = Gdx.graphics.getWidth();
        final int initialHeight = Gdx.graphics.getHeight();

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("ui/menuSkin.json"), new TextureAtlas("ui/atlas.pack"));

        Image imageKeys = new Image();
        imageKeys.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("keys.png")))));

        Image imageDoor = new Image();
        imageDoor.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("map/door3.png")))));

        Image imageEnemy = new Image();
        imageEnemy.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("goblinsword.png")))));

        Label heading = new Label("Instructions", skin, "default");
        heading.setFontScale(2);

        Label arrowKeysInstr = new Label("Navigate", skin, "default");
        arrowKeysInstr.setFontScale(1);

        Label doorsInstr = new Label("Hit doors for clues", skin, "default");
        doorsInstr.setFontScale(1);

        Label enemyInstr = new Label("Avoid enemies", skin, "default");
        enemyInstr.setFontScale(1);

        table = new Table(skin);
        table.setFillParent(true);

        // creating buttons
        TextButton buttonPlay = new TextButton("Play", skin, "default");
        buttonPlay.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                stage.addAction(sequence(moveTo(0, -stage.getHeight(), .5f), run(new Runnable() {

                    @Override
                    public void run() {
                        ((Game) Gdx.app.getApplicationListener()).setScreen(new Play(initialWidth, initialHeight));
                    }
                })));
            }
        });
        buttonPlay.pad(15);

        TextButton buttonBack = new TextButton("Back", skin, "default");
        buttonBack.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                stage.addAction(sequence(moveTo(0, -stage.getHeight(), .5f), run(new Runnable() {

                    @Override
                    public void run() {
                        ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu(initialWidth, initialHeight));
                    }
                })));
            }
        });
        buttonBack.pad(15);

        table.add(heading).spaceBottom(10).colspan(2).center();
        table.row();
        imageKeys.setScaling(Scaling.fit);
        table.add(imageKeys).spaceBottom(15);
        table.add(arrowKeysInstr).spaceBottom(15);
        table.row();

        imageDoor.setScaling(Scaling.fit);
        table.add(imageDoor).spaceBottom(15);
        table.add(doorsInstr).spaceBottom(15);
        table.row();

        //table.getCell(imageDoor).setActorHeight(imageDoor.getImageHeight() * 0.5f);

        imageEnemy.setScaling(Scaling.fit);
        table.add(imageEnemy).spaceBottom(15);
        table.add(enemyInstr).spaceBottom(15);
        table.row();

        table.add(buttonBack).spaceBottom(15).align(Align.right);;
        table.add(buttonPlay).spaceBottom(15);
        table.row();

        //table.debug();
        stage.addActor(table);
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
        stage.dispose();
        skin.dispose();
    }
}
