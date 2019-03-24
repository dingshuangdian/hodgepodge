package com.lsqidsd.hodgepodge.utils;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class JsonUtils {

    public static <T> String jsonKey(T t, int i) {
        Gson gson = new Gson();
        String url = null;
        JSONObject jsonObject = null;
        List<String> list = new ArrayList<>();
        String obj = gson.toJson(t);
        try {
            jsonObject = new JSONObject(obj);
            Iterator<String> keys = jsonObject.keys();
            while (keys.hasNext()) {
                String key = keys.next();
                list.add(key);
            }
            if (!list.isEmpty()) {
                if (list.size() >= 3) {
                    url = jsonObject.optString(list.get(i));
                } else {
                    url = jsonObject.optString(list.get(0));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static <T> JSONObject toJsonObject(T t) {
        Gson gson = new Gson();
        JSONObject jsonObject = null;
        String obj = gson.toJson(t);
        try {
            jsonObject = new JSONObject(obj);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
