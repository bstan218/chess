package dataaccess;

import model.GameData;

import java.util.ArrayList;

public interface GameDAO {
    int createGame(String gameName) throws DataAccessException;
    ArrayList<GameData> listGames();
    void updateGame(int gameID, String gameString);
    void deleteAllGames();
}
