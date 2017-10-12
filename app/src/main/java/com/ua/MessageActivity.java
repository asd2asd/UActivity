package com.ua;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.Header;

import com.ua.R;
import com.ua.bean.MessageBean;
import com.ua.parser.MessageParser;
import com.ua.view.CustomImageView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;

import com.ua.adapter.LazyAdapter;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MessageActivity extends Activity {
	private ListView lvMessage;
	private EditText etMessage;
	private Button btMessage;
	private MyApplication ma;
	private String id;
    private String activteTitle;
	private ArrayList<HashMap<String, String>> mListData;

	private ArrayList<MessageBean> messageList;

    private TextView title;
    private Button buttonBack;
    private Button buttonConfirm;
    private TextView actTitle;
    private ImageView detailImage;
    private String picUrl;
    private String headUrl;
    private CustomImageView activityCustomImage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(android.view.Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_message);
        getWindow().setFeatureInt(android.view.Window.FEATURE_CUSTOM_TITLE, R.layout.titlebar);

        title = (TextView)findViewById(R.id.title);
        title.setText("活动留言");
        buttonBack = (Button)findViewById(R.id.back);
        buttonConfirm = (Button)findViewById(R.id.confirm);
        buttonBack.setOnClickListener(onNavBarClick);
        buttonConfirm.setOnClickListener(onNavBarClick);


		lvMessage = (ListView) findViewById(R.id.listViewMessage);
		etMessage = (EditText)findViewById(R.id.EditTextMessageGive);
		btMessage = (Button)findViewById(R.id.buttonMessageOK);
        actTitle = (TextView)findViewById(R.id.activity_message_title);
        detailImage = (ImageView)findViewById(R.id.detailImageView);
        activityCustomImage = (CustomImageView)findViewById(R.id.activity_custom_image);
		ma = (MyApplication)getApplication();
		Intent intent = getIntent();
		id = intent.getStringExtra("id");
        activteTitle = intent.getStringExtra("title");
        picUrl = intent.getStringExtra("picurl");
        headUrl = intent.getStringExtra("headurl");
		mListData = new ArrayList<HashMap<String, String>>();
		
		btMessage.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				publish();
			}
			
		});
		newChoose();
	}
	private void publish(){
		if(etMessage.getText().length() == 0){
    		Toast.makeText(MessageActivity.this, "留言不能为空!", 0).show();   			
    		return;
		}
		RequestParams params = new RequestParams();
		AsyncHttpClient client = new AsyncHttpClient();  
		String url = ma.getConstVariables().getIPAddress() + "/msg/publish";
    	params.put("token",ma.getUser().getToken());
	    params.put("actid",id);
	    params.put("content", etMessage.getText());
	    client.post(url, params, new AsyncHttpResponseHandler() {  
	    	@Override
	    	public void onFailure(int arg0, Header[] arg1, byte[] arg2,
				Throwable arg3) {
				// TODO Auto-generated method stub
//	       		Toast.makeText(Register3Activity.this, "头像上传失败!", 0).show();
				if(arg2!=null) {
                    String jsonStream = new String(arg2);
                    System.out.println(jsonStream);
                }
        		Toast.makeText(MessageActivity.this, "留言发表失败!", 0).show();   
			}
			@Override
			public void onSuccess(int statusCode, Header[] arg1, byte[] responce) {
				// TODO Auto-generated method stub
	        	Toast.makeText(MessageActivity.this, "留言发表成功!", 0).show();
                refresh();
			}
		});
		
	}

    private OnClickListener onNavBarClick = new OnClickListener() {
        public void onClick(View v) {
            if (v == buttonBack) {
                finish();
            }
            else {
                btMessage.performClick();
            }
        }
    };

    void refresh()
    {
        newChoose();
        InputMethodManager imm = (InputMethodManager)getSystemService(MessageActivity.this.INPUT_METHOD_SERVICE);
        etMessage.setText(null);
        //显示键盘
//        imm.showSoftInput(etMessage, 0);
        //隐藏键盘
        imm.hideSoftInputFromWindow(etMessage.getWindowToken(), 0);



    }

	private void newChoose(){
        mListData.clear();
		RequestParams params = new RequestParams();
		AsyncHttpClient client = new AsyncHttpClient();
        if(activteTitle!=null)
            actTitle.setText(activteTitle);
        if((headUrl!=null)&&(activityCustomImage!=null)) {
            Picasso.with(getBaseContext()).load(headUrl).placeholder(R.drawable.icon_example).resize(100, 100).centerCrop().into(activityCustomImage);

        }
        if((picUrl!=null)&&(detailImage!=null))
        {
            Picasso.with(getBaseContext()).load(picUrl).placeholder(R.drawable.test).resize(400, 300).centerCrop().into(detailImage);
        }



		String url = ma.getConstVariables().getIPAddress() + "/msg/list";

        params.put("token",ma.getUser().getToken());
	    params.put("actid",id);
	    params.put("offset","0");
	    params.put("limit","10");
		Toast.makeText(MessageActivity.this, id + " " + ma.getUser().getToken(), 0).show();   
	    client.post(url, params, new AsyncHttpResponseHandler() {  
	    	@Override
	    	public void onFailure(int arg0, Header[] arg1, byte[] arg2,
				Throwable arg3) {
				// TODO Auto-generated method stub
//	       		Toast.makeText(Register3Activity.this, "头像上传失败!", 0).show();
				if(arg2!=null) {
                    String jsonStream = new String(arg2);
                    System.out.println(jsonStream);
                }
        		Toast.makeText(MessageActivity.this, "留言列表获取失败!", 0).show();   
			}
			@Override
			public void onSuccess(int statusCode, Header[] arg1, byte[] responce) {
				// TODO Auto-generated method stub
	       		String jsonString = new String(responce);
	       		MessageParser alp = new MessageParser();
	       		messageList = alp.parseStream(jsonString);
	       		if(messageList == null){
            		Toast.makeText(MessageActivity.this,jsonString, 0).show(); 
	            	return;
	        	}
	        	Toast.makeText(MessageActivity.this, "留言列表获取成功!", 0).show(); 
	       		for (MessageBean ml : messageList){
	       			HashMap<String, String> map = new HashMap<String, String>();
	       			map.put("id", "" + ml.getId());
	       			map.put("title", ml.getName());
	       			map.put("description", ml.getContent());
	       			map.put("time", ml.getTime());
	       			map.put("image", ma.getConstVariables().getIPAddress() + "/img/" + ml.getHead());
	       			mListData.add(map);
        			LazyAdapter la = new LazyAdapter(MessageActivity.this, mListData, getBaseContext(), R.layout.image_list_item_message);
	        		lvMessage.setAdapter(la);
	        	}
			}
		});

	}
}
