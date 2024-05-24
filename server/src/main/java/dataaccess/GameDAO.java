package dataaccess;

import model.GameData;

import java.util.ArrayList;

public interface GameDAO {
    int createGame(String gameName) throws DataAccessException;
    GameData getGame(int gameID) throws DataAccessException;
    ArrayList<GameData> listGames();
    void updateGame(int gameID, GameData gameData) throws DataAccessException;
    void deleteAllGames();

}
