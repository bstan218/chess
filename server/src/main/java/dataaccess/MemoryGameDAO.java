package dataaccess;

import model.GameData;
import java.util.ArrayList;
import java.util.HashMap;

public class MemoryGameDAO implements GameDAO {
    private HashMap<Integer, GameData> games;
    private int nextID;

    public MemoryGameDAO() {
        this.games = new HashMap<>();
        this.nextID = 1;
    }

    @Override
    public int createGame(String gameName) throws DataAccessException {
        if (gameName == null) throw new DataAccessException("Error: no game name provided");
        return addGameToGames(gameName);
    }

    @Override
    public GameData getGame(int gameID) throws DataAccessException {
        if (!games.containsKey(gameID)) throw new DataAccessException("Error: That game does not exist.");
        return games.get(gameID);
    }

    @Override
    public ArrayList<GameData> listGames() {
        ArrayList<GameData> gamesList = new ArrayList<>();
        games.forEach((key, value) -> {
            gamesList.add(value);
        });
        return gamesList;
    }

    @Override
    public void updateGame(int gameID, GameData gameData) throws  DataAccessException {
        if (!games.containsKey(gameID)) throw new DataAccessException("Error: That game does not exist.");
        games.put(gameID, gameData);
    }

    @Override
    public void deleteAllGames() {
        games.clear();
    }

    private int addGameToGames(String gameName) {
        GameData newGame = new GameData(nextID, null, null, gameName, null);
        nextID += 1;
        games.put(newGame.gameID(), newGame);
        return newGame.gameID();
    }

}
