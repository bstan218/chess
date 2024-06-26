package dataaccess;

import chess.ChessGame;
import com.google.gson.Gson;
import json.ToJsonG;
import model.GameData;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SqlGameDAO implements GameDAO {
    public SqlGameDAO() {
        try  {
            DatabaseManager.configureDatabase(createStatements);
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS  game (
                gameID int NOT NULL AUTO_INCREMENT,
                whiteUsername varchar(256) DEFAULT NULL,
                blackUsername varchar(256) DEFAULT NULL,
                gameName varchar(256) NOT NULL,
                json TEXT DEFAULT NULL,
                PRIMARY KEY (gameID),
                INDEX (gameName)
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
            """
    };


    @Override
    public int createGame(String gameName) throws DataAccessException {
        if (gameName == null) throw new DataAccessException("Error: no game name provided");
        try {
            getGameFromName(gameName);
        } catch (DataAccessException e) {
            var statement = "INSERT INTO game (gameName, json) VALUES (?, ?)";
            try {
                DatabaseManager.executeUpdate(statement, gameName, new ToJsonG().fromResponse(new ChessGame()));
            } catch (Exception ne) {
                throw new DataAccessException("Error: Unable to insert game into database");
            }

            return getGameFromName(gameName).gameID();
        } throw new DataAccessException("Game Name taken");
    }

    @Override
    public GameData getGame(int gameID) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT gameID, whiteUsername, blackUsername, gameName, json FROM game WHERE gameID=?";
            try (var ps = conn.prepareStatement(statement)) {
                ps.setInt(1, gameID);
                try(var rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return readGame(rs);
                    } else {
                        throw new DataAccessException("Error: No game with that gameID");
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private GameData getGameFromName(String gameName) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT gameID, whiteUsername, blackUsername, gameName, json FROM game WHERE gameName=?";
            try (var ps = conn.prepareStatement(statement)) {
                ps.setString(1, gameName);
                try(var rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return readGame(rs);
                    } else {
                        throw new DataAccessException("Error: No game with that gameID");
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    private GameData readGame(ResultSet rs) throws SQLException {
        var gameID = rs.getInt("gameID");
        var whiteUsername = rs.getString("whiteUsername");
        var blackUsername = rs.getString("blackUsername");
        var gameName = rs.getString("gameName");
        var json = rs.getString("json");
        var game = new Gson().fromJson(json, ChessGame.class);
        return new GameData(gameID, whiteUsername, blackUsername, gameName, game);
    }

    @Override
    public ArrayList<GameData> listGames() throws DataAccessException {
        var result = new ArrayList<GameData>();
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT gameID, whiteUsername, blackUsername, gameName, json FROM game";
            try (var ps = conn.prepareStatement(statement)) {
                try (var rs = ps.executeQuery()) {
                    while (rs.next()) {
                        result.add(readGame(rs));
                    }
                }
            }
        } catch (Exception e) {
            throw new DataAccessException("Couldn't Connect To Database");
        }

        return result;
    }

    @Override
    public void updateGame(int gameID, GameData gameData) throws DataAccessException {
        try {
            getGame(gameID);
        } catch (DataAccessException e) {
            throw new DataAccessException(e.getMessage());
        }

        var statement = "UPDATE game SET whiteUsername=?, blackUsername=?, json=? WHERE gameID=?";

        String game = new Gson().toJson(gameData.game());

        try {
            DatabaseManager.executeUpdate(statement, gameData.whiteUsername(), gameData.blackUsername(), game, gameID);
        } catch (Exception ne) {
            throw new DataAccessException("Error: Unable to insert game into database");
        }

    }

    @Override
    public void deleteAllGames() throws DataAccessException {
        var statement = "TRUNCATE game";
        DatabaseManager.executeUpdate(statement);
    }
}
