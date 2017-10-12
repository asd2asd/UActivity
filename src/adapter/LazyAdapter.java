package adapter;

import java.util.ArrayList;  
import java.util.HashMap;  

import com.example.uactivity.R;
import com.example.uactivity.view.CustomImageView;
import com.squareup.picasso.Picasso;

import android.app.Activity;  
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;  
import android.view.ViewGroup;  
import android.widget.BaseAdapter;  
import android.widget.ImageView;  
import android.widget.TextView;  
  
public class LazyAdapter extends BaseAdapter {  
      
    private Activity activity;  
    private ArrayList<HashMap<String, String>> data;  
    private static LayoutInflater inflater=null;
    private int resourceID;
    Context context;
      
    public LazyAdapter(Activity a, ArrayList<HashMap<String, String>> d, Context context,int resource ) {
        activity = a;  
        data=d;
        resourceID = resource;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);  
        this.context = context;
    }  
  
    public int getCount() {  
        return data.size();  
    }  
  
    public Object getItem(int position) {  
        return position;  
    }  
  
    public long getItemId(int position) {  
        return position;  
    }

    public static Bitmap convertStringToPic(String st)
    { // OutputStream out;
        Bitmap bitmap = null;
        try
        { // out = new FileOutputStream("/sdcard/aa.jpg");
            byte[] bitmapArray;
            bitmapArray = Base64.decode(st, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
            // bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            return bitmap;
        }
        catch (Exception e)
        {
            return null;
        }
    }
      
    public View getView(int position, View convertView, ViewGroup parent) {  
        View vi=convertView;  
        if(convertView==null)  
            vi = inflater.inflate(resourceID, null);
        TextView title = (TextView)vi.findViewById(R.id.activity_custom_titleA); // 标题
        TextView description = (TextView)vi.findViewById(R.id.activity_custom_descriptionA); // 时长
        TextView time = (TextView)vi.findViewById(R.id.activity_custom_timeA);
        CustomImageView imageView = (CustomImageView)vi.findViewById(R.id.activity_custom_imageA); // 缩略图
          
        HashMap<String, String> song = new HashMap<String, String>();  
        song = data.get(position);  

        // 设置ListView的相关值
        if(title!=null)
            title.setText(song.get("title"));
        if(description!=null)
            description.setText(song.get("description"));
        if(time!=null)
            time.setText(song.get("time"));
        if(imageView!=null)
            Picasso.with(context).load(song.get("image")).placeholder(R.drawable.icon_example).resize(100, 100).centerCrop().into((ImageView)imageView);

        return vi;  
    }  
}