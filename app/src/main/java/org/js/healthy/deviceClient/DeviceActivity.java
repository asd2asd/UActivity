package org.js.healthy.deviceClient;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.ua.R;

import org.js.healthy.deviceClient.parser.LoginParser;
import org.js.healthy.deviceClient.utils.DeviceUtil;
import org.js.healthy.web.vo.ResponseVO;
import org.json.JSONObject;
import org.json.JSONTokener;

public class DeviceActivity extends Activity {

    private Switch connectSwitch;
    private Device device;
    private TextView infoTextView;
    private String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device);

        connectSwitch = (Switch) findViewById(R.id.device_connect_switch);
        infoTextView = (TextView)findViewById(R.id.device_info_text_view);

        init();

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
                if(!connectSwitch.isChecked())
                {
                    connectDevice();
                }
                else
                {

                }
            }
        });
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
        device.heartBeat(token);
    }
}
