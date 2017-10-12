package com.ua;

//import com.baidu.mapapi.SDKInitializer;
import com.ua.bean.UserBean;
import com.ua.variable.ConstVariables;

import android.app.Application;

public class MyApplication extends Application {
	
	private UserBean userBean;
	private ConstVariables cv;
	
	@Override
	public void onCreate(){
		super.onCreate();
		cv = new ConstVariables();
	}
	public void setUser(UserBean ub){
		this.userBean = ub;
	}
	public ConstVariables getConstVariables(){
		return cv;
	}
	public UserBean getUser(){
		return this.userBean;
	}
}
