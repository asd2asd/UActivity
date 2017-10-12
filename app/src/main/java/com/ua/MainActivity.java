package com.ua;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.ua.ua.R;
import com.ua.view.PopMenu;

/**
 * Created by justs_000 on 2014/12/28.
 */
public class MainActivity extends FragmentActivity {



    Button buttonCreate;
    Button buttonSetting;
    Button buttonFilter;
    Button buttonRefresh;
    Button buttonAdd;
    TabHost tabHost;
    PopMenu settingMenu;
    LinearLayout mainView;

    @Override

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);



        tabHost = (TabHost) findViewById(android.R.id.tabhost);
        buttonCreate = (Button)findViewById(R.id.buttonListCreate);
        buttonSetting = (Button)findViewById(R.id.buttonListSetting);
        buttonFilter = (Button)findViewById(R.id.buttonListFilter);

        buttonRefresh = (Button)findViewById(R.id.activity_list_refresh);
        buttonAdd = (Button)findViewById(R.id.activity_list_add);
        mainView = (LinearLayout)findViewById(R.id.mainView);

        buttonCreate.setOnClickListener(clickListener);
        buttonSetting.setOnClickListener(clickListener);
        buttonFilter.setOnClickListener(clickListener);
        settingMenu = new PopMenu(this);
        settingMenu.addItem("个人中心");
        settingMenu.addItem("切换用户");
        settingMenu.addItem("程序信息");
        settingMenu.addItem("退出应用");
        settingMenu.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                    long arg3) {
                // TODO Auto-generated method stub
                if(settingMenu!=null)
                    settingMenu.dismiss();
                if(position==0) {
                    Intent intent = new Intent(MainActivity.this,SettingActivity.class);
                    MainActivity.this.startActivity(intent);
                }
                else if(position==1)
                {

                    finish();
                }
                else if(position==2)
                {
                    Toast.makeText(MainActivity.this, "工程师的自我修养", Toast.LENGTH_LONG).show();

                }
                else if(position==3)
                {
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_HOME);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    android.os.Process.killProcess(android.os.Process.myPid());
                }
            }

        });

        buttonAdd.setOnClickListener(clickListener);
        buttonRefresh.setOnClickListener(clickListener);

        tabHost.setup();



        tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator("最新").setContent(R.id.tab1));
        tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator("最热").setContent(R.id.tab2));
        tabHost.addTab(tabHost.newTabSpec("tab3").setIndicator("心愿卡").setContent(R.id.tab3));
        //tabHost.addTab(tabHost.newTabSpec("Tab1").setIndicator("Tab1").setContent(new Intent(MainActivity.this, CommentActivity.class)));
        //tabHost.addTab(tabHost.newTabSpec("Tab2").setIndicator("Tab2").setContent(new Intent(MainActivity.this, CommentActivity.class)));

        for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) {
            tabHost.getTabWidget().getChildAt(i).setBackgroundColor(getResources().getColor(R.color.lightblue));
            //tabHost.getTabWidget().getChildAt(i).setScrollBarStyle(R.drawable.bg_tab_mainpage);
            tabHost.getTabWidget().getChildAt(i).getLayoutParams().height = dip2px(this,45);
            //tabHost.getTabWidget().getChildAt(i).getLayoutParams().

            TextView tv = (TextView) tabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
            if(tv!=null) {
                tv.setTextSize(18);
                tv.setTextColor(Color.WHITE);
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) tv.getLayoutParams();
                params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, 0); //取消文字底边对齐
                params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE); //设置文字居中对齐
            }
            // 设置tab背景颜色
            tabHost.getTabWidget().getChildAt(i).setBackgroundColor(getResources().getColor(R.color.lightblue));
            // 选中的进行处理
            if (tabHost.getCurrentTab() == i)
                tabHost.getTabWidget().getChildAt(i).setBackgroundResource(R.drawable.bg_tab_mainpage);
        }



        for(int i=0;i<tabHost.getTabContentView().getChildCount();i++)
            tabHost.getTabContentView().getChildAt(i).setBackgroundColor(Color.WHITE);



        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            // tabId显示的是：newTabSpec里面的值
            @Override
            public void onTabChanged(String tabId) {
                // 首先把所有的view背景初始化了.
                for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) {
                    View v = tabHost.getTabWidget().getChildAt(i);
                    // 设置tab背景颜色
                    v.setBackgroundColor(getResources().getColor(R.color.lightblue));
                    // 选中的进行处理
                    if (tabHost.getCurrentTab() == i) {
                        v.setBackgroundResource(R.drawable.bg_tab_mainpage);
                    }

                }
            }
        });
        tabHost.setCurrentTab(getIntent().getIntExtra("tab",0));

    }



    @Override

    public boolean onCreateOptionsMenu(Menu menu) {

        //getMenuInflater().inflate(R.menu.activity_main, menu);

        return true;

    }

    private View.OnClickListener clickListener = new View.OnClickListener(){
        public void onClick(View v){
            // if button Register is clicked, skip to register1Activity
            if((v == buttonCreate)||(v == buttonAdd)){
                Intent intent = new Intent(MainActivity.this,PublicActivity.class);
                //ActivityListActivity.this.startActivity(intent);
                startActivityForResult(intent, 100);

            }
            else if(v == buttonSetting ){
                //Intent intent = new Intent(MainActivity.this,SettingActivity.class);
                //MainActivity.this.startActivity(intent);
                settingMenu.showAsDropDown(buttonSetting);
            }
            /**************************************
             * add code here
             */
            else if(v == buttonFilter ){
                Intent intent = new Intent(MainActivity.this,RecomendActivity.class);
                MainActivity.this.startActivity(intent);
            }
            else if(v== buttonRefresh)
            {
                refresh();
            }

            else
            {

            }
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        //可以根据多个请求代码来作相应的操作
        if(-1==resultCode)
        {
            refresh();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    void refresh()
    {

        Intent intent = new Intent(MainActivity.this, MainActivity.class);
        intent.putExtra("tab", tabHost.getCurrentTab());
        startActivity(intent);
        //close this activity
        finish();
    }




    public int dip2px(Context context, float dipValue)

    {

        float m=context.getResources().getDisplayMetrics().density ;

        return (int)(dipValue * m + 0.5f) ;

    }



    public int px2dip(Context context, float pxValue)

    {

        float m=context.getResources().getDisplayMetrics().density ;

        return (int)(pxValue / m + 0.5f) ;

    }

    @Override
    public void onStop()
    {
        if(settingMenu!=null)
            settingMenu.dismiss();
        super.onStop();
    }






}


