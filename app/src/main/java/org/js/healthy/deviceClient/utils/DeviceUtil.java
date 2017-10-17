package org.js.healthy.deviceClient.utils;

import android.content.Context;
import android.telecom.TelecomManager;

/**
 * Created by jose on 2017/10/16.
 */

public class DeviceUtil {

    public static String getPhoneImei(Context context)
    {
        TelecomManager TelephonyMgr = (TelecomManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        String szImei = TelephonyMgr.;
        return szImei;
    }

    public static String getDeviceIdentify(Context context)
    {
        return getPhoneImei(context);
    }

}
