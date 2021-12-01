package states;

import java.awt.Color;
import java.awt.Graphics;

import graphics.Assets;
import graphics.Text;
import main.C;
import math.Vector2D;
import ui.Action;
import ui.Button;

public class WinState extends State{
	
	private int score;
	
	private Button btnMainMenu;
	
	public WinState(int score) {
		this.score=score;
		
		btnMainMenu=new Button(
				Assets.btnBlue, 
				Assets.btnBlueF, 
				(C.WIDTH/2 - Assets.btnBlue.getWidth()/2 - 400), 
				C.HEIGHT/2 + 260, 
				""+C.MAIN_MENU, 
				new Action() {
			@Override
			public void doAction() {
				changeState(new MenuState());
			}
		});
	}

	@Override
	public void update() {
		btnMainMenu.update();
	}

	@Override
	public void draw(Graphics g) {
		Text.drawText(g, C.YOU_WIN, new Vector2D(C.WIDTH/2, C.HEIGHT/2 - 80), true, Color.WHITE, Assets.fontBig);
		
		Text.drawText(g, "Your Score: "+score, new Vector2D(C.WIDTH/2, C.HEIGHT/2 + 30), true, Color.WHITE, Assets.fontMed);
		
		btnMainMenu.draw(g);
	}

}
