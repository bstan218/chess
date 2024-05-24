package handler.json;

import com.google.gson.Gson;

public class ToJsonG implements ToJson {
    private static final Gson GSON = new Gson();

    @Override
    public String fromResponse(Object result) {
        String json = GSON.toJson(result);
        return json;
    }
}
