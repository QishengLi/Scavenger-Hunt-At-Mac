package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.data.Direction;
import com.mygdx.game.data.QuestionText;
import com.mygdx.game.utils.QuestionDialog;

/**
 * Created by Shuni on 2/25/17.
 *
 * TODO: game stops when message pops up
 */
public class Player extends Sprite {

    public static final float SPEED = 3f;
    public int HEALTH = 4;

    private Direction movingDir;
    private Stage stage;//?

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
    public void makeMove(Array<QuestionDialog> questions, Array<Rectangle> collisionRects,
                         Array<Rectangle> doorRects){

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
            popUpMessage(questions, doorRects);
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

    private Array<Rectangle> getExistingDoors(Array<QuestionDialog> questions, Array<Rectangle> doorRects) {
        Array<Rectangle> existingDoors = new Array<>();
        for (QuestionDialog questionDialog : questions) {
            if (questionDialog.getCorrectAnswer()) {
                existingDoors.add(doorRects.get(questions.indexOf(questionDialog, true)));
            }
        }
        return existingDoors;
    }

    private Array<Rectangle> addNextDoor(Array<Rectangle> existingDoors, Array<Rectangle> doorRects) {
        Array<Rectangle> newDoors = new Array<>(existingDoors);
        if(existingDoors.size < doorRects.size) {
            newDoors.add(doorRects.get(existingDoors.size));
        }
        return newDoors;
    }

    private void popUpMessage(Array<QuestionDialog> questions, Array<Rectangle> doorRects) {
        if (doorRects.random() == null) { // check if the array is empty
            return;
        }
        Array<Rectangle> existingDoors = getExistingDoors(questions, doorRects);
        Array<Rectangle> newDoors = addNextDoor(existingDoors, doorRects);
        System.out.println(existingDoors.toString());
        System.out.println(newDoors.toString());

        if (isOverlappedArray(newDoors)) {

            if(questions.random() == null) {
                return;
            }
            QuestionDialog dialogBox = questions.get(newDoors.size-1);
            dialogBox.show(this.stage);
//            doorRects.removeValue(rect, true);
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
                HEALTH--;
                enemies.removeValue(enemy, true);
            }
        }
    }

    public boolean isAlive(int health) {
        return (health > 0);
    }
}
