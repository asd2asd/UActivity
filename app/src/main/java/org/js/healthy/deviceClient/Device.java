package org.js.healthy.deviceClient;

/**
 * Created by jose on 2017/10/16.
 */

import android.content.Context;

import org.js.healthy.deviceClient.parser.BaseParser;
import org.js.healthy.deviceClient.utils.Contants;
import org.js.healthy.deviceClient.utils.HttpUtil;
import org.js.healthy.deviceClient.utils.UrlUtil;
import org.js.healthy.web.utils.JacksonJsonUtil;
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
                if(null!=deviceEvent) deviceEvent.OnLogin(BaseParser.getEmptyResponse(responseCode));
            }
        });

    }
    public void heartBeat(String token)
    {
        HttpUtil.asyncGet(context, UrlUtil.getFullUrl(Contants.getHeartBeatUrl(), Contants.getTokenFiled(token)), new HttpUtil.HttpResponse() {
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

    public void setDeviceEvent(DeviceEvent deviceEvent)
    {
        this.deviceEvent = deviceEvent;
    }

    public interface DeviceEvent
    {
        void OnRegistered(ResponseVO response);
        void OnLogin(ResponseVO response);
        void OnHeartBeat(ResponseVO response);
    }
}
