package handler;

import service.ClearService;
import spark.Request;
import spark.Response;

public class ClearHandler {
    private final ClearService service;

    public ClearHandler(ClearService clearService) {
        this.service = clearService;
    }

    public Object handleRequest(Request req, Response res) {
        service.clear();
        return "";
    }
}
