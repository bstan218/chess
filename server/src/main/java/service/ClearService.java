package service;

import dataaccess.*;

public class ClearService {
    private UserDAO userDAO;
    private AuthDAO authDAO;
    private GameDAO gameDAO;

    public ClearService(UserDAO userDAO, AuthDAO authDAO, GameDAO gameDAO) {
       this.userDAO = userDAO;
       this.authDAO = authDAO;
       this.gameDAO = gameDAO;
    }

    public void clear() {}
}
