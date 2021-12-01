package states;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import main.C;
import gameObjects.Boss;
import gameObjects.FollowingUfo;
import gameObjects.Message;
import gameObjects.Meteor;
import gameObjects.MovingObject;
import gameObjects.NormalUfo;
import gameObjects.Player;
import gameObjects.Size;
import gameObjects.StaticUfo;
import graphics.Animation;
import graphics.Assets;
import graphics.Sound;
import input.KeyBoard;
import io.JSONParser;
import io.ScoreData;
import math.Chrono;
import math.Vector2D;

public class GameState extends State{
	public static final Vector2D PLAYER_START_POSITION = new Vector2D(C.WIDTH/2 - Assets.player.getWidth()/2,
			C.HEIGHT/2 - Assets.player.getHeight()/2);
	
	private static Player player;
	private ArrayList<MovingObject> movingObjects = new ArrayList<MovingObject>();
	private ArrayList<Animation> explosions = new ArrayList<Animation>();
	private ArrayList<Message> messages = new ArrayList<Message>();
	
	private int score = 0;
	private int lives;
	
	private int meteors;
	private int waves = 1;
	private int Rufo = 0;
	private long spawnrate;
	
	float percent;
	
	private Sound bgMusic;
	
	private Chrono ufoSpawner, gameOverTimer;
	private boolean gameOver, spawnUfos, boss;
	
	public GameState()
	{
		player = new Player(PLAYER_START_POSITION, new Vector2D(),
				C.PLAYERSPEED, Assets.player, this);
		
		gameOverTimer=new Chrono();
		gameOver=false;
		spawnUfos=true;
		boss=false;
		movingObjects.add(player);
		
		meteors = 1;
		lives=C.START_LIVES;
		startWave();
		bgMusic = new Sound(Assets.bgMusic);
		bgMusic.loop();
		bgMusic.changeVolume(-10.0f);
		spawnrate = C.UFO_SPAWN_RATE;
		
		ufoSpawner=new Chrono();
		ufoSpawner.run(spawnrate);
	}
	
	
	public void addScore(int value, Vector2D position) {
		score += value;
		messages.add(new Message(position, true, "+"+value+" score", Color.WHITE, false, Assets.fontMed));
	}
	
	public void divideMeteor(Meteor meteor){
		
		Size size = meteor.getSize();
		
		BufferedImage[] textures = size.textures;
		
		Size newSize = null;
		
		switch(size){
		case BIG:
			newSize =  Size.MED;
			break;
		case MED:
			newSize = Size.SMALL;
			break;
		default:
			return;
		}
		
		for(int i = 0; i < size.quantity; i++){
			movingObjects.add(new Meteor(
					meteor.getPos(),
					new Vector2D(0, 1).setDirection(Math.random()*Math.PI*2),
					C.METEOR_VEL*Math.random() + 1,
					textures[(int)(Math.random()*textures.length)],
					this,
					newSize
					));
		}
	}
	
	private void startWave(){
		if(waves==16) {
			boss=true;
			bossWave();
		}else if(waves==17) {
			changeState(new WinState(score));
		}else {
			boss=false;
			standartWave();
			
			messages.add(new Message(new Vector2D(C.WIDTH/2, C.HEIGHT/2), false,
				"WAVE "+waves, Color.WHITE, true, Assets.fontBig));
		}
		
		waves++;
	}
	
	private void standartWave() {
		
		spawnUfos=true;
		
		switch(waves){
			case 2:
			case 3:
			case 4:
				meteors=2;
			break;
	
			case 6:
			case 7:
			case 8:
				meteors=3;
			break;
	
			case 9:
			case 11:
			case 12:
				meteors=4;
			break;
	
			case 13:
			case 14:
				meteors=5;
			break;
		}
		
		
		meteors=(int)((waves-1)/5)+2;
		
		spawnMeteors();
	}
	
