package org.js.healthy.deviceClient.parser;

import android.widget.BaseAdapter;

import org.js.healthy.web.vo.ResponseVO;
import org.js.healthy.web.vo.UserVO;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.HashMap;

/**
 * Created by jose on 2017/10/17.
 */

public class LoginParser extends BaseParser {

    public static String parserToken(String json)
    {
        ResponseVO responseVO = parser2Bean(json);
        return parserToken(responseVO);
    }
    public static String parserToken(ResponseVO responseVO)
    {
        String token = null;

        try {

            HashMap data = (HashMap) responseVO.getData();
            token = (String)data.get("token");
        }
        catch (Exception e)
        {

        }
        return token;
    }
}
