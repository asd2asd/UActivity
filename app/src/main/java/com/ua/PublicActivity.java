package com.ua;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Calendar;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import com.ua.ua.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;

import android.app.AlertDialog;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;



public class PublicActivity extends Activity {


    private String gradeString;
    private String schoolString;

    private Button buttonPublic;
    private Button buttonPicChoose;
    private Button buttonPicUpload;
    private EditText editTextTitle;
    private EditText editTextLocation;
    private EditText editTextFeature;
    private EditText editTextIntroduction;
    private EditText editTextBenefit;
    private EditText editTextTelephone;
    private EditText editTextFee;
    private EditText editTextPersonsNumber;
    private EditText editTextStartTime;
    private EditText editTextEndTime;
    private EditText editTextApplyTime;
    private EditText editTextSchool;
    private EditText editTextGrade;
    private Spinner spinnerType;
    private String portraitPath;
    private File imageFile;

    private boolean imageChoose = false;
    private boolean imageUploaded = false;
    private ImageView imageViewPortrait;


    private MyApplication ma;
    private RadioButton radioButtonMale, radioButtonFemale, radioButtonNolimit;

    private static final String[] types = {"读书","郊游","运动","文艺","吃","逛街"};
    private ArrayAdapter<String> adapterTypes;

    private TextView title;
    private Button buttonBack;
    private Button buttonConfirm;

