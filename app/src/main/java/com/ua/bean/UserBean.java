package com.ua.bean;

public class UserBean {
	
	private String token;
	private String mail;
	private String realname;
	private String phone;
	private String nickname;
	private String interest;
	private int gender;
	private int school;
	private int grade;
	private String department;
	private String stunum;
	private String headimg;
	private String password;
	private String id;
	private int privilege;
	private int role;
	
	public String getId(){
		return id;
	}
	
	public String getNickname(){
		return nickname;
	}
	
	public String getPassword(){
		return password;
	}
	
	public String getRealName(){
		return realname;
	}
	public String getToken(){
		return token;
	}
	public String getMail(){
		return mail;
	}
	public String getPhone(){
		return phone;
	}
	public String getInterest(){
		return interest;
	}
	public int getGender(){
		return gender;
	}
	public int getGrade(){
		return grade;
	}
	public int getSchool(){
		return school;
	}
	public String getDepartment(){
		return department;
	}
	public String getStunum(){
		return stunum;
	}
	public String getHeadimg(){
		return headimg;
	}
	public int getPrivilege(){
		return privilege;
	}
	public int getRole(){
		return role;
	}

	
	
	public void setId(String id){
		this.id = id;
	}
	
	public void setNickName(String nickname){
		this.nickname = nickname;
	}

	public void setPassword(String password){
		this.password = password;
	}
	
	public void setRealName(String realname){
		this.realname = realname;
	}
	public void setMail(String mail){
		this.mail = mail;
	}
	public void setToken(String token){
		this.token = token;
	}
	public void setInterest(String interest){
		this.interest = interest;
	}
	public void setGender(int gender){
		this.gender = gender;
	}
	public void setSchool(int school){
		this.school = school;
	}
	public void setDepartment(String department){
		this.department = department;
	}
	public void setheadimg(String headimg){
		this.headimg = headimg;
	}
	public void setPrivilege(int privilege){
		this.privilege = privilege;
	}
	public void setRole(int role){
		this.role = role;
	}
	public void setPhone(String phone){
		this.phone = phone;
	}
	public void setGrade(int grade){
		this.grade = grade;
	}

}
