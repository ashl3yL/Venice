package com.badlogic.mygame;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.graphics.g2d.Sprite;
public class Blocks{
    private Sprite block;
    private boolean spin;
    private boolean solid;
    private int slots;

    public Blocks(Sprite t, boolean sp, boolean so, int sl) {
        block = t;
        spin = sp;
        solid = so;
        slots = sl;
    }

    public Sprite getBlock(){
        return block;
    }
    public boolean isSpin(){
        return spin;
    }
    public boolean isSolid(){
        return solid;
    }
}
