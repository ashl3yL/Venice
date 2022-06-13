package com.badlogic.mygame;

import com.badlogic.gdx.graphics.Texture;
public class Blocks {
    private Texture block;
    private boolean spin;
    private boolean solid;

    public Blocks(Texture t, boolean sp, boolean so) {
        block = t;
        spin = sp;
        solid = so;
    }
}
