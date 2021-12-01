package io;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ScoreData {
	
	private String date;
	private int score, waves;
	
	public ScoreData(int score, int waves) {
		this.score=score;
		this.waves=waves-1;
		
		Date today=new Date(System.currentTimeMillis());
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		date=format.format(today);
	}
	
	public ScoreData() {
		
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getWaves() {
		return waves;
	}

	public void setWaves(int waves) {
		this.waves = waves;
	}

}
