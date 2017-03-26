package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.data.Direction;
import com.mygdx.game.data.QuestionText;
import com.mygdx.game.utils.QuestionDialog;

import java.util.Map;

/**
 * Created by Shuni on 2/25/17.
 *
 * TODO: game stops when message pops up
 */
public class Player extends Sprite {

    public static final float SPEED = 3f;
    public int health = 4;

    private Direction movingDir;
    private Stage stage;//?
    private Array<Rectangle> doorRects;
    private Array<QuestionDialog> questions;
    private Array<Rectangle> collisionRects;
    private Map<Rectangle, QuestionDialog> spots;

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

    //TODO: refactor
    public void makeMove(){

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

        if (isOverlappedArray(collisionRects)) {
            popUpMessage();
            setPosition(oldXPos, oldYPos);
            setDirection(Direction.IDLE);
        }
    }


    public boolean isOverlappedArray(Array<Rectangle> rects) {
        for (Rectangle rect : rects) {
            if (isOverlapped(rect)){
                return true;
            }
        }
        return false;
    }

    private boolean isOverlapped(Rectangle rec2) {
        float p1x = getX();
        float p1y = getY() + getHeight();
        float p2x = getX() + getWidth();
        float p2y = getY();

        float p3x = rec2.getX();
        float p3y = rec2.getY() + rec2.getHeight();
        float p4x = rec2.getX() + rec2.getWidth();
        float p4y = rec2.getY();
        return (!((p2x < p3x) || (p1y < p4y) || (p1x > p4x) || (p2y > p3y)));
    }

    private Array<Rectangle> getExistingDoors() {
        Array<Rectangle> existingDoors = new Array<>();
        for (QuestionDialog questionDialog : questions) {
            if (questionDialog.getCorrectAnswer()) {
                existingDoors.add(Chapter.getKeyByValue(spots, questionDialog));
            }
        }
        return existingDoors;
    }

    private Rectangle nextDoor(Array<Rectangle> existingDoors) {
        return (doorRects.get(existingDoors.size));
    }

    private void popUpMessage() {
        if (doorRects.random() == null || questions.random() == null) { // check if the array is empty
            return;
        }
        Array<Rectangle> existingDoors = getExistingDoors();
        Rectangle newDoor = nextDoor(existingDoors);

        for (Rectangle rect : existingDoors) {
            if (isOverlapped(rect)) {
                QuestionDialog dialogBox = spots.get(rect);
                dialogBox.show(this.stage);
            }
        }

        if (isOverlapped(newDoor)) {
            QuestionDialog dialogBox = spots.get(newDoor);
            dialogBox.show(this.stage);
        }
    }

    public Array<QuestionDialog> generateQuestions(Skin skin) {

        Array<QuestionDialog> questions = new Array<>();

        QuestionText qt = new QuestionText();
        qt.initQuestions();

        questions.add(new QuestionDialog("CLUE", skin, qt.getNthQuestion(0)));
        questions.add(new QuestionDialog("CLUE", skin, qt.getNthQuestion(1)));

        return questions;
    }

    public void hitEnemy(Array<Enemy> enemies) {
        for (Enemy enemy : enemies) {
            if(isOverlapped(enemy.getBoundingRectangle())) {
                health--;
                enemies.removeValue(enemy, true);
            }
        }
    }

    public boolean isAlive(int health) {
        return (health > 0);
    }

    public void setCollisionRects(Array<Rectangle> collisionRects) {
        this.collisionRects = collisionRects;
    }

    public void setDoorRects(Array<Rectangle> doorRects) {
        this.doorRects = doorRects;
    }

    public void setQuestions(Array<QuestionDialog> questions) {
        this.questions = questions;
    }

    public void setSpots(Map<Rectangle, QuestionDialog> spots) {
        this.spots = spots;
    }
}
