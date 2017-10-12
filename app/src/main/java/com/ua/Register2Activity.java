package com.ua;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Timer;

import com.ua.R;
import com.ua.parser.Register2Parser;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

public class Register2Activity extends Activity {
	private Button buttonReg2Finish;
	public ArrayList<CheckBox> cbList;
	private Timer timer;
	private ProgressDialog pd;
	private String mail;
	private String pwd;
	private String vName;
	private String interests;
	private MyApplication ma;

    private TextView title;
    private Button buttonBack;
    private Button buttonConfirm;
	
    public class TimerTaskTest extends java.util.TimerTask{  
        
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
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(android.view.Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.register2);
        getWindow().setFeatureInt(android.view.Window.FEATURE_CUSTOM_TITLE, R.layout.titlebar);

        title = (TextView)findViewById(R.id.title);
        title.setText("选择兴趣爱好");
        buttonBack = (Button)findViewById(R.id.back);
        buttonConfirm = (Button)findViewById(R.id.confirm);
        buttonBack.setOnClickListener(onNavBarClick);
        buttonConfirm.setOnClickListener(onNavBarClick);
        buttonBack.setVisibility(View.INVISIBLE);

		timer = new Timer();
		pd = new ProgressDialog(this);
		
		cbList = new ArrayList<CheckBox>();

	    ma = (MyApplication)getApplication();
	    buttonReg2Finish = (Button) findViewById(R.id.buttonReg2Finish);
	    cbList.add((CheckBox) findViewById(R.id.checkBoxRead));
	    cbList.add((CheckBox) findViewById(R.id.checkBoxTour));
	    cbList.add((CheckBox) findViewById(R.id.checkBoxSports));
	    cbList.add((CheckBox) findViewById(R.id.checkBoxArt));
	    cbList.add((CheckBox) findViewById(R.id.checkBoxFood));
	    cbList.add((CheckBox) findViewById(R.id.checkBoxShopping));
	    
	    buttonReg2Finish.setOnClickListener(clickListener);
	}
	
	private String getCheckBoxString(){
		String result = "";
		for(int i = 0;  i < cbList.size(); i ++){
			if(cbList.get(i).isChecked() == true){
				result += "," + (i + 1);
			}
		}
		if(result.length() > 0){
			result = result.substring(1, result.length());
		}
		
		return result;
	}
	
	private OnClickListener clickListener = new OnClickListener(){
		public void onClick(View v){

			if(v == buttonReg2Finish){
				Intent intent = getIntent();
				mail = intent.getStringExtra("Email");
				pwd = intent.getStringExtra("Password");
				vName = intent.getStringExtra("Vname");
				interests = getCheckBoxString();
				pd.show();
				// 启动线程从后台数据库中获取信息进行验证
				Thread  thread = new Thread(runnable);
				thread.start();
				
				/****************
				 * debug
				 ****************/
	 			Intent register2Intent = new Intent(Register2Activity.this,LoginActivity.class);
	 			Register2Activity.this.startActivity(register2Intent);
			}
		}
	};

    private OnClickListener onNavBarClick = new OnClickListener() {
        public void onClick(View v) {
            if (v == buttonBack) {
                //finish();
            }
            else {
                buttonReg2Finish.performClick();
            }
        }
    };
	
	Runnable runnable = new Runnable(){
		public void run(){
			try {
				timer.schedule(new TimerTaskTest(), 5000);
				URL url = new URL(ma.getConstVariables().getIPAddress() + "/reg");
				HttpURLConnection htc = (HttpURLConnection) url.openConnection();
				htc.setConnectTimeout(5000);
				htc.setRequestMethod("POST");
				htc.setDoInput(true);
				htc.setDoOutput(true);
				htc.setReadTimeout(5000);
				OutputStream out = htc.getOutputStream();
				if(out == null){
					htc.disconnect();
					return;
				}
				StringBuilder sb = new StringBuilder();
				sb.append("mail=" + mail + "&");
				sb.append("password=" + pwd + "&");
				sb.append("nickname=" + vName + "&");
				sb.append("interest=" + interests);
				byte userXml[] = sb.toString().getBytes();
				out.write(userXml);
				InputStream in = htc.getInputStream();
				Register2Parser rp = new Register2Parser();
				String result = rp.parserRegister2Bean(in);
				htc.disconnect();
				Message msg = new Message();
				msg.obj = result;
				handler.sendMessage(msg);

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
	 			Toast.makeText(Register2Activity.this, "登录失败,请检查网络情况", Toast.LENGTH_LONG).show();
	 			return;
			}
			if(re.equals("parameter-error")){
				pd.dismiss();
	 			Toast.makeText(Register2Activity.this, "参数失败", Toast.LENGTH_LONG).show();
	 			return;
			}
			if(re.equals("register-error")){
				pd.dismiss();
	 			Toast.makeText(Register2Activity.this, "注册失败", Toast.LENGTH_LONG).show();
	 			return;
			}
			if(re.equals("true")){
				pd.dismiss();
				result = re;
	 			Toast.makeText(Register2Activity.this, "注册成功", Toast.LENGTH_LONG).show();
			}
		};
	};
}
