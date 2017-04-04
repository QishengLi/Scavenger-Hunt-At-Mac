package com.mygdx.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

/**
 * Created by Shuni on 4/3/17.
 */
public class Instruction implements Screen {

    private Stage stage;
    private Skin skin;
    private Table table;

    private SpriteBatch batch;
    private Sprite keyboard;


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

        batch.begin();
        keyboard.draw(batch);
        batch.end();

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

        batch = new SpriteBatch();
        Texture keyBoardTexure = new Texture(Gdx.files.internal("keys.png"));
        keyboard = new Sprite(keyBoardTexure);
//        keyboard.setBounds();


        Label heading = new Label("Instructions", skin, "default");
        heading.setFontScale(2);

        Label arrowKeysInstr = new Label("use this to navigate", skin, "default");
        heading.setFontScale(2);

        Label doorsInstr = new Label("this is a door", skin, "default");
        heading.setFontScale(2);


        table = new Table(skin);
        table.setFillParent(true);

        // creating buttons
        TextButton buttonPlay = new TextButton("PLAY", skin, "default");
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

        table.add(heading).spaceBottom(100).row();
        table.add(arrowKeysInstr).spaceBottom(80).row();
        table.add(doorsInstr).spaceBottom(60).row();
        table.add(buttonPlay).spaceBottom(15).row();

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
        batch.dispose();
        keyboard.getTexture().dispose();

    }
}
