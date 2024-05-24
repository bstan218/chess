package service;

import dataaccess.*;
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

    }

    @BeforeEach
    public void setup() {
        userDAO.deleteAllUsers();
        authDAO.deleteAllAuths();
        gameDAO.deleteAllGames();

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


}
