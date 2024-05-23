package handler.utils;

import com.google.gson.Gson;
import model.AuthData;
import model.GameData;
import model.UserData;

public class FromJsonG implements FromJson {
    private static final Gson gson = new Gson();

    public static UserData fromJsonToUser(String string) {
        return gson.fromJson(string, UserData.class);
    }
    public static GameData fromJsonToGame(String string) {
        return gson.fromJson(string, GameData.class);
    }
    public static AuthData fromJsonToAuth(String string) {
        return gson.fromJson(string, AuthData.class);
    }

}
