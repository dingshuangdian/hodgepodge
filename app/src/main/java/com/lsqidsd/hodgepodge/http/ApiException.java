package com.lsqidsd.hodgepodge.http;

public class ApiException extends Exception {
    private int code;
    private String displayMessage;

    public ApiException(int code, String displayMessage) {
        this.code = code;
        this.displayMessage = displayMessage;
    }

    public ApiException(String message, int code, String displayMessage) {
        super(message);
        this.code = code;
        this.displayMessage = displayMessage;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDisplayMessage() {
        return displayMessage;
    }

    public void setDisplayMessage(String displayMessage) {
        this.displayMessage = displayMessage;
    }
}
