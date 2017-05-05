package com.mygdx.game.utils;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mygdx.game.data.Answer;
import com.mygdx.game.data.MultipleChoice;

import java.util.Arrays;

/**
 * TextDialog is used to show one or more statements with a single button.
 */
public class TextDialog extends CustomDialog {
    private Skin skin;
    private Object content;

    public TextDialog(String title, Skin skin, CustomDialog responseDialog) {
        super(title, skin, responseDialog);
        this.skin = skin;
    }

    public void renderContent(Object object) {
        content = object;
        // To render a series of messages right before a multiple choice question.
        if (object instanceof MultipleChoice) {
            MultipleChoice problem = (MultipleChoice) object;
            String[] strs = problem.getQuestion();
            String str1 = strs[0];
            addLabel(str1, skin);
            MultipleChoice newProblem = new MultipleChoice(Arrays.copyOfRange(strs, 1, strs.length),
                    (Answer[]) problem.getChoices().toArray());
            button("OK", newProblem);
        }
        // To render one or more messages
        if (object instanceof String[]) {
            String[] strs = (String[]) object;
            String str1 = strs[0];
            addLabel(str1, skin);
            button("OK", Arrays.copyOfRange(strs, 1, strs.length));
        }
    }

    public Object getContent() { return this.content; }

    @Override
    protected void result(Object object) {
        super.result(object);
    }
}
