package gameObjects;

import java.awt.geom.AffineTransform;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import graphics.Assets;
import graphics.Sound;
import math.Vector2D;
import states.GameState;

public abstract class MovingObject extends GameObject{
	
	protected Vector2D vel;
	protected AffineTransform at;
	protected double angle;
	protected double maxVel;
	protected int width;
	protected int height;
	protected GameState gameState;
	
	private Sound Xplosion;
	
	protected boolean dead;
	
	public MovingObject(Vector2D pos, Vector2D vel, double maxVel, BufferedImage texture, GameState gameState) {
		super(pos, texture);
		this.vel = vel;
		this.maxVel = maxVel;
		this.gameState = gameState;
		width = texture.getWidth();
		height = texture.getHeight();
		angle = 0;
		Xplosion = new Sound(Assets.Xplo);
	}
	
	protected void collidesWith(){
		
		ArrayList<MovingObject> movingObjects = gameState.getMovingObjects(); 
		
		for(int i = 0; i < movingObjects.size(); i++){
			
			MovingObject m  = movingObjects.get(i);
			
			if(m.equals(this))
				continue;
			
			double distance = m.getCenter().subtract(getCenter()).getMagnitude();
			
			if(distance < m.width/2 + width/2 && movingObjects.contains(this) && !m.dead && !dead){
				objectCollision(m, this);
			}
		}
	}
	
	private void objectCollision(MovingObject a, MovingObject b){
		
		if(a instanceof Player && ((Player)a).isSpawning()) {
			return;
		}
		if(b instanceof Player && ((Player)b).isSpawning()) {
			return;
		}
		
		if((a instanceof NormalUfo && b instanceof NormalUfo) || 
				(a instanceof StaticUfo && b instanceof StaticUfo) || 
				(a instanceof FollowingUfo && b instanceof FollowingUfo))
			return;
		
		if((a instanceof NormalUfo && b instanceof FollowingUfo) || (b instanceof NormalUfo && a instanceof FollowingUfo) 
			|| (a instanceof NormalUfo && b instanceof StaticUfo) || (b instanceof NormalUfo && a instanceof StaticUfo)
			|| (a instanceof StaticUfo && b instanceof FollowingUfo) || (b instanceof StaticUfo && a instanceof FollowingUfo))
			return;
		
		if((a instanceof NormalUfo && b instanceof Meteor) || (b instanceof NormalUfo && a instanceof Meteor) 
				|| (a instanceof Meteor && b instanceof StaticUfo) || (b instanceof Meteor && a instanceof StaticUfo)
				|| (a instanceof Meteor && b instanceof FollowingUfo) || (b instanceof Meteor && a instanceof FollowingUfo))
				return;
		
		if((a instanceof NormalUfo && b instanceof UfoLaser) || (b instanceof NormalUfo && a instanceof UfoLaser) 
				|| (a instanceof UfoLaser && b instanceof StaticUfo) || (b instanceof UfoLaser && a instanceof StaticUfo)
				|| (a instanceof UfoLaser && b instanceof FollowingUfo) || (b instanceof UfoLaser && a instanceof FollowingUfo))
				return;
		
		if((a instanceof NormalUfo && b instanceof Boss) || (b instanceof NormalUfo && a instanceof Boss) 
				|| (a instanceof Boss && b instanceof StaticUfo) || (b instanceof Boss && a instanceof StaticUfo)
				|| (a instanceof Boss && b instanceof FollowingUfo) || (b instanceof Boss && a instanceof FollowingUfo)
				|| (a instanceof Boss && b instanceof UfoLaser) || (b instanceof Boss && a instanceof UfoLaser))
				return;
		
		if((a instanceof Player && b instanceof Laser) || (b instanceof Player && a instanceof Laser))
				return;
		
		if(!(a instanceof Meteor && b instanceof Meteor) && !(a instanceof Laser && b instanceof Laser)){
			gameState.playExplosion(getCenter());
			a.destroy();
			b.destroy();
		}
	}
	
	
	protected void destroy(){
		dead=true;
		gameState.getMovingObjects().remove(this);
		if(!(this instanceof Laser || this instanceof UfoLaser))
			Xplosion.play();
	}
	
	protected Vector2D getCenter(){
		return new Vector2D(pos.getX() + width/2, pos.getY() + height/2);
	}

	public boolean isDead() {
		return dead;
	}
	
}