    private void startView(View v){
        // TODO Auto-generated method stub
        if(v.getId() ==buttonPicUpload.getId()){
            try {
                if(imageChoose == true)
                    uploadPicture(imageFile.getAbsolutePath());
                else
                    Toast.makeText(PublicActivity.this, "请选择图片", Toast.LENGTH_SHORT).show();
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        if(v.getId() == buttonPicChoose.getId() || (v.getId() == imageViewPortrait.getId() && imageChoose == true)){
            Intent intent = new Intent();
            /* 开启Pictures画面Type设定为image */
            intent.putExtra("crop", "true");// 才能出剪辑的小方框，不然没有剪辑功能，只能选取图片
            intent.putExtra("aspectX", 4); // 出现放大和缩小
            intent.putExtra("aspectY", 3);
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");

            String path = "/sdcard/Uactivity/";
            File dir = new File(path);
            if(!dir.exists()){
                dir.mkdir();
            }
            imageFile = new File(path + "abc.jpg");
            intent.putExtra("output", Uri.fromFile(imageFile));  // 传入目标文件
            intent.putExtra("outputFormat", "JPEG"); //输入文件格式    

            Intent wrapperIntent = Intent.createChooser(intent, "选择图片"); //开始 并设置标题  
            startActivityForResult(wrapperIntent, 1); // 设返回 码为 1  onActivityResult 中的 reques
        }
        if(v.getId() == buttonPublic.getId()){
            RequestParams params = new RequestParams();
            String token = ma.getUser().getToken();
            String url = ma.getConstVariables().getIPAddress() + "/activity/publish";
            params.put("token", token);
            // 标题
            if(editTextTitle.getText().length() > 0)
                params.put("title", editTextTitle.getText());
            else{
                Toast.makeText(PublicActivity.this, "请填写活动标题", Toast.LENGTH_SHORT).show();
                return;
            }
            //开始时间
            if(editTextStartTime.getText().length() > 0)
                params.put("starttime", editTextStartTime.getText());
            else{
                Toast.makeText(PublicActivity.this, "请填写活动开始时间", Toast.LENGTH_SHORT).show();
                return;
            }
            //结束时间
            if(editTextEndTime.getText().length() > 0)
                params.put("endtime", editTextEndTime.getText());
            else{
                Toast.makeText(PublicActivity.this, "请填写活动结束时间", Toast.LENGTH_SHORT).show();
                return;
            }
            //申请结束时间
            if(editTextApplyTime.getText().length() > 0)
                params.put("applytime", editTextApplyTime.getText());
            else{
                Toast.makeText(PublicActivity.this, "请填写申请结束时间", Toast.LENGTH_SHORT).show();
                return;
            }
            //人数限制
            if(editTextPersonsNumber.getText().length() > 0)
                params.put("numlimit", editTextPersonsNumber.getText());
            else{
                Toast.makeText(PublicActivity.this, "请填写人数限制", Toast.LENGTH_SHORT).show();
                return;
            }
            //活动地址
            if(editTextLocation.getText().length() > 0)
                params.put("address", editTextLocation.getText());
            else{
                Toast.makeText(PublicActivity.this, "请填写活动地点", Toast.LENGTH_SHORT).show();
                return;
            }
            //活动费用
            if(editTextFee.getText().length() > 0)
                params.put("fee", editTextFee.getText());
            else{
                Toast.makeText(PublicActivity.this, "请填写活动费用", Toast.LENGTH_SHORT).show();
                return;
            }
            //活动介绍
            if(editTextIntroduction.getText().length() > 0)
                params.put("description", editTextIntroduction.getText());
            else{
                Toast.makeText(PublicActivity.this, "请填写活动介绍", Toast.LENGTH_SHORT).show();
                return;
            }
            //活动特点
            if(editTextFeature.getText().length() > 0)
                params.put("feature", editTextFeature.getText());
            else{
                Toast.makeText(PublicActivity.this, "请填写活动特点", Toast.LENGTH_SHORT).show();
                return;
            }
            //活动好处
            if(editTextBenefit.getText().length() > 0)
                params.put("benefit", editTextBenefit.getText());
            else{
                Toast.makeText(PublicActivity.this, "请填写所有益处", Toast.LENGTH_SHORT).show();
                return;
            }
            //活动类型
            String typeId = "" + ma.getConstVariables().getInterestId(spinnerType.getSelectedItem().toString());
            params.put("type", typeId);
            //学校限制
            params.put("schoollimit", schoolString);
            //年级限制
            params.put("gradelimit", gradeString);
            if(imageUploaded == true){
                params.put("image", portraitPath);
            }
            //性别限制
            String gender = "0";
            if(radioButtonMale.isChecked())
                gender = "1";
            if(radioButtonFemale.isChecked())
                gender = "2";
            params.put("genderlimit", gender);
            //联系方式
            if(editTextTelephone.getText().length() > 0)
                params.put("contact", editTextTelephone.getText());
            else{
                Toast.makeText(PublicActivity.this, "请填写联系方式", Toast.LENGTH_SHORT).show();
                return;
            }


            buttonPublic.setEnabled(false);
            AsyncHttpClient client = new AsyncHttpClient();
            client.post(url, params, new AsyncHttpResponseHandler() {

                @Override
                public void onFailure(int arg0, Header[] arg1, byte[] arg2,
                                      Throwable arg3) {
                    // TODO Auto-generated method stub
//	        		Toast.makeText(PublicActivity.this, "头像上传失败!", 0).show();
                    if(arg2!=null) {
                        String jsonStream = new String(arg2);
                        System.out.println(jsonStream);
                    }
                    Toast.makeText(PublicActivity.this, "活动发布失败!", Toast.LENGTH_SHORT).show();
                    setResult(RESULT_CANCELED);
                    buttonPublic.setEnabled(true);
                    finish();
                }
                @Override
                public void onSuccess(int statusCode, Header[] arg1, byte[] responce) {
                    // TODO Auto-generated method stub
//	        		String jsonStream = new String(responce);
                    Toast.makeText(PublicActivity.this, "活动发布成功!", Toast.LENGTH_SHORT).show();
                    setResult(RESULT_OK);
                    finish();
                }
            });
        }
        if(v.hasFocus() && (v == editTextStartTime || v == editTextEndTime || v == editTextApplyTime))
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(PublicActivity.this);
            View view = View.inflate(PublicActivity.this, R.layout.date_time_dialog, null);
            final DatePicker datePicker = (DatePicker) view.findViewById(R.id.date_picker);
            final TimePicker timePicker = (android.widget.TimePicker) view.findViewById(R.id.time_picker);
            builder.setView(view);
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(System.currentTimeMillis());

            datePicker.init(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), null);
            datePicker.setCalendarViewShown(false);
            timePicker.setIs24HourView(true);
            timePicker.setCurrentHour(cal.get(Calendar.HOUR_OF_DAY));
            timePicker.setCurrentMinute(Calendar.MINUTE);

            if (v.getId() == R.id.editTextPublicStartTime && v.hasFocus()) {
                final int inType = editTextStartTime.getInputType();
                editTextStartTime.setInputType(InputType.TYPE_NULL);
//                editTextStartTime.onTouchEvent(event); 
                editTextStartTime.setInputType(inType);
                editTextStartTime.setSelection(editTextStartTime.getText().length());

                builder.setTitle("选取起始时间");
                builder.setPositiveButton("确  定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        StringBuffer sb = new StringBuffer();
                        sb.append(String.format("%d-%02d-%02d",
                                datePicker.getYear(),
                                datePicker.getMonth() + 1,
                                datePicker.getDayOfMonth()));
                        sb.append("  ");
                        sb.append(timePicker.getCurrentHour())
                                .append(":").append(timePicker.getCurrentMinute());

                        editTextStartTime.setText(sb);
//                        editTextEndTime.requestFocus(); 

                        dialog.dismiss();
                    }
                });

            }
            if (v.getId() == R.id.editTextPublicEndTime && v.hasFocus()) {
                int inType = editTextEndTime.getInputType();
                editTextEndTime.setInputType(InputType.TYPE_NULL);
//                editTextEndTime.onTouchEvent(event); 
                editTextEndTime.setInputType(inType);
                editTextEndTime.setSelection(editTextEndTime.getText().length());

                builder.setTitle("选取结束时间");
                builder.setPositiveButton("确  定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        StringBuffer sb = new StringBuffer();
                        sb.append(String.format("%d-%02d-%02d",
                                datePicker.getYear(),
                                datePicker.getMonth() + 1,
                                datePicker.getDayOfMonth()));
                        sb.append("  ");
                        sb.append(timePicker.getCurrentHour())
                                .append(":").append(timePicker.getCurrentMinute());
                        editTextEndTime.setText(sb);

                        dialog.cancel();
                    }
                });
            }
            if (v.getId() == R.id.editTextPublicApplyTime && v.hasFocus()) {
                int inType = editTextApplyTime.getInputType();
                editTextApplyTime.setInputType(InputType.TYPE_NULL);
//                editTextEndTime.onTouchEvent(event); 
                editTextApplyTime.setInputType(inType);
                editTextApplyTime.setSelection(editTextApplyTime.getText().length());

                builder.setTitle("选取申请结束时间");
                builder.setPositiveButton("确  定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        StringBuffer sb = new StringBuffer();
                        sb.append(String.format("%d-%02d-%02d",
                                datePicker.getYear(),
                                datePicker.getMonth() + 1,
                                datePicker.getDayOfMonth()));
                        sb.append("  ");
                        sb.append(timePicker.getCurrentHour())
                                .append(":").append(timePicker.getCurrentMinute());
                        editTextApplyTime.setText(sb);

                        dialog.cancel();
                    }
                });
            }

            builder.show();
        }

        if(v == editTextGrade && v.hasFocus()){
            // TODO Auto-generated method stub
            AlertDialog.Builder builder = new AlertDialog.Builder(PublicActivity.this);
            View view = View.inflate(PublicActivity.this, R.layout.grade_chooser, null);
            final RadioButton rbNolimit = (RadioButton)view.findViewById(R.id.radioButtonGradeChooserNoLimit);
            final RadioButton rbFree = (RadioButton)view.findViewById(R.id.radioButtonGradeChooserFree);
            builder.setView(view);
            final ArrayList<CheckBox> cbList = new ArrayList<CheckBox>();
            builder.setTitle("请选择年级");
            cbList.add((CheckBox)view.findViewById(R.id.checkBox1));
            cbList.add((CheckBox)view.findViewById(R.id.checkBox2));
            cbList.add((CheckBox)view.findViewById(R.id.checkBox3));
            cbList.add((CheckBox)view.findViewById(R.id.checkBox4));
            cbList.add((CheckBox)view.findViewById(R.id.checkBox5));
            cbList.add((CheckBox)view.findViewById(R.id.checkBox6));
            cbList.add((CheckBox)view.findViewById(R.id.checkBox7));
            cbList.add((CheckBox)view.findViewById(R.id.checkBox8));
            cbList.add((CheckBox)view.findViewById(R.id.checkBox9));
            if(rbNolimit.isChecked()){
                for(CheckBox cb:cbList)
                    cb.setEnabled(false);
            }
            rbNolimit.setOnCheckedChangeListener(new OnCheckedChangeListener(){

                @Override
                public void onCheckedChanged(CompoundButton arg0,
                                             boolean arg1) {
                    if(rbNolimit.isChecked()){
                        for(CheckBox cb:cbList)
                            cb.setEnabled(false);
                    }
                    if(!rbNolimit.isChecked()){
                        for(CheckBox cb:cbList)
                            cb.setEnabled(true);
                    }

                }});
            builder.setPositiveButton("确  定", new DialogInterface.OnClickListener() {
                String gradeSet = "";
                String gradeNameSet = "";
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if(!rbFree.isChecked()){
                        gradeString = "0";
                        editTextGrade.setText("不限");
                        return;
                    }
                    for(CheckBox cb : cbList){
                        if(cb.isChecked()){
                            gradeSet = gradeSet + (ma.getConstVariables().getGradeId(cb.getText().toString())) + ",";
                            gradeNameSet = gradeNameSet + cb.getText() + ",";
                            break;
                        }
                    }
                    if(gradeSet.length() > 0){
                        gradeSet = gradeSet.substring(0,gradeSet.length() -1);
                        gradeNameSet = gradeNameSet.substring(0,gradeNameSet.length() -1);
                        gradeString = gradeSet;
                        editTextGrade.setText(gradeNameSet);
                    }
                    else{
                        gradeString = "0";
                        editTextGrade.setText("不限");
                    }
                    dialog.cancel();
                }
            });
            builder.show();
        }
        if(v == editTextSchool && v.hasFocus()){
            // TODO Auto-generated method stub
            AlertDialog.Builder builder = new AlertDialog.Builder(PublicActivity.this);
            View view = View.inflate(PublicActivity.this, R.layout.school_chooser, null);
            final RadioButton rbNolimit = (RadioButton)view.findViewById(R.id.radioButtonSchoolChooserNoLimit);
            final RadioButton rbFree = (RadioButton)view.findViewById(R.id.radioButtonSchoolChooserFree);
            builder.setView(view);
            final ArrayList<CheckBox> cbList = new ArrayList<CheckBox>();
            builder.setTitle("请选择学校");
            cbList.add((CheckBox)view.findViewById(R.id.checkBoxSchoolA));
            cbList.add((CheckBox)view.findViewById(R.id.checkBoxSchoolB));
            cbList.add((CheckBox)view.findViewById(R.id.checkBoxSchoolC));
            cbList.add((CheckBox)view.findViewById(R.id.checkBoxSchoolD));
            cbList.add((CheckBox)view.findViewById(R.id.checkBoxSchoolE));
            if(rbNolimit.isChecked()){
                for(CheckBox cb:cbList)
                    cb.setEnabled(false);
            }
            rbNolimit.setOnCheckedChangeListener(new OnCheckedChangeListener(){

                @Override
                public void onCheckedChanged(CompoundButton arg0,
                                             boolean arg1) {
                    if(rbNolimit.isChecked()){
                        for(CheckBox cb:cbList)
                            cb.setEnabled(false);
                    }
                    if(!rbNolimit.isChecked()){
                        for(CheckBox cb:cbList)
                            cb.setEnabled(true);
                    }

                }});
            builder.setPositiveButton("确  定", new DialogInterface.OnClickListener() {
                String schoolSet = "";
                String schoolNameSet = "";
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if(!rbFree.isChecked()){
                        schoolString = "0";
                        editTextSchool.setText("不限");
                        return;
                    }
                    for(CheckBox cb : cbList){
                        if(cb.isChecked()){
                            schoolSet = schoolSet + (ma.getConstVariables().getGradeId(cb.getText().toString())) + ",";
                            schoolNameSet = schoolNameSet + cb.getText() + ",";
                            break;
                        }
                    }
                    if(schoolSet.length() > 0){
                        schoolSet = schoolSet.substring(0,schoolSet.length() -1);
                        schoolNameSet = schoolNameSet.substring(0,schoolNameSet.length() -1);
                        schoolString = schoolSet;
                        editTextSchool.setText(schoolNameSet);
                    }
                    else{
                        schoolString = "0";
                        editTextSchool.setText("不限");
                    }
                    dialog.cancel();
                }
            });
            builder.show();
        }

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);



        getWindow().requestFeature(android.view.Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_public);
        getWindow().setFeatureInt(android.view.Window.FEATURE_CUSTOM_TITLE, R.layout.titlebar);

        title = (TextView)findViewById(R.id.title);
        title.setText("活动发布");
        buttonBack = (Button)findViewById(R.id.back);
        buttonConfirm = (Button)findViewById(R.id.confirm);
        buttonBack.setOnClickListener(onNavBarClick);
        buttonConfirm.setOnClickListener(onNavBarClick);

        ma = (MyApplication)getApplication();
        buttonPublic = (Button)findViewById(R.id.buttonPubFinish);
        buttonPicChoose = (Button)findViewById(R.id.buttonUpPicChoose);
        buttonPicUpload = (Button)findViewById(R.id.buttonUpPicUpload);
        editTextTitle = (EditText)findViewById(R.id.editTextPublicTitle);
        editTextBenefit = (EditText)findViewById(R.id.editTextPublicBenefit);
        editTextLocation = (EditText)findViewById(R.id.editTextPublicLocation);
        editTextFeature = (EditText)findViewById(R.id.editTextPublicFeature);
        editTextIntroduction = (EditText)findViewById(R.id.editTextPublicIntroduction);
        editTextTelephone = (EditText)findViewById(R.id.editTextPublicTel);
        editTextFee = (EditText)findViewById(R.id.editTextPublicFee);
        editTextPersonsNumber = (EditText)findViewById(R.id.editTextPublicPersonsNum);
        editTextStartTime = (EditText)findViewById(R.id.editTextPublicStartTime);
        editTextEndTime = (EditText)findViewById(R.id.editTextPublicEndTime);
        editTextApplyTime = (EditText) findViewById(R.id.editTextPublicApplyTime);
        editTextSchool = (EditText)findViewById(R.id.editTextPublicSchool);
        editTextGrade = (EditText) findViewById(R.id.editTextPublicGrade);
        spinnerType = (Spinner)findViewById(R.id.spinnerPublicType);
        radioButtonMale = (RadioButton)findViewById(R.id.radioButtonPubMale);
        radioButtonFemale = (RadioButton)findViewById(R.id.radioButtonPubFemale);
        radioButtonNolimit = (RadioButton)findViewById(R.id.radioButtonPubNoLimit);
        editTextStartTime.setOnClickListener(clickListener);
        editTextEndTime.setOnClickListener(clickListener);
        editTextApplyTime.setOnClickListener(clickListener);
        editTextStartTime.setOnFocusChangeListener(focusChangeListener);
        editTextEndTime.setOnFocusChangeListener(focusChangeListener);
        editTextApplyTime.setOnFocusChangeListener(focusChangeListener);
        buttonPublic.setOnClickListener(clickListener);
        buttonPicUpload.setOnClickListener(clickListener);
