package com.mygdx.game.entities;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.data.MultipleChoice;
import com.mygdx.game.data.QuestionText;
import com.mygdx.game.utils.CustomDialog;
import com.mygdx.game.utils.QuestionDialog;
import com.mygdx.game.utils.TextDialog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by hys1435 on 4/28/17.
 */

/**
 * Generates different types of dialogs.
 */

public class DialogGenerator {

    private QuestionText qt = new QuestionText();

    public DialogGenerator() {
        qt.initQuestions();
    }

    /**
     * Generate the question dialogs for the question text.
     * @param skin skin of the dialog
     * @return a libgdx array of question dialogs.
     */
    public Array<CustomDialog> generateQuestions(Skin skin) {

        Array<CustomDialog> questions = new Array<>();

        for (int i = 0; i < qt.getNumQuestions(); i++){
            List<CustomDialog> responseDialogs = generateTextDialog(skin, 20, "ANSWER");
            CustomDialog responseDialog = responseDialogs.get(0);
            CustomDialog qd = new QuestionDialog("CLUE", skin, responseDialog);
            Object ithQuestion = qt.getNthQuestion(i);
            if (ithQuestion instanceof MultipleChoice) {
                MultipleChoice ithMC = (MultipleChoice) ithQuestion;
                if (ithMC.getQuestion().length > 1) {
                    List<CustomDialog> longQuestionChain = generateTextDialog(skin, ithMC
                            .getQuestion().length-1, "CLUE");
                    CustomDialog lastElement = longQuestionChain.get(longQuestionChain.size()-1);
                    lastElement.setResponseDialog(qd);
                    longQuestionChain.set(longQuestionChain.size()-1, lastElement);
                    CustomDialog firstElement = longQuestionChain.get(0);
                    firstElement.renderContent(ithMC);
                    questions.add(firstElement);
                }
                else {
                    qd.renderContent(ithQuestion);
                    questions.add(qd);
                }
            }
        }
        return questions;
    }

    /**
     * Generate num of empty TextDialogs in a list.
     * @param skin skin of the dialog
     * @param num Number of text dialogs
     * @param title the title of text dialog
     * @return a list of num empty text dialogs
     */
    public List<CustomDialog> generateTextDialog(Skin skin, int num, String title) {
        List<CustomDialog> responseDialogs = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            if (i == 0) {
                CustomDialog responseDialog = new TextDialog(title, skin, null);
                responseDialogs.add(responseDialog);
            }
            else {
                CustomDialog responseDialog = new TextDialog(title, skin, responseDialogs.get(i-1));
                responseDialogs.add(responseDialog);
            }
        }
        Collections.reverse(responseDialogs);
        return responseDialogs;
    }
}
