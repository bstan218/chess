package handler.utils;

import com.google.gson.Gson;

public class ToJsonG implements ToJson {
    private static final Gson gson = new Gson();


    public static String toJson(Object object) {
        return gson.toJson(object);
    }
}
