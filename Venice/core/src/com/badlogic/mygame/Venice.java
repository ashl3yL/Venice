package com.badlogic.mygame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.graphics.g2d.Sprite;
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
	// Most of my textures here were left unused because I kept getting stuck and was unable to move forwards in my map
	// The map I intended to make is also in assets, the screenshot
	private Blocks leftDiag;
	private Blocks rightDiag;
	private Blocks solidLog;
	private Blocks log1;
	private Blocks log2;
	private Blocks logDupe; // the dupes were labeled as dupes because I planned to use them twice in the map
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
	private ArrayList<Sprite> spinArt;
	private ArrayList<Polygon> spinHB;
	//Hitboxes
	private Rectangle gondolaHB;
	private Rectangle artifactHB;
	private Rectangle keySlotHB;
	private Rectangle coinSlotHB;
	private Rectangle starSlotHB;
	private Rectangle heartSlotHB;
	private Rectangle heartSlot1HB;
	private Rectangle heartSlot2HB;
	private Rectangle coinSlot1HB;
	private Rectangle starSlot1HB;
	// private Polygon starSlot2HB; This was my attempt to make the moving hitbox
	private Rectangle starSlot2HB;
	private Rectangle heartSlot3HB;
	private Rectangle heartSlot4HB;
	private Rectangle keySlot1HB;
	private Rectangle coinSlot2HB;
	private Rectangle coinSlot3HB;
	private Rectangle keySlot2HB;
	private Rectangle coinSlot4HB;
	private Rectangle starSlot3HB;
	private Rectangle heartSlot5HB;
	private Rectangle keySlot3HB;
	private Rectangle heartSlot6HB;
	private Rectangle starSlot4HB;
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
	private float circleCenterX;
	private float circleCenterY;
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
	//Spam Booleans
	private boolean filledkey = false;
	private boolean filledcoin = false;
	private boolean filledstar = false;
	private boolean filledheart = false;
	private boolean filledheart1 = false;
	private boolean filledheart2 = false;
	private boolean filledcoin1 = false;
	private boolean filledstar1 = false;

	private boolean filledstar2 = false;
	private boolean filledheart3 = false;
	private boolean filledheart4 = false;
	private boolean filledkey1 = false;
	private boolean filledcoin2 = false;
	private boolean filledcoin3 = false;
	private boolean filledkey2 = false;
	private boolean filledcoin4 = false;
	private boolean filledstar3 = false;
	private boolean filledheart5 = false;
	private boolean filledkey3 = false;
	private boolean filledheart6 = false;
	private boolean filledstar4 = false;


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
		circleCenterX = 600;
		circleCenterY = 450;
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
		starSlotHB = new Rectangle(312, 584, 40, 40);
		heartSlotHB = new Rectangle(371, 583, 40, 40);
		heartSlot1HB = new Rectangle(782, 582, 35, 35);
		heartSlot2HB = new Rectangle(840, 582, 40,40);
		coinSlot1HB = new Rectangle(946, 582, 40,40);
		starSlot1HB = new Rectangle(1010, 581, 40, 40);
		//starSlot2HB = new Polygon(new float[]{circleCenterX - 40, circleCenterY - 40, circleCenterX - 40, circleCenterY + 40, circleCenterX + 40, circleCenterY + 40, circleCenterX + 40, circleCenterY - 40});
		starSlot2HB = new Rectangle(579, 468, 40, 40);
		heartSlot3HB = new Rectangle(490, 406, 40, 40);
		heartSlot4HB = new Rectangle(668, 409, 40, 40);
		keySlot1HB = new Rectangle(513, 294, 40, 40);
		coinSlot2HB = new Rectangle(642, 292, 40,40);
		coinSlot3HB = new Rectangle(581, 898, 40,40);
		keySlot2HB = new Rectangle(583, 843, 40, 40);
		coinSlot4HB = new Rectangle(472, 790, 40,40);
		starSlot3HB = new Rectangle (526, 790, 40,40);
		heartSlot5HB = new Rectangle(640, 787, 40,40);
		keySlot3HB = new Rectangle(697, 786, 40, 40);
		heartSlot6HB = new Rectangle(583, 731, 40,40);
		starSlot4HB = new Rectangle(583, 680, 40,40);





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
        //leftDiag = new Blocks(new Texture("leftDiag.png"), false, true);		I'm not sure if these worked
        //rightDiag = new Blocks(new Texture("rightDiag.png"), false, true);    They were mostly placeholders
        //solidLog = new Blocks(new Texture("solidlog.png"), false, true);
		//log2 = new Blocks(new Texture("log2.png"), true, false);
		//logDupe2 = new Blocks(new Texture("logDupe.png"), true, false);
		//circle = new Blocks(new Texture("circle.png"), true, false);
		//pin2 = new Blocks(new Texture("pin2.png"), true, false);
		//pin3 = new Blocks(new Texture("pin3.png"), true, false)
		log1 = new Blocks(new Sprite(new Texture("log1.png")), false, false, 4);
		log1.getBlock().setPosition(log1X, log1Y);
		logDupe = new Blocks(new Sprite(new Texture("logDupe.png")), false, false, 4);
		logDupe.getBlock().setPosition(logDupeX, logDupeY);
		circleDupe = new Blocks(new Sprite(new Texture("circleDupe.png")), true, false, 5);
		circleDupe.getBlock().setPosition(circleDupeX, circleDupeY); //Served as the location as well as a rotating point
		circleDupe2 = new Blocks(new Sprite(new Texture("circleDupe.png")), true, false, 5);
		circleDupe2.getBlock().setPosition(circleDupeX, circleDupeY); // placeholder
		pin1 = new Blocks(new Sprite(new Texture("pin1.png")), true, false, 8);
		pin1.getBlock().setPosition(pin1X, pin1Y);;

		//Shorter Lists
		slotted = new ArrayList<>();
		spinArt = new ArrayList<>();
		spinHB = new ArrayList<>();
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
		for(int k = 0; k <= 3; k++){			// 4 keys
			artifacts.add(key);
			total++;
		}
		for(int h = 0; h <= 6; h++){			// 7 hearts
			artifacts.add(heart);
			total++;
		}
		for(int c = 0; c <= 4; c++){			// 5 coins
			artifacts.add(coin);
			total++;
		}
		for(int s = 0; s <= 4; s++){			// 5 stars
			artifacts.add(star);
			total++;
		}
		System.out.println("Queue Length: " + total);	// making sure the number of artifacts added is correct
		int random;
		for(int i = 0; i <= artifacts.size() - 1; i++){
			Artifacts temp = artifacts.get(i);
			random = (int)(Math.random() * artifacts.size() - 1);
			artifacts.set(i, artifacts.get(random));
			artifacts.set(random, temp);
		}
	}
	/* These were the variables that turned the blocks and artifact(when locked in) consistently
	int degree = 0;
	float aDegree = 0;
	float HBDegree = 0;
	 */
	@Override
	public void render () {
		ScreenUtils.clear(0.9f, 0.4f, 0.3f, 1);
		int length = artifacts.size() - 1;
		/* Here, the block spins correctly.
		   The artifact spins, but it's a little crooked
		   I'm not sure if the hitbox spun at all but im pretty sure it didn't
		for(int i = 0; i <= blocks.size() - 1; i++){
			if(blocks.get(i).isSpin()){
				blocks.get(i).getBlock().setRotation(degree += 1);
			}
		}
		for(int i = 0; i <= spinArt.size() - 1; i++){
			//spinArt.get(i).setOrigin(600, 300);
			spinArt.get(i).setPosition(circleDupeX + 75, circleDupeY + 70);
			spinArt.get(i).setOrigin(85, 85);
			spinArt.get(i).setRotation(aDegree += 3f);
		}
		for(int i = 0; i <= spinHB.size() - 1; i++){
			spinHB.get(i).setPosition(circleDupeX + 75, circleDupeY + 70);
			spinHB.get(i).setOrigin(85, 85);
			spinHB.get(i).setRotation(HBDegree += 3f);
		}

		 */

		batch.begin();
		batch.draw(map, 33, 0); // Three parts, 0, -600, -1300w
		batch.draw(water, -4, 0);
		batch.draw(gondola, gondolaX, gondolaY);
		for(int i = 0; i <= blocks.size() - 1; i++){
			blocks.get(i).getBlock().draw(batch);
		}
		for(int i = 0; i <= spinArt.size() - 1; i++){
			spinArt.get(i).draw(batch);
		}
		for(int i = 0; i <= slotted.size() - 1; i++){
			batch.draw(slotted.get(i).getArtifact(), slotted.get(i).getXCoord(), slotted.get(i).getYCoord());
		}
		if(length >= -1){
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
					if(!filledkey){
						if(artifacts.get(length).getArtifact().equals(key.getArtifact())){
							slotted.add(new Artifacts(artifacts.get(length).getArtifact(),(int)keySlotHB.x, (int)keySlotHB.y, false));
							artifacts.get(length).Launched();
							artifacts.remove(length);
							length--;
							filledkey = true;
						}
					}
				}
				if(artifactHB.overlaps(coinSlotHB)){
					if(!filledcoin){
						if(artifacts.get(length).getArtifact().equals(coin.getArtifact())){
							slotted.add(new Artifacts(artifacts.get(length).getArtifact(),(int)coinSlotHB.x, (int)coinSlotHB.y, false));
							artifacts.get(length).Launched();
							artifacts.remove(length);
							length--;
							filledcoin = true;
						}
					}
				}
				if(artifactHB.overlaps(starSlotHB)){
					if(!filledstar){
						if(artifacts.get(length).getArtifact().equals(star.getArtifact())){
							slotted.add(new Artifacts(artifacts.get(length).getArtifact(),(int)starSlotHB.x, (int)starSlotHB.y, false));
							artifacts.get(length).Launched();
							artifacts.remove(length);
							length--;
							filledstar = true;
						}
					}
				}
				if(artifactHB.overlaps(heartSlotHB)){
					if(!filledheart){
						if(artifacts.get(length).getArtifact().equals(heart.getArtifact())){
							slotted.add(new Artifacts(artifacts.get(length).getArtifact(),(int)heartSlotHB.x, (int)heartSlotHB.y, false));
							artifacts.get(length).Launched();
							artifacts.remove(length);
							length--;
							filledheart = true;
						}
					}
				}

				if(artifactHB.overlaps(heartSlot1HB)){
					if(!filledheart1) {
						if (artifacts.get(length).getArtifact().equals(heart.getArtifact())) {
							slotted.add(new Artifacts(artifacts.get(length).getArtifact(), (int) heartSlot1HB.x, (int) heartSlot1HB.y, false));
							artifacts.get(length).Launched();
							artifacts.remove(length);
							length--;
							filledheart1 = true;
						}
					}
				}
				if(artifactHB.overlaps(heartSlot2HB)){
					if(!filledheart2){
						if(artifacts.get(length).getArtifact().equals(heart.getArtifact())){
							slotted.add(new Artifacts(artifacts.get(length).getArtifact(),(int)heartSlot2HB.x, (int)heartSlot2HB.y, false));
							artifacts.get(length).Launched();
							artifacts.remove(length);
							length--;
							filledheart2 = true;
						}
					}
				}
				if(artifactHB.overlaps(coinSlot1HB)){
					if(!filledcoin1){
						if(artifacts.get(length).getArtifact().equals(coin.getArtifact())){
							slotted.add(new Artifacts(artifacts.get(length).getArtifact(),(int)coinSlot1HB.x, (int)coinSlot1HB.y, false));
							artifacts.get(length).Launched();
							artifacts.remove(length);
							length--;
							filledcoin1 = true;
						}
					}
				}
				if(artifactHB.overlaps(starSlot1HB)){
					if(!filledstar1){
						if(artifacts.get(length).getArtifact().equals(star.getArtifact())){
							slotted.add(new Artifacts(artifacts.get(length).getArtifact(),(int)starSlot1HB.x, (int)starSlot1HB.y, false));
							artifacts.get(length).Launched();
							artifacts.remove(length);
							length--;
							filledstar1 = true;
						}
					}
				}
				if(artifactHB.overlaps(starSlot2HB)){
					if(!filledstar2){
						if(artifacts.get(length).getArtifact().equals(star.getArtifact())){
							slotted.add(new Artifacts(artifacts.get(length).getArtifact(),(int)starSlot2HB.x, (int)starSlot2HB.y, false));
							artifacts.get(length).Launched();
							artifacts.remove(length);
							length--;
							filledstar2 = true;
						}
					}
				}
				if(artifactHB.overlaps(starSlot2HB)){
					if(!filledstar2){
						if(artifacts.get(length).getArtifact().equals(star.getArtifact())){
							slotted.add(new Artifacts(artifacts.get(length).getArtifact(),(int)starSlot2HB.x, (int)starSlot2HB.y, false));
							artifacts.get(length).Launched();
							artifacts.remove(length);
							length--;
							filledstar2 = true;
						}
					}
				}
				if(artifactHB.overlaps(heartSlot3HB)){
					if(!filledheart3){
						if(artifacts.get(length).getArtifact().equals(heart.getArtifact())){
							slotted.add(new Artifacts(artifacts.get(length).getArtifact(),(int)heartSlot3HB.x, (int)heartSlot3HB.y, false));
							artifacts.get(length).Launched();
							artifacts.remove(length);
							length--;
							filledheart3 = true;
						}
					}
				}
				if(artifactHB.overlaps(heartSlot4HB)){
					if(!filledheart4){
						if(artifacts.get(length).getArtifact().equals(heart.getArtifact())){
							slotted.add(new Artifacts(artifacts.get(length).getArtifact(),(int)heartSlot4HB.x, (int)heartSlot4HB.y, false));
							artifacts.get(length).Launched();
							artifacts.remove(length);
							length--;
							filledheart4 = true;
						}
					}
				}
				if(artifactHB.overlaps(keySlot1HB)){
					if(!filledkey1){
						if(artifacts.get(length).getArtifact().equals(key.getArtifact())){
							slotted.add(new Artifacts(artifacts.get(length).getArtifact(),(int)keySlot1HB.x, (int)keySlot1HB.y, false));
							artifacts.get(length).Launched();
							artifacts.remove(length);
							length--;
							filledkey1 = true;
						}
					}
				}
				if(artifactHB.overlaps(coinSlot2HB)){
					if(!filledcoin2){
						if(artifacts.get(length).getArtifact().equals(coin.getArtifact())){
							slotted.add(new Artifacts(artifacts.get(length).getArtifact(),(int)coinSlot2HB.x, (int)coinSlot2HB.y, false));
							artifacts.get(length).Launched();
							artifacts.remove(length);
							length--;
							filledcoin2 = true;
						}
					}
				}
				if(artifactHB.overlaps(coinSlot3HB)){
					if(!filledcoin3){
						if(artifacts.get(length).getArtifact().equals(coin.getArtifact())){
							slotted.add(new Artifacts(artifacts.get(length).getArtifact(),(int)coinSlot3HB.x, (int)coinSlot3HB.y, false));
							artifacts.get(length).Launched();
							artifacts.remove(length);
							length--;
							filledcoin3 = true;
						}
					}
				}
				if(artifactHB.overlaps(keySlot2HB)){
					if(!filledkey2){
						if(artifacts.get(length).getArtifact().equals(key.getArtifact())){
							slotted.add(new Artifacts(artifacts.get(length).getArtifact(),(int)keySlot2HB.x, (int)keySlot2HB.y, false));
							artifacts.get(length).Launched();
							artifacts.remove(length);
							length--;
							filledkey2 = true;
						}
					}
				}
				if(artifactHB.overlaps(coinSlot4HB)){
					if(!filledcoin4){
						if(artifacts.get(length).getArtifact().equals(coin.getArtifact())){
							slotted.add(new Artifacts(artifacts.get(length).getArtifact(),(int)coinSlot4HB.x, (int)coinSlot4HB.y, false));
							artifacts.get(length).Launched();
							artifacts.remove(length);
							length--;
							filledcoin4 = true;
						}
					}
				}
				if(artifactHB.overlaps(starSlot3HB)){
					if(!filledstar3){
						if(artifacts.get(length).getArtifact().equals(star.getArtifact())){
							slotted.add(new Artifacts(artifacts.get(length).getArtifact(),(int)starSlot3HB.x, (int)starSlot3HB.y, false));
							artifacts.get(length).Launched();
							artifacts.remove(length);
							length--;
							filledstar3 = true;
						}
					}
				}
				if(artifactHB.overlaps(heartSlot5HB)){
					if(!filledheart5){
						if(artifacts.get(length).getArtifact().equals(heart.getArtifact())){
							slotted.add(new Artifacts(artifacts.get(length).getArtifact(),(int)heartSlot5HB.x, (int)heartSlot5HB.y, false));
							artifacts.get(length).Launched();
							artifacts.remove(length);
							length--;
							filledheart5 = true;
						}
					}
				}
				if(artifactHB.overlaps(keySlot3HB)){
					if(!filledkey3){
						if(artifacts.get(length).getArtifact().equals(key.getArtifact())){
							slotted.add(new Artifacts(artifacts.get(length).getArtifact(),(int)keySlot3HB.x, (int)keySlot3HB.y, false));
							artifacts.get(length).Launched();
							artifacts.remove(length);
							length--;
							filledkey3 = true;
						}
					}
				}
				if(artifactHB.overlaps(heartSlot6HB)){
					if(!filledheart6){
						if(artifacts.get(length).getArtifact().equals(heart.getArtifact())){
							slotted.add(new Artifacts(artifacts.get(length).getArtifact(),(int)heartSlot6HB.x, (int)heartSlot6HB.y, false));
							artifacts.get(length).Launched();
							artifacts.remove(length);
							length--;
							filledheart6 = true;
						}
					}
				}
				if(artifactHB.overlaps(starSlot4HB)){
					if(!filledstar4){
						if(artifacts.get(length).getArtifact().equals(star.getArtifact())){
							slotted.add(new Artifacts(artifacts.get(length).getArtifact(),(int)starSlot4HB.x, (int)starSlot4HB.y, false));
							artifacts.get(length).Launched();
							artifacts.remove(length);
							length--;
							filledstar4 = true;
						}
					}
				}
				/* // This was my testing hitbox for when the circle rotated
				if(isCollision(starSlot2HB, artifactHB)){
					if(artifacts.get(length).getArtifact().equals(star.getArtifact())){
						spinArt.add(new Sprite(artifacts.get(length).getArtifact()));
						spinHB.add(starSlot2HB);
						artifacts.get(length).Launched();
						artifacts.remove(length);
						length--;
					}
				}
				 */
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
				batch.draw(artifacts.get(length).getArtifact(), gondolaX + toFireArtifactX, gondolaY + toFireArtifactY);
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
	}
	private boolean isCollision(Polygon p, Rectangle r) {
		Polygon rPoly = new Polygon(new float[] { 0, 0, r.width, 0, r.width,
				r.height, 0, r.height });
		rPoly.setPosition(r.x, r.y);
		if (Intersector.overlapConvexPolygons(rPoly, p))
			return true;
		return false;
	}

	@Override
	public void dispose () {
		batch.dispose();
		gondola.dispose();
	}
}
