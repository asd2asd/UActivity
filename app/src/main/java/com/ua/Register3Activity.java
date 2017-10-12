package com.ua;


import java.io.File;
import java.io.FileNotFoundException;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import com.ua.R;
import com.ua.parser.ImageParser;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Register3Activity extends Activity {
	private EditText editTextRname, editTextStudentID, editTextPhone;
	private RadioButton radioButtonMale, radioButtonFemale;
	private Spinner spinnerSchool, spinnerGrade, spinnerDepartment;
	private ArrayAdapter adapterSchool, adapterGrade, adapterDepartment;
	private Button buttonReg3Finish, buttonReg3Portrait, buttonReg3Upload;
	private ImageView imageViewPortrait;
	
	private MyApplication ma;
	
	private String portraitPath;
	private File imageFile;
	
	private boolean imageChoose = false;
	private boolean imageUploaded = false;
	
	private static final String[] schoolSet = {"SchoolA","SchoolB","SchoolC","SchoolD","SchoolE"};
	private static final String[] gradeSet = {"本科一年级", "本科二年级", "本科三年级", "本科四年级", "博硕一年级", "博硕二年级", "博硕三年级", "博士四年级", "博士五年级"};
	private static final String[] departmentSet = {"计算机", "日语", "电子", "水利"};

    private TextView title;
    private Button buttonBack;
    private Button buttonConfirm;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(android.view.Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.register3);
        getWindow().setFeatureInt(android.view.Window.FEATURE_CUSTOM_TITLE, R.layout.titlebar);

        title = (TextView)findViewById(R.id.title);
        title.setText("完善资料");
        buttonBack = (Button)findViewById(R.id.back);
        buttonConfirm = (Button)findViewById(R.id.confirm);
        buttonBack.setOnClickListener(onNavBarClick);
        buttonConfirm.setOnClickListener(onNavBarClick);

		imageChoose = false;
	    imageUploaded = false;
	    ma = (MyApplication)getApplication();
	    portraitPath = ma.getUser().getHeadimg();
	    if(portraitPath == null)
	    	portraitPath = "";
	    editTextRname = (EditText) findViewById(R.id.editTextRname);
	    radioButtonMale = (RadioButton) findViewById(R.id.radioButtonReg3Male);
	    radioButtonFemale = (RadioButton) findViewById(R.id.radioButtonReg3Female);
		spinnerSchool = (Spinner) findViewById(R.id.spinnerSchool);
		spinnerGrade = (Spinner) findViewById(R.id.spinnerGrade);
		spinnerDepartment = (Spinner) findViewById(R.id.spinnerDepartment);
		editTextStudentID = (EditText) findViewById(R.id.editTextStudentID);
		editTextPhone = (EditText) findViewById(R.id.editTextPhone);
		imageViewPortrait = (ImageView) findViewById(R.id.imageViewReg3Portrait);
		buttonReg3Finish = (Button) findViewById(R.id.buttonReg3Finish);
		buttonReg3Portrait = (Button) findViewById(R.id.buttonReg3Choose);
		buttonReg3Upload = (Button) findViewById(R.id.buttonReg3Upload);
		buttonReg3Finish.setOnClickListener(clickListener);
		buttonReg3Portrait.setOnClickListener(clickListener);
		buttonReg3Upload.setOnClickListener(clickListener);
		
	    //将可选内容与ArrayAdapter连接起来
	    adapterSchool = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, schoolSet);
	    adapterGrade = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, gradeSet);
	    adapterDepartment = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, departmentSet);
	    //设置下拉列表的风格
	    adapterSchool.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    adapterGrade.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    adapterDepartment.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    //将adapter 添加到spinner中
	    spinnerSchool.setAdapter(adapterSchool);
	    spinnerGrade.setAdapter(adapterGrade);
	    spinnerDepartment.setAdapter(adapterDepartment);
	}
	

	public void uploadPicture(String imgPath) throws FileNotFoundException{
		
		RequestParams params = new RequestParams();
		String token = ma.getUser().getToken();
		File imgFile = new File(imgPath);
		params.put("token", token);
		params.put("max", "1");
		params.put("file0", imgFile);
		String url = ma.getConstVariables().getIPAddress() + "/upload/image";

		AsyncHttpClient client = new AsyncHttpClient();  
        client.post(url, params, new AsyncHttpResponseHandler() {  
        	
			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2,
					Throwable arg3) {
				// TODO Auto-generated method stub
        		Toast.makeText(Register3Activity.this, "头像上传失败!", 0).show();   
			}
			@Override
			public void onSuccess(int statusCode, Header[] arg1, byte[] responce) {
				// TODO Auto-generated method stub
        		String jsonStream = new String(responce);
        		try {
					JSONObject json = new JSONObject(jsonStream);
					int errorcode = json.getInt("errorcode");
					if(errorcode == 0){
		        		Toast.makeText(Register3Activity.this, "头像上传成功!", 0).show();
						imageUploaded = true;
						portraitPath = json.getString("data");
						ma.getUser().setheadimg(portraitPath);
						RequestParams params = new RequestParams();
						String token = ma.getUser().getToken();
						params.put("token", token);
						params.put("headimg", portraitPath);
						String url = ma.getConstVariables().getIPAddress() + "/user/my/manage";
						System.out.println(url);
						AsyncHttpClient clientUpload = new AsyncHttpClient();  
				        clientUpload.post(url, params, new AsyncHttpResponseHandler() {  
							@Override
							public void onFailure(int arg0, Header[] arg1, byte[] arg2,
									Throwable arg3) {
								// TODO Auto-generated method stub
				        		Toast.makeText(Register3Activity.this, "头像更改失败!", 0).show();   
							}
							@Override
							public void onSuccess(int statusCode, Header[] arg1, byte[] responce) {
								// TODO Auto-generated method stub
				        		Toast.makeText(Register3Activity.this, "头像更改成功!", 0).show();
							}
				        }); 

					}
					else{
						portraitPath = "";
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}  
        }); 
	}
	
	private void changeRole(int role){
		RequestParams params = new RequestParams();
		String token = ma.getUser().getToken();
		params.put("token", token);
		params.put("id", ma.getUser().getId());
		params.put("role", role);
		String url = ma.getConstVariables().getIPAddress() + "/admin/role";
		AsyncHttpClient client = new AsyncHttpClient();  
        client.post(url, params, new AsyncHttpResponseHandler() {  
			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2,
					Throwable arg3) {
				// TODO Auto-generated method stub
        		Toast.makeText(Register3Activity.this, "用户权限修改失败!", 0).show();   
			}
			@Override
			public void onSuccess(int statusCode, Header[] arg1, byte[] responce) {
				// TODO Auto-generated method stub
        		Toast.makeText(Register3Activity.this, "用户权限修改成功!", 0).show();
        		Intent intent = new Intent();
				intent.setClass(Register3Activity.this, LoginActivity.class);
				startActivity(intent);
			}
        }); 
	}

    private OnClickListener onNavBarClick = new OnClickListener() {
        public void onClick(View v) {
            if (v == buttonBack) {
                finish();
            }
            else {
                buttonReg3Finish.performClick();
            }
        }
    };

	private OnClickListener clickListener = new OnClickListener(){
		public void onClick(View v){
			if(v == buttonReg3Upload){
				try {
					if(imageChoose == true)
						uploadPicture(imageFile.getAbsolutePath());
					else
		        		Toast.makeText(Register3Activity.this, "请选择图片", 0).show(); 
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(v == buttonReg3Finish){
				String realName = editTextRname.getText().toString();
				String stuID = editTextStudentID.getText().toString();
				String phone = editTextPhone.getText().toString();
				int gender = 2;
				if(radioButtonMale.isChecked())
					gender = 1;
				String school = spinnerSchool.getSelectedItem().toString();
				String grade = spinnerGrade.getSelectedItem().toString();
				String department = spinnerDepartment.getSelectedItem().toString();
				
				int schoolId = ma.getConstVariables().getSchoolId(school);
				int gradeId = ma.getConstVariables().getGradeId(grade);
								
				RequestParams params = new RequestParams();
				String token = ma.getUser().getToken();
				params.put("token", token);
				if(realName.length() > 0)
					params.put("realname", realName);
				if(phone.length() > 0)
					params.put("phone", phone);
				params.put("gender", gender);
				params.put("grade", gradeId);
				params.put("school", schoolId);
				if(department.length() > 0)
					params.put("department", department);
				if(stuID.length() > 0){
					params.put("stunum", stuID);
				}
				String url = ma.getConstVariables().getIPAddress() + "/user/my/manage";
				AsyncHttpClient client = new AsyncHttpClient();  
		        client.post(url, params, new AsyncHttpResponseHandler() {  
					@Override
					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
							Throwable arg3) {
						// TODO Auto-generated method stub
		        		Toast.makeText(Register3Activity.this, "信息修改失败!", 0).show();   
					}
					@Override
					public void onSuccess(int statusCode, Header[] arg1, byte[] responce) {
						// TODO Auto-generated method stub
		        		Toast.makeText(Register3Activity.this, "信息修改成功!", 0).show();
		        		if(editTextStudentID.getText().toString().length() > 0)
		        			changeRole(2);
		        		Intent intent = new Intent();
						intent.setClass(Register3Activity.this, LoginActivity.class);
						startActivity(intent);
					}
		        }); 
		
				
			}
			if(v == buttonReg3Portrait){
				Intent intent = new Intent();    
                /* 开启Pictures画面Type设定为image */
				intent.putExtra("crop", "true");// 才能出剪辑的小方框，不然没有剪辑功能，只能选取图片  
		        intent.putExtra("aspectX", 1); // 出现放大和缩小  
		        intent.putExtra("aspectY", 1);
		        intent.setAction(Intent.ACTION_GET_CONTENT); 
                intent.setType("image/*");    
                
                String path = "/sdcard/Uactivity/";
                imageFile = new File(path + "avatar.jpg");
                File dir = new File(path);
                if(!dir.exists()){
                	dir.mkdir();
                }  
                                
                intent.putExtra("output", Uri.fromFile(imageFile));  // 传入目标文件     
                intent.putExtra("outputFormat", "JPEG"); //输入文件格式    
                                
                
                Intent wrapperIntent = Intent.createChooser(intent, "选择图片"); //开始 并设置标题  
                startActivityForResult(wrapperIntent, 1); // 设返回 码为 1  onActivityResult 中的 reques

			}
		}
	};
	
	@Override  
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {  
        super.onActivityResult(requestCode, resultCode, data);  
        switch (requestCode) {  
	        case 1:
	        	if(imageFile == null)
	        		return;
//	        	System.out.println(imageFile.getAbsolutePath());
                Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
                ImageParser imageParser = new ImageParser();
                imageParser.compressBmpToFile(bitmap, imageFile);
                Picasso.with(getApplicationContext()).load(imageFile).resize(100, 100).centerCrop().into(imageViewPortrait);
	        	imageChoose = true;
        }
	}
      

}
