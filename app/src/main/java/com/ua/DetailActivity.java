package com.ua;

import java.text.ParseException;

import org.apache.http.Header;
import org.json.JSONException;


//import com.baidu.mapapi.SDKInitializer;
//import com.baidu.mapapi.map.BaiduMap;
//import com.baidu.mapapi.map.MapView;
//import com.baidu.mapapi.overlayutil.PoiOverlay;
//import com.baidu.mapapi.search.core.CityInfo;
//import com.baidu.mapapi.search.core.SearchResult;
//import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
//import com.baidu.mapapi.search.poi.PoiCitySearchOption;
//import com.baidu.mapapi.search.poi.PoiDetailResult;
//import com.baidu.mapapi.search.poi.PoiResult;
//import com.baidu.mapapi.search.poi.PoiSearch;
import com.ua.ua.R;
import com.ua.bean.ActivityBean;
import com.ua.parser.ActivityParser;
import com.ua.view.CustomImageView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;



public class DetailActivity extends Activity {


	private TextView textViewTitle;
	private TextView textViewStartTime;
	private ImageView imageViewAuthorHead;	
	
	private ActivityBean activityBean;
//	private MapView mMapView;
//	private PoiSearch poiSearch;
	private MyApplication ma;
	private TextView textViewDescription;
	private TextView textViewLimit;
	private TextView textViewAddress;
	private EditText etAddress;
    private CustomImageView detailImg;
	
	private String id;
    private String activityTitle;
    private String headUrl;
    private String imgUrl;
	
	private Button buttonComment;
	private Button buttonSave;
	private Button buttonJoin;
	int activityID;

    private TextView title;
    private Button buttonBack;
    private Button buttonConfirm;
    private Button buttonMap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);


//        SDKInitializer.initialize(getApplicationContext());getWindow().requestFeature(android.view.Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_detail);
        getWindow().setFeatureInt(android.view.Window.FEATURE_CUSTOM_TITLE, R.layout.titlebar);

//        title = (TextView)findViewById(R.id.title);
//        title.setText("活动详情");
//        buttonBack = (Button)findViewById(R.id.back);
//        buttonConfirm = (Button)findViewById(R.id.confirm);
//        buttonBack.setOnClickListener(onNavBarClick);
//        buttonConfirm.setOnClickListener(onNavBarClick);

		ma = (MyApplication)getApplication();
		etAddress = (EditText)findViewById(R.id.editTextDetailAddress);
		textViewAddress = (TextView)findViewById(R.id.textViewDetailAddress);
		textViewLimit = (TextView)findViewById(R.id.textViewDetailLimit);
		textViewTitle = (TextView)findViewById(R.id.textViewDetailTitle);
		textViewDescription = (TextView)findViewById(R.id.textViewDetailDescription);
		textViewStartTime = (TextView)findViewById(R.id.textViewDetailStartTime);
		imageViewAuthorHead = (ImageView)findViewById(R.id.imageViewDetailAuthor);
        detailImg = (CustomImageView)findViewById(R.id.activity_imageA);
		buttonComment = (Button) findViewById(R.id.buttonDetailComment);
		buttonJoin = (Button) findViewById(R.id.buttonDetailJoin);
		buttonSave = (Button) findViewById(R.id.buttonDetailSave);
		buttonComment.setOnClickListener(clickListener);
		buttonJoin.setOnClickListener(clickListener);
		buttonSave.setOnClickListener(clickListener);
        buttonMap = (Button)findViewById(R.id.buttonDetailMap);
		buttonMap.setOnClickListener(clickListener);


		Intent intent = getIntent();
		id = intent.getStringExtra("id");


        display();
