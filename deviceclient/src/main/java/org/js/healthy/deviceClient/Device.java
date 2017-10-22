package org.js.healthy.deviceclient;

/**
 * Created by jose on 2017/10/16.
 */

import android.content.Context;

import org.js.healthy.deviceclient.parser.BaseParser;
import org.js.healthy.deviceclient.utils.Contants;
import org.js.healthy.deviceclient.utils.HttpUtil;
import org.js.healthy.deviceclient.utils.UrlUtil;
import org.js.healthy.web.utils.JacksonJsonUtil;
import org.js.healthy.web.vo.HeartBeatVO;
import org.js.healthy.web.vo.ResponseVO;

public class Device {

    private DeviceEvent deviceEvent;
    private Context context;

    public Device(Context context)
    {
        this.context = context;
    }

    public void register(String identify)
    {
        HttpUtil.asyncGet(context, UrlUtil.getFullUrl(Contants.getRegisterUrl(), Contants.getIdentifyFiled(identify)), new HttpUtil.HttpResponse() {
            @Override
            public void OnSuccess(String responseJson) {
                if(null!=deviceEvent) deviceEvent.OnRegistered((ResponseVO)JacksonJsonUtil.jsonToBean(responseJson,ResponseVO.class));
            }

            @Override
            public void OnFailed(int responseCode) {
                if(null!=deviceEvent) deviceEvent.OnRegistered(BaseParser.getEmptyResponse(responseCode));
            }
        });

    }
    public void connect(String identify)
    {
        HttpUtil.asyncGet(context, UrlUtil.getFullUrl(Contants.getConnectUrl(), Contants.getIdentifyFiled(identify)), new HttpUtil.HttpResponse() {
            @Override
            public void OnSuccess(String responseJson) {
                if(null!=deviceEvent) deviceEvent.OnLogin((ResponseVO)JacksonJsonUtil.jsonToBean(responseJson,ResponseVO.class));
            }

            @Override
            public void OnFailed(int responseCode) {
                if(null!=deviceEvent) deviceEvent.OnLogin(BaseParser.getEmptyResponse(-1));
            }
        });

    }
    public void heartBeat(HeartBeatVO heartBeatVO, String token)
    {
        heartBeatVO.setToken(token);

        HttpUtil.asyncPost(context, UrlUtil.getFullUrl(Contants.getHeartBeatUrl()),heartBeatVO, new HttpUtil.HttpResponse() {
            @Override
            public void OnSuccess(String responseJson) {
                if(null!=deviceEvent) deviceEvent.OnHeartBeat((ResponseVO)JacksonJsonUtil.jsonToBean(responseJson,ResponseVO.class));
            }

            @Override
            public void OnFailed(int responseCode) {
                if(null!=deviceEvent) deviceEvent.OnHeartBeat(BaseParser.getEmptyResponse(responseCode));
            }
        });

    }

    public void disConnect(String token)
    {
        HttpUtil.asyncGet(context, UrlUtil.getFullUrl(Contants.getDisConnectUrl(), Contants.getTokenFiled(token)), new HttpUtil.HttpResponse() {
            @Override
            public void OnSuccess(String responseJson) {
                if(null!=deviceEvent) deviceEvent.OnDisConnect((ResponseVO)JacksonJsonUtil.jsonToBean(responseJson,ResponseVO.class));
            }

            @Override
            public void OnFailed(int responseCode) {
                if(null!=deviceEvent) deviceEvent.OnDisConnect(BaseParser.getEmptyResponse(responseCode));
            }
        });
    }

    public void setDeviceEvent(DeviceEvent deviceEvent)
    {
        this.deviceEvent = deviceEvent;
    }

    public interface DeviceEvent
    {
        void OnRegistered(ResponseVO response);
        void OnLogin(ResponseVO response);
        void OnHeartBeat(ResponseVO response);
        void OnDisConnect(ResponseVO response);
    }
}
