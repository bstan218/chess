package service;

import dataaccess.*;
import handler.response.UserResponse;
import org.junit.jupiter.api.*;
import model.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserServiceTests {
    private static UserDAO userDAO;
    private static AuthDAO authDAO;

    private static UserService userService;

    private static UserData existingUser;
    private static String exisitingAuthToken;
    private static AuthData existingAuthorization;

    private static DummyResponseStub dummyResponseStub;

    @BeforeAll
    public static void init() {
        userDAO = new MemoryUserDAO();
        authDAO = new MemoryAuthDAO();

        userService = new UserService(userDAO, authDAO);

    }

    @BeforeEach
    public void setup() {
        try {
            userDAO.deleteAllUsers();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
        authDAO.deleteAllAuths();

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
    @DisplayName("Normal Register")
    public void successRegister() {
        UserData newUser = new UserData("New User", "1234", "1234@gmail.com");

        userService.register(newUser, dummyResponseStub);

        Assertions.assertEquals(200, dummyResponseStub.status());
    }

    @Test
    @Order(2)
    @DisplayName("Register with existing username")
    public void failRegister() {
        UserData newUser = new UserData("Existing User", "1234", "1234@gmail.com");

        userService.register(newUser, dummyResponseStub);

        Assertions.assertEquals(403, dummyResponseStub.status());
    }

    @Test
    @Order(3)
    @DisplayName("Normal Login")
    public void successLogin() {
        userService.login(existingUser, dummyResponseStub);

        Assertions.assertEquals(200, dummyResponseStub.status());
    }

    @Test
    @Order(4)
    @DisplayName("Login with no password")
    public void failLogin() {
        UserData passwordlessUserData = new UserData("Existing User", null, null);

        userService.login(passwordlessUserData, dummyResponseStub);

        Assertions.assertEquals(401, dummyResponseStub.status());
    }

    @Test
    @Order(5)
    @DisplayName("Successful logout existing user")
    public void successLogout() {
        userService.logout(existingAuthorization, dummyResponseStub);

        Assertions.assertEquals(200, dummyResponseStub.status());
    }

    @Test
    @Order(6)
    @DisplayName("No auth token")
    public void failLogout() {
        AuthData tokenlessAuthData = new AuthData(null, null);

        userService.logout(tokenlessAuthData, dummyResponseStub);

        Assertions.assertEquals(401, dummyResponseStub.status());
    }

}
