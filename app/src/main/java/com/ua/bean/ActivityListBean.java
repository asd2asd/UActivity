package com.ua.bean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ActivityListBean {
	private int id;
	private String title;
	private String aName;
	private Date actTime;
	private String head;
	private String image;
	private String description;
    private int applyNum;
	
	public ActivityListBean(){
	}
	
	public ActivityListBean(String initString) throws ParseException{
		String []elements = initString.split("\t");
		id = Integer.parseInt(elements[0]);
		title = elements[1];
		aName = elements[2];
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		actTime = sdf.parse(elements[3]);
		head = elements[4];
		description = elements[5];		
	}
	
	
	
	public String outputString(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return "" + id + "\t" + title + "\t" + aName + "\t" + sdf.format(actTime) + "\t" + head + "\t" + description;
 	}
	
	public void setTitle(String title){
		this.title = title;
	}
	
	public void setImage(String image){
		this.image = image;
	}
	
	public void setId(int id){
		this.id = id;
	}
		
	public void setApplyNum(int applyNum){
		this.applyNum = applyNum;
	}

	public void setAuthorName(String name){
		this.aName = aName;
	}
	
	public void setActTime(String actTime) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		this.actTime = sdf.parse(actTime);
	}
	
	public void setHead(String head){
		this.head = head;
	}
	
	public void setDescription(String description){
		this.description = description;
	}
	
	
	public int getId(){
		return id;
	}
		
	public int getApplyNum(){
		return applyNum;
	}
	public String getImage(){
		return image;
	}
	
	public String getTitle(){
		return title;
	}
	
	public String getAuthor(){
		return aName;
	}
	
	public String getActTime(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(actTime);
	}
	
	public String getDescription(){
		return description;
	}
	
	public String getHead(){
		return head;
	}
}
