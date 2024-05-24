package handler.json;

import com.google.gson.Gson;
import handler.request.JoinGameRequest;
import model.AuthData;
import model.GameData;
import model.UserData;

import java.io.Reader;
import java.util.Set;

public class FromJsonG implements FromJson {
    private Gson gson;

    public FromJsonG() {
        this.gson = new Gson();
    }

    public UserData fromJsonToUser(String body) { return gson.fromJson(body, UserData.class); }
    public GameData fromJsonToGame(String body) {
        return gson.fromJson(body, GameData.class);
    }
    public AuthData fromJsonToAuth(String body) {
        return gson.fromJson(body, AuthData.class);
    }
    public AuthData fromHeaderToAuth(String authToken) { return new AuthData(authToken, null); }
    public JoinGameRequest fromJsonToJoinGameRequest(String body) { return gson.fromJson(body, JoinGameRequest.class); }
}
