package handler.json;

import com.google.gson.Gson;

public class ToJsonG implements ToJson {
    private static final Gson Gson = new Gson();

    @Override
    public String fromResponse(Object result) {
        String json = Gson.toJson(result);
        return json;
    }
}
