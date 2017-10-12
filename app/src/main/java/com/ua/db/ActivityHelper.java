/*
 * Time: 2014/11/11
 * Author: Justin Song
 * Purpose: this class is used to create table user
 */
package com.ua.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class ActivityHelper extends SQLiteOpenHelper {
	
	// table name
	private static String nameTableActivity = "activity";
	
	public String getTableName(){
		return nameTableActivity;
	}
	
    public ActivityHelper(Context c){
    	this(c,nameTableActivity,null,1);
    }
	
	public ActivityHelper(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		String createTableString = "create table " + nameTableActivity +"(id integer primary key,title text, orgniserID integer,actTime timestamp, startTime timestamp," +
				"endTime timestamp, applyTime timestamp, status int, applyNum int, numLimit int, address text, fee int, benefit text, feature text, description, text," +
				"type int, collectNum int, schoolLimit int, gradeLimit int, genderLimit int, contact text);";
		db.execSQL(createTableString);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		// TODO Auto-generated method stub
		String dropTable = "drop table" + nameTableActivity;
		db.execSQL(dropTable);
		this.onCreate(db);
	}

}