	private void bossWave() {
		spawnUfos=false;
		boss=true;
		
		messages.add(new Message(new Vector2D(C.WIDTH/2, C.HEIGHT/2), false,
				C.BOSS_WAVE, Color.WHITE, true, Assets.fontBig));
		
		int rand = (int) (Math.random()*2);
		
		double x = rand == 0 ? (Math.random()*C.WIDTH): C.WIDTH;
		double y = rand == 0 ? C.HEIGHT : (Math.random()*C.HEIGHT);	
		
		ArrayList<Vector2D> path = new ArrayList<Vector2D>();
		
		double posX, posY;
		//
		posX = Math.random()*C.WIDTH/2;
		posY = Math.random()*C.HEIGHT/2;	
		path.add(new Vector2D(posX, posY));
		
		posX = Math.random()*C.WIDTH/2;
		posY = Math.random()*C.HEIGHT/2;	
		path.add(new Vector2D(posX, posY));
		
		posX = Math.random()*C.WIDTH/2;
		posY = Math.random()*C.HEIGHT/2;	
		path.add(new Vector2D(posX, posY));

		posX = Math.random()*(C.WIDTH/2) + C.WIDTH/2;
		posY = Math.random()*C.HEIGHT/2;	
		path.add(new Vector2D(posX, posY));
		
		posX = Math.random()*(C.WIDTH/2) + C.WIDTH/2;
		posY = Math.random()*C.HEIGHT/2;	
		path.add(new Vector2D(posX, posY));
		
		posX = Math.random()*(C.WIDTH/2) + C.WIDTH/2;
		posY = Math.random()*C.HEIGHT/2;	
		path.add(new Vector2D(posX, posY));
		
		posX = Math.random()*C.WIDTH/2;
		posY = Math.random()*(C.HEIGHT/2) + C.HEIGHT/2;	
		path.add(new Vector2D(posX, posY));
		
		posX = Math.random()*C.WIDTH/2;
		posY = Math.random()*(C.HEIGHT/2) + C.HEIGHT/2;	
		path.add(new Vector2D(posX, posY));
		
		posX = Math.random()*C.WIDTH/2;
		posY = Math.random()*(C.HEIGHT/2) + C.HEIGHT/2;	
		path.add(new Vector2D(posX, posY));
		
		posX = Math.random()*(C.WIDTH/2) + C.WIDTH/2;
		posY = Math.random()*(C.HEIGHT/2) + C.HEIGHT/2;	
		path.add(new Vector2D(posX, posY));
		
		posX = Math.random()*(C.WIDTH/2) + C.WIDTH/2;
		posY = Math.random()*(C.HEIGHT/2) + C.HEIGHT/2;	
		path.add(new Vector2D(posX, posY));
		
		posX = Math.random()*(C.WIDTH/2) + C.WIDTH/2;
		posY = Math.random()*(C.HEIGHT/2) + C.HEIGHT/2;	
		path.add(new Vector2D(posX, posY));
		
		movingObjects.add(new Boss(
				new Vector2D(x, y),
				new Vector2D(),
				C.UFO_MAX_VEL,
				Assets.boss,
				path,
				this
				));
		
	}
	
	public void playExplosion(Vector2D pos){
		explosions.add(new Animation(
				Assets.exp,
				50,
				pos.subtract(new Vector2D(Assets.exp[0].getWidth()/2, Assets.exp[0].getHeight()/2))
				));
	}
	
	private void spawnUfo(){
		int cont = 2; 
		
		switch(waves){
    	   case 1:
    	   case 2:
    	   case 3:
    		   cont = 2;
    		   break;
    	   case 4:
    	   case 5:
    	   case 6:
    		   cont = 3;
    		   break;
    	   case 7:
    	   case 8:
    	   case 9:
    		   cont = 4;
    		   break;
    	   case 10:
    	   case 11:
    	   case 12:
    		   spawnrate = 8500 ;
    		   cont = 4;
    		   break;
    	   case 13:
    	   case 14:
    	   case 15:
    		   spawnrate = 7000 ;
    		   cont = 4;
    	   break;
		}

		Rufo = (int) (Math.random() * cont );
		int rand = (int) (Math.random()*2);
    	 
		switch(Rufo){
		
		case 3:
			double x = rand == 0 ? (Math.random()*C.WIDTH): C.WIDTH;
			double y = rand == 0 ? C.HEIGHT : (Math.random()*C.HEIGHT);	
			
			normalUfo(x, y);
			
		case 2:
			
			x = rand == 0 ? (Math.random()*C.WIDTH): C.WIDTH;
			y = rand == 0 ? C.HEIGHT : (Math.random()*C.HEIGHT);	
			
			staticUfo(x, y);
		
		case 1:
			x = rand == 0 ? (Math.random()*C.WIDTH): C.WIDTH;
			y = rand == 0 ? C.HEIGHT : (Math.random()*C.HEIGHT);
			followingUfo(x, y);
			break;
   			
   			default:
   				spawnUfo();
   			
    	   
    	   }
   	}
	
	private void followingUfo(double x, double y) {	
		
		movingObjects.add(new FollowingUfo(
				new Vector2D(x, y),
				new Vector2D(),
				C.UFO_MAX_VEL,
				Assets.gUfo,
				this
				));
	}
	
