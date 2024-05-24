package handler.json;

import model.AuthData;
import model.UserData;

import java.util.Set;

public interface FromJson {
    UserData fromJsonToUser(String body);
    AuthData fromJsonToAuth(String body);
    AuthData fromHeaderToAuth(String authToken);
}
