package com.ua.parser;

import java.io.IOException;
import java.io.InputStream;

import org.json.JSONObject;
import org.json.JSONTokener;

import com.ua.bean.UserBean;

public class LoginParser {
	private String result;
	private UserBean ub;
	public UserBean getUserBean(){
		return ub;
	}
	public LoginParser(){
		ub = new UserBean();
	}
	public String parserLoginBean( InputStream in) throws IOException{
//		ub = new UserBean();
//		StringBuilder sb = new StringBuilder();
		result = "true";
        byte [] buffer = new byte[in.available()] ; 
        in.read(buffer);
        String json = new String(buffer,"utf-8");   		
		System.out.println(json);
        try {
			JSONTokener jsonTokener = new JSONTokener(json);
			JSONObject jUser = (JSONObject) jsonTokener.nextValue();
			
			int errorcode = jUser.getInt("errorcode");
			if(errorcode == 0){
				// 返回成功
				JSONObject data = jUser.getJSONObject("data");
				String token = data.getString("token");
				JSONObject user = data.getJSONObject("user");
				String id = user.getString("id");
				String mail = user.getString("mail");
				String password = user.getString("password");
				String realname;
				if(user.has("realname")){
					realname = user.getString("realname");
					ub.setRealName(realname);
				}
				String phone;
				if(user.has("phone")){
					phone = user.getString("phone");
					ub.setPhone(phone);
				}
				String nickname = user.getString("nickname");
				String interest = user.getString("interest");
				int gender = user.getInt("gender");
				int school = user.getInt("school");
				String department;
				if(user.has("department")){
					department = user.getString("department");
					ub.setDepartment(department);
				}
				int grade = user.getInt("grade");
				String headimg;
				if(user.has("headimg")){
					headimg = user.getString("headimg");
					ub.setheadimg(headimg);
				}
				int privilege = user.getInt("privilege");
				int role = user.getInt("role");
//				UserBean ub = new UserBean();
				ub.setToken(token);
				ub.setGender(gender);
				ub.setId(id);
				ub.setInterest(interest);
				ub.setMail(mail);
				ub.setNickName(nickname);
				ub.setPassword(password);
				ub.setPrivilege(privilege);
				ub.setRole(role);
				ub.setSchool(school);
				
			}
			if(errorcode != 0){
				result = "error";
			}
			
		}catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return result;
	}
}
