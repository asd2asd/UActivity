package org.js.healthy.deviceclient.utils;

/**
 * Created by jose on 2017/10/16.
 */

public class Contants {

    public final static String SeverIpAddress = "http://192.168.10.178:8080";

    public final  static String ProjectName = "healthy";

    public static String getPreUrl()
    {
        return SeverIpAddress+"/"+ProjectName+"/";
    }

    public final static String RegisterUrl = "register";
    public final static String ConnectUrl = "login";
    public final static String DisConnectUrl = "logout";
    public final static String HeartBeatUrl = "heartbeat";



    public static String getRegisterUrl() {
        return getPreUrl()+RegisterUrl;
    }

    public static String getConnectUrl() {
        return getPreUrl()+ConnectUrl;
    }

    public static String getDisConnectUrl() {
        return getPreUrl()+DisConnectUrl;
    }

    public static String getHeartBeatUrl() {
        return getPreUrl()+HeartBeatUrl;
    }

    public final static String TokenFiledName = "token";

    public final static String IdentifyFiledName = "identify";

    public static String getTokenFiled(String limit) {
        return TokenFiledName+"="+limit;
    }

    public static String getIdentifyFiled(String limit) {
        return IdentifyFiledName+"="+limit;
    }
}
