package main;

import javax.swing.filechooser.FileSystemView;

public class C {
	
	public static final int WIDTH = 1280;
	public static final int HEIGHT = 720;
	
	public static final double PLAYERSPEED=7.0;
	public static final double ACC=0.2;
	public static final double DELTAANGLE=0.1;//0.1
	public static final double PLAYER_ROT_SPEED=0.09;//Minimum 0.10; Maximum 0.15 with upgrades
	public static final int START_LIVES = 5;//5 ONLY five!!!!
	public static final long FLICKER_TIME = 200;
	public static final long SPAWNING_TIME = 3000;
	
	public static final int FIRERATE = 400;//300
	public static final double LASERSPEED = 10.0;//10.0
	
	//meteors properties
	public static final double METEOR_VEL = 2.0;
	public static final int METEOR_SCORE = 20;
	
	public static final int NODE_RADIUS=160;
	public static final double UFO_MASS = 60.0;
	public static final double UFO_ROTATION = 0.05;
	public static final int UFO_MAX_VEL = 3;
	public static final long UFO_FIRERATE = 500;
	public static final int UFO_SCORE = 40;
	public static double UFO_ANGLE_RANGE = Math.PI/2;
	public static final long UFO_SPAWN_RATE = 10000;
	
	public static final String PLAY = "Play";
	public static final String EXIT = "Exit";
	public static final String GAME_OVER = "Game Over";
	public static final long GAME_OVER_TIME = 3000;
	public static final String RETURN = "Back";
	public static final String HIGH_SCORES = "High Scores";
	public static final String SCORE = "Score";
	public static final String DATE = "Date";
	public static final String WAVES = "Waves";
	public static final String RESUME = "Resume";
	public static final String MAIN_MENU = "Main Menu";
	public static final String RESTART = "Restart";
	public static final String MAIN_MENU_TEXT = "Are you sure do yo want to return to the Main Menu?";
	public static final String RESTART_TEXT = "Are you sure do yo want to restart the game?";
	public static final String RESTART_TEXT_2 = "Your progress will not be saved";
	public static final String CANCEL = "Cancel";
	public static final String CONFIRM = "Confirm";
	public static final String PAUSE = "Pause";
	public static final String BOSS_WAVE = "Final Boss";
	public static final String YOU_WIN = "You Win";
	
	public static final int LOADING_BAR_WIDTH = WIDTH/2;
	public static final int LOADING_BAR_HEIGHT = 50;
	
	public static final String SCORE_PATH = FileSystemView.getFileSystemView().getDefaultDirectory().getPath()+"\\My Games\\Space Ship Game\\data.json";
	
	public static final long BOSS_FIRERATE=400;
	public static final float BOSS_LIVES=100;
	public static final double BOSS_MASS = 20.0;
	public static final int BOSS_SCORE = 10000;
}
