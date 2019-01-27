package com.lsqidsd.hodgepodge.utils;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class JsonUtils {
    static Gson gson = new Gson();

    public static <T> String jsonKey(T t, int i) {
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
                url = jsonObject.get(list.get(i)).toString();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static <T> JSONObject toJsonObject(T t) {
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
