package com.ua.parser;

import java.io.IOException;
import java.io.InputStream;

import org.json.JSONObject;
import org.json.JSONTokener;

public class Register1Parser {
	String result;
	public String parserPasswordBean(String pwd1, String pwd2){
		result = "true";
		if(pwd1 == null || pwd1.matches("")){
			result = "null-error";
			return result;
		}
		if(pwd2 == null || !pwd1.equals(pwd2)){
			result = "diff-error";
		}
		return result;
	}
	public String parserEmailBean(InputStream in) throws IOException{
//		StringBuilder sb = new StringBuilder();
		result = "";
        byte [] buffer = new byte[in.available()] ; 
        in.read(buffer);
        String json = new String(buffer,"utf-8");   		

        try {
			JSONTokener jsonTokener = new JSONTokener(json);
			JSONObject email = (JSONObject) jsonTokener.nextValue();
			
			int errorcode = email.getInt("errorcode");
			if(errorcode == 0){
				result = "true";
			}
			if(errorcode == 100){
				result = "parameter-error";
			}
			if(errorcode == 200){
				result = "duplicate";
			}
			if(errorcode == 201)
				result = "format-error";
		}catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return result;
		
	}
	public String parserNicknameBean( InputStream in) throws IOException{
//		StringBuilder sb = new StringBuilder();
		result = "true";
        byte [] buffer = new byte[in.available()] ; 
        in.read(buffer);
        String json = new String(buffer,"utf-8");   		
		
        try {
			JSONTokener jsonTokener = new JSONTokener(json);
			JSONObject user = (JSONObject) jsonTokener.nextValue();
			
			int errorcode = user.getInt("errorcode");
			if(errorcode == 100){
				result = "format-error";
			}
			if(errorcode == 202){
				result = "duplicate";
			}
			
		}catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return result;
	}
}
