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
	//Textures
	private SpriteBatch batch;
	private Texture water;
    private Texture gondola;
	private Texture gondolaL;
    private Texture gondolaR;
	private Artifacts key;
	private Artifacts heart;
	private Artifacts coin;
	private Artifacts star;
	//Lists
	private ArrayList<Artifacts> artifacts;
	private ArrayList<Texture> queue;
	private ArrayList<Artifacts> testing;
	//Coords
	private int gondolaX;
	private int gondolaY;
	private int slottedArtifactX;
	private int slottedArtifactY;
	private int toFireArtifactX;
	private int toFireArtifactY;
	private int artifactX;
	private int artifactY;
	private int speed;
	//Others
	float elapsedTime;
	long actionBeginTime;
	private OrthographicCamera cam;


	@Override
	public void create () {
		batch = new SpriteBatch();
		//Starting Coords
		gondolaX = 0;  //if you change this you gotta delete the +gondolaX on line 31
		gondolaY = 10;
		slottedArtifactX = gondolaX + 132;    // (132, 12) are the coods to get the
		slottedArtifactY = gondolaY + 12;     // artifact into the slot on the gondola
		toFireArtifactX = slottedArtifactX;
		toFireArtifactY = slottedArtifactY + 120;

		artifactX = toFireArtifactX;
		artifactY = toFireArtifactY;
		speed = 30;
		//Pngs
		water = new Texture("water.png");
		gondolaL = new Texture("gondolaRight.png");
        gondolaR = new Texture("gondolaLeft.png");
        gondola = gondolaL;
		key = new Artifacts(new Texture("key.png"), false);
		heart = new Artifacts(new Texture("heart.png"), false);
		coin = new Artifacts(new Texture("coin.png"), false);
		star = new Artifacts(new Texture("star.png"), false);
		//I used cam as a divider idk what it actually does
		cam = new OrthographicCamera();
		cam.setToOrtho(false, 1200, 1200);
		//Artifact List
		artifacts = new ArrayList<Artifacts>();
		int total = 0;
		for(int k = 0; k <= 0; k++){			// one key
			artifacts.add(key);
			total++;
		}
		for(int h = 0; h <= 1; h++){			// two hearts
			artifacts.add(heart);
			total++;
		}
		for(int c = 0; c <= 2; c++){			// three coins
			artifacts.add(coin);
			total++;
		}
		for(int s = 0; s <= 3; s++){			// four stars
			artifacts.add(star);
			total++;
		}
		System.out.println("Queue Length: " + total);	// making sure the number of artifacts added is correct
		int random = 0;
		for(int i = 0; i <= artifacts.size() - 1; i++){
			Artifacts temp = artifacts.get(i);
			random = (int)(Math.random() * artifacts.size() - 1);
			artifacts.set(i, artifacts.get(random));
			System.out.println(artifacts.get(i).getArtifact());
			artifacts.set(random, temp);
		}

		/* 																idk ill just hold these for now
		artifacts = new ArrayList<Artifacts>();
		artifacts.add(new Artifacts(new Texture("key.png"), 1));
		artifacts.add(new Artifacts(new Texture("heart.png"), 2));
		artifacts.add(new Artifacts(new Texture("coin.png"), 3));
		artifacts.add(new Artifacts(new Texture("star.png"), 4));
		//Making the Lists
		testing = new ArrayList<Artifacts>();
		for(int i = 0; i <= artifacts.size() - 1; i++){
			while(artifacts.get(i).getNumArtifacts() != 0){
				testing.add(new Artifacts(artifacts.get(i).getArtifact(), false));
				artifacts.get(i).Subtract();
			}
		}
		System.out.println("Queue Length: " + testing.size()); // should add up 1 + 2 + 3 + 4 = 10
		// shuffles the queue
		int random = 0;
		for(int i = 0; i <= testing.size() - 1; i++){
			Artifacts temp = testing.get(i);
			random = (int)(Math.random() * testing.size() - 1);
			System.out.println(testing.set(i, testing.get(random)));
			testing.set(random, temp);
		}
		 */


	}
	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0.2f, 1);
		int length = artifacts.size() - 1;
		batch.begin();
		batch.draw(water, -5, 0);
		batch.draw(gondola, gondolaX, gondolaY);
		if(length > -1){
			if(artifacts.get(length).isLaunched()) {
				if (artifactY > 1150) {
					speed *= -1;
				}
				if (artifactY < 30) {
					speed *= -1;
				}
				if(artifactY <= 30){
					System.out.println("You Lose");
					artifacts.get(length).Launched();
					artifacts.remove(length);
					length --;
				}
				artifactY += speed;
				if(length >= 0){
					batch.draw(artifacts.get(length).getArtifact(), artifactX , artifactY);
				}
			}
			else{
				batch.draw(artifacts.get(length).getArtifact(), gondolaX + toFireArtifactX, gondolaY + toFireArtifactY);
			}
			if(length > 0) {
				batch.draw(artifacts.get(length - 1).getArtifact(), gondolaX + slottedArtifactX, gondolaY + slottedArtifactY);
			}

			if(length == 0){
				System.out.println("YOU WIN");
			}
		}
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
		if(Gdx.input.isKeyPressed(Input.Keys.S)) {
			elapsedTime=(System.nanoTime()-actionBeginTime)/1000000000.0f; // time between the last "S" click and the new one
			if(elapsedTime >  0.05f){ // sets a delay so it doesnt swap a stupid large number of times
				if(!(artifacts.get(length).isLaunched())){
					if(length >= 1){
						Artifacts temp = artifacts.get(length);
						artifacts.set(length, artifacts.get(length - 1));
						artifacts.set(length - 1, temp);
					}

				}
			}
			actionBeginTime=System.nanoTime();// marks the time after the last "S" click
		}

		if(Gdx.input.isKeyPressed(Input.Keys.W)) {
			if(!(artifacts.get(length).isLaunched())){
				artifacts.get(length).Launched();
				artifactX = gondolaX + toFireArtifactX;
				artifactY = gondolaY + toFireArtifactY;
			}
			/*
			elapsedTime=(System.nanoTime()-actionBeginTime)/1000000000.0f;
			if(elapsedTime >  0.2f) {
				artifacts.get(length).Launched();
				artifactX = gondolaX + toFireArtifactX;
				artifactY = gondolaY + toFireArtifactY;

			}
			actionBeginTime=System.nanoTime();

			 */
		}
	}

	@Override
	public void dispose () {
		batch.dispose();
		gondola.dispose();
	}
}
