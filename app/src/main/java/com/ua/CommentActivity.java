package com.ua;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ua.ua.R;
import com.ua.bean.ActivityListBean;
import com.ua.parser.ActivityListParser;
import com.ua.view.CardAdapter;
import com.ua.view.CardView;
import com.ua.view.CustomImageView;
import com.ua.view.TestFragment;
import com.ua.view.Utils;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;

import android.os.Bundle;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.Header;
//import org.jetbrains.annotations.Nullable;

import com.ua.adapter.LazyAdapter;


public class CommentActivity extends Fragment implements CardView.OnCardClickListener {
    List<String> list;
    private TestFragment frag;

    private View root;

    ArrayList<HashMap<String, String>> mListData;
    LazyAdapter lazyAdapter;
    MyApplication ma;ArrayList<ActivityListBean> activityBeanList;

    View mView;


    @Override
    public View onCreateView(LayoutInflater inflater,
                              ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.activity_comment, container,false);
        return root;
    }

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
        ma = (MyApplication)this.getActivity().getApplication();
        activityBeanList = new ArrayList<ActivityListBean>();

        mListData = new ArrayList<HashMap<String, String>>();
        newChoose();

	}


    private void hotChoose(){
        choose("/activity/list/hot");
    }
    private void recChoose(){
//        newChoose();
//		ArrayList<ActivityListBean>s abl_in = new ArrayList<ActivityListBean>();
        for(int vi = mListData.size();vi>0;--vi){
			/*
			 * for 循环进行排序
			 */
            for(int j = 0; j<vi-1; j++){
                if(activityBeanList.get(j).getApplyNum() < activityBeanList.get(j+1).getApplyNum()){
                    HashMap<String, String> mld = mListData.get(j);
                    mListData.remove(j);
                    mListData.add(j+1, mld);
                    ActivityListBean alb = activityBeanList.get(j);
                    activityBeanList.remove(j);
                    activityBeanList.add(j+1, alb);
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
                Toast.makeText(CommentActivity.this.getActivity(), "活动列表获取失败!", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onSuccess(int statusCode, Header[] arg1, byte[] responce) {
                // TODO Auto-generated method stub
                String jsonString = new String(responce);
                ActivityListParser alp = new ActivityListParser();
                activityBeanList = alp.parseStream(jsonString);
                if(activityBeanList == null){
                    Toast.makeText(CommentActivity.this.getActivity(),jsonString, Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(CommentActivity.this.getActivity(), "活动列表获取成功!", Toast.LENGTH_SHORT).show();
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
                    //map.put("applyNum",abl.getApplyNum());
                    mListData.add(map);
                    //LazyListAdapter la = new LazyListAdapter(CommentActivity.this.getActivity(), mListData, CommentActivity.this.getActivity().getBaseContext(), R.layout.image_list_item,false,true);
                    //activityListView.setAdapter(la);
                }
                recChoose();
                initUI(root);
            }
        });

    }

    private void newChoose(){
        choose("/activity/list/new");
    }




    private void initUI(final View root) {
        if(mListData==null)
            return;
        if(mListData.size()==0)
            return;
        CardView cardView = (CardView) root.findViewById(R.id.cardView1);
        cardView.setOnCardClickListener(this);
        cardView.setItemSpace(Utils.convertDpToPixelInt(this.getActivity(), 30));

        MyCardAdapter adapter = new MyCardAdapter(this.getActivity());
        adapter.addAll(mListData);
        cardView.setAdapter(adapter);

        FragmentManager manager = this.getFragmentManager();
        frag = new TestFragment();
        manager.beginTransaction().add(R.id.fragContentView, frag).commit();
    }

    @Override
    public void onCardClick(final View view, final int position) {
        //Toast.makeText(CommentActivity.this, position + "", Toast.LENGTH_SHORT).show();
        Bundle bundle = new Bundle();
        mListData.get(position%mListData.size());
        bundle.putString("text", mListData.get(position%mListData.size()).get("title"));
        bundle.putString("id", mListData.get(position%mListData.size()).get("id"));
        frag.show(view,bundle);
    }



    public class MyCardAdapter extends CardAdapter<HashMap<String, String>>
    {
        public MyCardAdapter(Context context) {

            super(context);
        }

        @Override
        public int getCount() {
            //return Integer.MAX_VALUE;
            return mListData.size();
        }

        @Override
        protected View getCardView(int position,
                                   View convertView, ViewGroup parent) {
            if(convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(this.getContext());
                convertView = inflater.inflate(R.layout.item_layout, parent, false);
            }
            TextView tv = (TextView) convertView.findViewById(R.id.card_activity_custom_title);
            TextView description = (TextView)convertView.findViewById(R.id.card_activity_custom_description); // 时长
            TextView time = (TextView)convertView.findViewById(R.id.card_activity_custom_time);
            CustomImageView imageView = (CustomImageView)convertView.findViewById(R.id.card_activity_custom_image); // 缩略图
            CustomImageView imageActivityView = (CustomImageView)convertView.findViewById(R.id.card_activity_image);

            HashMap<String, String> song = getItem(position% mListData.size());
            tv.setText(song.get("title"));

            // 设置ListView的相关值
            description.setText(song.get("description"));
            time.setText(song.get("time"));
            if(imageView!=null){
                Picasso.with(super.getContext()).load(song.get("image")).placeholder(R.drawable.icon_example).resize(100, 100).centerCrop().into(imageView);

            }
            if(imageActivityView!=null)
                Picasso.with(super.getContext()).load(song.get("imageActivity")).placeholder(R.drawable.test).resize(400, 300).centerCrop().into(imageActivityView);



            return convertView;
        }
    }



	
}
