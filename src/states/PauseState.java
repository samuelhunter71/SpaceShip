package states;

import java.awt.Color;
import java.awt.Graphics;

import graphics.Assets;
import graphics.Sound;
import graphics.Text;
import input.KeyBoard;
import main.C;
import math.Vector2D;
import ui.Action;
import ui.Button;

public class PauseState extends State{
	
	private GameState gameState;
	private Button btnResume, btnMainMenu, btnRestart, btnCancel, btnConfirm;
	private int action=0;
	private Sound music;

	public PauseState(GameState gameState, Sound music) {
		this.gameState=gameState;
		this.music=music;
		
		btnRestart=new Button(
				Assets.btnBlue, 
				Assets.btnBlueF, 
				(C.WIDTH/2 - Assets.btnBlue.getWidth()/2) - 220, 
				C.HEIGHT/2 - Assets.btnBlue.getHeight()/2, 
				C.RESTART, 
				new Action() {
			
			@Override
			public void doAction() {
				action=2;
			}
		});
		
		btnMainMenu=new Button(
				Assets.btnBlue, 
				Assets.btnBlueF, 
				(C.WIDTH/2 - Assets.btnBlue.getWidth()/2), 
				C.HEIGHT/2 - Assets.btnBlue.getHeight()/2, 
				C.MAIN_MENU, 
				new Action() {
			@Override
			public void doAction() {
				action=1;
			}
		});
		
		btnResume=new Button(
				Assets.btnBlue, 
				Assets.btnBlueF, 
				(C.WIDTH/2 - Assets.btnBlue.getWidth()/2) + 220, 
				C.HEIGHT/2 - Assets.btnBlue.getHeight()/2, 
				C.RESUME, 
				new Action() {
			@Override
			public void doAction() {
				music.resume();
				State.changeState(gameState);
				KeyBoard.PAUSE=!KeyBoard.PAUSE;
			}
		});
		
		btnCancel=new Button(
				Assets.btnBlue, 
				Assets.btnBlueF, 
				(C.WIDTH/2 - Assets.btnBlue.getWidth()/2) - 150, 
				C.HEIGHT/2 - Assets.btnBlue.getHeight()/2 + 30, 
				C.CANCEL, 
				new Action() {
			
			@Override
			public void doAction() {
				State.changeState(new PauseState(gameState, music));
			}
		});
		
		btnConfirm=new Button(
				Assets.btnBlue, 
				Assets.btnBlueF, 
				(C.WIDTH/2 - Assets.btnBlue.getWidth()/2) + 150, 
				C.HEIGHT/2 - Assets.btnBlue.getHeight()/2 + 30, 
				C.CONFIRM, 
				new Action() {
			
			@Override
			public void doAction() {
				if(action==1) {
					State.changeState(new MenuState());
				}else if(action==2) {
					State.changeState(new GameState());
				}
			}
		});
	}

	@Override
	public void update() {
		
		if(action==0) {
			btnResume.update();
			btnMainMenu.update();
			btnRestart.update();		
		}else {
			btnCancel.update();
			btnConfirm.update();
		}
		
		if(!KeyBoard.PAUSE) {
			music.resume();
			State.changeState(gameState);
		}
	}

	@Override
	public void draw(Graphics g) {
		if(action==0) {
			Text.drawText(g, C.PAUSE, new Vector2D(C.WIDTH/2, C.HEIGHT/2 - 80), true, Color.WHITE, Assets.fontBig);
			
			btnResume.draw(g);
			btnMainMenu.draw(g);
			btnRestart.draw(g);
		}else {
			btnCancel.draw(g);
			btnConfirm.draw(g);
			
			Text.drawText(g, action == 1 ? C.MAIN_MENU_TEXT : C.RESTART_TEXT, new Vector2D(C.WIDTH/2, C.HEIGHT/2 - 70), true, Color.WHITE, Assets.fontMed);
			Text.drawText(g, C.RESTART_TEXT_2, new Vector2D(C.WIDTH/2, C.HEIGHT/2 - 50), true, Color.WHITE, Assets.fontMed);
		}
	}

}
