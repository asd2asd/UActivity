package com.ua;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Timer;

import org.apache.http.Header;
import org.json.JSONObject;

import com.ua.R;
import com.ua.handler.LoginHandler;
import com.ua.parser.LoginParser;
import com.ua.snow.SnowSurfaceView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {

    private EditText editTextUser, editTextPassword;
    private Button buttonLogin, buttonRegister;
    private CheckBox checkBoxPwdSaved;
    private CheckBox checkBoxAutoLogin;
    private LoginHandler loginHandler;
    private String name;
    private String password;
    private SharedPreferences sp;
    private ProgressDialog pd;
    private Timer timer;
    private MyApplication ma;
    SnowSurfaceView snowSurfaceView;

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
    public void onResume()
    {
        super.onResume();
        //if(snowSurfaceView==null)
        {
            //snowSurfaceView = new SnowSurfaceView(this);
            snowSurfaceView = (SnowSurfaceView)findViewById(R.id.snowSurfaceView);
        }
    }
    @Override
    public void onPause()
    {
        super.onPause();
        //snowSurfaceView.surfaceDestroyed(snowSurfaceView.getHolder());
        //snowSurfaceView = null;
    }


    @Override
    public void onCreate(Bundle savedInstanceState){
        timer = new Timer();
        // eliminate title
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);





        // get objects based on ids
        ma = (MyApplication)getApplication();
        sp = this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        pd = new ProgressDialog(this);
        editTextUser = (EditText) findViewById(R.id.editTextUser);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        checkBoxPwdSaved = (CheckBox) findViewById(R.id.checkBoxPwdSaved);
        checkBoxAutoLogin = (CheckBox) findViewById(R.id.checkBoxAutoLogin);
        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        buttonRegister = (Button) findViewById(R.id.buttonRegister);



        //设置默认是记录密码状态
        if(sp.getBoolean("ISCHECK", false)){
            checkBoxPwdSaved.setChecked(true);
            editTextUser.setText(sp.getString("USER_NAME", ""));
            editTextPassword.setText(sp.getString("PASSWORD",""));
        }
        //判断自动登录多选框状态
        if(sp.getBoolean("AUTO_ISCHECK", false)){
            //设置默认是自动登录状态
            checkBoxAutoLogin.setChecked(true);
            name = editTextUser.getText().toString();
            password = editTextPassword.getText().toString();

            // 如果没有记住用户名和密码，则启动一个新的ProgressDialog,首先从数据库中选取信息
            pd.show();
            // 启动线程从后台数据库中获取信息进行验证
            Thread  thread = new Thread(runnable);
            thread.start();
            //跳转界面
// 			Toast.makeText(LoginActivity.this, "欢迎进入U活动", Toast.LENGTH_LONG).show();
//
//			Intent intent = new Intent(LoginActivity.this,ActivityListActivity.class);
//			LoginActivity.this.startActivity(intent);
        }


        loginHandler = new LoginHandler();
        buttonLogin.setOnClickListener(clickListener);
        buttonRegister.setOnClickListener(clickListener);

        //监听记住密码多选框按钮事件  
        checkBoxPwdSaved.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                if (checkBoxPwdSaved.isChecked()) {
                    sp.edit().putBoolean("ISCHECK", true).commit();

                }else {
                    sp.edit().putBoolean("ISCHECK", false).commit();

                }

            }
        });

        //监听自动登录多选框事件  
        checkBoxAutoLogin.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                if (checkBoxAutoLogin.isChecked()) {
                    sp.edit().putBoolean("AUTO_ISCHECK", true).commit();

                } else {
                    sp.edit().putBoolean("AUTO_ISCHECK", false).commit();
                }
            }
        });

        snowSurfaceView = new SnowSurfaceView(this);
        snowSurfaceView = (SnowSurfaceView)findViewById(R.id.snowSurfaceView);



    }


    private OnClickListener clickListener = new OnClickListener(){
        public void onClick(View v){
            // if button Register is clicked, skip to register1Activity
            if(v == buttonRegister){
                Intent loginIntent = new Intent(LoginActivity.this,Register1Activity.class);
                LoginActivity.this.startActivity(loginIntent);
            }
            // if buttonLogin is clicked, set to database and start the thread
            if(v == buttonLogin ){
                name = editTextUser.getText().toString();
                password = editTextPassword.getText().toString();

//                // 如果没有记住用户名和密码，则启动一个新的ProgressDialog,首先从数据库中选取信息
//                pd.show();
//                // 启动线程从后台数据库中获取信息进行验证
//                Thread  thread = new Thread(runnable);
//                thread.start();

                RequestParams params = new RequestParams();
                AsyncHttpClient client = new AsyncHttpClient();
                String url = ma.getConstVariables().getIPAddress() + "/login";
                params.put("mail",name);
                params.put("pwd",password);
                client.get(LoginActivity.this, url, params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int i, Header[] headers, byte[] bytes) {
                        InputStream in = new ByteArrayInputStream(bytes);
                        LoginParser lp = new LoginParser();
                        String result = null;
                        try {
                            result = lp.parserLoginBean(in);
                            if(result == "true"){
                                MyApplication ma = (MyApplication) getApplication();
                                ma.setUser(lp.getUserBean());
                            }
                            Message msg = new Message();
                            msg.obj = result;
                            handler.sendMessage(msg);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

                        Toast.makeText(LoginActivity.this, "login失败!", 0).show();
                    }
                });
            }
        }
    };

    public void request(){
        name = editTextUser.getText().toString();
        password = editTextPassword.getText().toString();

        // 如果没有记住用户名和密码，则启动一个新的ProgressDialog,首先从数据库中选取信息
        pd.show();
        // 启动线程从后台数据库中获取信息进行验证
        Thread  thread = new Thread(runnable);
        thread.start();
    }

    public boolean checkRole(){
        MyApplication ma = (MyApplication) getApplication();
        if(ma.getUser().getRole() == 1)
            return false;

        return true;
    }



    Runnable runnable = new Runnable(){
        public void run(){
            try {
                timer.schedule(new TimerTaskTest(), 5000);
                URL url = new URL(ma.getConstVariables().getIPAddress() + "/login");
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
                JSONObject myJsonObject = new JSONObject();
                sb.append("mail=" + editTextUser.getText().toString() + "&" + "pwd=" + editTextPassword.getText().toString());
                byte userXml[] = sb.toString().getBytes();
                out.write(userXml);

                InputStream in = htc.getInputStream();
                LoginParser lp = new LoginParser();
                String result = lp.parserLoginBean(in);
                if(result == "true"){
                    MyApplication ma = (MyApplication) getApplication();
                    ma.setUser(lp.getUserBean());
                }
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
                Toast.makeText(LoginActivity.this, "登录失败,请检查网络情况", Toast.LENGTH_LONG).show();
                return;
            }
            if(!re.equals("true")){
                Editor editor = sp.edit();
                editor.clear();
                pd.dismiss();
                Toast.makeText(LoginActivity.this, "登录失败,请检查用户名密码是否正确", Toast.LENGTH_LONG).show();
                return;
            }
            if(re.equals("true")){
                pd.dismiss();
                result = re;
                // 登陆成功和记住密码框为选中状态才保存信息
                if(checkBoxPwdSaved.isChecked()){
                    //记住用户名、密码
                    Editor editor = sp.edit();
                    editor.remove("USER_NAME");
                    editor.remove("PASSWORD");
                    editor.putString("USER_NAME", name);
                    editor.putString("PASSWORD", password);
                    editor.putBoolean("ISCHECK", true);
                    editor.commit();
                }
                if(!checkRole()){
                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                    View view = View.inflate(LoginActivity.this, R.layout.hint_user_info, null);
                    final Button buttonYES = (Button) view.findViewById(R.id.buttonHintYES);
                    final Button buttonNO = (Button) view.findViewById(R.id.buttonHintNO);
                    builder.setView(view);
                    buttonYES.setOnClickListener(new OnClickListener(){
                        @Override
                        public void onClick(View arg0) {
                            Intent intent = new Intent(LoginActivity.this, Register3Activity.class);
                            LoginActivity.this.startActivity(intent);
                        }});
                    buttonNO.setOnClickListener(new OnClickListener(){

                        @Override
                        public void onClick(View arg0) {
                            Intent intent = new Intent(LoginActivity.this, ActivityListActivity.class);
                            LoginActivity.this.startActivity(intent);
                        }

                    });
                    Dialog dialog = builder.create();
                    dialog.show();

                }
                else{
                    Toast.makeText(LoginActivity.this, "欢迎进入", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    LoginActivity.this.startActivity(intent);
                    //finish();
                }

            }
        };
    };
}
