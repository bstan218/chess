package service;

import dataaccess.*;
import model.AuthData;
import model.GameData;
import java.util.ArrayList;

public class GameService {
    private UserDAO userDAO;
    private AuthDAO authDAO;
    private GameDAO gameDAO;

    public GameService(UserDAO userDAO, AuthDAO authDAO, GameDAO gameDAO) {
        this.userDAO = userDAO;
        this.authDAO = authDAO;
        this.gameDAO = gameDAO;
    }

    public GameData createGame(AuthData auth, GameData game) {}
    public ArrayList<GameData> listGames(AuthData auth) {}
    public void joinGame(AuthData auth, GameData game) {}
}
