package graphics;

import java.awt.image.BufferedImage;

import math.Vector2D;

public class Animation {
	
	private BufferedImage[] frames;
	private int vel;
	private int index;
	private boolean isRunning;
	private Vector2D pos;
	
	private long time, lastTime;
	
	public Animation(BufferedImage[] frames, int vel, Vector2D pos){
		this.frames = frames;
		this.vel = vel;
		this.pos = pos;
		index = 0;
		isRunning = true;
		time = 0;
		lastTime = System.currentTimeMillis();
	}
	
	public void update(){
		
		time += System.currentTimeMillis() - lastTime;
		lastTime = System.currentTimeMillis();
		
		if(time > vel){
			time  = 0;
			index ++;
			if(index >= frames.length){
				isRunning = false;
			}
		}
	}

	public boolean isRunning() {
		return isRunning;
	}

	public Vector2D getPos() {
		return pos;
	}
	
	public BufferedImage getCurrentFrame(){
		return frames[index];
	}
	
	
	
	
	
}