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

public class UserHelper extends SQLiteOpenHelper {
	
	// table name
	private static String nameTableUser = "user";
	
	public String getTableName(){
		return nameTableUser;
	}
	
    public UserHelper(Context c){
    	this(c,nameTableUser,null,1);
    }
	
	public UserHelper(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		String createTableString = "create table " + nameTableUser +"(user_id integer primary key autoincrement,user_name text,user_pswd text, user_level integer, checkBoxState integer);";
		db.execSQL(createTableString);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		// TODO Auto-generated method stub
		String dropTable = "drop table" + nameTableUser;
		db.execSQL(dropTable);
		this.onCreate(db);
	}

}
