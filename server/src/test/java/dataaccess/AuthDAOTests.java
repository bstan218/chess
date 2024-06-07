package dataaccess;

import handler.response.UserResponse;
import org.junit.jupiter.api.*;
import model.*;
import service.DummyResponseStub;
import service.UserService;

public class AuthDAOTests {
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
    @DisplayName("success delete db")
    public void deleteAuths() throws DataAccessException {
        authDAO.deleteAllAuths();
    }


}