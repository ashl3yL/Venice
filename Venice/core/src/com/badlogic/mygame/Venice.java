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
	private Texture map;
	private Artifacts key;
	private Artifacts heart;
	private Artifacts coin;
	private Artifacts star;
	private Blocks leftDiag;
	private Blocks rightDiag;
	private Blocks solidLog;
	private Blocks log1;
	private Blocks log2;
	private Blocks logDupe;
    private Blocks logDupe2;
	private Blocks circle;
	private Blocks circleDupe;
    private Blocks circleDupe2;
    private Blocks pin1;
    private Blocks pin2;
    private Blocks pin3;

	//Lists
	private ArrayList<Artifacts> artifacts;
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
		map = new Texture("map.png");
		water = new Texture("water.png");
		gondolaL = new Texture("gondolaRight.png");
        gondolaR = new Texture("gondolaLeft.png");
        gondola = gondolaL;
		key = new Artifacts(new Texture("key.png"), false);
		heart = new Artifacts(new Texture("heart.png"), false);
		coin = new Artifacts(new Texture("coin.png"), false);
		star = new Artifacts(new Texture("star.png"), false);
        leftDiag = new Blocks(new Texture("leftDiag.png"), false, true);
        rightDiag = new Blocks(new Texture("rightDiag.png"), false, true);
        solidLog = new Blocks(new Texture("solidlog.png"), false, true);
        log1 = new Blocks(new Texture("log1.png"), false, false);
        log2 = new Blocks(new Texture("log2.png"), true, false);
        logDupe = new Blocks(new Texture("logDupe.png"), false, false);
        logDupe2 = new Blocks(new Texture("logDupe.png"), true, false);
        circle = new Blocks(new Texture("circle.png"), true, false);
        circleDupe = new Blocks(new Texture("circleDupe.png"), true, false);
        circleDupe2 = new Blocks(new Texture("circleDupe.png"), true, false);
        pin1 = new Blocks(new Texture("pin1.png"), true, false);
        pin2 = new Blocks(new Texture("pin2.png"), true, false);
        pin3 = new Blocks(new Texture("pin3.png"), true, false);
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
	}
	@Override
	public void render () {
		ScreenUtils.clear(0.9f, 0.4f, 0.3f, 1);
		int length = artifacts.size() - 1;
		batch.begin();
		batch.draw(map, 33, 0); // Three parts, 0, -600, -1300
		batch.draw(water, -4, 0);
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
			if(length >= 1){
				if(!(artifacts.get(length).isLaunched())){
					artifacts.get(length).Launched();
					artifactX = gondolaX + toFireArtifactX;
					artifactY = gondolaY + toFireArtifactY;
				}
			}

		}
	}

	@Override
	public void dispose () {
		batch.dispose();
		gondola.dispose();
	}
}
