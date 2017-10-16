package org.js.healthy.deviceClient.utils;

/**
 * Created by jose on 2017/10/16.
 */

public class UrlUtil {

    public static String getFullUrl(String url,String... filedStr)
    {
        String fullUrl = url;
        if(filedStr.length>0) fullUrl+="?";
        int t=0;
        for(t=0;t<filedStr.length;t++);
        {
            fullUrl += filedStr[t];
            if(t<filedStr.length-1) fullUrl+= "&";
        }
        return fullUrl;
    }

}
