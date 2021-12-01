package gameObjects;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import main.C;
import math.Vector2D;
import states.GameState;

public class FollowingUfo extends MovingObject{

	public FollowingUfo(Vector2D pos, Vector2D vel, double maxVel, BufferedImage texture, GameState gameState) {
		super(pos, vel, maxVel, texture, gameState);
	}
	
	@Override
	public void update() {
		
		Vector2D pathFollowing = gameState.getPlayer().getCenter().subtract(getCenter());
		
		pathFollowing = pathFollowing.scale(1/C.UFO_MASS);
		
		vel = vel.add(pathFollowing);
		
		vel = vel.limit(maxVel);
		
		pos = pos.add(vel);
		
		if(pos.getX() > C.WIDTH || pos.getY() > C.HEIGHT
				|| pos.getX() < -width || pos.getY() < -height)
			destroy();
		
		angle += C.UFO_ROTATION;
		
		collidesWith();
		
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
