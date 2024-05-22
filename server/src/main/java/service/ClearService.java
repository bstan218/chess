package service;

import dataaccess.*;

public class ClearService {
    private static final AuthDAO authDAO = new MemoryAuthDAO();
    private static final UserDAO userDAO = new MemoryUserDAO();
    private static final GameDAO gameDAO = new MemoryGameDAO();

    public void clear() {}
}
