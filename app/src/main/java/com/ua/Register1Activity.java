package com.ua;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Timer;

import com.ua.R;
import com.ua.parser.Register1Parser;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Register1Activity extends Activity {
	private EditText editTextEmail, editTextPwdReg1, editTextPwdConfirm, editTextVname;
	private Button buttonReg1Next;
	private ProgressDialog pd;
	private Timer timerWait;
	private MyApplication ma;
	
	private boolean emailValidated;
	private boolean passwordValidated;
	private boolean nicknameValidated;
    private boolean[] isRunning;

    private TextView title;
    private Button buttonBack;
    private Button buttonConfirm;


    public class TimerTaskWait extends java.util.TimerTask{  
        
    	@Override  
    	public void run() {  
    		// TODO Auto-generated method stub  
    		if(pd.isShowing() == true){
    			Message msg = new Message();
				msg.obj = "time-error";
				handler.sendMessage(msg);
    		}
    	}  
    }
    
	@Override
	public void onCreate(Bundle savedInstanceState) {




		emailValidated = false;
		passwordValidated = false;
		nicknameValidated = false;
        isRunning = new boolean[4];
        for(int i =0;i<4;i++)
            isRunning[i] =false;
		timerWait = new Timer();
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(android.view.Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.register1);
        getWindow().setFeatureInt(android.view.Window.FEATURE_CUSTOM_TITLE, R.layout.titlebar);

        title = (TextView)findViewById(R.id.title);
        title.setText("用户注册");
        buttonBack = (Button)findViewById(R.id.back);
        buttonConfirm = (Button)findViewById(R.id.confirm);
        buttonBack.setOnClickListener(onNavBarClick);
        buttonConfirm.setOnClickListener(onNavBarClick);


	    ma = (MyApplication)getApplication();
	    editTextEmail = (EditText) findViewById(R.id.editTextEmail);
		editTextPwdReg1 = (EditText) findViewById(R.id.editTextPwdReg1);
		editTextPwdConfirm = (EditText) findViewById(R.id.editTextPwdConfirm);
		editTextVname = (EditText) findViewById(R.id.editTextVname);
		buttonReg1Next = (Button) findViewById(R.id.buttonReg1Next);
		editTextPwdConfirm.setOnFocusChangeListener(new OnFocusChangeListener(){

			@Override
			public void onFocusChange(View v, boolean arg1) {
				// TODO Auto-generated method stub
				if(!v.hasFocus()){
					String pwdConfirm = editTextPwdConfirm.getText().toString();
					String pwd = editTextPwdReg1.getText().toString();

					Register1Parser rp = new Register1Parser();
					String result = rp.parserPasswordBean(pwd, pwdConfirm);
					if(result == "null-error"){
						Toast.makeText(Register1Activity.this, "密码不能为空", Toast.LENGTH_LONG).show();
			 			passwordValidated = false;
			 			return;
					}
					if(result == "diff-error"){
						Toast.makeText(Register1Activity.this, "密码确认与原密码不一致，请重新输入", Toast.LENGTH_LONG).show();
			 			passwordValidated = false;
			 			return;
					}
					if(result == "true"){
			 			passwordValidated = true;
			 			return;
					}
				}
			}
			
		});
		editTextEmail.setOnFocusChangeListener(new OnFocusChangeListener(){

			@Override
			public void onFocusChange(View v, boolean arg1) {
				// TODO Auto-generated method stub
				if (!v.hasFocus()) {

			    	  pd.show();
			    	  Thread thread = new Thread(emailRunnable);
			    	  thread.start();
			    }
			}
			
		});
		editTextVname.setOnFocusChangeListener(new OnFocusChangeListener(){

			@Override
			public void onFocusChange(View v, boolean arg1) {
				// TODO Auto-generated method stub
			      if (!v.hasFocus()) {
			    	  if(editTextVname.getText().toString().matches("")){
							Toast.makeText(Register1Activity.this, "昵称不能为空", Toast.LENGTH_LONG).show();
							return;
			    	  }
			    	  pd.show();
			    	  Thread thread = new Thread(runnable);
			    	  thread.start();
			      }
			}
			
		});
		
		pd = new ProgressDialog(this);
		
		buttonReg1Next.setOnClickListener(clickListener);
		
		editTextEmail.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
		editTextVname.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
	}

    private OnClickListener onNavBarClick = new OnClickListener() {
        public void onClick(View v) {
            if (v == buttonBack) {
                finish();
            }
            else {
                buttonReg1Next.performClick();
            }
        }
    };
	
	private OnClickListener clickListener = new OnClickListener(){
		public void onClick(View v){
			if(v == buttonReg1Next ){
                editTextEmail.setSelected(false);
                editTextEmail.clearFocus();
                editTextVname.setSelected(false);
                editTextVname.clearFocus();
                editTextPwdConfirm.setSelected(false);
                editTextPwdConfirm.clearFocus();
                //editTextPwdReg1.requestFocus();
                while(isRunning[0]||isRunning[3]){};
				if( emailValidated == true && passwordValidated == true && nicknameValidated == true){
		 			Intent register1Intent = new Intent(Register1Activity.this,Register2Activity.class);
		 			register1Intent.putExtra("Email", editTextEmail.getText().toString());
		 			register1Intent.putExtra("Password", editTextPwdReg1.getText().toString());
		 			register1Intent.putExtra("Vname", editTextVname.getText().toString());
		 			Register1Activity.this.startActivity(register1Intent);
				}
				else{
					Toast.makeText(Register1Activity.this, "填写信息有误，请重新填写", Toast.LENGTH_LONG).show();

				}
			}
			
		}
	};
	Runnable emailRunnable = new Runnable(){
		public void run(){
			try {
                isRunning[0] = true;
				timerWait.schedule(new TimerTaskWait(), 5000);
				URL url = new URL(ma.getConstVariables().getIPAddress() + "/mailcheck");
				HttpURLConnection htc = (HttpURLConnection) url.openConnection();
				htc.setConnectTimeout(5000);
				htc.setRequestMethod("POST");
				htc.setDoInput(true);
				htc.setDoOutput(true);
				htc.setReadTimeout(5000);
				OutputStream out = htc.getOutputStream();
				if(out == null){
					htc.disconnect();
                    isRunning[0] = false;
					return;
				}
				StringBuilder sb = new StringBuilder();
				sb.append("mail=" + editTextEmail.getText().toString() + "");
				byte userXml[] = sb.toString().getBytes();
				out.write(userXml);
				
				InputStream in = htc.getInputStream();
				Register1Parser lp = new Register1Parser();
				String result = lp.parserEmailBean(in);
				htc.disconnect();
				Message msg = new Message();
				msg.obj = result;
				emailHandler.sendMessage(msg);
                isRunning[0] = false;

			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	};
	
	String emailResult;
	Handler emailHandler = new Handler(){
		public void handleMessage(Message msg){
			String re = msg.obj + "";
			if(re.equals("time-error")){
				pd.dismiss();
	 			Toast.makeText(Register1Activity.this, "请求失败,请检查网络情况", Toast.LENGTH_LONG).show();
	 			emailValidated = false;
	 			//return;
			}
			if(re.equals("duplicate")){
				pd.dismiss();
	 			Toast.makeText(Register1Activity.this, "邮箱已被使用,请重新输入昵称", Toast.LENGTH_LONG).show();
	 			emailValidated = false;
	 			//return;
			}
			if(re.equals("format-error")){
				pd.dismiss();
				Toast.makeText(Register1Activity.this, "邮箱格式错误，请重新输入邮箱", Toast.LENGTH_LONG).show();
				emailValidated = false;
			}
			if(re.equals("parameter-error")){
				pd.dismiss();
				Toast.makeText(Register1Activity.this, "邮箱参数错误，请重新输入邮箱", Toast.LENGTH_LONG).show();
				emailValidated = false;
			}
			if(re.equals("true")){
				pd.dismiss();
				Toast.makeText(Register1Activity.this, "邮箱可用", Toast.LENGTH_LONG).show();
				emailValidated = true;
			}
		};
	};
	
	Runnable runnable = new Runnable(){
		public void run(){
			try {
                isRunning[3] = true;
				timerWait.schedule(new TimerTaskWait(), 5000);
				URL url = new URL(ma.getConstVariables().getIPAddress() + "/nickcheck");
				HttpURLConnection htc = (HttpURLConnection) url.openConnection();
				htc.setConnectTimeout(5000);
				htc.setRequestMethod("POST");
				htc.setDoInput(true);
				htc.setDoOutput(true);
				htc.setReadTimeout(5000);
				OutputStream out = htc.getOutputStream();
				if(out == null){
					htc.disconnect();
                    isRunning[3] = false;
					return;
				}
				StringBuilder sb = new StringBuilder();
				sb.append("nickname=" + editTextVname.getText().toString() + "");
				byte userXml[] = sb.toString().getBytes();
				out.write(userXml);
				
				InputStream in = htc.getInputStream();
				Register1Parser lp = new Register1Parser();
				String result = lp.parserNicknameBean(in);
				htc.disconnect();
				Message msg = new Message();
				msg.obj = result;
				handler.sendMessage(msg);
                isRunning[3] = false;

			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	};
	
	String result;
	Handler handler = new Handler(){
		public void handleMessage(Message msg){
			String re = msg.obj + "";
			if(re.equals("time-error")){
				pd.dismiss();
	 			Toast.makeText(Register1Activity.this, "请求失败,请检查网络情况", Toast.LENGTH_LONG).show();
	 			nicknameValidated = false;
	 			//return;
			}
			if(re.equals("duplicate")){
				pd.dismiss();
	 			Toast.makeText(Register1Activity.this, "昵称已被使用,请重新输入昵称", Toast.LENGTH_LONG).show();
	 			nicknameValidated = false;
	 			//return;
			}
			if(re.equals("format-error")){
				pd.dismiss();
				Toast.makeText(Register1Activity.this, "昵称格式错误，请重新输入昵称", Toast.LENGTH_LONG).show();
				nicknameValidated = false;
			}
			if(re.equals("true")){
				pd.dismiss();
				Toast.makeText(Register1Activity.this, "昵称可用", Toast.LENGTH_LONG).show();
				nicknameValidated = true;
			}
		};
	};

}
