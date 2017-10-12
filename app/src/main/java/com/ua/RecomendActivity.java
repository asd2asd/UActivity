package com.ua;

import java.util.ArrayList;

import android.app.AlertDialog;

import android.os.Bundle;
import android.app.Activity;
import android.content.DialogInterface;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.ua.R;


public class RecomendActivity extends Activity {

	
	private String gradeString;
	private String schoolString;
	
	private Button buttonRec;
	private Button buttonHot;
	private Button buttonNew;
	private EditText editTextPersonsNumber;
	private EditText editTextSchool;
	private EditText editTextGrade;
	private Spinner spinnerType;
	private MyApplication ma;
	private RadioButton radioButtonMale, radioButtonFemale, radioButtonNolimit;
	
	private static final String[] types = {"读书","郊游","运动","文艺","吃","逛街"};
	private ArrayAdapter<String> adapterTypes;

    private TextView title;
    private Button buttonBack;
    private Button buttonConfirm;
	
	private void filter(String type){
	}
	private void startView(View v){
		// TODO Auto-generated method stub
		if(v.getId() == buttonHot.getId() || v.getId() == buttonNew.getId() || v.getId() == buttonRec.getId()){
		 			//Intent intent = new Intent(RecomendActivity.this,ActivityListActivity.class);
		 			//RecomendActivity.this.startActivity(intent);
            finish();
		}
		if(v == editTextGrade && v.hasFocus()){
			// TODO Auto-generated method stub
			AlertDialog.Builder builder = new AlertDialog.Builder(RecomendActivity.this); 
			View view = View.inflate(RecomendActivity.this, R.layout.grade_chooser, null);
			final RadioButton rbNolimit = (RadioButton)view.findViewById(R.id.radioButtonGradeChooserNoLimit);
			final RadioButton rbFree = (RadioButton)view.findViewById(R.id.radioButtonGradeChooserFree);
			builder.setView(view);
			final ArrayList<CheckBox> cbList = new ArrayList<CheckBox>();
			builder.setTitle("请选择年级");
			cbList.add((CheckBox)view.findViewById(R.id.checkBox1));
			cbList.add((CheckBox)view.findViewById(R.id.checkBox2));
			cbList.add((CheckBox)view.findViewById(R.id.checkBox3));
			cbList.add((CheckBox)view.findViewById(R.id.checkBox4));
			cbList.add((CheckBox)view.findViewById(R.id.checkBox5));
			cbList.add((CheckBox)view.findViewById(R.id.checkBox6));
			cbList.add((CheckBox)view.findViewById(R.id.checkBox7));
			cbList.add((CheckBox)view.findViewById(R.id.checkBox8));
			cbList.add((CheckBox)view.findViewById(R.id.checkBox9));
			if(rbNolimit.isChecked()){
				for(CheckBox cb:cbList)
					cb.setEnabled(false);
			}
			rbNolimit.setOnCheckedChangeListener(new OnCheckedChangeListener(){

				@Override
				public void onCheckedChanged(CompoundButton arg0,
						boolean arg1) {
					if(rbNolimit.isChecked()){
						for(CheckBox cb:cbList)
							cb.setEnabled(false);
					}
					if(!rbNolimit.isChecked()){
						for(CheckBox cb:cbList)
							cb.setEnabled(true);
					}
					
				}});
            builder.setPositiveButton("确  定", new DialogInterface.OnClickListener() {
         	   	String gradeSet = "";
         	   	String gradeNameSet = "";
                @Override 
                public void onClick(DialogInterface dialog, int which) { 
                	if(!rbFree.isChecked()){
                		gradeString = "0";
                		editTextGrade.setText("不限");
                		return;
                	}
                	for(CheckBox cb : cbList){
                		if(cb.isChecked()){
                			gradeSet = gradeSet + (ma.getConstVariables().getGradeId(cb.getText().toString())) + ",";
                			gradeNameSet = gradeNameSet + cb.getText() + ",";
                		}
                	}
                	if(gradeSet.length() > 0){
                		gradeSet = gradeSet.substring(0,gradeSet.length() -1);
                		gradeNameSet = gradeNameSet.substring(0,gradeNameSet.length() -1);
                		gradeString = gradeSet;
                		editTextGrade.setText(gradeNameSet);
                	}
                	else{
                		gradeString = "0";
                		editTextGrade.setText("不限");
                	}
                    dialog.cancel(); 
                } 
            }); 
            builder.show();
		}	
		if(v == editTextSchool && v.hasFocus()){
			// TODO Auto-generated method stub
			AlertDialog.Builder builder = new AlertDialog.Builder(RecomendActivity.this); 
			View view = View.inflate(RecomendActivity.this, R.layout.school_chooser, null); 
			final RadioButton rbNolimit = (RadioButton)view.findViewById(R.id.radioButtonSchoolChooserNoLimit);
			final RadioButton rbFree = (RadioButton)view.findViewById(R.id.radioButtonSchoolChooserFree);
			builder.setView(view);
			final ArrayList<CheckBox> cbList = new ArrayList<CheckBox>();
			builder.setTitle("请选择学校");
			cbList.add((CheckBox)view.findViewById(R.id.checkBoxSchoolA));
			cbList.add((CheckBox)view.findViewById(R.id.checkBoxSchoolB));
			cbList.add((CheckBox)view.findViewById(R.id.checkBoxSchoolC));
			cbList.add((CheckBox)view.findViewById(R.id.checkBoxSchoolD));
			cbList.add((CheckBox)view.findViewById(R.id.checkBoxSchoolE));
			if(rbNolimit.isChecked()){
				for(CheckBox cb:cbList)
					cb.setEnabled(false);
			}
			rbNolimit.setOnCheckedChangeListener(new OnCheckedChangeListener(){

				@Override
				public void onCheckedChanged(CompoundButton arg0,
						boolean arg1) {
					if(rbNolimit.isChecked()){
						for(CheckBox cb:cbList)
							cb.setEnabled(false);
					}
					if(!rbNolimit.isChecked()){
						for(CheckBox cb:cbList)
							cb.setEnabled(true);
					}
					
				}});
            builder.setPositiveButton("确  定", new DialogInterface.OnClickListener() {
            	String schoolSet = "";
         	   	String schoolNameSet = "";
                @Override 
                public void onClick(DialogInterface dialog, int which) { 
            	   	if(!rbFree.isChecked()){
        				schoolString = "0";
        				editTextSchool.setText("不限");
        				return;
             	   	}
                	for(CheckBox cb : cbList){
                		if(cb.isChecked()){
                			schoolSet = schoolSet + (ma.getConstVariables().getGradeId(cb.getText().toString())) + ",";
                			schoolNameSet = schoolNameSet + cb.getText() + ",";
                		}
                	}
                	if(schoolSet.length() > 0){
                		schoolSet = schoolSet.substring(0,schoolSet.length() -1);
                		schoolNameSet = schoolNameSet.substring(0,schoolNameSet.length() -1);
                		schoolString = schoolSet;
                		editTextSchool.setText(schoolNameSet);
                	}
                	else{
                		schoolString = "0";
                		editTextSchool.setText("不限");
                	}
                    dialog.cancel(); 
                } 
            }); 
            builder.show();
		}				
		
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		getWindow().requestFeature(android.view.Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_filter);
        getWindow().setFeatureInt(android.view.Window.FEATURE_CUSTOM_TITLE, R.layout.titlebar);

        title = (TextView)findViewById(R.id.title);
        title.setText("用户设置");
        buttonBack = (Button)findViewById(R.id.back);
        buttonConfirm = (Button)findViewById(R.id.confirm);
        buttonBack.setOnClickListener(onNavBarClick);
        buttonConfirm.setOnClickListener(onNavBarClick);

		ma = (MyApplication)getApplication();
		buttonHot = (Button)findViewById(R.id.buttonFilterHot);
		buttonNew = (Button)findViewById(R.id.buttonFilterNew);
		buttonRec = (Button)findViewById(R.id.buttonFilterRec);
		editTextSchool = (EditText)findViewById(R.id.editTextFilterSchool);
		editTextGrade = (EditText) findViewById(R.id.editTextFilterGrade);
		spinnerType = (Spinner)findViewById(R.id.spinnerFilterType);
		radioButtonMale = (RadioButton)findViewById(R.id.radioButtonFilterMale);
		radioButtonFemale = (RadioButton)findViewById(R.id.radioButtonFilterFemale);
		radioButtonNolimit = (RadioButton)findViewById(R.id.radioButtonFilterNoLimit);
		buttonRec.setOnClickListener(clickListener);
		buttonNew.setOnClickListener(clickListener);
		buttonHot.setOnClickListener(clickListener);
		
		gradeString = "0";
		schoolString = "0";
		
		adapterTypes = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, types);
		spinnerType.setAdapter(adapterTypes);
		
		editTextGrade.setOnFocusChangeListener(new OnFocusChangeListener(){
			@Override
			public void onFocusChange(View v, boolean arg1) {
				startView(v);
			}
		});
		editTextSchool.setOnFocusChangeListener(new OnFocusChangeListener(){
			@Override
			public void onFocusChange(View v, boolean arg1) {
				startView(v);
			}
		});
		
		
		editTextGrade.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				startView(v);
				// TODO Auto-generated method stub				
			}
			
		});
		
		
		editTextSchool.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startView(v);
			}
			
		});

	}

    private OnClickListener onNavBarClick = new OnClickListener() {
        public void onClick(View v) {
            if (v == buttonBack) {
                finish();
            }
            else {
                finish();
            }
        }
    };
	
	private OnFocusChangeListener focusChangeListener = new OnFocusChangeListener(){

		@Override
		public void onFocusChange(View v, boolean arg1) {
			// TODO Auto-generated method stub
			startView(v);
		}
	};
	
	private OnClickListener clickListener = new OnClickListener(){
		public void onClick(View v){
			startView(v);
		}
	};
	

}
