package org.js.healthy.deviceclient.utils;

import android.content.Context;
import android.telephony.TelephonyManager;

/**
 * Created by jose on 2017/10/16.
 */

public class DeviceUtil {

    public static String getPhoneImei(Context context)
    {
        TelephonyManager TelephonyMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String szImei = TelephonyMgr.getDeviceId();
        return szImei;
    }

    public static String getDeviceIdentify(Context context)
    {
        return getPhoneImei(context);
    }

}