	private void staticUfo(double x, double y) {
		
		ArrayList<Vector2D> path = new ArrayList<Vector2D>();
		double posX, posY;
		posX = Math.random()*C.WIDTH;
		posY = Math.random()*C.HEIGHT;	
		path.add(new Vector2D(posX, posY));
		
		movingObjects.add(new StaticUfo(
				new Vector2D(x, y),
				new Vector2D(),
				C.UFO_MAX_VEL,
				Assets.rUfo,
				path,
				this
				));
	}
	
	private void normalUfo(double x, double y) {
		ArrayList<Vector2D> path = new ArrayList<Vector2D>();
		
		double posX, posY;
		posX = Math.random()*C.WIDTH/2;
		posY = Math.random()*C.HEIGHT/2;	
		path.add(new Vector2D(posX, posY));

		posX = Math.random()*(C.WIDTH/2) + C.WIDTH/2;
		posY = Math.random()*C.HEIGHT/2;	
		path.add(new Vector2D(posX, posY));
		
		posX = Math.random()*C.WIDTH/2;
		posY = Math.random()*(C.HEIGHT/2) + C.HEIGHT/2;	
		path.add(new Vector2D(posX, posY));
		
		posX = Math.random()*(C.WIDTH/2) + C.WIDTH/2;
		posY = Math.random()*(C.HEIGHT/2) + C.HEIGHT/2;	
		path.add(new Vector2D(posX, posY));
		
		movingObjects.add(new NormalUfo(
				new Vector2D(x, y),
				new Vector2D(),
				C.UFO_MAX_VEL,
				Assets.yUfo,
				path,
				this
				));
	}
	
	private void spawnMeteors() {
		double x, y;
		
		for(int i = 0; i < meteors; i++){
			 
			x = i % 2 == 0 ? Math.random()*C.WIDTH : 0;
			y = i % 2 == 0 ? 0 : Math.random()*C.HEIGHT;
			
			BufferedImage texture = Assets.bigs[(int)(Math.random()*Assets.bigs.length)];
			
			movingObjects.add(new Meteor(
					new Vector2D(x, y),
					new Vector2D(0, 1).setDirection(Math.random()*Math.PI*2),
					C.METEOR_VEL*Math.random() + 1,
					texture,
					this,
					Size.BIG
					));
		}
	}

