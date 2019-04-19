package com.lsqidsd.hodgepodge.http.download;

public interface DwonProgress {
    void progress(int progress, float downSize, float totleSize);

}
