package handler.json;

import model.UserData;

public interface FromJson {
    UserData fromJsonToUser(String body);
}
