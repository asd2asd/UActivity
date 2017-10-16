package org.js.healthy.deviceClient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Switch;

import com.ua.R;

public class DeviceActivity extends AppCompatActivity {

    private Switch connectSwitch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device);

        connectSwitch = (Switch) findViewById(R.id.device_connect_switch);

        init();

    }

    private void init()
    {

    }
}
