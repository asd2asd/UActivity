package com.ua.handler;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class LoginHandler extends DefaultHandler {
	private String val = "";
	private static String name;
	private static String id;
	private static String sex;
	private static String uname;
	private static String NO;
	
	public static String getUname(){
		return uname;
	}
	public static void setUname(String uname){
		LoginHandler.uname = uname;
	}
	public String getSex(){
		return sex;
	}
	public void setSex(String sex){
		this.sex = sex;
	}
	public String getId(){
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException{
		super.startElement(uri, localName, qName, attributes);
		
	}
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException{
		if (qName.equals("id")){
			this.id = val;
		}
		if (qName.equals("name")){
			this.name = val;
		}
		if(qName.equals("uname")){
			this.uname = val;
		}
	}
}
