package main;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
//import java.awt.Toolkit;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

import graphics.Assets;
import graphics.Text;
import input.KeyBoard;
import input.Mouse;
import math.Vector2D;
import states.LoadingState;
import states.State;

public class Window extends JFrame implements Runnable{
	
	//private static final long serialVersionUID = 1L;
	
	private Canvas canvas;
	private Thread thread;
	private boolean isRunning = false;
	
	private BufferStrategy bs;
	private Graphics g;
	
	private final int FPS = 60; //SIEMPRE DEJARLO A 60, mas FPS 
	private double TARGETTIME = 1000/FPS;
	private double delta = 0;
	private int AVERAGEFPS = FPS;
	
	private KeyBoard kb;
	private Mouse mouse;
	
	public Window()
	{
		setTitle("Space Ship Game");
		setSize(C.WIDTH, C.HEIGHT);
		//setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("../hud/icon.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);
		
		canvas = new Canvas();
		kb = new KeyBoard();
		mouse = new Mouse();
		
		canvas.setPreferredSize(new Dimension(C.WIDTH, C.HEIGHT));
		canvas.setMaximumSize(new Dimension(C.WIDTH, C.HEIGHT));
		canvas.setMinimumSize(new Dimension(C.WIDTH, C.HEIGHT));
		canvas.setFocusable(true);
		
		add(canvas);
		canvas.addKeyListener(kb);
		canvas.addMouseListener(mouse);
		canvas.addMouseMotionListener(mouse);
		setVisible(true);
	}
	
	

	public static void main(String[] args) {
		new Window().start();
	}
	
	private void update(){
		kb.update();
		State.getCurrentState().update();
	}

	private void draw(){
		bs = canvas.getBufferStrategy();
		
		if(bs == null){
			canvas.createBufferStrategy(3);
			return;
		}
		
		g = bs.getDrawGraphics();
		
		//-----------------------
		
		g.setColor(Color.BLACK);
		
		g.fillRect(0, 0, C.WIDTH, C.HEIGHT);
		
		State.getCurrentState().draw(g);
		
		g.setColor(Color.WHITE);
		
		Text.drawText(g, ""+AVERAGEFPS, new Vector2D(10, 20), false, Color.WHITE, Assets.fontSmall);
		
		
		//---------------------
		
		g.dispose();
		bs.show();
	}
	
	private void init()
	{
		Thread loadThread=new Thread(new Runnable() {
			
			@Override
			public void run() {
				Assets.init();
			}
		});
		
		State.changeState(new LoadingState(loadThread));
	}
	
	
	@Override
	public void run() {
		
		long now = 0;
		long lastTime = System.currentTimeMillis();
		int frames = 0;
		long time = 0;
		
		init();
		
		while(isRunning)
		{
			now = System.currentTimeMillis();
			delta += (now - lastTime)/TARGETTIME;
			time += (now - lastTime);
			lastTime = now;
			
			if(delta >= 1){		
				update();
				draw();
				delta --;
				frames ++;
			}
			
			if(time >= 1000){
				AVERAGEFPS = frames;
				frames = 0;
				time = 0;
			}
		}
		
		stop();
	}
	
	private void start(){
		thread = new Thread(this);
		thread.start();
		isRunning = true;
	}
	
	private void stop(){
		try {
			thread.join();
			isRunning = false;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}