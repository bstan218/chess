package server;

import dataaccess.*;
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

    private static UserDAO service;

    public Server() {
        clearHandler = new ClearHandler();
        registerHandler = new RegisterHandler();
        loginHandler = new LoginHandler();
        logoutHandler = new LogoutHandler();
        listGameHandler = new ListGameHandler();
        createGameHandler = new CreateGameHandler();
        joinGameHandler = new JoinGameHandler();

        service = new MemoryUserDAO();
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
        Spark.delete("/db", (req, res) ->
                clearHandler.handleRequest(service, req, res));
        Spark.post("/user", (req, res) ->
                registerHandler.handleRequest(service, req, res));
        Spark.post("/session", (req, res) ->
                loginHandler.handleRequest(service, req, res));
        Spark.delete("/session", (req, res) ->
                logoutHandler.handleRequest(service, req, res));
        Spark.get("/game", (req, res) ->
                listGameHandler.handleRequest(service, req, res));
        Spark.post("/game", (req, res) ->
                createGameHandler.handleRequest(service, req, res));
        Spark.put("/game", (req, res) ->
                joinGameHandler.handleRequest(service, req, res));
    }
}
