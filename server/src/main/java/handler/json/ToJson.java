package handler.json;

import model.AuthData;

public interface ToJson {
    Object toJsonFromAuthData(AuthData result);
}
