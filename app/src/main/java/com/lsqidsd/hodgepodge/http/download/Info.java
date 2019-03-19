package com.lsqidsd.hodgepodge.http.download;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.Transient;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Info {
    private long id;
    private String savePath;
    private long countLength;
    private long readLength;
    private String url;
    @Transient
    private DownService service;

    public DownService getService() {
        return service;
    }

    public void setService(DownService service) {
        this.service = service;
    }

    @Transient
    private HttpDownOnNextListener listener;

    public HttpDownOnNextListener getListener() {
        return listener;
    }

    public void setListener(HttpDownOnNextListener listener) {
        this.listener = listener;
    }


    public void setState(State state) {
        setStateInte(state.getState());
    }

    private int stateInte;

    @Generated(hash = 786316455)
    public Info(long id, String savePath, long countLength, long readLength,
                String url, int stateInte) {
        this.id = id;
        this.savePath = savePath;
        this.countLength = countLength;
        this.readLength = readLength;
        this.url = url;
        this.stateInte = stateInte;
    }

    @Keep
    public Info(String url) {
        setUrl(url);
    }

    @Generated(hash = 614508582)
    public Info() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSavePath() {
        return this.savePath;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }

    public long getCountLength() {
        return this.countLength;
    }

    public void setCountLength(long countLength) {
        this.countLength = countLength;
    }

    public long getReadLength() {
        return this.readLength;
    }

    public void setReadLength(long readLength) {
        this.readLength = readLength;
    }

    public int getStateInte() {
        return stateInte;
    }

    public void setStateInte(int stateInte) {
        this.stateInte = stateInte;
    }

    public State getState() {
        switch (getStateInte()) {
            case 0:
                return State.START;
            case 1:
                return State.DOWN;
            case 2:
                return State.PAUSE;
            case 3:
                return State.STOP;
            case 4:
                return State.ERROR;
            case 5:
                return State.END;
            default:
                return State.END;
        }
    }


}
