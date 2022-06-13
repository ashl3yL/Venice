package com.badlogic.mygame;

import com.badlogic.gdx.graphics.Texture;

public class Artifacts {
    private Texture artifact;
    private boolean launched;
    private float xCoord;
    private float yCoord;

    public Artifacts(Texture a, boolean l) {
        artifact = a;
        launched = l;
    }
    public Artifacts(Texture a, float x, float y){
        artifact = a;
        xCoord = x;
        yCoord = y;
    }

    public Texture getArtifact(){
        return artifact;
    }
    public boolean isLaunched(){
        return launched;
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