//		buttonPublic.setOnClickListener(clickListener);
        buttonPicChoose.setOnClickListener(clickListener);
        imageViewPortrait = (ImageView) findViewById(R.id.imageViewPubPortrait);
//        imageViewPortrait.setOnClickListener(clickListener);

        gradeString = "0";
        schoolString = "0";

        adapterTypes = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, types);
        spinnerType.setAdapter(adapterTypes);


        editTextGrade.setOnFocusChangeListener(new OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean arg1) {
                startView(v);
            }
        });
        editTextSchool.setOnFocusChangeListener(new OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean arg1) {
                startView(v);
            }
        });


        editTextGrade.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v) {
                startView(v);
                // TODO Auto-generated method stub
            }

        });


        editTextSchool.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                startView(v);
            }

        });

    }

    private OnClickListener onNavBarClick = new OnClickListener() {
        public void onClick(View v) {
            if (v == buttonBack) {
                finish();
            }
            else {
                buttonPublic.performClick();
            }
        }
    };

    private OnFocusChangeListener focusChangeListener = new OnFocusChangeListener(){

        @Override
        public void onFocusChange(View v, boolean arg1) {
            // TODO Auto-generated method stub
            startView(v);
        }
    };

    private OnClickListener clickListener = new OnClickListener(){
        public void onClick(View v){
            startView(v);
        }
    };
    public void uploadPicture(String imgPath) throws FileNotFoundException{

        RequestParams params = new RequestParams();
        String token = ma.getUser().getToken();
        File imgFile = new File(imgPath);
        params.put("token", token);
        params.put("max", "1");
        params.put("file0", imgFile);
        String url = ma.getConstVariables().getIPAddress() + "/upload/image";

        AsyncHttpClient client = new AsyncHttpClient();
        client.post(url, params, new AsyncHttpResponseHandler() {

            @Override
            public void onFailure(int arg0, Header[] arg1, byte[] arg2,
                                  Throwable arg3) {
                // TODO Auto-generated method stub
                Toast.makeText(PublicActivity.this, "头像上传失败!", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onSuccess(int statusCode, Header[] arg1, byte[] responce) {
                // TODO Auto-generated method stub
                String jsonStream = new String(responce);
                try {
                    JSONObject json = new JSONObject(jsonStream);
                    int errorcode = json.getInt("errorcode");
                    if(errorcode == 0){
                        Toast.makeText(PublicActivity.this, "图片上传成功!", Toast.LENGTH_SHORT).show();
                        imageUploaded = true;
                        portraitPath = json.getString("data");
                    }
                    else{
                        portraitPath = "";
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if(imageFile == null)
                    return;
                System.out.println(imageFile.getAbsolutePath());
//                Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
//                ImageParser imageParser = new ImageParser();
//                imageParser.compressBmpToFile(bitmap, imageFile);
                Picasso.with(getApplicationContext()).load(imageFile).resize(400, 300).centerCrop().into(imageViewPortrait);
                imageChoose = true;
                buttonPicChoose.setVisibility(View.GONE);
                buttonPicUpload.performClick();
        }
    }
}