	public void update(){
		for(int i = 0; i < movingObjects.size(); i++)
			movingObjects.get(i).update();
		
		for(int i = 0; i < explosions.size(); i++){
			Animation anim = explosions.get(i);
			anim.update();
			if(!anim.isRunning()){
				explosions.remove(i);
			}
			
		}
		
		if(KeyBoard.PAUSE) {
			bgMusic.pause();
			State.changeState(new PauseState(this, bgMusic));
		}
		
		if(gameOver && !gameOverTimer.isRunning()) {
			try {
				ArrayList<ScoreData> dataList=JSONParser.readFile();
				dataList.add(new ScoreData(score, waves));
	            JSONParser.writeFile(dataList);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			State.changeState(new MenuState());
			
			
		}
		
		gameOverTimer.update();
		
		if(!ufoSpawner.isRunning() && spawnUfos) {
			ufoSpawner.run(spawnrate);
			spawnUfo();
		}
		
		ufoSpawner.update();
		
		int rand = (int) (Math.random()*2);
		double x, y;
		
		//Bathroom codes HAHAHA...
		if(percent<0.7 && percent>0.6) {
			if(!ufoSpawner.isRunning()) {
				ufoSpawner.run(spawnrate-5000);
				
				x = rand == 0 ? (Math.random()*C.WIDTH): C.WIDTH;
				y = rand == 0 ? C.HEIGHT : (Math.random()*C.HEIGHT);
				
				followingUfo(x, y);
			}
			
			ufoSpawner.update();
		}
		
		if(percent<0.4 && percent>0.25) {
			if(!ufoSpawner.isRunning()) {
				ufoSpawner.run(spawnrate-3000);
				
				x = rand == 0 ? (Math.random()*C.WIDTH): C.WIDTH;
				y = rand == 0 ? C.HEIGHT : (Math.random()*C.HEIGHT);
				
				staticUfo(x, y);
			}
			
			ufoSpawner.update();
		}
		
		if(percent<0.15 && percent>0.005) {
			if(!ufoSpawner.isRunning()) {
				ufoSpawner.run(spawnrate-3000);
				
				x = rand == 0 ? (Math.random()*C.WIDTH): C.WIDTH;
				y = rand == 0 ? C.HEIGHT : (Math.random()*C.HEIGHT);	
				
				normalUfo(x, y);
			}
			
			ufoSpawner.update();
		}
		
		for(int i = 0; i < movingObjects.size(); i++)
			if(movingObjects.get(i) instanceof Meteor || movingObjects.get(i) instanceof NormalUfo 
					|| movingObjects.get(i) instanceof FollowingUfo || movingObjects.get(i) instanceof StaticUfo
					|| movingObjects.get(i) instanceof Boss)
				return;
		
		startWave();
		
	}
	
	public void draw(Graphics g)
	{	
		Graphics2D g2d = (Graphics2D)g;
		
		g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		
		for(int i = 0; i < messages.size(); i++) {
			messages.get(i).draw(g2d);
			if(messages.get(i).isDead())
				messages.remove(i);
		}
		
		for(int i = 0; i < movingObjects.size(); i++)
			movingObjects.get(i).draw(g);
		
		for(int i = 0; i < explosions.size(); i++){
			Animation anim = explosions.get(i);
			g2d.drawImage(anim.getCurrentFrame(), (int)anim.getPos().getX(), (int)anim.getPos().getY(),
					null);
			
		}
		drawScore(g);
		drawLives(g);
		
		if(boss) {
			GradientPaint motoGP = new GradientPaint(
					C.WIDTH/2-C.LOADING_BAR_WIDTH/2, 
					20-(C.LOADING_BAR_HEIGHT/2)/2, 
					Color.red, 
					C.WIDTH/2+C.LOADING_BAR_WIDTH/2, 
					20+(C.LOADING_BAR_HEIGHT/2)/2, 
					Color.GREEN);
			
			g2d.setPaint(motoGP);
			
			percent=Boss.lives/C.BOSS_LIVES;
			
			g2d.fillRect(
					C.WIDTH/2-C.LOADING_BAR_WIDTH/2, 
					20-(C.LOADING_BAR_HEIGHT/2)/2, 
					(int)(C.LOADING_BAR_WIDTH*percent), 
					C.LOADING_BAR_HEIGHT/2);
			
			g2d.drawRect(
					C.WIDTH/2-C.LOADING_BAR_WIDTH/2, 
					20-(C.LOADING_BAR_HEIGHT/2)/2, 
					C.LOADING_BAR_WIDTH, 
					C.LOADING_BAR_HEIGHT/2);
		}
	}
	
	private void drawScore(Graphics g) {

		Vector2D posi = new Vector2D(C.WIDTH-100, 25);
		
		String strScore = Integer.toString(score);
		
		for(int i = 0; i < strScore.length(); i++) {
			
			g.drawImage(Assets.numbers[Integer.parseInt(strScore.substring(i, i + 1))],
					(int)posi.getX(), (int)posi.getY(), null);
			posi.setX(posi.getX() + 20);
			
		}
		
	}
	
	private void drawLives(Graphics g){
		
		if(lives < 0)
			return;
		
		Vector2D lPos = new Vector2D(25, 25);
		
		g.drawImage(Assets.life, (int)lPos.getX(), (int)lPos.getY(), null);
		
		g.drawImage(Assets.numbers[10], (int)lPos.getX() + 40,
				(int)lPos.getY() + 5, null);
		
		String strLive = Integer.toString(lives);
		
		Vector2D pos = new Vector2D(lPos.getX(), lPos.getY());
		
		for(int i = 0; i < strLive.length(); i ++){
			int num = Integer.parseInt(strLive.substring(i, i+1));
			
			if(num < 0)
				break;
			g.drawImage(Assets.numbers[num],
					(int)pos.getX() + 60, (int)pos.getY() + 5, null);
			pos.setX(pos.getX() + 20);
		}
		
	}
	
	public ArrayList<MovingObject> getMovingObjects() {
		return movingObjects;
	}
	
	public ArrayList<Message> getMessages() {
		return messages;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public static Vector2D getPlayerPos() {
		return player.getPos();
	}
	
	public boolean subtractLife() {
		lives --;
		return lives > 0;
	}
	
	public void gameOver() {
		Message gameOverMsg=new Message(
				PLAYER_START_POSITION, 
				true, 
				C.GAME_OVER, 
				Color.WHITE, 
				true, 
				Assets.fontBig);
		
		this.messages.add(gameOverMsg);
		gameOverTimer.run(C.GAME_OVER_TIME);
		gameOver=true;
		bgMusic.stop();
	}
	
}