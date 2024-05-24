package handler.json;

import com.google.gson.Gson;
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

    public UserData fromJsonToUser(String string) {
        return gson.fromJson(string, UserData.class);
    }
    public GameData fromJsonToGame(String string) {
        return gson.fromJson(string, GameData.class);
    }
    public AuthData fromJsonToAuth(String string) {
        return gson.fromJson(string, AuthData.class);
    }
    public AuthData fromHeaderToAuth(String authToken) {
        return new AuthData(authToken, null);
    }

}
