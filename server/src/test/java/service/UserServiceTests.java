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

    @BeforeAll
    public static void init() {
        userDAO = new MemoryUserDAO();
        authDAO = new MemoryAuthDAO();

        userService = new UserService(userDAO, authDAO);

    }

    @BeforeEach
    public void setup() {
        userDAO.deleteAllUsers();
        authDAO.deleteAllAuths();

        existingUser = new UserData("Existing User",
                "existing user's password",
                "existing@gmail.com");


        UserResponse userResponse = userService.register(existingUser, new DummyResponseStub());
        exisitingAuthToken = userResponse.authToken();
    }

    @Test
    @Order(1)
    @DisplayName("Normal Register")
    public void successRegister() {
        DummyResponseStub dummyResponseStub = new DummyResponseStub();

        UserData newUser = new UserData("New User", "1234", "1234@gmail.com");

        userService.register(newUser, dummyResponseStub);

        Assertions.assertEquals(200, dummyResponseStub.status());
    }

    @Test
    @Order(2)
    @DisplayName("Register with existing username")
    public void failRegister() {
        DummyResponseStub dummyResponseStub = new DummyResponseStub();

        UserData newUser = new UserData("Existing User", "1234", "1234@gmail.com");

        userService.register(newUser, dummyResponseStub);

        Assertions.assertEquals(403, dummyResponseStub.status());
    }

}
