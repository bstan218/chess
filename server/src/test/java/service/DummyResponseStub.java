package service;

import spark.Response;

public class DummyResponseStub extends Response {
    private int statusCode;
    private String body;
    // You can add other fields as needed for testing

    public DummyResponseStub () {
        statusCode = 200;
    }

    @Override
    public void status(int statusCode) {
        this.statusCode = statusCode;
    }

    @Override
    public int status() {
        return statusCode;
    }

    @Override
    public void body(String body) {
        this.body = body;
    }

    @Override
    public String body() {
        return body;
    }

    // You can override other methods as needed for testing
}
