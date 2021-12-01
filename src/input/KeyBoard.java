package input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyBoard implements KeyListener{
	
	public boolean[] keys=new boolean[256];
	
	public static boolean UP, DOWN, LEFT, RIGHT, SHOOT, PAUSE;
	
	private int kUP, kDOWN, kLEFT, kRIGHT, kSHOOT, kPAUSE;
	
	public KeyBoard() {
		UP=false;
		DOWN=false;
		LEFT=false;
		RIGHT=false;
		SHOOT=false;
		
		kUP=KeyEvent.VK_W;
		kDOWN=KeyEvent.VK_S;
		kLEFT=KeyEvent.VK_A;
		kRIGHT=KeyEvent.VK_D;
		kSHOOT=KeyEvent.VK_SPACE;
		kPAUSE=KeyEvent.VK_ESCAPE;
	}
	
	public void update() {
		UP=keys[kUP];
		DOWN=keys[kDOWN];
		LEFT=keys[kLEFT];
		RIGHT=keys[kRIGHT];
		SHOOT=keys[kSHOOT];
		//PAUSE=keys[kPAUSE];
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		keys[e.getKeyCode()]=true;
		System.out.println(e.getKeyCode());
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		keys[e.getKeyCode()]=false;
		
		if(e.getKeyCode()==kPAUSE)
			PAUSE=!PAUSE;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		
	}
}
