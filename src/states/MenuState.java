package states;

import java.awt.Graphics;


import main.C;
import graphics.Assets;
import ui.Action;
import ui.Button;

public class MenuState extends State{

	private Button btnPlay, btnExit, btnHS;
	
	public MenuState() {
		btnPlay = new Button(
				Assets.btnBlue,
				Assets.btnBlueF,
				C.WIDTH / 2 - Assets.btnBlue.getWidth()/2,
				C.HEIGHT / 2 - Assets.btnBlue.getHeight()*2,
				C.PLAY,
				new Action() {
					@Override
					public void doAction() {
						State.changeState(new GameState());
					}
				}
				);
		
		btnExit = new Button(
				Assets.btnBlue,
				Assets.btnBlueF,
				C.WIDTH / 2 - Assets.btnBlue.getWidth()/2,
				C.HEIGHT / 2 + Assets.btnBlue.getHeight()*2 ,
				C.EXIT,
				new Action() {
					@Override
					public void doAction() {
						System.exit(0);
					}
				}
				);
		
		btnHS = new Button(
				Assets.btnBlue,
				Assets.btnBlueF,
				C.WIDTH / 2 - Assets.btnBlue.getWidth()/2,
				C.HEIGHT / 2,
				C.HIGH_SCORES,
				new Action() {
					@Override
					public void doAction() {
						State.changeState(new ScoreState());
					}
				}
				);
		
	}
	
	
	@Override
	public void update() {
		btnPlay.update();
		btnExit.update();
		btnHS.update();
	}

	@Override
	public void draw(Graphics g) {
		btnPlay.draw(g);
		btnExit.draw(g);
		btnHS.draw(g);
	}

}