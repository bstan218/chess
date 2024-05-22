package dataaccess;

import java.util.HashMap;
import model.AuthData;
import model.GameData;

public class MemoryAuthDAO implements AuthDAO {
    private HashMap<String, AuthData> auths = new HashMap<>();

    @Override
    public void deleteAllAuths() {
        auths.clear();
    }
}
