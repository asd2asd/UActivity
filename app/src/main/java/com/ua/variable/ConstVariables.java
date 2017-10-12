package com.ua.variable;

import java.util.HashMap;

public class ConstVariables {
	
	private String IPAddress;

	private HashMap<String, Integer> schoolMap;
	private HashMap<String, Integer> gradeMap;
	private HashMap<String, Integer> genderMap;
	private HashMap<String, Integer> interestMap;
	
	private HashMap<Integer, String> schoolRMap;
	private HashMap<Integer, String> gradeRMap;
	private HashMap<Integer, String> genderRMap;
	private HashMap<Integer, String> interestRMap;
	
	
	public ConstVariables(){
		
//		IPAddress = "http://10.0.3.2:8080";
		IPAddress = "http://192.168.1.23:8080";
		
		schoolMap = new HashMap<String, Integer>();
		gradeMap = new HashMap<String, Integer>();
		genderMap = new HashMap<String, Integer>();
		interestMap = new HashMap<String, Integer>();
		
		schoolRMap = new HashMap<Integer, String>();
		gradeRMap = new HashMap<Integer, String>();
		genderRMap = new HashMap<Integer, String>();
		interestRMap = new HashMap<Integer, String>();
		
		schoolMap.put("SchoolA", 1);
		schoolMap.put("SchoolB", 2);
		schoolMap.put("SchoolC", 3);
		schoolMap.put("SchoolD", 4);
		schoolMap.put("SchoolE", 5);
		schoolMap.put("SchoolF", 6);
		
		gradeMap.put("本科一年级", 1);
		gradeMap.put("本科二年级", 2);
		gradeMap.put("本科三年级", 3);
		gradeMap.put("本科四年级", 4);
		gradeMap.put("博硕一年级", 5);
		gradeMap.put("博硕二年级", 6);
		gradeMap.put("博硕三年级", 7);
		gradeMap.put("博硕四年级", 8);
		gradeMap.put("博硕五年级", 9);
		
		genderMap.put("男", 1);
		genderMap.put("女", 2);
		
		interestMap.put("读书", 1);
		interestMap.put("郊游", 2);		
		interestMap.put("运动", 3);
		interestMap.put("文艺", 4);
		interestMap.put("吃", 5);
		interestMap.put("逛街", 6);
		
		schoolRMap.put(1, "SchoolA");
		schoolRMap.put(2, "SchoolB");
		schoolRMap.put(3, "SchoolC");
		schoolRMap.put(4, "SchoolD");
		schoolRMap.put(5, "SchoolE");
		schoolRMap.put(6, "SchoolF");
		
		gradeRMap.put(1, "本科一年级");
		gradeRMap.put(2, "本科二年级");
		gradeRMap.put(3, "本科三年级");
		gradeRMap.put(4, "本科四年级");
		gradeRMap.put(5, "博硕一年级");
		gradeRMap.put(6, "博硕二年级");
		gradeRMap.put(7, "博硕三年级");
		gradeRMap.put(8, "博硕四年级");
		gradeRMap.put(9, "博硕五年级");
		
		genderRMap.put(1, "男");
		genderRMap.put(2, "女");
		
		interestRMap.put(1, "读书");
		interestRMap.put(2, "郊游");		
		interestRMap.put(3, "运动");
		interestRMap.put(4, "文艺");
		interestRMap.put(5, "吃");
		interestRMap.put(6, "逛街");

	}
	
	public String getIPAddress(){
		return this.IPAddress;
	}
	
	public int getSchoolId(String school){
		if(schoolMap.containsKey(school)){
			return schoolMap.get(school);
		}
		
		return 0;
	}
	public String getSchoolName(int schoolId){
		if(schoolRMap.containsKey(schoolId)){
			return schoolRMap.get(schoolId);
		}
		return "无限制";
	}
	public int getGradeId(String grade){
		if(gradeMap.containsKey(grade)){
			return gradeMap.get(grade);
		}
		
		return 0;
	}
	public String getGradeName(int gradeId){
		if(gradeRMap.containsKey(gradeId)){
			return gradeRMap.get(gradeId);
		}
		return "无限制";
	}
	public int getInterestId(String interest){
		if(interestMap.containsKey(interest)){
			return interestMap.get(interest);
		}
		
		return -1;
	}
	public String getInterestName(int schoolId){
		if(interestRMap.containsKey(schoolId)){
			return interestRMap.get(schoolId);
		}
		return null;
	}
	public int getGenderId(String gender){
		if(genderMap.containsKey(gender)){
			return genderMap.get(gender);
		}
		
		return 0;
	}	
	public String getGenderName(int genderId){
		if(genderRMap.containsKey(genderId)){
			return genderRMap.get(genderId);
		}
		return "无限制";
	}
}
