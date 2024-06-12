package service;

import chess.ChessGame;
import dataaccess.*;
import handler.request.JoinGameRequest;
import chess.response.CreateGameResponse;
import chess.response.EmptyResponse;
import chess.response.ListGameResponse;
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

    public EmptyResponse joinGame(AuthData auth, JoinGameRequest joinGameRequest, Response res) {
        AuthData authData;
        try {
            authData = authDAO.getAuth(auth.authToken());
        } catch (DataAccessException e) {
            res.status(401);
            return new EmptyResponse(e.getMessage());
        }

        if (joinGameRequest.playerColor() == null) {
            res.status(400);
            return new EmptyResponse("Error: Must specify color as 'Black' or 'White'.");
        }

        GameData gameData;
        try {
            gameData = gameDAO.getGame(joinGameRequest.gameID());
        } catch (DataAccessException e) {
            res.status(400);
            return new EmptyResponse(e.getMessage());
        }

        GameData newGameData;
        if (joinGameRequest.playerColor() == ChessGame.TeamColor.WHITE) {
            if (gameData.whiteUsername() != null) {
                res.status(403);
                return new EmptyResponse("Error: Color already taken");
            } else {
                newGameData = new GameData(gameData.gameID(), authData.username(), gameData.blackUsername(), gameData.gameName(), gameData.game());
            }
        } else {
            if (gameData.blackUsername() != null) {
                res.status(403);
                return new EmptyResponse("Error: Color already taken");
            } else {
                newGameData = new GameData(gameData.gameID(), gameData.whiteUsername(), authData.username(), gameData.gameName(), gameData.game());
            }
        }

        try {
            gameDAO.updateGame(gameData.gameID(), newGameData);
        } catch (DataAccessException e) {
            res.status(400);
            return new EmptyResponse(e.getMessage());
        }

        return new EmptyResponse(null);
    }

    public ListGameResponse listGames(AuthData auth, Response res) {
        try {
            authDAO.getAuth(auth.authToken());
        } catch (DataAccessException e) {
            res.status(401);
            return new ListGameResponse(e.getMessage(), null);
        }

        ArrayList<GameData> gamesList = null;
        try {
            gamesList = gameDAO.listGames();
        } catch (DataAccessException e) {
            res.status(500);
            return new ListGameResponse(e.getMessage(), null);
        }
        return new ListGameResponse(null, gamesList);
    }
}

