package dataaccess;

import handler.response.UserResponse;
import org.junit.jupiter.api.*;
import model.*;
import service.DummyResponseStub;
import service.UserService;

public class UserDAOTests {
    private static UserDAO userDAO;
    private static AuthDAO authDAO;

    private static UserService userService;

    private static UserData existingUser;
    private static String exisitingAuthToken;
    private static AuthData existingAuthorization;

    private static DummyResponseStub dummyResponseStub;

    @BeforeAll
    public static void init() {
        userDAO = new SqlUserDAO();
        authDAO = new SqlAuthDAO();

        userService = new UserService(userDAO, authDAO);

    }

    @BeforeEach
    public void setup() {
        try {
            userDAO.deleteAllUsers();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
        try {
            authDAO.deleteAllAuths();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }

        existingUser = new UserData("Existing User",
                "existing user's password",
                "existing@gmail.com");


        UserResponse userResponse = userService.register(existingUser, new DummyResponseStub());
        exisitingAuthToken = userResponse.authToken();
        existingAuthorization = new AuthData(exisitingAuthToken, userResponse.username());

        dummyResponseStub = new DummyResponseStub();
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
}

