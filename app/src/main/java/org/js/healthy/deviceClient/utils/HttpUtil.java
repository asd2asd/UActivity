package org.js.healthy.deviceClient.utils;

import android.content.Context;
import android.os.Message;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.ua.LoginActivity;
import com.ua.MyApplication;
import com.ua.parser.LoginParser;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by jose on 2017/10/16.
 */

public class HttpUtil {

    public static void asyncGet(Context context, String url, final HttpResponse responseHandler)
    {

        RequestParams params = new RequestParams();
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(context, url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
//                InputStream in = new ByteArrayInputStream(bytes);
//                LoginParser lp = new LoginParser();
//                String result = null;
//                try {
//                    result = lp.parserLoginBean(in);
//                    if(result == "true"){
//                        MyApplication ma = (MyApplication) getApplication();
//                        ma.setUser(lp.getUserBean());
//                    }
//                    Message msg = new Message();
//                    msg.obj = result;
//                    handler.sendMessage(msg);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
                responseHandler.OnSuccess(new String(bytes));

            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

                responseHandler.OnFailed(i);
            }
        });
    }

    public static void asyncPost(Context context,String url,RequestParams params, final HttpResponse responseHandler)
    {
        AsyncHttpClient client = new AsyncHttpClient();

        StringEntity stringEntity = null;
        client.post(context, url, stringEntity,"application/json", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
//
                responseHandler.OnSuccess(new String(bytes));

            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

                responseHandler.OnFailed(i);
            }
        });
    }

    public interface HttpResponse
    {
        void OnSuccess(String responseJson);
        void OnFailed(int responseCode);
    }
}
