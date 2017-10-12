package com.ua.bean;

import java.text.ParseException;

public class MessageBean {
	private int id;
	private int uid;
	private String head;
	private String time;
	private String content;
	private String name;
	
	public MessageBean(){
	}
	
	public void setId(int id){
		this.id = id;
	}
	
	public void setUid(int uid){
		this.uid = uid;
	}
	
	public void setAuthorName(String name){
		this.name = name;
	}
	
	public void setTime(String time) throws ParseException{
		this.time = time;
	}
	
	public void setHead(String head){
		this.head = head;
	}
	
	public void setContent(String content){
		this.content = content;
	}
	
	
	public int getId(){
		return id;
	}
	
	public String getContent(){
		return content;
	}
	
	public String getName(){
		return name;
	}
	
	public String getTime(){
		return time;
	}
		
	public String getHead(){
		return head;
	}
}




