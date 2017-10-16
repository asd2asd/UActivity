package org.js.healthy.deviceClient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.ua.R;

import org.js.healthy.deviceClient.utils.DeviceUtil;
import org.js.healthy.web.vo.ResponseVO;

public class DeviceActivity extends AppCompatActivity {

    private Switch connectSwitch;
    private Device device;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device);

        connectSwitch = (Switch) findViewById(R.id.device_connect_switch);

        init();

    }

    private void init()
    {
        device = new Device(this);
        device.setDeviceEvent(new Device.DeviceEvent() {
            @Override
            public void OnRegistered(ResponseVO response) {

            }

            @Override
            public void OnLogin(ResponseVO response) {

            }

            @Override
            public void OnHeartBeat(ResponseVO response) {

            }
        });
        connectSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!connectSwitch.isChecked())
                {
                    device.connect(DeviceUtil.getDeviceIdentify(DeviceActivity.this));
                }
                else
                {

                }
            }
        });
    }
}
