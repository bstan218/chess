package dataaccess;

import model.AuthData;

public class SqlAuthDAO implements AuthDAO {
    @Override
    public void deleteAllAuths() {

    }

    @Override
    public AuthData createAuth(String username) throws DataAccessException {
        return null;
    }

    @Override
    public AuthData getAuth(String authToken) throws DataAccessException {
        return null;
    }

    @Override
    public void deleteAuth(String authToken) {

    }
}
