package handler.json;

import handler.response.UserResponse;
import model.AuthData;

public interface ToJson {
    String fromResponse(Object result);
}
