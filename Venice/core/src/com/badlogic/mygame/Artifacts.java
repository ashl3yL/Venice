package com.badlogic.mygame;

import com.badlogic.gdx.graphics.Texture;

public class Artifacts {
    private Texture artifact;
    private boolean launched;

    public Artifacts(Texture a, boolean l) {
        artifact = a;
        launched = l;
    }

    public Texture getArtifact(){
        return artifact;
    }
    public boolean isLaunched(){
        return launched;
    }

    public void Launched(){
        launched = !launched;
    }

}
