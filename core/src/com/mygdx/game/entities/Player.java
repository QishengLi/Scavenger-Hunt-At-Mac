package com.mygdx.game.entities;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.data.Direction;
import com.mygdx.game.data.MultipleChoice;
import com.mygdx.game.data.QuestionText;
import com.mygdx.game.screens.Play;
import com.mygdx.game.utils.CustomDialog;
import com.mygdx.game.utils.QuestionDialog;
import com.mygdx.game.utils.TextDialog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static java.lang.Math.min;

/**
 * Created by Shuni on 2/25/17.
 *
 */
public class Player extends Sprite {

    public static final float SPEED = 12f;
    public static int health = 12;
    public static final int TOTALHEALTH = 12;

    private List<Direction> movingDirs;

    private Stage stage;
    private Array<Rectangle> doorRects;
    private Array<CustomDialog> questions;
    private Array<Rectangle> collisionRects;
    private Map<Rectangle, CustomDialog> spots;

    public QuestionText qt = new QuestionText();

    public ArrayList<Explosion> explosions;


    public Player(Texture texture, Stage stage) {
        super(texture);
        this.movingDirs = new ArrayList<>();
        this.stage = stage;
        //TODO: Refactor!
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



    //TODO: refactor
    public void makePlayerMove(){

        float oldXPos = getX();
        float oldYPos = getY();

        for (Direction dirInCurDir : movingDirs) {
            makeMove(dirInCurDir, 1.0f);
        }

        if (isOverlappedArray(collisionRects)) {
            popUpMessage();
            setPosition(oldXPos, oldYPos);
        }
    }

    public void makeMove(Direction direction, float scale) {
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


    public boolean isOverlappedArray(Array<Rectangle> rects) {
        for (Rectangle rect : rects) {
            if (isOverlapped(rect)){
                return true;
            }
        }
        return false;
    }

    public boolean isOverlapped(Rectangle rec2) {
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

    public Array<Rectangle> getExistingDoors() {
        //TODO: Refactor this part. Confusing.
        Array<Rectangle> existingDoors = new Array<>();
        for (CustomDialog questionDialog : questions) {
            int i = 0;
            if (questionDialog instanceof TextDialog) {
                CustomDialog customDialog = questionDialog;
                while (customDialog instanceof TextDialog && i < 20) { //TODO: change parameter. 7: number of nested dialogs
                    customDialog = customDialog.getResponseDialog(); //@nullable if the whole chain is TextDialog
                    i++;
                }
                if (customDialog instanceof QuestionDialog) {
                    QuestionDialog question = (QuestionDialog) customDialog;
                    if (question.isCorrect()) {
                        existingDoors.add(Chapter.getKeyByValue(spots, questionDialog));
                    }
                }
            }
            if (questionDialog instanceof QuestionDialog) {
                QuestionDialog question = (QuestionDialog) questionDialog;
                if (question.isCorrect()) {
                    existingDoors.add(Chapter.getKeyByValue(spots, question));
                }
            }
        }
        return existingDoors;
    }

    private Rectangle nextDoor(Array<Rectangle> existingDoors) {
        if (isFinished(existingDoors)) {
            return doorRects.get(existingDoors.size - 1);
        }
        return (doorRects.get(existingDoors.size));
    }

    public boolean isFinished(Array<Rectangle> existingDoors) {
        return (existingDoors.size == doorRects.size);
    }

    // TODO：factor out sound playing and update current clue
    private void popUpMessage() {

        if (doorRects.random() == null || questions.random() == null) { // check if the array is empty
            return;
        }
        Array<Rectangle> existingDoors = getExistingDoors();
        Rectangle newDoor = nextDoor(existingDoors);

        Screen curScreen = ((Game) Gdx.app.getApplicationListener()).getScreen();
        final Play play = (Play) curScreen;

        // Visiting answered questions
        for (Rectangle rect : existingDoors) {
            //If it's the 5th or 9th door at Olin Rice, or 3rd door at Art, skip.
            if ((existingDoors.size >= 6 && rect == existingDoors.get(4))
                    ||(existingDoors.size >= 10 && rect == existingDoors.get(8))
                    || (existingDoors.size >= 10 && rect == existingDoors.get(2))) continue;
            if (isOverlapped(rect)) {
                Play.hitCorrectDoor.play();
                resetDirection();
//                CustomDialog dialogBox = spots.get(rect);
//                while (dialogBox instanceof TextDialog) {
//                    dialogBox = dialogBox.getResponseDialog();
//                }
//                dialogBox.getResponseDialog().show(this.stage);
                CustomDialog tmpDialog = spots.get(rect);
                while (tmpDialog != null && tmpDialog instanceof TextDialog) {
                    tmpDialog = tmpDialog.getResponseDialog();
                }
                QuestionDialog mcDialog = (QuestionDialog) tmpDialog;
                MultipleChoice mc = (MultipleChoice) mcDialog.getContent();
                CustomDialog curClueDialog = new TextDialog("Clue", play.getSkin(), null);
                curClueDialog.renderContent(new String[]{mc.getCorretResponse()});
                curClueDialog.show(this.stage);

            }
        }

        // Visiting a new spot
        if (isOverlapped(newDoor)) {
            Play.hitCorrectDoor.play();
            resetDirection();
            final CustomDialog dialogBox = spots.get(newDoor);
            if (isFinished(existingDoors)) {
                dialogBox.getResponseDialog().show(this.stage);
                return;
            }

            CustomDialog tmpDialog = dialogBox;
            while (tmpDialog != null && tmpDialog instanceof TextDialog) {
                tmpDialog = tmpDialog.getResponseDialog();
            }
            final QuestionDialog mcDialog = (QuestionDialog) tmpDialog;
            if (mcDialog != null && mcDialog.isAnswered()) {
                CustomDialog introDialog = new CustomDialog("QUESTION", play.getSkin(), null) {
                    @Override
                    public void renderContent(Object object) {
                        addLabel("Which path do you want to go?", play.getSkin());
                        button("Next", dialogBox);
                        button("Skip", mcDialog);
                    }

                    @Override
                    public Object getContent() { return null; }

//                    @Override
//                    public Dialog button (Button button, Object object) {
//                        return super.button(button, object);
//                    }

                    @Override
                    protected void result(Object object) {
                        if (object instanceof TextDialog) {
                            setResponseDialog(dialogBox);
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
                    health = min(health + 3, TOTALHEALTH);
                    GameStats.remainingFlashingTime = 4.0f;
                }
            }



            //不要删，list of clues

//            if ((qt.getQs().get(existingDoors.size)) instanceof MultipleChoice) {
//                MultipleChoice mc = (MultipleChoice) qt.getQs().get(existingDoors.size);
//                play.setCurClue(mc.getCorretResponse());
//            }

                //Object[] old = play.sampleSelectBox;
                //play.sampleSelectBox = new Object[old.length + 1];
                //for (int i = 0; i < old.length; i++) {
                //    play.sampleSelectBox[i] =  old[i];
                //}
                //play.sampleSelectBox[old.length] = "update";
            }


        for (Rectangle rect : doorRects) {
            if (isOverlapped(rect) && !existingDoors.contains(rect, true)) {
                Play.hitWrongDoor.play();
                resetDirection();
            }
        }
    }

    public void hitEnemy(Array<Enemy> enemies) {
        for (Enemy enemy : enemies) {
            if(isOverlapped(enemy.getBoundingRectangle())) {
                health--;
                GameStats.remainingFlashingTime = 2.0f;
                enemies.removeValue(enemy, true);
                explosions.add(new Explosion(enemy.getX(), enemy.getY()));
                Play.enemyHit.play();
            }
        }
    }

    public boolean isAlive(int health) {
        return (health > 0);
    }

    public List<Direction> getMovingDirs() {
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
