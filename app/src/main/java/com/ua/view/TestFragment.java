package com.ua.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.TextView;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;

import com.ua.DetailActivity;
import com.ua.R;

//import org.jetbrains.annotations.Nullable;

public class TestFragment extends Fragment{
	private TextView tv;
	private View root;
	private View view;
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState) {
		root = inflater.inflate(R.layout.frag_layout, container,false);
		initUI(root);
		return root;
	}
	
	private void initUI(final View root) {
		root.setClickable(true);
		root.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {}
		});
		tv = (TextView) root.findViewById(R.id.frag_textView1);
		root.findViewById(R.id.frag_close_button).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				root.setClickable(false);
				root.animate()
				.rotationY(-90).setDuration(1)
				.setListener(new AnimatorListenerAdapter(){
					@Override
					public void onAnimationEnd(Animator animation) {
						root.clearAnimation();
						root.setVisibility(View.INVISIBLE);
						view.setEnabled(true);
					}
				});
			}
		});
	}
	
	public void show(final View view,Bundle bundle){
		view.setEnabled(false);
		this.view = view;
		String text = bundle.getString("text");
        final String activityID = bundle.getString("id");

		tv.setText(text);
		view.setRotationY( 0);
		root.setRotationY(-90);
		root.setVisibility(View.VISIBLE);

        view.animate().rotationY(90)
		.setDuration(300).setInterpolator(new AccelerateInterpolator());

        root.animate().rotationY(0).setStartDelay(300).setDuration(200).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {


                Intent intent = new Intent();
                intent.setClass(view.getContext(), DetailActivity.class);
                intent.putExtra("id", "" + activityID);
                //startActivity(intent);
                //Intent intent = new Intent(MainActivity.this,PublicActivity.class);
                //ActivityListActivity.this.startActivity(intent);
                startActivityForResult(intent, 100);


            }
        });
		



	}
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        //可以根据多个请求代码来作相应的操作
        if(0==resultCode)
        {
            root.setRotationY(0);
            view.setRotationY( 0);
            root.setVisibility(View.INVISIBLE);
            view.setEnabled(true);
            view.setVisibility(View.VISIBLE);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
