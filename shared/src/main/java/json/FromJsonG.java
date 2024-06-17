package json;

import com.google.gson.Gson;
import model.AuthData;
import model.GameData;
import model.UserData;

public class FromJsonG implements FromJson {
    private Gson gson;

    public FromJsonG() {
        this.gson = new Gson();
    }

    public <T> T fromJson(String body, Class<T> tClass) { return gson.fromJson(body, tClass); }
    public UserData fromJsonToUser(String body) { return gson.fromJson(body, UserData.class); }
    public GameData fromJsonToGame(String body) {
        return gson.fromJson(body, GameData.class);
    }
    public AuthData fromHeaderToAuth(String authToken) { return new AuthData(authToken, null); }
}
