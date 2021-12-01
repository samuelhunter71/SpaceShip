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

public class StaticUfo extends MovingObject{
	
private ArrayList<Vector2D> path;
	
	private Vector2D currentNode;
	
	private int index;
	
	private Chrono fireRate;
	
	private Sound shoot;
	
	public StaticUfo(Vector2D pos, Vector2D vel, double maxVel, BufferedImage texture,
			ArrayList<Vector2D> path, GameState gameState) {
		super(pos, vel, maxVel, texture, gameState);
		this.path = path;
		index = 0;
		fireRate = new Chrono();
		fireRate.run(C.UFO_FIRERATE+500);
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
		
		Vector2D pathFollowing= pathFollowing();
		
		pathFollowing = pathFollowing.scale(1/C.UFO_MASS);
		
		vel = vel.add(pathFollowing);
		
		vel = vel.limit(maxVel);
		
		pos = pos.add(vel);
		
		if(pos.getX() > C.WIDTH || pos.getY() > C.HEIGHT
				|| pos.getX() < -width || pos.getY() < -height)
			destroy();
		
		// shoot
		
		if(!fireRate.isRunning()) {
			
			Vector2D toPlayer = gameState.getPlayer().getCenter().subtract(getCenter());
			
			toPlayer = toPlayer.normalize();
			
			double currentAngle = toPlayer.getAngle();
			
			currentAngle += Math.random()*C.UFO_ANGLE_RANGE - C.UFO_ANGLE_RANGE / 2;
			
			if(toPlayer.getX() < 0)
				currentAngle = -currentAngle + Math.PI;
			
			toPlayer = toPlayer.setDirection(currentAngle);
			
			UfoLaser laser = new UfoLaser(
					getCenter().add(toPlayer.scale(width)),
					toPlayer,
					C.LASERSPEED,
					currentAngle + Math.PI/2,
					Assets.rLaser,
					gameState
					);
			
			gameState.getMovingObjects().add(0, laser); 
			
			fireRate.run(C.UFO_FIRERATE+500);
			
			shoot.play();
			
		}//*/
		
		if(shoot.getFramePosition() > 8500) {
			shoot.stop();
		}
		
		angle += C.UFO_ROTATION;
		
		collidesWith();
		fireRate.update();
		
	}
	@Override
	public void destroy() {
		gameState.addScore(C.UFO_SCORE, pos);
		super.destroy();
	}
	
	@Override
	public void draw(Graphics g) {
		
		Graphics2D g2d = (Graphics2D)g;
		
		at = AffineTransform.getTranslateInstance(pos.getX(), pos.getY());
		
		at.rotate(angle, width/2, height/2);
		
		g2d.drawImage(texture, at, null);
		
	}
}
