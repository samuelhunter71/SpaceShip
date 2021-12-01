package graphics;

import java.awt.Font;
import java.awt.image.BufferedImage;

import javax.sound.sampled.Clip;

public class Assets {
	
	public static boolean loaded=false;
	public static float count=0;
	public static float MAX_COUNT=47;
	
	public static BufferedImage icon;
	
	public static BufferedImage player;
	
	//effects
	
	public static BufferedImage speed;
	
	//lasers
	
	public static BufferedImage rLaser, gLaser, bLaser;;
	
	//meteors
	public static BufferedImage[] bigs = new BufferedImage[4];
	public static BufferedImage[] meds = new BufferedImage[2];
	public static BufferedImage[] smalls = new BufferedImage[2];
	
	//explosions
	public static BufferedImage[] exp = new BufferedImage[9];
	
	//ufo
	public static BufferedImage yUfo, gUfo, rUfo;
	
	//numbers
	public static BufferedImage[] numbers = new BufferedImage[11];

	public static BufferedImage life;
	
	//font
	public static Font fontBig, fontMed, fontSmall;
	
	//sounds
	public static Clip bgMusic, Xplo, plLoose, plShoot, ufoShoot;
	
	//Buttons
	public static BufferedImage btnBlue, btnBlueF;
	
	//boss
	public static BufferedImage boss;
	
	public static void init() {
		icon=loadImg("res/hud/icon.png");
		
		player=loadImg("res/ships/player.png");
		
		speed=loadImg("res/effects/fire08.png");
		
		rLaser=loadImg("res/lasers/laserRed01.png");
		gLaser=loadImg("res/lasers/laserGreen11.png");
		bLaser=loadImg("res/lasers/laserBlue01.png");
		
		for (int i = 0; i < bigs.length; i++) {
			bigs[i] = loadImg("res/meteors/big"+(i+1)+".png");
		}
		
		for (int i = 0; i < meds.length; i++) {
			meds[i] = loadImg("res/meteors/med"+(i+1)+".png");
		}
		
		for (int i = 0; i < smalls.length; i++) {
			smalls[i] = loadImg("res/meteors/small"+(i+1)+".png");
		}
		
		for (int i = 0; i < exp.length; i++) {
			exp[i] = loadImg("res/explosion/"+(i)+".png");
		}
		
		yUfo=loadImg("res/ships/ufo.png");
		gUfo=loadImg("res/ships/ufoGreenSmall.png");
		rUfo=loadImg("res/ships/rUfo.png");
		
		for (int i = 0; i < numbers.length; i++) {
			numbers[i] = loadImg("res/hud/numbers/"+(i)+".png");
		}
		
		life=loadImg("res/hud/life.png");
		
		fontBig=loadFont("res/fonts/future.ttf", 42);
		fontMed=loadFont("res/fonts/future.ttf", 20);
		fontSmall=loadFont("res/fonts/future.ttf", 14);
		
		bgMusic=loadSound("res/sounds/bgMusic.wav");
		Xplo=loadSound("res/sounds/Xplo.wav");
		plLoose=loadSound("res/sounds/plLoose.wav");
		plShoot=loadSound("res/sounds/plShoot.wav");
		ufoShoot=loadSound("res/sounds/ufoShoot.wav");
		
		btnBlue=loadImg("res/hud/btnNormal.png");
		btnBlueF=loadImg("res/hud/btnFocus.png");
		
		boss=loadImg("res/ships/boss.png");
		
		loaded=true;
	}
	
	public static BufferedImage loadImg(String path) {
		count++;
		return Loader.imageLoader(path);
	}
	
	public static Clip loadSound(String path) {
		count++;
		return Loader.loadSound(path);
	}
	
	public static Font loadFont(String path, int size) {
		count++;
		return Loader.loadFont(path, size);
	}
	
}
