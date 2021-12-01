package gameObjects;

import java.awt.Graphics;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import graphics.Assets;
import graphics.Sound;
import main.C;
import math.Chrono;
import math.Vector2D;
import states.GameState;

public class Boss extends MovingObject{
	
	private ArrayList<Vector2D> path;
	
	private Vector2D currentNode;
	
	private int index;
	
	public static float lives;
	
	private Chrono fireRate;
	
	private Sound shoot;

	public Boss(Vector2D pos, Vector2D vel, double maxVel, BufferedImage texture,
			ArrayList<Vector2D> path, GameState gameState) {
		super(pos, vel, maxVel, texture, gameState);
		this.path = path;
		lives=C.BOSS_LIVES;
		index = 0;
		fireRate = new Chrono();
		fireRate.run(C.BOSS_FIRERATE);
		shoot = new Sound(Assets.ufoShoot);
	}
	
	private Vector2D pathFollowing() {
		currentNode = path.get(index);
		
		double distanceToNode = currentNode.subtract(getCenter()).getMagnitude();
		
		if(distanceToNode < C.NODE_RADIUS) {
			index ++;
			if(index >= path.size()) {
				index=0;
			}
		}
		
		return seekForce(currentNode);
		
	}
	
	private Vector2D seekForce(Vector2D target) {
		Vector2D desiredVel = target.subtract(getCenter());
		desiredVel = desiredVel.normalize().scale(maxVel);
		return desiredVel.subtract(vel);
	}

	@Override
	public void update() {
		Vector2D pathFollowing = pathFollowing();
		
		pathFollowing = pathFollowing.scale(1/C.BOSS_MASS);
		
		vel = vel.add(pathFollowing);
		
		vel = vel.limit(maxVel);
		
		pos = pos.add(vel);
		
		// shoot
		
		Vector2D toPlayer=gameState.getPlayer().getCenter().subtract(getCenter());
		double currentAngle = toPlayer.getAngle();
		
		toPlayer = toPlayer.normalize();
		
		if(toPlayer.getX() < 0)
			currentAngle = -currentAngle + Math.PI;
		
		toPlayer = toPlayer.setDirection(currentAngle);
		
		if(!fireRate.isRunning()) {
			UfoLaser laser = new UfoLaser(
					getCenter().add(toPlayer.scale(width)),
					toPlayer,
					C.LASERSPEED,
					currentAngle + Math.PI/2,
					Assets.rLaser,
					gameState
					);
			
			gameState.getMovingObjects().add(0, laser); 
			
			fireRate.run(C.BOSS_FIRERATE);
			
			shoot.play();
			
		}//*/
		
		if(shoot.getFramePosition() > 8500) {
			shoot.stop();
		}
		
		angle = currentAngle - Math.PI/2;
		
		collidesWith();
		fireRate.update();
	}

	@Override
	public void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		
		at = AffineTransform.getTranslateInstance(pos.getX(), pos.getY());
		
		at.rotate(angle, width/2, height/2);
		
		g2d.drawImage(texture, at, null);
	}
	
	@Override
	public void destroy() {
		lives--;
		if(lives<=0) {
			gameState.addScore(C.BOSS_SCORE, pos);
			super.destroy();
		}
	}
	

}
