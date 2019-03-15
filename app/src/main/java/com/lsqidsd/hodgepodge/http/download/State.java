package com.lsqidsd.hodgepodge.http.download;

public enum State {
    START(0),
    DOWN(1),
    PAUSE(2),
    STOP(3),
    ERROR(4),
    END(5);
    private int state;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    State(int state) {
        this.state = state;
    }
}
