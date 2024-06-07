package service;

import dataaccess.*;

public class ClearService {
    private final UserDAO userDAO;
    private final AuthDAO authDAO;
    private final GameDAO gameDAO;

    public ClearService(UserDAO userDAO, AuthDAO authDAO, GameDAO gameDAO) {
       this.userDAO = userDAO;
       this.authDAO = authDAO;
       this.gameDAO = gameDAO;
    }

    public void clear() {
        try {
            userDAO.deleteAllUsers();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
        try {
            authDAO.deleteAllAuths();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
        try {
            gameDAO.deleteAllGames();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
