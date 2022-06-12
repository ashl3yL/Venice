package com.badlogic.mygame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import java.util.*;

public class Venice extends ApplicationAdapter {
	private SpriteBatch batch;
    private Texture gondola;
	private Texture gondolaL;
    private Texture gondolaR;
	private Texture water;
	private ArrayList<Artifacts> artifacts;
	private ArrayList<Texture> queue;
	private int gondolaX;
	private int gondolaY;
	private int slottedArtifactX;
	private int slottedArtifactY;
	private int toFireArtifactX;
	private int toFireArtifactY;
	private OrthographicCamera cam;


	@Override
	public void create () {
		batch = new SpriteBatch();
		gondolaX = 0;  //if you change this you gotta delete the +gondolaX on line 31
		gondolaY = 10;
		slottedArtifactX = gondolaX + 132;    // (132, 12) are the coods to get the
		slottedArtifactY = gondolaY + 12;     // artifact into the slot on the gondola
		toFireArtifactX = slottedArtifactX;
		toFireArtifactY = slottedArtifactY + 120;
		gondolaL = new Texture("gondolaRight.png");
        gondolaR = new Texture("gondolaLeft.png");
        gondola = gondolaL;
		water = new Texture("water.png");
		cam = new OrthographicCamera();
		cam.setToOrtho(false, 1200, 1200);
		artifacts = new ArrayList<Artifacts>();
		artifacts.add(new Artifacts(new Texture("key.png"), 1));
		artifacts.add(new Artifacts(new Texture("heart.png"), 2));
		artifacts.add(new Artifacts(new Texture("coin.png"), 3));
		artifacts.add(new Artifacts(new Texture("star.png"), 4));
		queue = new ArrayList<Texture>();
		for(int i = 0; i <= artifacts.size() - 1; i++){
			while(artifacts.get(i).getNumArtifacts() != 0){
				queue.add(artifacts.get(i).getArtifact());
				artifacts.get(i).Subtract();
			}
		}
		System.out.print("Queue Length: " + queue.size());


	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0.2f, 1);
		batch.begin();
		batch.draw(water, -5, 0);
		batch.draw(gondola, gondolaX, gondolaY);
		batch.draw(queue.get(0), gondolaX + slottedArtifactX, gondolaY + slottedArtifactY);
		batch.draw(queue.get(2), gondolaX + toFireArtifactX, gondolaY + toFireArtifactY);
		batch.end();
		//boat move and no go off screen
		if(Gdx.input.isKeyPressed(Input.Keys.A)) {
            gondola = gondolaL;
			if(gondolaX > 0) {
				gondolaX = gondolaX - 20;
			}
		}
		if(Gdx.input.isKeyPressed(Input.Keys.D)) {
            gondola = gondolaR;
			if(gondolaX < 900) {
				gondolaX = gondolaX + 20;
			}
		}
		//if(Gdx.input.isKeyPressed(Input.Keys.W)) //fire
		//if(Gdx.input.isKeyPressed(Input.Keys.S)) //swap
	}

	@Override
	public void dispose () {
		batch.dispose();
		gondola.dispose();
	}
}
