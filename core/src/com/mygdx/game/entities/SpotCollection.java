package com.mygdx.game.entities;


import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.utils.CustomDialog;
import com.mygdx.game.utils.QuestionDialog;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by Zhaoqi on 2017/3/26.
 */

/**
 * This class groups doors and questions together.
 */
public class SpotCollection {

    private Map<Rectangle, CustomDialog> spots;

    public SpotCollection() {}

    public void initSpots(Array<Rectangle> doorRects, Array<CustomDialog> questions) {
        spots = new HashMap<>();
        for(int i=0; i<doorRects.size; i++){
            Rectangle rect = doorRects.get(i);
            CustomDialog question = questions.get(i);
            spots.put(rect, question);
        }
    }

    public static <T, E> T getKeyByValue(Map<T, E> map, E value) {
        for (Map.Entry<T, E> entry : map.entrySet()) {
            if (Objects.equals(value, entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }

    public Map<Rectangle, CustomDialog> getSpots() {
        return spots;
    }
}
