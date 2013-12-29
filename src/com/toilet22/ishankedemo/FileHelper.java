package com.toilet22.ishankedemo;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

public class FileHelper {

	public static final String FILE_NAME_COURSES_CHOSEN = "courses_chosen.json";
	
	public static final String Tag = "FileHelper";
	
	Context context;
	public FileHelper(Context c){
		context = c;
	}
	
	/*
	 * This method will read and parse the file and return a Course Array.
	 * If there is no file exsited, method will create one and return null.
	 */
	public Course[] readCoursesChosenFromFile() throws Exception{
		Course[] coursesChosen;
		
		try {
			FileInputStream fis = context.openFileInput(FILE_NAME_COURSES_CHOSEN);
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int len = 0;
			while((len = fis.read(buffer))!=-1){
				bos.write(buffer, 0, len);
			}
			Log.v(Tag, "before new JSONObject.");
			String jsonstr = bos.toString();
			JSONObject jobj = new JSONObject(jsonstr);

			Log.v(Tag, "before JSON2Courses.");
			Log.v(Tag, jsonstr);
			coursesChosen = JSONHelper.searchResultsJSON2Courses(jobj);
			
			Log.v(Tag, "courses == null? " + Boolean.toString(coursesChosen == null));
			fis.close();
			return coursesChosen;
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e(Tag, "Read error: file does not exist.");
			
			FileOutputStream fos = context.openFileOutput(FILE_NAME_COURSES_CHOSEN, 
					Context.MODE_PRIVATE);
			return null;
		}
		
	}
	
	public boolean writeCoursesChosenIntoFile(Course[] couses){
		
		
		return false;	
	}
	
	
	
	
}
