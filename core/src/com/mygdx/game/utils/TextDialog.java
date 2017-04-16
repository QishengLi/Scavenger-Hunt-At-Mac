package com.mygdx.game.utils;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.data.Answer;
import com.mygdx.game.data.MultipleChoice;

import java.util.Arrays;

/**
 * Created by Shuni on 3/12/17.
 */
public class TextDialog extends CustomDialog {
    private Skin skin;

    public TextDialog(String title, Skin skin, CustomDialog responseDialog) {
        super(title, skin, responseDialog);
        this.skin = skin;
    }

    public void renderContent(Object object) {
        if (object instanceof MultipleChoice) {
            MultipleChoice problem = (MultipleChoice) object;
            String[] strs = problem.getQuestion();
            String str1 = strs[0];
            // TODO: Merge this with same thing in QuestionDialog
            Label label = new Label(str1, skin);
            label.setWrap(true);
            label.setAlignment(Align.center);
            getContentTable().clearChildren();
            getContentTable().add(label).prefWidth(LABEL_WIDTH);
            getButtonTable().clearChildren();
            MultipleChoice newProblem = new MultipleChoice(Arrays.copyOfRange(strs, 1, strs.length),
                    (Answer[]) problem.getChoices().toArray());
            button("OK", newProblem);
        }
        if (object instanceof String[]) {
            String[] strs = (String[]) object;
            String str1 = strs[0];
            // TODO: Merge this with same thing in QuestionDialog
            Label label = new Label(str1, skin);
            label.setWrap(true);
            label.setAlignment(Align.center);
            getContentTable().clearChildren();
            getContentTable().add(label).prefWidth(LABEL_WIDTH);
            getButtonTable().clearChildren();
            button("OK", Arrays.copyOfRange(strs, 1, strs.length));
        }
    }

    @Override
    protected void result(Object object) {
        super.result(object);

        //TODO: move the part in CustomDialog here.
//        if (object instanceof String[]) {
//            String[] strs = (String[]) object;
//            if (!(strs.length == 0)) {
//                getResponseDialog().renderContent(strs);
//                getResponseDialog().show(getStage());
//            }
//        }
    }
}
