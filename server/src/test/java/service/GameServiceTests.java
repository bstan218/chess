package service;

import chess.ChessGame;
import dataaccess.*;
import handler.request.JoinGameRequest;
import handler.response.CreateGameResponse;
import handler.response.UserResponse;
import model.AuthData;
import model.GameData;
import model.UserData;
import org.junit.jupiter.api.*;

public class GameServiceTests {
    private static UserDAO userDAO;
    private static AuthDAO authDAO;
    private static GameDAO gameDAO;

    private static UserService userService;
    private static GameService gameService;
    private static ClearService clearService;

    private static UserData existingUser;
    private static String exisitingAuthToken;
    private static AuthData existingAuthorization;
    private static GameData existingGame;

    private static DummyResponseStub dummyResponseStub;

    @BeforeAll
    public static void init() {
        userDAO = new MemoryUserDAO();
        authDAO = new MemoryAuthDAO();
        gameDAO = new MemoryGameDAO();

        userService = new UserService(userDAO, authDAO);
        gameService = new GameService(userDAO, authDAO, gameDAO);
        clearService = new ClearService(userDAO, authDAO, gameDAO);

    }

    @BeforeEach
    public void setup() {
        clearService.clear();

        existingUser = new UserData("Existing User",
                "existing user's password",
                "existing@gmail.com");

        UserResponse userResponse = userService.register(existingUser, new DummyResponseStub());
        exisitingAuthToken = userResponse.authToken();
        existingAuthorization = new AuthData(exisitingAuthToken, userResponse.username());

        dummyResponseStub = new DummyResponseStub();

        CreateGameResponse createGameResponse = gameService.createGame(existingAuthorization,
                new GameData(0,null,null, "Existing Game", null),
                dummyResponseStub);

        try {
            existingGame = gameDAO.getGame(createGameResponse.gameID());
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    @Order(1)
    @DisplayName("Successful create game")
    public void successCreateGame() {
        gameService.createGame(existingAuthorization,
                new GameData(0, null, null, "New Game", null),
                dummyResponseStub);

        Assertions.assertEquals(200, dummyResponseStub.status());
    }

    @Test
    @Order(2)
    @DisplayName("Try to create game without auth token")
    public void failCreateGame() {
        gameService.createGame(new AuthData(null, null),
                new GameData(0, null, null, "New Game", null),
                dummyResponseStub);

        Assertions.assertEquals(401, dummyResponseStub.status());

    }

    @Test
    @Order(3)
    @DisplayName("Successfully list games")
    public void successListGames() {
        gameService.listGames(existingAuthorization, dummyResponseStub);

        Assertions.assertEquals(200, dummyResponseStub.status());
    }

    @Test
    @Order(4)
    @DisplayName("try to list games without auth token")
    public void failListGames() {
        gameService.listGames(new AuthData(null, null), dummyResponseStub);

        Assertions.assertEquals(401, dummyResponseStub.status());
    }

    @Test
    @Order(5)
    @DisplayName("Successfully join game")
    public void successJoinGame() {
        JoinGameRequest joinGameRequest = new JoinGameRequest(ChessGame.TeamColor.BLACK, existingGame.gameID());
        gameService.joinGame(existingAuthorization, joinGameRequest, dummyResponseStub);

        Assertions.assertEquals(200, dummyResponseStub.status());
    }

    @Test
    @Order(6)
    @DisplayName("Trying to join as taken color")
    public void failJoinGame() {
        JoinGameRequest joinGameRequest = new JoinGameRequest(ChessGame.TeamColor.BLACK, existingGame.gameID());
        gameService.joinGame(existingAuthorization, joinGameRequest, dummyResponseStub);

        JoinGameRequest newJoinGameRequest = new JoinGameRequest(ChessGame.TeamColor.BLACK, existingGame.gameID());
        gameService.joinGame(existingAuthorization, newJoinGameRequest, dummyResponseStub);

        Assertions.assertEquals(403, dummyResponseStub.status());
    }

    @Test
    @Order(7)
    @DisplayName("clear test")
    public void successClear() {
        clearService.clear();

        Assertions.assertTrue(true);
    }


}
