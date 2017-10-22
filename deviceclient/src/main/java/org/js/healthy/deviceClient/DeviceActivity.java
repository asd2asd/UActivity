package org.js.healthy.deviceclient;

import android.app.Activity;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationManager;
import android.os.Handler;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.js.healthy.deviceclient.parser.LoginParser;
import org.js.healthy.deviceclient.utils.DeviceUtil;
import org.js.healthy.deviceclient.utils.LocationUtils;
import org.js.healthy.web.vo.HeartBeatVO;
import org.js.healthy.web.vo.ResponseVO;

import cn.bingoogolapple.qrcode.zxing.QRCodeEncoder;

public class DeviceActivity extends Activity {

    private Switch connectSwitch;
    private Device device;
    private TextView infoTextView;
    private ImageView qcodeImageView;
    private String token;

    private BaiduLocationClass locationClass;
    private double latitude;
    private double longtitude;
    private String locationInfo;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device);

        connectSwitch = (Switch) findViewById(R.id.device_connect_switch);
        infoTextView = (TextView)findViewById(R.id.device_info_text_view);
        qcodeImageView = (ImageView)findViewById(R.id.device_qcode_imageView);

        locationClass = new BaiduLocationClass(this, new BaiduLocationClass.BaiduLocationEvent() {
            @Override
            public void OnReceiveLocationInfo(double lan, double lon, String info) {
                latitude = lan;
                longtitude = lon;
                locationInfo = info;
            }
        });
        locationClass.onCreate();

        init();

    }


    @Override
    protected void onDestroy() {
        stopSendHeartBeatInfo();
       locationClass.onDestory();
        super.onDestroy();
    }

    private void init()
    {
        latitude = -1;
        longtitude = -1;
        device = new Device(this);
        device.setDeviceEvent(new Device.DeviceEvent() {
            @Override
            public void OnRegistered(ResponseVO response) {
                infoTextView.setText(response.getMsg());

                if(response.getErrorcode()==ResponseVO.SUCCESS) {
                    connectSwitch.setChecked(true);
                    token = LoginParser.parserToken(response);

                    startSendHeartBeatInfo();
                }
            }

            @Override
            public void OnLogin(ResponseVO response) {
                infoTextView.setText(response.getMsg());
                if(response.getErrorcode()==ResponseVO.SUCCESS||response.getErrorcode()==ResponseVO.ALREADY)
                {
                    connectSwitch.setChecked(true);
                    if(response.getErrorcode()==ResponseVO.SUCCESS)
                        token = LoginParser.parserToken(response);
                    startSendHeartBeatInfo();
                }
                else
                {
                    if(response.getErrorcode() == ResponseVO.NOUSER)
                    {
                        registerDeivce();
                    }
                    connectSwitch.setChecked(false);
                }
            }

            @Override
            public void OnHeartBeat(ResponseVO response) {
                infoTextView.setText(response.getMsg());

            }

            @Override
            public void OnDisConnect(ResponseVO response) {

            }
        });
        connectSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(connectSwitch.isChecked())
                {
                    connectDevice();
                }
                else
                {
                    stopSendHeartBeatInfo();
                }

            }
        });

        Bitmap qcodeBitmap = QRCodeEncoder.syncEncodeQRCode(DeviceUtil.getDeviceIdentify(this),500,R.color.black);
        qcodeImageView.setImageBitmap(qcodeBitmap);
    }

    Handler handler=new Handler();
    Runnable runnable=new Runnable() {
        @Override
        public void run() {
            // TODO Auto-generated method stub
            //要做的事情

            locationClass.startLocation();
            heartBeat();
            handler.postDelayed(this, 2000);
        }
    };

    private void startSendHeartBeatInfo()
    {
        handler.removeCallbacks(runnable);
        handler.postDelayed(runnable, 2000);//每两秒执行一次runnable.
    }

    private void stopSendHeartBeatInfo()
    {
        handler.removeCallbacks(runnable);
        locationClass.stopLocation();
        disconnectDevice();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private void registerDeivce()

    {
        infoTextView.setText("start register");
        device.register(DeviceUtil.getDeviceIdentify(DeviceActivity.this));
    }

    private void connectDevice()
    {
        infoTextView.setText("start connect");
        device.connect(DeviceUtil.getDeviceIdentify(DeviceActivity.this));
    }

    private void heartBeat()
    {
        infoTextView.setText("heart beat");
        HeartBeatVO heartBeatVO = new HeartBeatVO();
        heartBeatVO.setLatitude(latitude);
        heartBeatVO.setLongtitude(longtitude);
        device.heartBeat(heartBeatVO,token);
    }

    private void disconnectDevice()
    {
        infoTextView.setText("disconnect");
        device.disConnect(token);
    }

    /**
     * 通过网络等获取定位信息
     */
    private void getNetworkLocation() {
        Location net = LocationUtils.getNetWorkLocation(this);
//        if (net == null) {
//            Toast.makeText(this, "net location is null", Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(this, "network location: lat==" + net.getLatitude() + "  lng==" + net.getLongitude(), Toast.LENGTH_SHORT).show();
//        }

        if (net == null) {
            //设置定位监听，因为GPS定位，第一次进来可能获取不到，通过设置监听，可以在有效的时间范围内获取定位信息
            LocationUtils.addLocationListener(getApplicationContext(), LocationManager.PASSIVE_PROVIDER, new LocationUtils.ILocationListener() {
                @Override
                public void onSuccessLocation(Location location) {
                    if (location != null) {
                        Toast.makeText(DeviceActivity.this, "gps onSuccessLocation location:  lat==" + location.getLatitude() + "     lng==" + location.getLongitude(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(DeviceActivity.this, "gps location is null", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            Toast.makeText(this, "gps location: lat==" + net.getLatitude() + "  lng==" + net.getLongitude(), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 通过GPS获取定位信息
     */
    public void getGPSLocation() {
        Location gps = LocationUtils.getGPSLocation(this);
        if (gps == null) {
            //设置定位监听，因为GPS定位，第一次进来可能获取不到，通过设置监听，可以在有效的时间范围内获取定位信息
            LocationUtils.addLocationListener(getApplicationContext(), LocationManager.GPS_PROVIDER, new LocationUtils.ILocationListener() {
                @Override
                public void onSuccessLocation(Location location) {
                    if (location != null) {
                        Toast.makeText(DeviceActivity.this, "gps onSuccessLocation location:  lat==" + location.getLatitude() + "     lng==" + location.getLongitude(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(DeviceActivity.this, "gps location is null", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            Toast.makeText(this, "gps location: lat==" + gps.getLatitude() + "  lng==" + gps.getLongitude(), Toast.LENGTH_SHORT).show();
        }
    }
}
