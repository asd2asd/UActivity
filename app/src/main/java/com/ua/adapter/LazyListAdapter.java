package com.ua.adapter;

import java.util.ArrayList;  
import java.util.HashMap;  

import com.ua.R;
import com.squareup.picasso.Picasso;

import android.app.Activity;  
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;  
import android.view.ViewGroup;  
import android.widget.BaseAdapter;  
import android.widget.ImageView;  
import android.widget.TextView;  
  
public class LazyListAdapter extends BaseAdapter {  
      
    private Activity activity;  
    private ArrayList<HashMap<String, String>> data;  
    private LayoutInflater inflater;
    private int resourceID;
    private boolean isScrolling;
    Context context;
      
    public LazyListAdapter(Activity a, ArrayList<HashMap<String, String>> d, Context context,int resource,boolean mBusy ) {
        activity = a;  
        data=d;
        resourceID = resource;
        isScrolling =mBusy;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);  
        this.context = context;
    }

    @Override
    public int getCount() {  
        return data.size();  
    }

    @Override
    public Object getItem(int position) {  
        return position;  
    }

    @Override
    public long getItemId(int position) {  
        return position;  
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {  

        if(isScrolling)
            return null;
        View vi=convertView;
        if(convertView==null)  
            vi = inflater.inflate(resourceID, null);
        TextView title = (TextView)vi.findViewById(R.id.activity_custom_titleA); // 标题  
        TextView description = (TextView)vi.findViewById(R.id.activity_custom_descriptionA); // 时长  
        TextView time = (TextView)vi.findViewById(R.id.activity_custom_timeA);
        View imageView = (View)vi.findViewById(R.id.activity_custom_imageA); // 缩略图
        View imageActivityView = (View)vi.findViewById(R.id.activity_imageA);
          
        HashMap<String, String> song = new HashMap<String, String>();  
        song = data.get(position);

        // 设置ListView的相关值
        if(title!=null)
            title.setText(song.get("title"));
        if(description!=null)
            description.setText(song.get("description"));
        if(time!=null)
            time.setText(song.get("time"));

        {
            if(imageView!=null) {
                Picasso.with(context).load(song.get("image")).placeholder(R.drawable.icon_example).resize(100, 100).centerCrop().into((ImageView)imageView);
            }
            if(imageActivityView!=null) {
                Picasso.with(context).load(song.get("imageActivity")).placeholder(R.drawable.test).resize(400, 300).centerCrop().into((ImageView)imageActivityView);
            }
        }


        return vi;
    }
}