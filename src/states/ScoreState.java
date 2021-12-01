package states;

import java.awt.Color;
import java.awt.Graphics;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

import graphics.Assets;
import graphics.Text;
import io.JSONParser;
import io.ScoreData;
import main.C;
import math.Vector2D;
import ui.Action;
import ui.Button;

public class ScoreState extends State{
	
	private Button returnBnt;
	
	private PriorityQueue<ScoreData> highScr;
	private Comparator<ScoreData> scoreComp;
	private ScoreData[] auxArray;
	
	public ScoreState() {
		returnBnt=new Button(
				Assets.btnBlue, 
				Assets.btnBlueF, 
				Assets.btnBlue.getHeight(), 
				C.HEIGHT-Assets.btnBlue.getHeight()*2, 
				C.RETURN, 
				new Action() {
			
			@Override
			public void doAction() {
				State.changeState(new MenuState());
			}
		});
		
		scoreComp=new Comparator<ScoreData>() {
			
			@Override
			public int compare(ScoreData o1, ScoreData o2) {
				return o1.getScore() < o2.getScore() ? -1: o1.getScore() > o2.getScore() ? -1: 0;
			}
		};
		
		highScr=new PriorityQueue<ScoreData>(10, scoreComp);
		
		try {
			ArrayList<ScoreData> dataList=JSONParser.readFile();
			
			for(ScoreData d: dataList) {
				highScr.add(d);
			}
			
			while(highScr.size()>10) {
				highScr.poll();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void update() {
		returnBnt.update();
	}

	@Override
	public void draw(Graphics g) {
		returnBnt.draw(g);
		
		auxArray=highScr.toArray(new ScoreData[highScr.size()]);
		Arrays.sort(auxArray, scoreComp);
		
		Vector2D scorePos=new Vector2D(C.WIDTH/2-300, 100);
		
		Vector2D datePos=new Vector2D(C.WIDTH/2, 100);
		
		Vector2D wavesPos=new Vector2D(C.WIDTH/2+300, 100);
		
		Text.drawText(g, C.SCORE, scorePos, true, Color.BLUE, Assets.fontBig);
		Text.drawText(g, C.DATE, datePos, true, Color.BLUE, Assets.fontBig);
		Text.drawText(g, C.WAVES, wavesPos, true, Color.BLUE, Assets.fontBig);
		
		scorePos.setY(scorePos.getY()+40);
		datePos.setY(datePos.getY()+40);
		wavesPos.setY(wavesPos.getY()+40);
		
		for (int i = auxArray.length-1; i > -1; i--) {
			ScoreData d=auxArray[i];
			
			Text.drawText(g, Integer.toString(d.getScore()), scorePos, true, Color.WHITE, Assets.fontMed);
			Text.drawText(g, d.getDate(), datePos, true, Color.WHITE, Assets.fontMed);
			Text.drawText(g, Integer.toString(d.getWaves()), wavesPos, true, Color.WHITE, Assets.fontMed);
			
			scorePos.setY(scorePos.getY()+40);
			datePos.setY(datePos.getY()+40);
			wavesPos.setY(wavesPos.getY()+40);
		}
	}

}
