package server;

import dataaccess.*;

import handler.utils.FromJson;
import handler.utils.FromJsonG;
import handler.utils.ToJson;
import handler.utils.ToJsonG;
import service.ClearService;
import service.GameService;
import service.UserService;
import spark.*;
import handler.*;

public class Server {
    private static ClearHandler clearHandler;
    private static RegisterHandler registerHandler;
    private static LoginHandler loginHandler;
    private static LogoutHandler logoutHandler;
    private static ListGameHandler listGameHandler;
    private static CreateGameHandler createGameHandler;
    private static JoinGameHandler joinGameHandler;

    public Server() {
        UserDAO userDAO = new MemoryUserDAO();
        AuthDAO authDAO = new MemoryAuthDAO();
        GameDAO gameDAO = new MemoryGameDAO();

        ClearService clearService = new ClearService(userDAO, authDAO, gameDAO);
        GameService gameService = new GameService(userDAO, authDAO, gameDAO);
        UserService userService = new UserService(userDAO, authDAO);

        FromJson fromJson = new FromJsonG();
        ToJson toJson = new ToJsonG();

        clearHandler = new ClearHandler(clearService);
        registerHandler = new RegisterHandler(userService, fromJson, toJson);
        loginHandler = new LoginHandler(userService, fromJson, toJson);
        logoutHandler = new LogoutHandler(userService, fromJson, toJson);
        listGameHandler = new ListGameHandler(gameService, fromJson, toJson);
        createGameHandler = new CreateGameHandler(gameService, fromJson, toJson);
        joinGameHandler = new JoinGameHandler(gameService, fromJson, toJson);
    }

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.
        createRoutes();

        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }

    private static void createRoutes() {
        //Spark.get("/db", (req, res) -> "Hello BYU!");
        Spark.delete("/db", (req, res) ->
                clearHandler.handleRequest(req, res));
        Spark.post("/user", (req, res) ->
                registerHandler.handleRequest(req, res));
        Spark.post("/session", (req, res) ->
                loginHandler.handleRequest(req, res));
        Spark.delete("/session", (req, res) ->
                logoutHandler.handleRequest(req, res));
        Spark.get("/game", (req, res) ->
                listGameHandler.handleRequest(req, res));
        Spark.post("/game", (req, res) ->
                createGameHandler.handleRequest(req, res));
        Spark.put("/game", (req, res) ->
                joinGameHandler.handleRequest(req, res));
    }
}
