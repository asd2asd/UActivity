package com.ua;

/**
 * Created by justs_000 on 2014/12/29.
 */


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.ua.R;
import com.ua.bean.ActivityListBean;
import com.ua.parser.ActivityListParser;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
//import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;

import com.ua.adapter.LazyListAdapter;

public class ActivityTileGridFragment extends Fragment {
    GridView activityGridView;

    LazyListAdapter lazyAdapter;
    View mView;
    ArrayList<HashMap<String, String>> mListData;
    private boolean mBusy;

    //private TestFragment frag;
    int layoutID;
    private ProgressDialog pd;

    MyApplication ma;

    ArrayList<ActivityListBean> activityBeanList;
    private View root;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.activity_grid_hot, container, false);
        mListData = new ArrayList<HashMap<String, String>>();


        //mListData = new ArrayList<HashMap<String, String>>();
        ma = (MyApplication) this.getActivity().getApplication();
        activityBeanList = new ArrayList<ActivityListBean>();
        mBusy = false;
        initView();
        //setContentView(R.layout.activity_list);
        activityGridView = (GridView) root.findViewById(R.id.activityGridView);
        //mView = LayoutInflater.from(this.getActivity()).inflate(R.layout.image_list_item, null);


        activityGridView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
                switch (i) {
                    case 0:
                        mBusy = false;
                        break;
                    case 1:
                        mBusy = true;
                        break;
                    case 2:
                        mBusy = true;
                        break;
                }
            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i2, int i3) {

            }
        });

        hotChoose();


        return root;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    private void initView() {
        activityGridView = (GridView) root.findViewById(R.id.activityGridView);
        activityGridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        activityGridView.setNumColumns(2);

        activityGridView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                    long arg3) {
                // TODO Auto-generated method stub
                HashMap<String, String> map = mListData.get(position);
                String activityID = map.get("id");
                Intent intent = new Intent();
                intent.setClass(ActivityTileGridFragment.this.getActivity(), DetailActivity.class);
                intent.putExtra("id", "" + activityID);
                startActivity(intent);
            }

        });

    }

    private void newChooseOld() {
        RequestParams params = new RequestParams();
        AsyncHttpClient client = new AsyncHttpClient();
        String url = ma.getConstVariables().getIPAddress() + "/activity/list/new";
        params.put("offset", "0");
        params.put("limit", "10");
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
                Toast.makeText(ActivityTileGridFragment.this.getActivity(), "活动列表获取失败!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] arg1, byte[] responce) {
                // TODO Auto-generated method stub
                String jsonString = new String(responce);
                ActivityListParser alp = new ActivityListParser();
                activityBeanList = alp.parseStream(jsonString);
                if (activityBeanList == null) {
                    Toast.makeText(ActivityTileGridFragment.this.getActivity(), jsonString, Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(ActivityTileGridFragment.this.getActivity(), "活动列表获取成功!", Toast.LENGTH_SHORT).show();
                for (ActivityListBean abl : activityBeanList) {
                    System.out.println(abl.getTitle());
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("id", "" + abl.getId());
                    map.put("title", abl.getTitle());
                    System.out.println(abl.getTitle());
                    map.put("description", abl.getDescription());
                    map.put("time", abl.getActTime());
                    map.put("image", ma.getConstVariables().getIPAddress() + "/img/" + abl.getHead());
                    map.put("imageActivity", ma.getConstVariables().getIPAddress() + "/img/" + abl.getImage());
                    mListData.add(map);

                }
                LazyListAdapter la = new LazyListAdapter(ActivityTileGridFragment.this.getActivity(), mListData, ActivityTileGridFragment.this.getActivity().getBaseContext(), R.layout.image_list_item_tile, mBusy);
                activityGridView.setAdapter(la);
            }
        });

    }

    private void hotChoose(){
        choose("/activity/list/hot");
    }
    private void recChoose(){
        newChoose();
//		ArrayList<ActivityListBean>s abl_in = new ArrayList<ActivityListBean>();
        for(int i = 0; i< activityBeanList.size(); i++){
			/*
			 * for 循环进行排序
			 */
            for(int j = i; j< activityBeanList.size() - 1; j++){
                if(activityBeanList.get(j).getApplyNum() < activityBeanList.get(j+1).getApplyNum()){
                    ActivityListBean alb = activityBeanList.get(j);
                    activityBeanList.remove(j);
                    activityBeanList.add(j, alb);
                }
            }
        }

    }

    private void choose(String itemURL){
		activityBeanList.clear();
		mListData.clear();
        RequestParams params = new RequestParams();
        AsyncHttpClient client = new AsyncHttpClient();
        String url = ma.getConstVariables().getIPAddress() + itemURL;
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
                Toast.makeText(ActivityTileGridFragment.this.getActivity(), "活动列表获取失败!", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onSuccess(int statusCode, Header[] arg1, byte[] responce) {
                // TODO Auto-generated method stub
                String jsonString = new String(responce);
                ActivityListParser alp = new ActivityListParser();
                activityBeanList = alp.parseStream(jsonString);
                if(activityBeanList == null){
                    Toast.makeText(ActivityTileGridFragment.this.getActivity(),jsonString, Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(ActivityTileGridFragment.this.getActivity(), "活动列表获取成功!", Toast.LENGTH_SHORT).show();
                for (ActivityListBean abl : activityBeanList){
                    System.out.println(abl.getTitle());
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("id", "" + abl.getId());
                    map.put("title", abl.getTitle());
                    System.out.println(abl.getTitle());
                    map.put("description", abl.getDescription());
                    map.put("time", abl.getActTime());
                    map.put("image", ma.getConstVariables().getIPAddress() + "/img/" + abl.getHead());
                    map.put("imageActivity", ma.getConstVariables().getIPAddress() + "/img/" + abl.getImage());
                    mListData.add(map);

                }
                LazyListAdapter la = new LazyListAdapter(ActivityTileGridFragment.this.getActivity(), mListData, ActivityTileGridFragment.this.getActivity().getBaseContext(), R.layout.image_list_item_tile, mBusy);
                activityGridView.setAdapter(la);
            }
        });

    }

    private void newChoose(){
        choose("/activity/list/new");
    }

}

