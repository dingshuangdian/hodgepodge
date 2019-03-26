package com.lsqidsd.hodgepodge.utils;


import android.support.annotation.IntRange;

import com.lsqidsd.hodgepodge.http.download.DownloadStatus;
import com.lsqidsd.hodgepodge.http.download.InnerConstant;


public class DebugUtils {
    public static String getStatusDesc(@IntRange(from = DownloadStatus.WAIT, to = DownloadStatus.FAIL) int status){
        switch (status){
            case DownloadStatus.WAIT:
                return " wait ";
            case DownloadStatus.PREPARE:
                return " prepare ";
            case DownloadStatus.LOADING:
                return " loading ";
            case DownloadStatus.PAUSE:
                return " pause ";
            case DownloadStatus.COMPLETE:
                return " complete ";
            case DownloadStatus.FAIL:
                return " fail ";
            default:
                return "  错误的未知状态 ";
        }
    }

    public static String getRequestDictateDesc(@IntRange(from = InnerConstant.Request.loading, to = InnerConstant.Request.pause) int dictate){
        switch (dictate){
            case InnerConstant.Request.loading:
                return " loading ";
            case InnerConstant.Request.pause:
                return " pause ";
            default:
                return " dictate描述错误  ";
        }
    }
}
