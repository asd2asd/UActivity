package com.ua;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.Header;

import com.ua.ua.R;
import com.ua.bean.ActivityListBean;
import com.ua.parser.MyActivityListParser;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import com.ua.adapter.LazyListAdapter;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;



public class SettingActivity extends Activity {

	ListView activityMyListView;
	ImageView buttonSettingPerson;
	Button buttonMyCollectActivity;
    Button buttonMyApplyActivity;
    private TextView title;
    private Button buttonBack;
    private Button buttonConfirm;
	ArrayList<HashMap<String, String>> mListData;
	MyApplication ma;
	ArrayList<ActivityListBean> activityBeanList;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
        getWindow().requestFeature(android.view.Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_setting);
        getWindow().setFeatureInt(android.view.Window.FEATURE_CUSTOM_TITLE, R.layout.titlebar);

		activityMyListView = (ListView) findViewById(R.id.activityMyListView);
		buttonSettingPerson = (ImageView) findViewById(R.id.buttonSettingPerson);
		buttonMyCollectActivity = (Button)findViewById(R.id.buttonMyCollectActivity);
        buttonMyApplyActivity = (Button)findViewById(R.id.buttonMyApplyActivity);


		ma = (MyApplication)this.getApplication();
        title = (TextView)findViewById(R.id.title);
        title.setText("个人中心");
        buttonBack = (Button)findViewById(R.id.back);
        buttonConfirm = (Button)findViewById(R.id.confirm);
        buttonBack.setOnClickListener(onNavBarClick);
        buttonConfirm.setOnClickListener(onNavBarClick);

        buttonSettingPerson.setOnClickListener(clickListener);
		buttonMyCollectActivity.setOnClickListener(clickListener);
        buttonMyApplyActivity.setOnClickListener(clickListener);
		activityBeanList = new ArrayList<ActivityListBean>();
		mListData = new ArrayList<HashMap<String, String>>();
	    activityMyListView.setOnItemClickListener(new OnItemClickListener(){
			@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				// TODO Auto-generated method stub
				HashMap<String, String> map = mListData.get(position);
				String activityID = map.get("id");
				Intent intent = new Intent();
				intent.setClass(SettingActivity.this, DetailActivity.class);
				intent.putExtra("id", "" + activityID);
				startActivity(intent);
			}
			
		});

	}
	
	private OnClickListener clickListener = new OnClickListener(){
		public void onClick(View v){
			// if button Register is clicked, skip to register1Activity
			if(v == buttonSettingPerson){
//				System.out.println(v.getId() + ":" + buttonSettingMyActivity.getId());

	 			Intent register3Intent = new Intent(SettingActivity.this,Register3Activity.class);
	 			SettingActivity.this.startActivity(register3Intent);
			}

			else if(v == buttonMyCollectActivity){
				System.out.println("print here");
				collectMyChoose();
			}
            else if(v == buttonMyApplyActivity){
                System.out.println("print here");
                applyMyChoose();
            }
            System.out.println(v.getId() + ":" + buttonMyCollectActivity.getId());
		}
		
	};
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
	private void applyMyChoose(){
		myChoose("/apply/action/list");
	}
	private void collectMyChoose(){
		myChoose("/collect/my/list");
	}

	private void myChoose(String itemURL){
        if(mListData!=null)
            mListData.clear();
        if(activityBeanList!=null)
            activityBeanList.clear();
		RequestParams params = new RequestParams();
		AsyncHttpClient client = new AsyncHttpClient();  
		String url = ma.getConstVariables().getIPAddress() + itemURL;
		params.put("token", ma.getUser().getToken());
    	params.put("offset","0");
    	params.put("limit","10");
        client.post(url, params, new AsyncHttpResponseHandler() {  
			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2,
					Throwable arg3) {
				// TODO Auto-generated method stub
//        		Toast.makeText(Register3Activity.this, "头像上传失败!", 0).show();
                if(arg2!=null) {
                    String jsonStream = new String(arg2);
                    System.out.println(jsonStream);
                }
        		Toast.makeText(SettingActivity.this, "活动列表获取失败!", Toast.LENGTH_SHORT).show();
			}
			@Override
			public void onSuccess(int statusCode, Header[] arg1, byte[] responce) {
				// TODO Auto-generated method stub
        		String jsonString = new String(responce);
        		MyActivityListParser alp = new MyActivityListParser();
        		activityBeanList = alp.parseStream(jsonString);
        		if(activityBeanList == null){
            		Toast.makeText(SettingActivity.this,jsonString, Toast.LENGTH_SHORT).show();
            		return;
        		}
        		Toast.makeText(SettingActivity.this, "活动列表获取成功!", Toast.LENGTH_SHORT).show();
        		for (ActivityListBean abl : activityBeanList){
        			System.out.println(abl.getTitle());
        			HashMap<String, String> map = new HashMap<String, String>();
        			map.put("id", "" + abl.getId());
        			map.put("title", abl.getTitle());
        			System.out.println(abl.getTitle());
        			map.put("description", abl.getDescription());
        			System.out.println(abl.getApplyNum());
        			map.put("time", abl.getActTime());
        			map.put("image", ma.getConstVariables().getIPAddress() + "/img/" + abl.getHead());
        			map.put("imageActivity", ma.getConstVariables().getIPAddress() + "/img/" + abl.getImage());
        			mListData.add(map);

        		}
                LazyListAdapter la = new LazyListAdapter(SettingActivity.this, mListData, getBaseContext(), R.layout.image_list_item,false);
                activityMyListView.setAdapter(la);
			}
        });
	}
	
}
