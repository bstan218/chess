package dataaccess;

import model.UserData;

public class SqlUserDAO implements UserDAO {
    @Override
    public void deleteAllUsers() {

    }

    @Override
    public UserData getUser(String username) throws DataAccessException {
        return null;
    }

    @Override
    public void createUser(UserData userData) throws DataAccessException {

    }

    @Override
    public boolean usersIsEmpty() {
        return false;
    }
}
