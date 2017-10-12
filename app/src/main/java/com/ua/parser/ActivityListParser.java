package com.ua.parser;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.ua.bean.ActivityListBean;

public class ActivityListParser {
	ArrayList<ActivityListBean> alBeanList;
	
	public ActivityListParser(){
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
					String title = jo.getString("title");
					String acttime = jo.getString("acttime");
					int applynum = jo.getInt("applynum");
					String description = jo.getString("description");
					JSONObject user = jo.getJSONObject("author");
					String head = user.getString("head");
					String image = "";
					if(jo.has("image"))
						image = jo.getString("image");
					String name = user.getString("name");
					
					ActivityListBean alb = new ActivityListBean();
					alb.setImage(image);
					alb.setActTime(acttime);
					alb.setApplyNum(applynum);
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
