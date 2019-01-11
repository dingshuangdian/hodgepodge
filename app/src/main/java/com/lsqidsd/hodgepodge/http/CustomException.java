package com.lsqidsd.hodgepodge.http;

import com.google.gson.JsonParseException;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.ParseException;

/**
 * 本地错误码定义处理
 */
public class CustomException {
    /**
     * 未知错误
     */
    public static final int UNKNOWN = 1000;
    /**
     * 解析错误
     */
    public static final int PARSE_ERROR = 1001;
    /**
     * 网络错误
     */
    public static final int NETWORK_ERROR = 1002;
    /**
     * 协议错误
     */
    public static final int HTTP_ERROR = 1003;

    public static ApiException handleException(Throwable e) {
        if (e instanceof JsonParseException || e instanceof JSONException || e instanceof ParseException) {
            //解析错误
            return new ApiException(PARSE_ERROR, e.getMessage());
        } else if (e instanceof ConnectException) {
            //网络错误
            return new ApiException(NETWORK_ERROR, e.getMessage());

        } else if (e instanceof UnknownHostException || e instanceof SocketTimeoutException) {
            //连接错误
            return new ApiException(NETWORK_ERROR, e.getMessage());

        } else {
            return new ApiException(UNKNOWN, e.getMessage());
        }
    }

}