//		poiSearch = PoiSearch.newInstance();
//		poiSearch.setOnGetPoiSearchResultListener(new OnGetPoiSearchResultListener(){
//
//			@Override
//			public void onGetPoiDetailResult(PoiDetailResult arg0) {
//				// TODO Auto-generated method stub
//
//			}
//
//			@Override
//			public void onGetPoiResult(PoiResult result) {
//				// TODO Auto-generated method stub
//				if (result == null
//						|| result.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {
//					Toast.makeText(DetailActivity.this, "未找到结果", Toast.LENGTH_LONG)
//					.show();
//                    textViewAddress.setText(textViewAddress.getText()+"(在地图上未能找到地点)");
//					return;
//				}
//				if (result.error == SearchResult.ERRORNO.NO_ERROR) {
//					BaiduMap map = mMapView.getMap();
//					map.clear();
//					PoiOverlay overlay = new PoiOverlay(map);
//					map.setOnMarkerClickListener(overlay);
//					overlay.setData(result);
//					overlay.addToMap();
//					overlay.zoomToSpan();
//					return;
//				}
//				if (result.error == SearchResult.ERRORNO.AMBIGUOUS_KEYWORD) {
//
//					// 当输入关键字在本市没有找到，但在其他城市找到时，返回包含该关键字信息的城市列表
//					String strInfo = "在";
//					for (CityInfo cityInfo : result.getSuggestCityList()) {
//						strInfo += cityInfo.city;
//						strInfo += ",";
//					}
//					strInfo += "找到结果";
//					Toast.makeText(DetailActivity.this, strInfo, Toast.LENGTH_LONG)
//							.show();
//				}
//
//			}
//
//		});
//		mMapView = (MapView)findViewById(R.id.bmapViewDetail);

	}
	
	private OnClickListener clickListener = new OnClickListener(){
		public void onClick(View v){
						if( v == buttonSave){
				String token = ma.getUser().getToken();
				RequestParams params = new RequestParams();
				AsyncHttpClient client = new AsyncHttpClient();  
				String url = ma.getConstVariables().getIPAddress() + "/collect/my/add";
				params.put("token", token);
		    	params.put("activity",id);
		        client.post(url, params, new AsyncHttpResponseHandler() {  
					@Override
					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
							Throwable arg3) {
						// TODO Auto-generated method stub
//		        		Toast.makeText(Register3Activity.this, "头像上传失败!", 0).show();
						if(arg2!=null) {
                            String jsonStream = new String(arg2);
                        }
		        		Toast.makeText(DetailActivity.this, "活动收藏失败!", Toast.LENGTH_SHORT).show();
					}
					@Override
					public void onSuccess(int statusCode, Header[] arg1, byte[] responce) {
						// TODO Auto-generated method stub
		        		Toast.makeText(DetailActivity.this, "活动收藏成功!", Toast.LENGTH_SHORT).show();
					}
		        });
				
			}
			if( v == buttonJoin){
				String token = ma.getUser().getToken();
				RequestParams params = new RequestParams();
				AsyncHttpClient client = new AsyncHttpClient();  
				String url = ma.getConstVariables().getIPAddress() + "/apply/action/add";
				params.put("token", token);
		    	params.put("activity",id);
		        client.post(url, params, new AsyncHttpResponseHandler() {  
					@Override
					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
							Throwable arg3) {
						// TODO Auto-generated method stub
//		        		Toast.makeText(Register3Activity.this, "头像上传失败!", 0).show();
						if(arg2!=null) {
                            String jsonStream = new String(arg2);
                        }
		        		Toast.makeText(DetailActivity.this, "参加活动失败!", Toast.LENGTH_SHORT).show();
					}
					@Override
					public void onSuccess(int statusCode, Header[] arg1, byte[] responce) {
						// TODO Auto-generated method stub
		        		Toast.makeText(DetailActivity.this, "参加活动成功!", Toast.LENGTH_SHORT).show();
					}
		        });
				
			}

			if( v == buttonMap){
				if(etAddress.getText().length() == 0)
					return;
//				poiSearch.searchInCity((new PoiCitySearchOption())
//						.city("北京")
//						.keyword(etAddress.getText().toString()).pageNum(0));
			}

			if(v == buttonComment){
				Intent intent = new Intent(DetailActivity.this,MessageActivity.class);
				intent.putExtra("id", id);
                intent.putExtra("title",activityTitle);
                intent.putExtra("headurl",headUrl);
                intent.putExtra("picurl",imgUrl);
				DetailActivity.this.startActivity(intent);
			}
		}
	};
	
	private void display(){
		RequestParams params = new RequestParams();
		AsyncHttpClient client = new AsyncHttpClient();  
		String url = ma.getConstVariables().getIPAddress() + "/activity/detail";
    	params.put("id",id);
        client.post(url, params, new AsyncHttpResponseHandler() {  
			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2,
					Throwable arg3) {
				// TODO Auto-generated method stub
//        		Toast.makeText(Register3Activity.this, "头像上传失败!", 0).show();
				if(arg2!=null) {
                    String jsonStream = new String(arg2);
                }
        		Toast.makeText(DetailActivity.this, "活动信息获取失败!", Toast.LENGTH_SHORT).show();
			}
			@Override
			public void onSuccess(int statusCode, Header[] arg1, byte[] responce) {
				// TODO Auto-generated method stub
        		String jsonString = new String(responce);
        		ActivityParser alp = new ActivityParser();
        		try {
					activityBean = alp.parseStream(jsonString);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        		if(activityBean == null){
            		Toast.makeText(DetailActivity.this,jsonString, Toast.LENGTH_SHORT).show();
            		return;
        		}
        		Toast.makeText(DetailActivity.this, "活动信息获取成功!", Toast.LENGTH_SHORT).show();
        		
        		textViewTitle.setText(activityBean.getTitle());
                activityTitle = activityBean.getTitle();

        		textViewAddress.setText( activityBean.getAddress());
        		textViewStartTime.setText("开始时间：" +activityBean.getStartTime());
        		String limit = "费用：" + activityBean.getFee() + "\n" + 
        					   "学校：" + ma.getConstVariables().getSchoolName(activityBean.getSchoolLimit()) + "\n" +
        					   "性别：" + ma.getConstVariables().getGenderName(activityBean.getGradeLimit()) + "\n" +
        					   "参加数量：" + activityBean.getApplyNum() + "\n" +
        					   "截止报名时间：" + activityBean.getApplyTime();
        		String description = activityBean.getDescription();
        		textViewDescription.setText(description);
        		textViewLimit.setText(limit);
        		etAddress.setText(activityBean.getAddress());
                imgUrl =  ma.getConstVariables().getIPAddress() + "/img/" + activityBean.getImg();
                //urlImage = ma.getConstVariables().getIPAddress() + "/img/" + activityBean.get
                if (detailImg != null)Picasso.with(getBaseContext()).load(imgUrl).placeholder(R.drawable.test).resize(400, 300).centerCrop().into((ImageView)detailImg);
                headUrl = ma.getConstVariables().getIPAddress() + "/img/" + activityBean.getHead();
                Picasso.with(getBaseContext()).load(headUrl).placeholder(R.drawable.icon_example).resize(100, 100).centerCrop().into(imageViewAuthorHead);

                buttonMap.performClick();

			}
        });

	}

    private OnClickListener onNavBarClick = new OnClickListener() {
        public void onClick(View v) {
            if (v == buttonBack) {

                setResult(RESULT_CANCELED);
                finish();
            }
            else {
                setResult(RESULT_CANCELED);
                finish();
            }
        }
    };
	
	@Override  
    protected void onDestroy() {  
        super.onDestroy();  
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理  
//        mMapView.onDestroy();
    }  
    @Override  
    protected void onResume() {  
        super.onResume();  
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理  
//        mMapView.onResume();
    }  
    @Override  
    protected void onPause() {  
        super.onPause();  
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理  
//        mMapView.onPause();
    }  
}
