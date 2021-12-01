package states;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;

import graphics.Assets;
import graphics.Loader;
import graphics.Text;
import main.C;
import math.Vector2D;

public class LoadingState extends State{
	
	private Thread loadThread;
	
	private Font font;
	
	public LoadingState(Thread loadThread) {
		this.loadThread=loadThread;
		this.loadThread.start();
		
		font=Loader.loadFont("res/fonts/future.ttf", 38);
	}

	@Override
	public void update() {
		if(Assets.loaded) {
			State.changeState(new MenuState());
			try {
				loadThread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}

	@Override
	public void draw(Graphics g) {
		GradientPaint motoGP = new GradientPaint(
				C.WIDTH/2-C.LOADING_BAR_WIDTH/2, 
				C.HEIGHT/2-C.LOADING_BAR_HEIGHT/2, 
				Color.WHITE, 
				C.WIDTH/2+C.LOADING_BAR_WIDTH/2, 
				C.HEIGHT/2+C.LOADING_BAR_HEIGHT/2, 
				Color.BLUE);
		
		Graphics2D g2d=(Graphics2D)g;
		
		g2d.setPaint(motoGP);
		
		float percent=Assets.count/Assets.MAX_COUNT;
		
		g2d.fillRect(
				C.WIDTH/2-C.LOADING_BAR_WIDTH/2, 
				C.HEIGHT/2-C.LOADING_BAR_HEIGHT/2, 
				(int)(C.LOADING_BAR_WIDTH*percent), 
				C.LOADING_BAR_HEIGHT);
		
		g2d.drawRect(
				C.WIDTH/2-C.LOADING_BAR_WIDTH/2, 
				C.HEIGHT/2-C.LOADING_BAR_HEIGHT/2, 
				C.LOADING_BAR_WIDTH, 
				C.LOADING_BAR_HEIGHT);
		
		Text.drawText(g2d, "Loading...", new Vector2D(C.WIDTH/2, C.HEIGHT/2-50), true, Color.WHITE, font);
		
	}

}
