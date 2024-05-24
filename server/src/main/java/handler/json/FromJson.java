package handler.json;

import model.AuthData;
import model.GameData;
import model.UserData;

import java.util.Set;

public interface FromJson {
    UserData fromJsonToUser(String body);
    AuthData fromJsonToAuth(String body);
    GameData fromJsonToGame(String body);
    AuthData fromHeaderToAuth(String authToken);
}
