package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;

/**
 * Created by Shuni on 2/25/17.
 */
public class Player extends Sprite {

    public static final float SPEED = 3f;
    public int HEALTH = 3;

    private Direction movingDir;
    private Stage stage;

    public Player(Texture texture, Stage stage) {
        super(texture);
        this.movingDir = Direction.IDLE;
        this.stage = stage;
    }

    public void setDirection(Direction dir) {
        this.movingDir = dir;
    }

    public Direction getDirection() {
        return this.movingDir;
    }

    public void makeMove(Array<QuestionDialog> questions, Array<Rectangle> collisionRects, Array<Rectangle> doorRects, Skin skin){

        float oldXPos = getX();
        float oldYPos = getY();

        switch (this.movingDir) {
            case UP:
                translateY(SPEED);
                break;
            case DOWN:
                translateY(-SPEED);
                break;
            case LEFT:
                translateX(-SPEED);
                break;
            case RIGHT:
                translateX(SPEED);
                break;
        }

        if (checkOverlap(collisionRects)) {
            popUpMessage(questions, doorRects, skin);
            setPosition(oldXPos, oldYPos);
        }
    }


    public boolean checkOverlap(Array<Rectangle> rects) {
        for (Rectangle rect : rects) {
            if (checkTileOverlap(rect)){
                return true;
            }
        }
        return false;
    }

    private boolean checkTileOverlap(Rectangle rec2) {
        float p1x = getX();
        float p1y = getY() + getHeight();
        float p2x = getX() + getWidth();
        float p2y = getY();

        float p3x = rec2.getX();
        float p3y = rec2.getY() + rec2.getHeight();
        float p4x = rec2.getX() + rec2.getWidth();
        float p4y = rec2.getY();
        return (! ( (p2x < p3x) || (p1y < p4y) || (p1x > p4x) || (p2y > p3y)));
    }

    //TODO: Merge this method with the previous one.
    private boolean checkEnemyOverlap(Enemy rec2) {
        float p1x = getX();
        float p1y = getY() + getHeight();
        float p2x = getX() + getWidth();
        float p2y = getY();

        float p3x = rec2.getX();
        float p3y = rec2.getY() + rec2.getHeight();
        float p4x = rec2.getX() + rec2.getWidth();
        float p4y = rec2.getY();
        return (! ( (p2x < p3x) || (p1y < p4y) || (p1x > p4x) || (p2y > p3y)));
    }

    private void popUpMessage(Array<QuestionDialog> questions, Array<Rectangle> doorRects, Skin skin) {
        if (doorRects.random() == null) { // check if the array is empty
            return;
        }
        Rectangle rect = doorRects.first();
        if (checkTileOverlap(rect)) {
//            TextDialog txtBox = new TextDialog("CLUE", skin, "Is shuni the smartest person in this world?");
//            txtBox.show(this.stage);

            if(questions.random() == null) {
                return;
            }
            QuestionDialog dialogBox = questions.removeIndex(0);
            dialogBox.show(this.stage);
            doorRects.removeValue(rect, true);
        }
    }

    public Array<QuestionDialog> generateQuestions(Skin skin) {
        Array<QuestionDialog> questions = new Array<>();

        ArrayList<ChoiceResponseTuple> sample = new ArrayList<>();
        sample.add(new ChoiceResponseTuple("1874", "You are correct!"));
        sample.add(new ChoiceResponseTuple("1974", "You are wrong!"));
        sample.add(new ChoiceResponseTuple("2074", "You are wrong!"));
        questions.add(new QuestionDialog("CLUE", skin, "When was Macalester founded?",
                sample, this.stage));

        ArrayList<ChoiceResponseTuple> sample2 = new ArrayList<>();
        sample2.add(new ChoiceResponseTuple("Shuni", "You are correct!"));
        sample2.add(new ChoiceResponseTuple("Zhaoqi", "You are correct!"));
        sample2.add(new ChoiceResponseTuple("Qisheng", "You are correct!"));
        questions.add(new QuestionDialog("CLUE", skin, "Who is the smartest?",
                sample2, this.stage));
        return questions;
    }

    public void hitEnemy(Array<Enemy> enemies) {
        for (Enemy enemy : enemies) {
            if(checkEnemyOverlap(enemy)) {
                HEALTH--;
                enemies.removeValue(enemy, true);
            }
        }
    }

    public boolean isAlive(int health) {
        return (health > 0);
    }
}
