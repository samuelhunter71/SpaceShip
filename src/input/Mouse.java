package input;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Mouse extends MouseAdapter{
	
	//private boolean[] buttons = new boolean[10];
	
	public static int X, Y;
	public static boolean SHOOT, MLB;
	//private int kSHOOT;

	public Mouse() {
		SHOOT=false;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		X=e.getX();
		Y=e.getY();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		X=e.getX();
		Y=e.getY();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getButton()==MouseEvent.BUTTON1) {
			SHOOT=true;
			MLB=true;
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(e.getButton()==MouseEvent.BUTTON1) {
			SHOOT=false;
			MLB=false;
		}

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		//MLB=true;
	}
	
	public static void isClicked(boolean clicked) {
		//MLB=clicked;
	}
	
	
	
}
