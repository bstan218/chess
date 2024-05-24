package service;

import dataaccess.*;
import handler.response.CreateGameResponse;
import model.AuthData;
import model.GameData;
import spark.Response;

import java.util.ArrayList;

public class GameService {
    private final AuthDAO authDAO;
    private final GameDAO gameDAO;

    public GameService(UserDAO userDAO, AuthDAO authDAO, GameDAO gameDAO) {
        this.authDAO = authDAO;
        this.gameDAO = gameDAO;
    }

    public CreateGameResponse createGame(AuthData auth, GameData gameData, Response res) {
        try {
            authDAO.getAuth(auth.authToken());
        } catch (DataAccessException e) {
            res.status(401);
            return new CreateGameResponse(e.getMessage(), null);
        }

        int gameID;
        try {
            gameID = gameDAO.createGame(gameData.gameName());
        } catch (DataAccessException e) {
            res.status(400);
            return new CreateGameResponse(e.getMessage(), null);
        }
        return new CreateGameResponse(null, gameID);

    }
    public ArrayList<GameData> listGames(AuthData auth) {
        return null;
    }
    public void joinGame(AuthData auth, GameData game) {}
}
