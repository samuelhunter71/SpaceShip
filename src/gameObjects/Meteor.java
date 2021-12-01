package gameObjects;

import java.awt.Graphics;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import main.C;
import math.Vector2D;
import states.GameState;

public class Meteor extends MovingObject{

	private Size size;
	
	public Meteor(Vector2D pos, Vector2D vel, double maxVel, BufferedImage texture, GameState gameState, Size size) {
		super(pos, vel, maxVel, texture, gameState);
		this.size = size;
		this.vel = vel.scale(maxVel);
		
	}

	@Override
	public void update() {
		pos = pos.add(vel);
		
		if(pos.getX() > C.WIDTH)
			pos.setX(-width);
		if(pos.getY() > C.HEIGHT)
			pos.setY(-height);
		
		if(pos.getX() < -width)
			pos.setX(C.WIDTH);
		if(pos.getY() < -height)
			pos.setY(C.HEIGHT);
		
		angle += C.DELTAANGLE/2;
		
	}
	
	@Override
	public void destroy(){
		gameState.divideMeteor(this);
		gameState.addScore(C.METEOR_SCORE, pos);
		super.destroy();
	}
	
	
	@Override
	public void draw(Graphics g) {
		
		Graphics2D g2d = (Graphics2D)g;
		
		at = AffineTransform.getTranslateInstance(pos.getX(), pos.getY());
		
		at.rotate(angle, width/2, height/2);
		
		g2d.drawImage(texture, at, null);
	}

	public Size getSize(){
		return size;
	}
	
	
}