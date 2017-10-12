package com.ua.parser;

import java.io.IOException;
import java.io.InputStream;

import org.json.JSONObject;
import org.json.JSONTokener;

public class Register2Parser {
	public String parserRegister2Bean( InputStream in) throws IOException{
//		StringBuilder sb = new StringBuilder();
		String result = "true";
        byte [] buffer = new byte[in.available()] ; 
        in.read(buffer);
        String json = new String(buffer,"utf-8");   		
		
        try {
			JSONTokener jsonTokener = new JSONTokener(json);
			JSONObject user = (JSONObject) jsonTokener.nextValue();
			
			int errorcode = user.getInt("errorcode");
			if(errorcode == 100){
				result = "parameter-error";
			}
			if(errorcode == 103){
				result = "register-error";
			}
			
		}catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return result;
	}
}
