package com.ua.parser;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.ua.bean.ActivityListBean;

public class MyActivityListParser {
	ArrayList<ActivityListBean> alBeanList;
	
	public MyActivityListParser(){
		alBeanList = new ArrayList<ActivityListBean>();
	}
	public ArrayList<ActivityListBean> parseStream(String jsonString){
		System.out.println(jsonString);
        try {
			JSONTokener jsonTokener = new JSONTokener(jsonString);
			JSONObject jList = (JSONObject) jsonTokener.nextValue();
			int errorcode = jList.getInt("errorcode");
			if(errorcode == 0){
				// 返回成功
				JSONArray data = jList.getJSONArray("data");
				for(int i = 0; i < data.length(); i ++){
					JSONObject jo = data.getJSONObject(i);
					int id = jo.getInt("id");
					String acttime = jo.getString("date");
					JSONObject activity = jo.getJSONObject("activity");
					String title = activity.getString("title");
					int applynum = activity.getInt("applynum");
					String description = activity.getString("description");
					JSONObject user = activity.getJSONObject("author");
					String head = user.getString("head");
					String image = "";
					if(activity.has("image"))
						image = activity.getString("image");
					String name = user.getString("name");
					
					ActivityListBean alb = new ActivityListBean();
					alb.setApplyNum(applynum);
					alb.setImage(image);
					alb.setActTime(acttime);
					alb.setAuthorName(name);
					alb.setDescription(description);
					alb.setHead(head);
					alb.setId(id);
					alb.setTitle(title);
					alBeanList.add(alb);
				}
				if(errorcode != 0){
					return null;
				}
			}
			
		}catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return alBeanList;
			}

		return this.alBeanList;
	}
}
