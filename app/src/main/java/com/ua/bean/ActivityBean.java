package com.ua.bean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ActivityBean {
	private int id;
	private String title;
	private int orgniserID;
	private Date actTime;
	private Date startTime;
	private Date endTime;
	private Date applyTime;
	
	private int status;
	private int applyNum;
	private int numLimit;
	private String address;
	private int fee;
	private String benefit;
	private String feature;
	private String description;
	private int type;
	private int collectNum;
	private int schoolLimit;
	private int gradeLimit;
	private int genderLimit;
	private String contact;
	private String head;
    private String img;
	private ArrayList<Candidate> candidateList;
	
	class Candidate{
		String name;
		String head;
        String img;
		int id;
		String cApplytime;
		public Candidate(int id,String name, String head,String img, String applytime){
			this.name = name;
			this.head = head;
            this.img = img;
			this.id = id;
			this.cApplytime = applytime;
		}
		public String getName(){
			return name;
		}
		public String getHead(){
			return head;
		}
        public String getImg(){
            return img;
        }
		public int getId(){
			return id;
		}
		public String getApplyTime(){
			return cApplytime;
		}
	}
	
	public ActivityBean(){
		candidateList = new ArrayList<Candidate>();
	}
	
	
	public String outputListString(){
		String listString = "" + title + "\t" + orgniserID + "\t" + "startTime" + "\t" + head;
		return listString;
	}
	
	public String outputActivityString(String orgniserName){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String activityString = "活动编号:\t" + id + "\n";
		activityString +="活动名称:\t" + title + "\n";
		activityString +="组织者:\t" + orgniserName + "\n";
		activityString +="开始时间:\t" + sdf.format(actTime) + "\n";
		activityString +="发布时间:\t" + sdf.format(startTime) + "\n";
		activityString +="结束时间:\t" + sdf.format(endTime) + "\n";
		activityString +="申请时间:\t" + sdf.format(applyTime) + "\n";
		activityString +="当前状态:\t" + status + "\n";
		activityString +="申请人:\t" + applyNum + "\n";
		activityString +="人数限制:\t" + numLimit + "\n";
		activityString +="地点:\t" + address + "\n";
		activityString +="费用:\t" + fee + "\n";
		activityString +="好处:\t" + benefit + "\n";
		activityString +="特色:\t" + feature + "\n";
		activityString +="活动描述:\t" + description + "\n";
		activityString +="活动类型:\t" + type + "\n";
		activityString +="已参加人数:\t" +  collectNum + "\n";
		activityString +="学校限制:\t" + schoolLimit + "\n";
		activityString +="年级限制:\t" + gradeLimit + "\n";
		activityString +="性别限制:\t" + genderLimit + "\n";
		activityString +="联系方式:\t" + contact;
		return activityString;
	}
	
	
	public ActivityBean(String classData) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String []elements = classData.split("\t");
		this.id = Integer.parseInt(elements[0]);
		title = elements[1];
		this.orgniserID = Integer.parseInt(elements[2]);
		actTime = sdf.parse(elements[3]);
		startTime = sdf.parse(elements[4]);
		endTime = sdf.parse(elements[5]);
		applyTime = sdf.parse(elements[6]);
		status = Integer.parseInt(elements[7]);
		applyNum = Integer.parseInt(elements[8]);
		numLimit = Integer.parseInt(elements[9]);
		address = elements[10];
		fee = Integer.parseInt(elements[11]);
		benefit = elements[12];
		feature = elements[13];
		description = elements[14];
		type = Integer.parseInt(elements[15]);
		collectNum = Integer.parseInt(elements[16]);
		schoolLimit = Integer.parseInt(elements[17]);
		gradeLimit = Integer.parseInt(elements[18]);
		genderLimit = Integer.parseInt(elements[19]);
		contact = elements[20];
	}
	
	public ArrayList<Candidate> getCandidateList(){
		return candidateList;
	}
	
	public int getId(){
		return id; 
	}
	public String getTitle(){
		return title;
	}
	public int getOrgniserID(){
		return orgniserID;
	}
	public Date getActTime(){
		return actTime;
	}	
	public String getStartTime(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(startTime);
	}
	public String getEndTime(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(endTime);
	}
	public String getApplyTime(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(applyTime);
	}
	public int getStatus(){
		return status;
	}
	public int getApplyNum(){
		return applyNum;
	}
	public int getNumLimit(){
		return numLimit;
	}
	public String getAddress(){
		return address;
	}
	public int getFee(){
		return fee;
	}
	public String getBenefit(){
		return benefit;
	}
	public String getFeature(){
		return feature;
	}
	public String getDescription(){
		return description;
	}
	public int getType(){
		return type;
	}
	public int getCollectNum(){
		return collectNum;
	}
	public int getSchoolLimit(){
		return schoolLimit;
	}
	public int getGradeLimit(){
		return gradeLimit;
	}
	public String getContact(){
		return contact;
	}
	public String getHead(){
		return head;
	}
    public String getImg(){
        return img;
    }
	public void setTitle(String title){
		this.title = title;
	}
	public void setOneCandidate(int id, String name, String head,String img, String applyTime){
		Candidate candidate = new Candidate(id, name, head,img, applyTime);
		candidateList.add(candidate);
	}
	public void setOrgniserID(int orgniserID){
		this.orgniserID = orgniserID;
	}
	public void setActTime(Date actTime){
		this.actTime = actTime;
	}
	public void setStartTime(String startTime) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		this.startTime = sdf.parse(startTime);
	}
	public void setEndTime(String endTime) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		this.endTime = sdf.parse(endTime);
	}
	public void setApplyTime(String applyTime) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		this.applyTime = sdf.parse(applyTime);
	}
	public void setStatus(int status){
		this.status = status;
	}
	public void setApplyNum(int applyNum){
		this.applyNum = applyNum;
	}
	public void setNumLimit(int numLimit){
		this.numLimit = numLimit;
	}
	public void setAddress(String address){
		this.address = address;
	}
	public void setFee(int fee){
		this.fee = fee;
	}
	public void setBenefit(String benefit){
		this.benefit = benefit;
	}
	public void setFeature(String feature){
		this.feature = feature;
	}
	public void setDescription(String description){
		this.description = description;
	}
	public void setType(int type){
		this.type = type;
	}
	public void setCollectNum(int collectNum){
		this.collectNum = collectNum;
	}
	public void setGradeLimit(int gradeLimit){
		this.gradeLimit = gradeLimit;
	}
	public void setSchoolLimit(int schoolLimit){
		this.schoolLimit = schoolLimit;
	}
	public void setGenderLimit(int genderLimit){
		this.genderLimit = genderLimit;
	}
	public void setContact(String contact){
		this.contact = contact;
	}
	public void setHead(String head){
		this.head = head;
	}
    public void setImg(String img){
        this.img = img;
    }
	
}
