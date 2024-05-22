package service;

import dataaccess.*;
import model.AuthData;
import model.GameData;
import java.util.ArrayList;

public class GameService {
    private static final AuthDAO authDAO = new MemoryAuthDAO();
    private static final UserDAO userDAO = new MemoryUserDAO();
    private static final GameDAO gameDAO = new MemoryGameDAO();

    public GameData createGame(AuthData auth, GameData game) {}
    public ArrayList<GameData> listGames(AuthData auth) {}
    public void joinGame(AuthData auth, GameData game) {}
}
