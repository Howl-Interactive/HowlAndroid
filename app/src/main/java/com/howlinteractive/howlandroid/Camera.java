package com.howlinteractive.howlandroid;

import android.graphics.RectF;

/**
 * Created by jacobmacdonald on 1/18/15.
 */
public class Camera {

    Object obj;
    boolean bindX, bindY;
    float unboundX, unboundY;
    float getX() { return !bindX || obj == null ? unboundX : obj.x; }
    float getY() { return !bindY || obj == null ? unboundY : obj.y; }

    Camera(Object obj, boolean bindX, boolean bindY, float unboundX, float unboundY) {
        this.obj = obj;
        this.bindX = bindX;
        this.bindY = bindY;
        this.unboundX = unboundX;
        this.unboundY = unboundY;
    }

    Camera(Object obj) {
        this(obj, true, true, 0, 0);
    }

    Camera(float unboundX, float unboundY) {
        this(null, false, false, unboundX, unboundY);
    }

    Camera() {
        this(null, false, false, Game.width / 2, Game.height / 2);
    }

    RectF getRect(RectF original) {
        RectF newRect = new RectF(
            original.left - getX() + Game.width / 2,
            original.top - getY() + Game.height / 2,
            original.right - getX() + Game.width / 2,
            original.bottom - getY() + Game.height / 2
        );
        return newRect;
    }
}