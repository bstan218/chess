package handler.json;

import handler.request.JoinGameRequest;
import model.AuthData;
import model.GameData;
import model.UserData;

import java.util.Set;

public interface FromJson {
    <T> T fromJson(String body, Class<T> tClass);
    UserData fromJsonToUser(String body);
    GameData fromJsonToGame(String body);
    AuthData fromHeaderToAuth(String authToken);
    JoinGameRequest fromJsonToJoinGameRequest(String body);
}
