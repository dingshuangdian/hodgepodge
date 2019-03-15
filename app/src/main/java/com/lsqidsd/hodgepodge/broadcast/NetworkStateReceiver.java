package com.lsqidsd.hodgepodge.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

import com.lsqidsd.hodgepodge.base.BaseActivity;
import com.lsqidsd.hodgepodge.utils.NetUtil;

public class NetworkStateReceiver extends BroadcastReceiver {
    public NetChangeListener listener = BaseActivity.listener;

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
            int netWorkState = NetUtil.getNetWorkState(context);
            // 当网络发生变化，判断当前网络状态，并通过NetEvent回调当前网络状态
            if (listener != null) {
                listener.onChangeListener(netWorkState);
            }
        }
    }
    // 自定义接口
    public interface NetChangeListener {
        void onChangeListener(int status);
    }
}
