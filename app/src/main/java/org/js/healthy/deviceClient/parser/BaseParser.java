package org.js.healthy.deviceClient.parser;

import android.content.Context;

import org.js.healthy.web.vo.ResponseVO;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by justs on 2017/10/16.
 */

public class BaseParser {


    public static ResponseVO parser2Bean(String json) {
        String result = "true";
        ResponseVO response = new ResponseVO();

        try {
            JSONTokener jsonTokener = new JSONTokener(json);
            JSONObject user = (JSONObject) jsonTokener.nextValue();

            int resultCode = user.getInt("errorcode");
            String message = user.getString("msg");
            response.setMsg(message);
            response.setErrorcode(resultCode);
            try {
                JSONObject data = user.getJSONObject("data");
                response.setData(data);
            }
            catch (Exception e)
            {

            }

        }catch (Exception e) {
            // TODO Auto-generated catch block
//            e.printStackTrace();
            response.setErrorcode(-1);
        }
        return response;
    }

    public static ResponseVO getEmptyResponse(int errorCode)
    {
        ResponseVO responseVO = new ResponseVO();
        responseVO.setErrorcode(errorCode);
        return responseVO;
    }

}
