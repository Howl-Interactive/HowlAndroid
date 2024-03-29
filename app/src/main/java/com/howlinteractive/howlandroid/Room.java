package com.howlinteractive.howlandroid;

import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.Collections;

public class Room {

    static float playerStartX = Game.width / 2, playerStartY = Game.height - 200;
    static Player p;

    ArrayList<Object> objs;

    float scrollX, scrollY;

    private int waveCounter = 100, waveTime = 100;

    Room(float scrollX, float scrollY) {
        this.scrollX = scrollX;
        this.scrollY = scrollY;
        objs = new ArrayList<>();
        if(p == null) { p = new Player(playerStartX, playerStartY); }
        objs.add(p);
        objs.add(new Background(Game.height / 2));
        objs.add(new Background(-Game.height / 2));
    }

    Room() {
        this(0, 0);
    }

    void reset() {
        objs = new ArrayList<>();
        p.x = playerStartX;
        p.y = playerStartY;
        p.setDir(0, true);
        objs.add(p);
        objs.add(new Background(Game.height * 3 / 2));
        objs.add(new Background(Game.height / 2));
        objs.add(new Background(-Game.height / 2));
    }

    void update() {
        if(waveCounter >= waveTime) {
            createObjects();
            waveCounter = 0;
        }
        else {
            waveCounter++;
        }
        for(Object obj : objs) {
            if(obj.isAlive) { obj.update(); }
        }
        for(int i = objs.size() - 1; i >= 0; i--) {
            if(!objs.get(i).isAlive) { objs.remove(i); }
        }
        scroll();
    }

    void scroll() {
        p.negateScroll();
        for(Object obj : objs) {
            if(obj instanceof Bullet) {
                obj.negateScroll();
            }
        }
        for(Object obj : objs) {
            obj.x += scrollX;
            obj.y += scrollY;
        }
        LevelCreator.scrollCounter += Math.abs(scrollY);
    }

    void draw(Canvas canvas) {
        rearrangeByDepth();
        for(Object obj : objs) {
            obj.draw(canvas);
        }
    }

    void createObjects() {
        ArrayList<Object> section = LevelCreator.loadSection();
        for(Object obj : section) {
            objs.add(obj);
        }
    }

    void rearrangeByDepth() {
        Collections.sort(objs);
    }

    void onTouch(float[] coords) {
        if(coords[0] < p.x - p.speed) {
            p.velX = -p.speed;
        }
        else if(coords[0] > p.x + p.speed) {
            p.velX = p.speed;
        }
        if(coords[1] < p.y - p.speed) {
            p.velY = -p.speed;
        }
        else if(coords[1] > p.y + p.speed) {
            p.velY = p.speed;
        }
        p.adjustRotation();
    }

    static void drawLine(float x1, float y1, float x2, float y2) {
        //TODO
    }
}