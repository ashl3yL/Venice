package com.badlogic.mygame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
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
	private ArrayList<Blocks> blocks;
	private ArrayList<Artifacts> slotted;
	//Hitboxes
	private Rectangle gondolaHB;
	private Rectangle artifactHB;
	private Rectangle keySlotHB;
	private Rectangle heartSlotHB;
	private Rectangle heartSlot1HB;
	private Rectangle heartSlot2HB;
	private Rectangle coinSlotHB;
	private Rectangle coinSlot1HB;
	private Rectangle starSlotHB;
	private Rectangle starSlot1HB;
	private Rectangle starSlot2HB;
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
	private float circleDupeX;
	private float circleDupeY;
	private int logDupeX;
	private int logDupeY;
	private int log1X;
	private int log1Y;
	private float pin1X;
	private float pin1Y;
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

		circleDupeX = 443;
		circleDupeY = 230;
		logDupeX = 130;
		logDupeY = 440;
		log1X = 755;
		log1Y = logDupeY;
		pin1X = circleDupeX;
		pin1Y = 650;
		//Hitboxes
		gondolaHB = new Rectangle(gondolaX, gondolaY, 296, 120);
		artifactHB = new Rectangle(artifactX, artifactY, 35, 35);
		keySlotHB = new Rectangle(156, 581, 40,40);
		coinSlotHB = new Rectangle(219, 583, 40, 40);
		coinSlot1HB = new Rectangle(946, 582, 40,40);
		starSlotHB = new Rectangle(312, 584, 40, 40);
		starSlot1HB = new Rectangle(1010, 581, 40, 40);
		starSlot2HB = new Rectangle(443, 280, 40, 40);
		heartSlotHB = new Rectangle(371, 583, 40, 40);
		heartSlot1HB = new Rectangle(782, 582, 40, 40);
		heartSlot2HB = new Rectangle(840, 582, 40,40);


		//Pngs
		map = new Texture("map.png");
		water = new Texture("water.png");
		gondolaL = new Texture("gondolaRight.png");
        gondolaR = new Texture("gondolaLeft.png");
        gondola = gondolaL;
		key = new Artifacts(new Texture("key.png"), false);
		heart = new Artifacts(new Texture("heart.png"), false);
		coin = new Artifacts(new Texture("coin.png"),  false);
		star = new Artifacts(new Texture("star.png"),  false);
        //leftDiag = new Blocks(new Texture("leftDiag.png"), false, true);
        //rightDiag = new Blocks(new Texture("rightDiag.png"), false, true);
        //solidLog = new Blocks(new Texture("solidlog.png"), false, true);

		log1 = new Blocks(new Sprite(new Texture("log1.png")), false, false, 4);
		log1.getBlock().setPosition(log1X, log1Y);

        //log2 = new Blocks(new Texture("log2.png"), true, false);


		logDupe = new Blocks(new Sprite(new Texture("logDupe.png")), false, false, 4);
		logDupe.getBlock().setPosition(logDupeX, logDupeY);
        //logDupe2 = new Blocks(new Texture("logDupe.png"), true, false);
        //circle = new Blocks(new Texture("circle.png"), true, false);

		circleDupe = new Blocks(new Sprite(new Texture("circleDupe.png")), true, false, 5);
		circleDupe.getBlock().setPosition(circleDupeX, circleDupeY);
		circleDupe2 = new Blocks(new Sprite(new Texture("circleDupe.png")), true, false, 5);
		circleDupe2.getBlock().setPosition(circleDupeX, circleDupeY); // placeholders
		pin1 = new Blocks(new Sprite(new Texture("pin1.png")), true, false, 8);
		pin1.getBlock().setPosition(pin1X, pin1Y);


		//pin2 = new Blocks(new Texture("pin2.png"), true, false);
        //pin3 = new Blocks(new Texture("pin3.png"), true, false);

		//Slotted List
		slotted = new ArrayList<>();
		//Blocks List
		blocks = new ArrayList<>();
		//blocks.add(leftDiag);
		//blocks.add(rightDiag);
		//blocks.add(solidLog);
		blocks.add(log1);
		//blocks.add(log2);
		blocks.add(logDupe);
		//blocks.add(logDupe2);
		//blocks.add(circle);
		blocks.add(circleDupe);
		blocks.add(circleDupe2);
		blocks.add(pin1);
		//blocks.add(pin2);
		//blocks.add(pin3);
		//I used cam as a divider idk what it actually does
		cam = new OrthographicCamera();
		cam.setToOrtho(false, 1200, 1200);
		//Artifact List
		artifacts = new ArrayList<>();
		int total = 0;
		for(int k = 0; k <= 12; k++){			// 13 keys
			artifacts.add(key);
			total++;
		}
		for(int h = 0; h <= 15; h++){			// 16 hearts
			artifacts.add(heart);
			total++;
		}
		for(int c = 0; c <= 12; c++){			// 13 coins
			artifacts.add(coin);
			total++;
		}
		for(int s = 0; s <= 11; s++){			// 12 stars
			artifacts.add(star);
			total++;
		}
		System.out.println("Queue Length: " + total);	// making sure the number of artifacts added is correct
		int random;
		for(int i = 0; i <= artifacts.size() - 1; i++){
			Artifacts temp = artifacts.get(i);
			random = (int)(Math.random() * artifacts.size() - 1);
			artifacts.set(i, artifacts.get(random));
			System.out.println(artifacts.get(i).getArtifact());
			artifacts.set(random, temp);
		}
	}
	int degree = 0;
	@Override
	public void render () {
		ScreenUtils.clear(0.9f, 0.4f, 0.3f, 1);
		int length = artifacts.size() - 1;
		for(int i = 0; i <= blocks.size() - 1; i++){
			if(blocks.get(i).isSpin()){
				blocks.get(i).getBlock().setRotation(degree += 1);
			}
		}

		batch.begin();
		batch.draw(map, 33, 0); // Three parts, 0, -600, -1300
		batch.draw(water, -4, 0);
		batch.draw(gondola, gondolaX, gondolaY);
		for(int i = 0; i <= blocks.size() - 1; i++){
			blocks.get(i).getBlock().draw(batch);
		}
		for(int i = 0; i <= slotted.size() - 1; i++){
			batch.draw(slotted.get(i).getArtifact(), slotted.get(i).getXCoord(), slotted.get(i).getYCoord());
		}
		if(length > -1){
			if(artifacts.get(length).isLaunched()) {
				if (artifactY > 1150) {
					speed *= -1;
				}
				if (artifactY < 30) {
					speed *= -1;
				}
				if(gondolaHB.overlaps(artifactHB)) {
					elapsedTime=(System.nanoTime()-actionBeginTime)/1000000000.0f;
					if(elapsedTime >  0.2f){
						System.out.print("yay ");
						artifacts.get(length).Launched();
						artifacts.add(0, artifacts.remove(length));
						speed *= -1;
					}
					actionBeginTime=System.nanoTime();
				}
				if(artifactHB.overlaps(keySlotHB)){
					if(artifacts.get(length).getArtifact().equals(key.getArtifact())){
						slotted.add(new Artifacts(artifacts.get(length).getArtifact(),(int)keySlotHB.x, (int)keySlotHB.y));
						artifacts.remove(length);
						length--;
					}
				}
				if(artifactHB.overlaps(coinSlotHB)){
					if(artifacts.get(length).getArtifact().equals(coin.getArtifact())){
						slotted.add(new Artifacts(artifacts.get(length).getArtifact(),(int)coinSlotHB.x, (int)coinSlotHB.y));
						artifacts.remove(length);
						length--;
					}
				}
				if(artifactHB.overlaps(coinSlot1HB)){
					if(artifacts.get(length).getArtifact().equals(coin.getArtifact())){
						slotted.add(new Artifacts(artifacts.get(length).getArtifact(),(int)coinSlot1HB.x, (int)coinSlot1HB.y));
						artifacts.remove(length);
						length--;
					}
				}
				if(artifactHB.overlaps(starSlotHB)){
					if(artifacts.get(length).getArtifact().equals(star.getArtifact())){
						slotted.add(new Artifacts(artifacts.get(length).getArtifact(),(int)starSlotHB.x, (int)starSlotHB.y));
						artifacts.remove(length);
						length--;
					}
				}
				if(artifactHB.overlaps(starSlot1HB)){
					if(artifacts.get(length).getArtifact().equals(star.getArtifact())){
						slotted.add(new Artifacts(artifacts.get(length).getArtifact(),(int)starSlot1HB.x, (int)starSlot1HB.y));
						artifacts.remove(length);
						length--;
					}
				}
				if(artifactHB.overlaps(starSlot2HB)){
					if(artifacts.get(length).getArtifact().equals(star.getArtifact())){
						slotted.add(new Artifacts(artifacts.get(length).getArtifact(),(int)starSlot2HB.x, (int)starSlot2HB.y));
						artifacts.remove(length);
						length--;
					}
				}
				if(artifactHB.overlaps(heartSlotHB)){
					if(artifacts.get(length).getArtifact().equals(heart.getArtifact())){
						slotted.add(new Artifacts(artifacts.get(length).getArtifact(),(int)heartSlotHB.x, (int)heartSlotHB.y));
						artifacts.remove(length);
						length--;
					}
				}
				if(artifactHB.overlaps(heartSlot1HB)){
					if(artifacts.get(length).getArtifact().equals(heart.getArtifact())){
						slotted.add(new Artifacts(artifacts.get(length).getArtifact(),(int)heartSlot1HB.x, (int)heartSlot1HB.y));
						artifacts.remove(length);
						length--;
					}
				}
				if(artifactHB.overlaps(heartSlot2HB)){
					if(artifacts.get(length).getArtifact().equals(heart.getArtifact())){
						slotted.add(new Artifacts(artifacts.get(length).getArtifact(),(int)heartSlot2HB.x, (int)heartSlot2HB.y));
						artifacts.remove(length);
						length--;
					}
				}
				if(artifactY <= 20){
					System.out.println("You Lose");
					artifacts.get(length).Launched();
					artifacts.remove(length);
					length--;
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
		gondolaHB.setPosition(gondolaX, gondolaY);
		artifactHB.setPosition(artifactX, artifactY);
		int x = 10; //443, 420;
		int y = 10;
		starSlot2HB.setPosition((float)(443 + Math.sqrt(1000 - Math.pow((double)y, 2.0) + (3 * y * 420) - (420 * 420))), (float)(Math.sqrt(1000 - Math.pow((double)x, 2.0) + (2 * x * 443) - (443 * 443)) + 420));
	}

	@Override
	public void dispose () {
		batch.dispose();
		gondola.dispose();
	}
}
