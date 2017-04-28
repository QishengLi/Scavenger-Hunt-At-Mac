package com.mygdx.game.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by Shuni on 3/19/17.
 * Tutorial from https://www.youtube.com/watch?v=CNjkPPveqG8
 * https://bitbucket.org/dermetfan/blackpoint2/downloads/
 */
public class Exit implements Screen{

    private Stage stage;
    private Skin skin;
    private Table table;

    private int initialWidth;
    private int initialHeight;
    private boolean gameWon;

    public Exit() {
        initialWidth = 0;
        initialHeight = 0;
    }

    public Exit(int w, int h, boolean gameWon) {
        initialWidth = w;
        initialHeight = h;
        this.gameWon = gameWon;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

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

        float w = (this.initialWidth == 0) ? Gdx.graphics.getWidth() : this.initialWidth;
        float h = (this.initialHeight== 0) ? Gdx.graphics.getHeight() : this.initialHeight;

        Viewport viewport = new FitViewport(w, h, new OrthographicCamera());
        stage = new Stage(viewport);

        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("ui/menuSkin.json"), new TextureAtlas("ui/atlas.pack"));

        table = new Table(skin);
        table.setFillParent(true);

        // creating heading
        Label heading = showHeading(gameWon);

        // creating buttons
        TextButton buttonExit = new TextButton("EXIT", skin, "default");
        buttonExit.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
        buttonExit.pad(15);


        // putting stuff together
        table.add(heading).spaceBottom(100).row();
        table.add(buttonExit);
//        table.add(buttonExit).spaceBottom(20).row();
//        table.add(buttonPlay).spaceBottom(15).row();

        stage.addActor(table);
    }


    public Label showHeading(boolean gameWon){
        if (gameWon){
            Label heading = new Label("Congrats, you win!", skin, "default");
            heading.setFontScale(2);
            return heading;
        }
        else{
            Label heading = new Label("Game over", skin, "default");
            heading.setFontScale(2);
            return heading;
        }

    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}
