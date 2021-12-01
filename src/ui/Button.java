package ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import graphics.Assets;
import graphics.Text;
import input.Mouse;
import math.Vector2D;

public class Button {
	
	private BufferedImage mouseOut;
	private BufferedImage mouseIn;
	private boolean isFocus;
	private Rectangle boundBox;
	private Action action;
	private String text;
	
	public Button(
			BufferedImage mouseOut,
			BufferedImage mouseIn,
			int x, int y,
			String text,
			Action action
			) {
		this.mouseIn = mouseIn;
		this.mouseOut = mouseOut;
		this.text = text;
		boundBox = new Rectangle(x, y, mouseIn.getWidth(), mouseIn.getHeight());
		this.action = action;
	}
	
	public void update() {
		
		if(boundBox.contains(Mouse.X, Mouse.Y)) {
			isFocus = true;
		}else {
			isFocus = false;
		}
		
		if(isFocus && Mouse.MLB) {
			action.doAction();
			Mouse.MLB=false;
		}
	}
	
	public void draw(Graphics g) {
		
		if(isFocus) {
			g.drawImage(mouseIn, boundBox.x, boundBox.y, null);
		}else {
			g.drawImage(mouseOut, boundBox.x, boundBox.y, null);
		}
		
		Text.drawText(
				g,
				text,
				new Vector2D(
						boundBox.getX() + boundBox.getWidth() / 2,
						boundBox.getY() + boundBox.getHeight()),
				true,
				Color.BLACK,
				Assets.fontMed);
		
		
	}
	
}