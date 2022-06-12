package com.badlogic.mygame;

import com.badlogic.gdx.graphics.Texture;

public class Artifacts {
    private Texture artifact;
    private int numArtifacts;

    public Artifacts(Texture a, int nA){
        artifact = a;
        numArtifacts = nA;
    }
    public Texture getArtifact(){
        return artifact;
    }
    public int getNumArtifacts(){
        return numArtifacts;
    }
    public void Subtract(){
        numArtifacts--;
    }

}
