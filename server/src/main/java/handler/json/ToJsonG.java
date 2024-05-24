package handler.json;

import com.google.gson.Gson;

public class ToJsonG implements ToJson {
    private static final Gson gson = new Gson();

    @Override
    public String fromResponse(Object result) {
        String json = gson.toJson(result);
        return json;
    }
}
