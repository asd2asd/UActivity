package com.ua.parser;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.ua.bean.MessageBean;

public class MessageParser {
	ArrayList<MessageBean> messageList;
	
	public MessageParser(){
		messageList = new ArrayList<MessageBean>();
	}
	public ArrayList<MessageBean> parseStream(String jsonString){
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
					int uid = jo.getInt("uid");
					String content = jo.getString("content");
					String date = jo.getString("date");
					JSONObject user = jo.getJSONObject("author");
					String head = user.getString("head");
					String name = user.getString("name");
					
					MessageBean mb = new MessageBean();
					mb.setAuthorName(name);
					mb.setContent(content);
					mb.setHead(head);
					mb.setId(id);
					mb.setUid(uid);
					mb.setTime(date);
		
					messageList.add(mb);
				}
				if(errorcode != 0){
					return null;
				}
			}
			
		}catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return messageList;
			}

		return this.messageList;
	}
}
