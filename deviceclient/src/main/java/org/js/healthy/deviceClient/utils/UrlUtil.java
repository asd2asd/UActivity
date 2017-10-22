package org.js.healthy.deviceclient.utils;

/**
 * Created by jose on 2017/10/16.
 */

public class UrlUtil {

    public static String getFullUrl(String url,String... filedStr)
    {
        String fullUrl = url;
        if(filedStr.length>0) fullUrl+="?";
        int t=0;
        while(t<filedStr.length)
        {
            fullUrl += filedStr[t];
            if(t<filedStr.length-1) fullUrl+= "&";
            t++;
        }
        return fullUrl;
    }

}
