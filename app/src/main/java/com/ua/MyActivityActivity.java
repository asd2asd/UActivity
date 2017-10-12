package com.ua;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

import com.ua.R;


public class MyActivityActivity extends Activity {

	ImageView buttonSettingPerson;
	Button buttonSettingMyActivity;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		buttonSettingPerson = (ImageView) findViewById(R.id.buttonSettingPerson);
		buttonSettingMyActivity = (Button)findViewById(R.id.buttonMyCollectActivity);
	}
	
	private OnClickListener clickListener = new OnClickListener(){
		public void onClick(View v){
			// if button Register is clicked, skip to register1Activity
			if(v == buttonSettingPerson){
	 			Intent register3Intent = new Intent(MyActivityActivity.this,Register3Activity.class);
	 			MyActivityActivity.this.startActivity(register3Intent);
			}
			
			if(v == buttonSettingMyActivity){
				/*****************
				 * to be added here
				 */
			}
		}
		
	};

	
}
