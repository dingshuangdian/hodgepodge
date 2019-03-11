package com.lsqidsd.hodgepodge.http;

public enum State {
    CACHE("cache"),
    HEAD("head"),
    PARAMS("params"),
    DOWM("dowm");
    private String state;
    public String getState() {
        return state;
    }
    State(String state) {
        this.state = state;
    }
    public void setState(String state) {
        this.state = state;
    }
}
