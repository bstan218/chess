package dataaccess;

import java.util.HashMap;
import java.util.UUID;

import model.AuthData;

public class MemoryAuthDAO implements AuthDAO {
    private HashMap<String, AuthData> auths = new HashMap<>(); //key is authToken

    @Override
    public void deleteAllAuths() {
        auths.clear();
    }

    @Override
    public AuthData createAuth(String username) throws DataAccessException {
        if (auths.containsKey(username)) throw new DataAccessException("Error: user already logged in");
        String authToken = UUID.randomUUID().toString();
        AuthData authorization = new AuthData(authToken, username);
        auths.put(authToken, authorization);
        return authorization;
    }

    @Override
    public AuthData getAuth(String authToken) throws DataAccessException {
        if (!auths.containsKey(authToken)) throw new DataAccessException("Error: Invalid authToken");
        return auths.get(authToken);
    }

    @Override
    public void deleteAuth(String authToken) {
        auths.remove(authToken);
    }

}
