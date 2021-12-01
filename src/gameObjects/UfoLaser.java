package gameObjects;

import java.awt.Graphics;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import main.C;
import math.Vector2D;
import states.GameState;

public class UfoLaser extends MovingObject{

	public UfoLaser(Vector2D pos, Vector2D vel, double maxVel, double angle, BufferedImage texture, GameState gameState) {
		super(pos, vel, maxVel, texture, gameState);
		this.angle = angle;
		this.vel = vel.scale(maxVel);
	}

	@Override
	public void update() {
		pos = pos.add(vel);
		if(pos.getX() < 0 || pos.getX() > C.WIDTH ||
				pos.getY() < 0 || pos.getY() > C.HEIGHT){
			destroy();
		}
		
		collidesWith();
		
	}

	@Override
	public void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		
		at = AffineTransform.getTranslateInstance(pos.getX() - width/2, pos.getY());
		
		at.rotate(angle, width/2, 0);
		
		g2d.drawImage(texture, at, null);
		
	}
	
	@Override
	public Vector2D getCenter(){
		return new Vector2D(pos.getX() + width/2, pos.getY() + width/2);
	}

}
