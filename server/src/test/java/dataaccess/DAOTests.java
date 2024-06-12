package dataaccess;

import chess.response.UserResponse;
import org.junit.jupiter.api.*;
import model.*;
import service.ClearService;
import service.DummyResponseStub;
import service.GameService;
import service.UserService;

public class DAOTests {
    private static UserDAO userDAO;
    private static AuthDAO authDAO;
    private static GameDAO gameDAO;

    private static UserService userService;
    private static ClearService clearService;

    private static UserData existingUser;
    private static String exisitingAuthToken;

    private static int existingGameID;

    @BeforeAll
    public static void init() {
        userDAO = new SqlUserDAO();
        authDAO = new SqlAuthDAO();
        gameDAO = new SqlGameDAO();

        userService = new UserService(userDAO, authDAO);
        GameService gameService = new GameService(userDAO, authDAO, gameDAO);
        clearService = new ClearService(userDAO, authDAO, gameDAO);

    }

    @BeforeEach
    public void setup() throws DataAccessException {
        clearService.clear();

        existingUser = new UserData("Existing User",
                "existing user's password",
                "existing@gmail.com");


        UserResponse userResponse = userService.register(existingUser, new DummyResponseStub());
        exisitingAuthToken = userResponse.authToken();
        AuthData existingAuthorization = new AuthData(exisitingAuthToken, userResponse.username());

        existingGameID = gameDAO.createGame("existing game");


    }

    @Test
    @Order(1)
    @DisplayName("Good getUser")
    public void successGetUser() {
        try {
            UserData dbUser = userDAO.getUser(existingUser.username());
            Assertions.assertEquals(dbUser.username(), existingUser.username());
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    @Order(2)
    @DisplayName("bad get user")
    public void failGetUser() {
        try {
            UserData newUser = new UserData("newUser", "password", "email.com");
            userDAO.getUser(newUser.username());
        } catch (DataAccessException e) {
            Assertions.assertEquals("Error: No user with that username", e.getMessage());
        }

    }

    @Test
    @Order(3)
    @DisplayName("SuccessCreateUser")
    public void successCreateUser() {
        try {
            UserData newUser = new UserData("newUser", "password", "email.com");
            userDAO.createUser(newUser);
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    @Order(4)
    @DisplayName("fail create user")
    public void  failCreateUser() {
        try {
            UserData newUser = new UserData("newUser", null, "email.com");
            userDAO.createUser(newUser);
            throw new RuntimeException("fail");
        } catch (DataAccessException ignored) {

        }

    }

    @Test
    @Order(5)
    @DisplayName("Success clear db")
    public void successClearDB() throws DataAccessException {
        userDAO.deleteAllUsers();
    }

    @Test
    @Order(6)
    @DisplayName("success delete db")
    public void deleteAuths() throws DataAccessException {
        authDAO.deleteAllAuths();
    }

    @Test
    @Order(7)
    @DisplayName("success create auth")
    public void successCreateAuth() throws DataAccessException {
        authDAO.createAuth("username");
    }

    @Test
    @Order(8)
    @DisplayName("fail create auth")
    public void failCreateAuth() {
        try {
            authDAO.createAuth(null);
            throw new RuntimeException();
        } catch (Exception ignored) {

        }
    }

    @Test
    @Order(9)
    @DisplayName("success get auth")
    public void successGetAuth() throws DataAccessException {
        authDAO.getAuth(exisitingAuthToken);
    }

    @Test
    @Order(10)
    @DisplayName("fail get auth")
    public void failGetAuth() {
        try {
            authDAO.getAuth(null);
            throw new RuntimeException();
        } catch (DataAccessException ignored) {
        }
    }

    @Test
    @Order(11)
    @DisplayName("success delete auth")
    public void successDeleteAuth() {
        authDAO.deleteAuth(exisitingAuthToken);
    }

    @Test
    @Order(12)
    @DisplayName("fail delete auth")
    public void failDeleteAuth() {
        try {
            authDAO.deleteAuth(null);
            throw new DataAccessException("didnt fail");
        } catch (Exception ignored) {

        }
    }

    @Test
    @Order(13)
    @DisplayName("Success delete games")
    public void deleteGames() throws DataAccessException {
        gameDAO.deleteAllGames();
    }

    @Test
    @Order(14)
    @DisplayName("success create game")
    public void successCreateGame() throws DataAccessException {
        gameDAO.createGame("new game");
    }

    @Test
    @Order(15)
    @DisplayName("fail create game")
    public void failCreateGame() {
        try {
            gameDAO.createGame(null);
            throw new DataAccessException("didnt fail");
        } catch (Exception ignored) {

        }
    }

    @Test
    @Order(16)
    @DisplayName("success update game")
    public void successUpdateGame() throws DataAccessException {
        gameDAO.updateGame(existingGameID, new GameData(existingGameID, "New Player", null, "game name", null));
    }

    @Test
    @Order(17)
    @DisplayName("fail update game")
    public void failUpdateGame() {
        try {
            gameDAO.updateGame(0, new GameData(existingGameID, "New Player", null, "game name", null));
            throw new DataAccessException("didnt fail");
        } catch (Exception ignored) {

        }

    }

    @Test
    @Order(18)
    @DisplayName("success list games")
    public void sListGames() throws DataAccessException {
        gameDAO.listGames();
    }

    @Test
    @Order(19)
    @DisplayName("success get game")
    public void sGetGame() throws DataAccessException {
        gameDAO.getGame(existingGameID);
    }

    @Test
    @Order(20)
    @DisplayName("fail get game")
    public void fGetGame() throws DataAccessException {
        try {
            gameDAO.getGame(0);

            throw new DataAccessException("didnt fail");
        } catch (Exception ignored) {

        }

    }


}

