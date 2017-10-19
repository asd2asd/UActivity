package org.js.healthy.deviceClient;

import android.app.Activity;
import android.location.Location;
import android.location.LocationManager;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import com.ua.R;

import org.js.healthy.deviceClient.parser.LoginParser;
import org.js.healthy.deviceClient.utils.DeviceUtil;
import org.js.healthy.deviceClient.utils.LocationUtils;
import org.js.healthy.web.vo.HeartBeatVO;
import org.js.healthy.web.vo.ResponseVO;

public class DeviceActivity extends Activity {

    private Switch connectSwitch;
    private Device device;
    private TextView infoTextView;
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

                }

            }
        });
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
        stopSendHeartBeatInfo();
        handler.postDelayed(runnable, 2000);//每两秒执行一次runnable.
    }

    private void stopSendHeartBeatInfo()
    {
        handler.removeCallbacks(runnable);
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
        device.heartBeat(token);
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
