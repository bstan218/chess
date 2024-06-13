package client;

import client.websocket.ServerMessageObserver;
import org.junit.jupiter.api.*;
import server.Server;
import websocket.messages.ServerMessage;

import java.net.URISyntaxException;


public class ServerFacadeTests {

    private static Server server;
    private static ServerFacade serverFacade;
    private static HttpCommunicator httpCommunicator;
    private String authToken;

    @BeforeAll
    public static void init() throws ResponseException, URISyntaxException {
        server = new Server();
        var port = server.run(0);
        System.out.println("Started test HTTP server on " + port);

        httpCommunicator = new HttpCommunicator("http://localhost:" + port);
        httpCommunicator.makeRequest("DELETE", "/db", null, null, null);

        serverFacade = new ServerFacade("http://localhost:" + port, new ServerMessageObserver() {
            @Override
            public void notify(ServerMessage message) {

            }
        });


    }

    @BeforeEach
    public void populateDatabase() throws ResponseException {
        String[] registerInfo = {"existinguser", "password", "1234"};
        authToken = serverFacade.register(registerInfo);
        serverFacade.createGame(new String[]{"existing game"}, authToken);

    }

    @AfterEach
    public void clearDatabase() {
        try {
            httpCommunicator.makeRequest("DELETE", "/db", null, null, null);
        } catch (ResponseException e) {
            throw new RuntimeException(e);
        }
    }


    @AfterAll
    static void stopServer() {
        server.stop();
    }

    @Order(1)
    @Test
    public void successRegister() {
        String[] registerInfo = {"b", "b", "b"};
        try {
            serverFacade.register(registerInfo);
        } catch (ResponseException e) {
            throw new RuntimeException(e);
        }
    }

    @Order(2)
    @Test
    public void failRegister() {
        String[] registerInfo = {"b", "c"};
        try {
            serverFacade.register(registerInfo);
            throw new RuntimeException();
        } catch (Exception ignored) {
        }
    }

    @Order(3)
    @Test
    public void successfulLogin() throws ResponseException {
        String[] loginInfo = {"b", "b"};
        String[] registerInfo = {"b", "b", "b"};
        serverFacade.register(registerInfo);
        try {
            serverFacade.login(loginInfo);
        } catch (ResponseException e) {
            throw new RuntimeException(e);
        }
    }

    @Order(4)
    @Test
    public void failedLogin() {
        String[] loginInfo = {"b", "c"};
        try {
            serverFacade.login(loginInfo);
            throw new RuntimeException();
        } catch (ResponseException ignored) {
        }
    }

    @Order(5)
    @Test
    public void successfulLogout() throws ResponseException {
        serverFacade.logout(authToken);
    }

    @Order(6)
    @Test
    public void failedLogout() {
        try {
            serverFacade.logout("this aint an auth token");
            throw new RuntimeException();
        } catch (ResponseException ignored) {}
    }

    @Order(7)
    @Test
    public void successCreateGame() throws ResponseException {
        serverFacade.createGame(new String[]{"new game"}, authToken);
    }

    @Order(8)
    @Test
    public void failedCreateGame() {
        try {
            serverFacade.createGame(new String[]{"new game"}, "dummy auth token");
            throw new RuntimeException();
        } catch (ResponseException ignored) {}
    }

    @Order(9)
    @Test
    public void successListGames() throws ResponseException {
        serverFacade.listGames(authToken);
    }

    @Order(10)
    @Test
    public void failListGames() {
        try {
            serverFacade.listGames("dummy auth token");
            throw new RuntimeException();
        } catch (ResponseException ignored) {}
    }

    @Order(11)
    @Test
    public void successPlayGame() throws ResponseException {
        serverFacade.playGame(new String[]{"1", "white"}, serverFacade.listGames(authToken), authToken);
    }

    @Order(12)
    @Test
    public void failPlayGame(){
        try {
            serverFacade.playGame(new String[]{"2", "white"}, serverFacade.listGames(authToken), authToken);
            throw new RuntimeException();
        } catch (Exception ignored) {}
    }
}
