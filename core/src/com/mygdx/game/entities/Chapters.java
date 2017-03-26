package com.mygdx.game.entities;


import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.utils.QuestionDialog;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Zhaoqi on 2017/3/26.
 */

/**
 * This class groups doors and questions together.
 */
public class Chapters {

    private Map<Rectangle, QuestionDialog> spots;

    public Chapters() {
        this.spots = spots;
    }

    private void initSpots(Array<Rectangle> doorRects, Array<QuestionDialog> questions) {
        spots = new HashMap<>();
        for(int i=0;i<doorRects.size;i++){
            Rectangle rect = doorRects.get(i);
            QuestionDialog question = questions.get(i);
            spots.put(rect, question);
        }
    }

    public Map<Rectangle, QuestionDialog> getSpots() {
        return spots;
    }
}
