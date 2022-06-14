package com.badlogic.mygame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Artifacts {
    private Texture artifact;
    private boolean launched;
    private float xCoord;
    private float yCoord;
    private boolean slotted;

    public Artifacts(Texture a, boolean l) {
        artifact = a;
        launched = l;
    }
    public Artifacts(Texture a, float x, float y, boolean s){
        artifact = a;
        xCoord = x;
        yCoord = y;
        slotted = s;
    }

    public Texture getArtifact(){
        return artifact;
    }
    public boolean isLaunched(){
        return launched;
    }
    public boolean doSpin(){
        return slotted;
    }
    public float getXCoord(){
        return xCoord;
    }
    public float getYCoord(){
        return yCoord;
    }

    public void Launched(){
        launched = !launched;
    }

}
