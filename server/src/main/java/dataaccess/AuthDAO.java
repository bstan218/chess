package dataaccess;

import model.AuthData;

public interface AuthDAO {
    void deleteAllAuths() throws DataAccessException;
    AuthData createAuth(String username) throws DataAccessException;
    AuthData getAuth(String authToken) throws DataAccessException;
    void deleteAuth(String authToken);
}
