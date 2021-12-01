package gameObjects;

import java.awt.Graphics;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import graphics.Assets;
import graphics.Sound;
import input.KeyBoard;
import input.Mouse;
import main.C;
import math.Chrono;
import math.Vector2D;
import states.GameState;

public class Player extends MovingObject{
	
	private Vector2D heading;	
	private Vector2D acel;
	private boolean isAcel = false;
	
	private Chrono fireRate;
	
	private boolean isSpawning, isVisible;
	
	private Chrono spawnTime, flickerTime;
	
	private Sound shoot, loose;
	
	private int[] upgrades = new int[3];
	
	public Player(Vector2D pos, Vector2D vel, double maxVel, BufferedImage texture, GameState gameState) {
		super(pos, vel, maxVel, texture, gameState);
		heading = new Vector2D(0, 1);
		acel = new Vector2D();
		fireRate = new Chrono();
		spawnTime = new Chrono();
		flickerTime = new Chrono();
		shoot = new Sound(Assets.plShoot);
		loose = new Sound(Assets.plLoose);
		
		for (int i = 0; i < upgrades.length; i++) {
			upgrades[i]=0;
		}
	}
	
	@Override
	public void update() 
	{
		
		if(!spawnTime.isRunning()) {
			isSpawning = false;
			isVisible = true;
		}
		
		if(isSpawning) {
			
			if(!flickerTime.isRunning()) {
				flickerTime.run(C.FLICKER_TIME);
				isVisible = !isVisible;
				
			}
			
		}
		
		if(Mouse.SHOOT && !fireRate.isRunning() && !isSpawning)
		{		
			shoot.play();
			
			gameState.getMovingObjects().add(0,new Laser(
					getCenter().add(heading.scale(width)),
					heading,
					C.LASERSPEED,
					angle,
					Assets.bLaser,
					gameState
					));
			
			/*gameState.getMovingObjects().add(0,new Laser(
					getCenter().add(heading.scale(width)),
					heading.setDirection(angle-1.2),
					C.LASERSPEED,
					angle+Math.PI/10,
					Assets.bLaser,
					gameState
					));
			
			gameState.getMovingObjects().add(0,new Laser(
					getCenter().add(heading.scale(width)),
					heading.setDirection(angle-1.95),
					C.LASERSPEED,
					angle-Math.PI/10,
					Assets.bLaser,
					gameState
					));
			
			gameState.getMovingObjects().add(0,new Laser(
					getCenter().add(heading.scale(width)),
					heading.setDirection(angle-1.1),
					C.LASERSPEED,
					angle+Math.PI/8,
					Assets.bLaser,
					gameState
					));
			
			gameState.getMovingObjects().add(0,new Laser(
					getCenter().add(heading.scale(width)),
					heading.setDirection(angle-2.5),
					C.LASERSPEED,
					angle-Math.PI/8,
					Assets.bLaser,
					gameState
					));//*/
			
			switch(upgrades[1]) {
			case 2:
				
				
			case 1:
				break;
			}
			
			/*gameState.getMovingObjects().add(0,new Laser(
					getCenter().add(heading.scale(width)),
					heading,
					C.LASERSPEED,
					angle,
					Assets.bLaser,
					gameState
					));
			fireRate.run(C.FIRERATE);*/
			
			fireRate.run(C.FIRERATE - (upgrades[0]*150));
		}
		
		if(shoot.getFramePosition() > 8500) {
			shoot.stop();
		}
		
		if(KeyBoard.RIGHT)
			angle += (C.PLAYER_ROT_SPEED + (upgrades[2]*0.05));
		if(KeyBoard.LEFT)
			angle -= (C.PLAYER_ROT_SPEED + (upgrades[2]*0.05));
		
		/*if(KeyBoard.UP)
		{
			acceleration = heading.scale(C.ACC);
			accelerating = true;
		}else {
			if(velocity.getMagnitude() != 0)
				acceleration = (velocity.scale(-1).normalize()).scale(Constants.ACC/2);
			accelerating = false;
		}*/
		
		vel = vel.add(acel);
		
		vel = vel.limit(maxVel);
		
		heading = heading.setDirection(angle - Math.PI/2);
		
		pos = pos.add(vel);
		
		if(pos.getX() > C.WIDTH)
			pos.setX(0);
		if(pos.getY() > C.HEIGHT)
			pos.setY(0);
		
		if(pos.getX() < -width)
			pos.setX(C.WIDTH);
		if(pos.getY() < -height)
			pos.setY(C.HEIGHT);
		
		pos.setX(Mouse.X);
		pos.setY(Mouse.Y);
		
		fireRate.update();
		spawnTime.update();
		flickerTime.update();
		collidesWith();
	}
	
	@Override
	public void destroy() {
		isSpawning = true;
		spawnTime.run(C.SPAWNING_TIME);
		loose.play();
		if(!gameState.subtractLife()) {
			gameState.gameOver();
			super.destroy();
		}
		resetValues();
	}
	
	private void resetValues() {
		
		angle = 0;
		vel = new Vector2D();
		pos = new Vector2D(Mouse.X, Mouse.Y);
	}
	
	@Override
	public void draw(Graphics g) {
		
		if(!isVisible)
			return;
		
		Graphics2D g2d = (Graphics2D)g;
		
		AffineTransform at1 = AffineTransform.getTranslateInstance(pos.getX() + width/2 + 5, pos.getY() + height/2 + 10);
		
		AffineTransform at2 = AffineTransform.getTranslateInstance(pos.getX() + 5, pos.getY() + height/2 + 10);
		
		at1.rotate(angle, -5, -10);
		at2.rotate(angle, width/2 -5, -10);
		
		if(isAcel)
		{
			g2d.drawImage(Assets.speed, at1, null);
			g2d.drawImage(Assets.speed, at2, null);
		}
		
		
		
		at = AffineTransform.getTranslateInstance(pos.getX(), pos.getY());
		
		at.rotate(angle, width/2, height/2);
		
		g2d.drawImage(texture, at, null);
		
	}
	
	public boolean isSpawning() {return isSpawning;}
	
}