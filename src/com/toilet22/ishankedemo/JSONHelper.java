package com.toilet22.ishankedemo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class JSONHelper {
	public static final String Tag = "JSONHelper";
	
	/*
	 * This method parses JSON object which contains an array of concise information of courses
	 * into a Course array. 
	 * The JSON object is returned by sever after SEARCH_COURSES request posted.
	 */
	public static Course[] searchResultsJSON2Courses(JSONObject jObj) throws JSONException{
		Log.v(Tag, "before jobj.getJSONArray.");
		JSONArray jArry = jObj.getJSONArray(IShangkeHeader.LIST_ITEM_LIST);
		int len = jArry.length(); 
		Log.v(Tag, "the length of JSONArray is " + Integer.toString(len));
		Course[] courses = new Course[len];
		JSONObject jobj;

		Log.v(Tag, "before for loop.");
		for (int i = 0; i<len; i++){
			jobj = jArry.getJSONObject(i);
			Log.v(Tag, "before getJSONObject.");
			courses[i] = searchResultsJSON2Course(jobj);
		}

		return courses;
	}
	
	/*
	 * This method is called by searchResultsJSON2Courses() method.
	 */
	public static Course searchResultsJSON2Course(JSONObject jObj){
		Course c = new Course();
		Log.v(Tag, "in JSON2Course,");
		try{
			c.name = jObj.getString(IShangkeHeader.LIST_ITEM_NAME);
			c.teacher = jObj.getString(IShangkeHeader.LIST_ITEM_TEACHER);
			c.configID = jObj.getString(IShangkeHeader.LIST_ITEM_CONFIG_ID);
			c.courseID = jObj.getString(IShangkeHeader.LIST_ITEM_COURSE_ID);
			Log.v(Tag, "name:" + c.name + ", teacher: " + c.teacher + ", configID: " + c.configID
					+ ", courseID: " + c.courseID);
			return c;
		}catch (Exception e) {
			Log.e(Tag, "Error in JSON2Course.");
			e.printStackTrace();
		}		
		return null;
	}
	

	/*
	 * This method parses JSON object which contains the detailed information of a course.
	 * The JSON object is returned by server after GET_COURSE request posted.
	 */
	public static Course courseInfoJSON2Course(JSONObject jObj){
		Course c = new Course();
		Log.v(Tag, "in JSON2Course,");
		try{
			c.name = jObj.getString(IShangkeHeader.DETAIL_NAME);
			c.teacher = jObj.getString(IShangkeHeader.DETAIL_TEACHER);
			c.courseID = jObj.getString(IShangkeHeader.DETAIL_COURSE_ID);
			c.period = jObj.getInt(IShangkeHeader.DETAIL_PERIOD);
			c.credit = jObj.getDouble(IShangkeHeader.DETAIL_CREDIT);
			c.teachWay = jObj.getString(IShangkeHeader.DETAIL_TEACHING_WAY);
			c.examWay = jObj.getString(IShangkeHeader.DETAIL_EXAM_WAY);
			
			Log.v(Tag, "after getStrings.");
			c.strJSON = jObj.toString();
			Log.v(Tag, "after jObj.toString().");
			
			JSONArray jA = jObj.getJSONObject(IShangkeHeader.DETAIL_TIME)
					.getJSONArray(IShangkeHeader.DETAIL_COURSE_TIME);
			Log.v(Tag, "before JSON2CourseTimeLocations(jA)");
			c.courseTimeLocations = c.JSON2CourseTimeLocations(jA);
			
			
			Log.v(Tag, "name:" + c.name + ", teacher: " + c.teacher + ", configID: " + c.configID
					+ ", courseID: " + c.courseID);
			Log.v(Tag, "day: " + Integer.toString(c.courseTimeLocations[0].day) + 
					", classroom: " + c.courseTimeLocations[0].classroom);
			return c;
		}catch (Exception e) {
			Log.e(Tag, "Error in JSON2Course.");
			e.printStackTrace();
		}		
		return null;
	}
	
	
	
	/*
	 * This method packs an array of course's detailed information into a JSON string.
	 * The method is called by FileHelper.
	 */
	public static  JSONObject coursesChosen2JSON(Course[] courses) throws JSONException{
		Log.v(Tag, "Course2JSON");
		JSONArray jArry = new JSONArray();
		JSONObject jObj = new JSONObject();
		int len = courses.length;
		jObj.put(IShangkeHeader.LIST_ITEM_LIST, jArry);
		for(int i=0; i<len; i++){
			if(courses[i].strJSON != null){
				jObj.accumulate(IShangkeHeader.LIST_ITEM_LIST, courses[i].strJSON);
			}else{
				jObj.accumulate(IShangkeHeader.LIST_ITEM_LIST, JSONObject.NULL);
				Log.e(Tag, "coursesChosen2JSON Error: strJSON is null!");
			}			
		}		
		return jObj;
	}
	
	
}