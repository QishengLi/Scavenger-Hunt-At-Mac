package com.mygdx.game.entities;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.data.Direction;
import com.mygdx.game.data.MultipleChoice;
import com.mygdx.game.screens.Play;
import com.mygdx.game.utils.CustomDialog;
import com.mygdx.game.utils.QuestionDialog;
import com.mygdx.game.utils.TextDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.lang.Math.min;

/**
 * Created by Shuni on 2/25/17.
 *
 */
public class Player extends Sprite {

    static final float SPEED = 12f;
    public static int health = 12;
    static final int TOTAL_HEALTH = 12;

    private List<Direction> movingDirs;

    private Stage stage;
    private Array<Rectangle> doorRects;
    private Array<CustomDialog> questions;
    private Array<Rectangle> collisionRects;
    private Map<Rectangle, CustomDialog> spots;

    public ArrayList<Explosion> explosions;

    public Player(Texture texture, Stage stage) {
        super(texture);
        this.movingDirs = new ArrayList<>();
        this.stage = stage;
        explosions = new ArrayList<>();
    }

    public void addNewDirection(Direction dir) {
        this.movingDirs.add(dir);
    }

    public void removeDirection(Direction dir) {
        this.movingDirs.remove(dir);
    }

    public void resetDirection() {
        this.movingDirs.clear();
    }

    public void makePlayerMove(Rectangle newDoor, Array<Rectangle> existingDoors){

        float oldXPos = getX();
        float oldYPos = getY();

        for (Direction dirInCurDir : movingDirs) {
            makeMove(dirInCurDir, 1.0f);
        }

        if (isCollidedWithAll(collisionRects)) {
            popUpMessage(newDoor, existingDoors);
            setPosition(oldXPos, oldYPos);
        }
    }

    void makeMove(Direction direction, float scale) {
        switch (direction) {
            case UP:
                translateY(SPEED * scale);
                break;
            case DOWN:
                translateY(-SPEED * scale);
                break;
            case LEFT:
                translateX(-SPEED * scale);
                break;
            case RIGHT:
                translateX(SPEED * scale);
                break;
        }
    }

    /**
     * check if a sprite is overlapped with an array of rectangle boxes
     */
    public boolean isCollidedWithAll(Array<Rectangle> rects) {
        for (Rectangle rect : rects) {
            if (isCollided(rect)){
                return true;
            }
        }
        return false;
    }

    /**
     * check if a sprite is overlapped with a rectangle box.
     */
    boolean isCollided(Rectangle rec2) {
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

    // TODO：factor out sound playing and update current clue
    private void popUpMessage(Rectangle newDoor, Array<Rectangle> existingDoors) {

        if (doorRects.random() == null || questions.random() == null) { // check if the array is empty
            return;
        }

        Screen curScreen = ((Game) Gdx.app.getApplicationListener()).getScreen();
        final Play play = (Play) curScreen;

        // Visiting answered questions
        for (Rectangle rect : existingDoors) {
            //If it's the 5th or 9th door at Olin Rice, or 3rd door at Art, skip.
            if ((existingDoors.size >= 6 && rect == existingDoors.get(4))
                    ||(existingDoors.size >= 10 && rect == existingDoors.get(8))
                    || (existingDoors.size >= 10 && rect == existingDoors.get(2))) continue;
            if (isCollided(rect)) {
                Play.hitCorrectDoor.play();
                resetDirection();
                CustomDialog dialogBox = spots.get(rect);
                while (dialogBox instanceof TextDialog) {
                    dialogBox = dialogBox.getResponseDialog();
                }
                dialogBox.getResponseDialog().show(this.stage);
            }
        }

        // Visiting a new spot
        if (isCollided(newDoor)) {
            Play.hitCorrectDoor.play();
            resetDirection();
            final CustomDialog dialogBox = spots.get(newDoor);

            CustomDialog tmpDialog = dialogBox;
            while (tmpDialog != null && tmpDialog instanceof TextDialog) {
                tmpDialog = tmpDialog.getResponseDialog();
            }
            final QuestionDialog mcDialog = (QuestionDialog) tmpDialog;
            if (mcDialog != null && mcDialog.isAnswered()) {
                CustomDialog introDialog = new CustomDialog("QUESTION", play.getSkin(), null) {
                    @Override
                    public void renderContent(Object object) {
                        Object content = dialogBox.getContent();
                        String label;
                        if (content instanceof MultipleChoice) {
                            MultipleChoice problem = (MultipleChoice) content;
                            label = problem.getQuestion()[0];
                        } else if (content instanceof String[]) {
                            label = ((String[]) content)[0];
                        } else {
                            label = "Which path do you want to go?";
                        }

                        addLabel(label, play.getSkin());
                        button("See clues again", dialogBox.getResponseDialog());
                        button("Skip to question", mcDialog);
                    }

                    @Override
                    public Object getContent() { return null; }

                    @Override
                    protected void result(Object object) {
                        if (object instanceof TextDialog) {
                            setResponseDialog(dialogBox.getResponseDialog());
                            super.result(((TextDialog) object).getContent());
                        } else if (object instanceof QuestionDialog){
                            setResponseDialog(mcDialog);
                            super.result(((QuestionDialog) object).getContent());
                        }
                    }
                };
                introDialog.renderContent(null);
                introDialog.show(this.stage);
            } else {
                dialogBox.show(this.stage);
                // +3 life at door 5 and door 10.
                if (existingDoors.size == 4 || existingDoors.size == 9){
                    health = min(health + 3, TOTAL_HEALTH);
                    GameStats.remainingFlashingTime = 4.0f;
                }
            }
        }

        for (Rectangle rect : doorRects) {
            if (isCollided(rect) && !existingDoors.contains(rect, true)) {
                Play.hitWrongDoor.play();
                resetDirection();
            }
        }
    }

    // perform the effect of hitting the enemies, including checking if a player hits the enemy, and decreasing life
    public void hitEnemy(Array<Enemy> enemies) {
        for (Enemy enemy : enemies) {
            if(isCollided(enemy.getBoundingRectangle())) {
                health--;
                GameStats.remainingFlashingTime = 3.0f;
                enemies.removeValue(enemy, true);
                explosions.add(new Explosion(enemy.getX(), enemy.getY()));
                Play.enemyHit.play();
            }
        }
    }

    // see if the player is alive
    public boolean isAlive(int health) {
        return (health > 0);
    }

    // get the list of directions that are currently being pressed down
    List<Direction> getMovingDirs() {
        return movingDirs;
    }

    public void setCollisionRects(Array<Rectangle> collisionRects) {
        this.collisionRects = collisionRects;
    }

    public void setDoorRects(Array<Rectangle> doorRects) {
        this.doorRects = doorRects;
    }

    public void setQuestions(Array<CustomDialog> questions) {
        this.questions = questions;
    }

    public void setSpots(Map<Rectangle, CustomDialog> spots) {
        this.spots = spots;
    }
}
