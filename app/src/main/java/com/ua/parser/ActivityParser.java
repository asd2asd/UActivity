package com.ua.parser;

import java.text.ParseException;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.ua.bean.ActivityBean;

public class ActivityParser {
	private ActivityBean activityBean;
	public ActivityParser(){
		activityBean = new ActivityBean();
	}
	public ActivityBean parseStream(String jsonString) throws JSONException, ParseException{
		
		JSONTokener jsonTokener = new JSONTokener(jsonString);
		JSONObject jList = (JSONObject) jsonTokener.nextValue();
		int errorcode = jList.getInt("errorcode");
		if(errorcode == 0){
			// 返回成功
			JSONObject data = jList.getJSONObject("data");
			activityBean.setTitle(data.getString("title"));
			activityBean.setAddress(data.getString("address"));
			activityBean.setFee(data.getInt("fee"));
			activityBean.setDescription(data.getString("description"));
			activityBean.setContact(data.getString("contact"));
			activityBean.setApplyNum(data.getInt("applynum"));
			JSONObject author = (JSONObject)data.getJSONObject("author");
			activityBean.setStartTime(data.getString("starttime"));
			activityBean.setApplyTime(data.getString("applytime"));
			activityBean.setHead(author.getString("head"));
            if(data.has("image"))
                activityBean.setImg(data.getString("image"));
			activityBean.setGradeLimit(data.getInt("gradelimit"));
			activityBean.setGenderLimit(data.getInt("genderlimit"));
			activityBean.setSchoolLimit(data.getInt("schoollimit"));
			
			/*
			JSONArray ja = jList.getJSONArray("candidate");
			for(int i = 0; i< ja.length(); i++){
				JSONObject jo = ja.getJSONObject(i);
				String date = jo.getString("date");
				JSONObject candidate = jo.getJSONObject("author");
				String name = candidate.getString("name");
				String head = candidate.getString("head");
				int id = candidate.getInt("id");
				activityBean.setOneCandidate(id, name, head, date);
			}
			*/
		}
		else{
			return null;
		}
		
		return activityBean;
	}
}
