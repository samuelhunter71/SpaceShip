package io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import main.C;

public class JSONParser {
	// readFile methodo
	public static ArrayList<ScoreData> readFile() throws FileNotFoundException{
		ArrayList<ScoreData> dataList=new ArrayList<ScoreData>();
		
		File file=new File(C.SCORE_PATH);
		
		if(!file.exists() || file.length()==0) {
			return dataList;
		}
		
		JSONTokener parser=new JSONTokener(new FileInputStream(file));
		JSONArray jsonList = new JSONArray(parser);
		
		for (int i = 0; i < jsonList.length(); i++) {
			JSONObject obj=(JSONObject)jsonList.get(i);
			ScoreData data=new ScoreData();
			data.setScore(obj.getInt("score"));
			data.setDate(obj.getString("date"));
			data.setWaves(obj.getInt("waves"));
			
			dataList.add(data);
		}
		
		return dataList;
	}
	
	public static void writeFile(ArrayList<ScoreData> dataList) throws IOException {
		File outFile=new File(C.SCORE_PATH);
		
		outFile.getParentFile().mkdirs();
		outFile.createNewFile();
		
		JSONArray jsonArr=new JSONArray();
		
		for(ScoreData data: dataList) {
			JSONObject obj=new JSONObject();
			obj.put("score", data.getScore());
			obj.put("date", data.getDate());
			obj.put("waves", data.getWaves());
			
			jsonArr.put(obj);
		}
		
		BufferedWriter writer=Files.newBufferedWriter(Paths.get(outFile.toURI()));
		jsonArr.write(writer);
		writer.close();
	}

}
