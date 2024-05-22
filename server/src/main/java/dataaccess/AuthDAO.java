package dataaccess;

import model.AuthData;

public interface AuthDAO {
    void deleteAllAuths();
    AuthData createAuth(String username);
    AuthData getAuth(String authToken);
}
