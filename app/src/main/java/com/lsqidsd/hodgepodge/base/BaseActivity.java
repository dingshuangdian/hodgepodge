package com.lsqidsd.hodgepodge.base;

import android.Manifest;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.widget.Toast;

import com.lsqidsd.hodgepodge.R;
import com.lsqidsd.hodgepodge.broadcast.NetworkStateReceiver;
import com.lsqidsd.hodgepodge.utils.StatusBarUtil;
import java.util.Date;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public abstract class BaseActivity extends FragmentActivity implements NetworkStateReceiver.NetChangeListener {
    private PermissionHandler mHandler;
    private NetworkStateReceiver receiver;
    public static NetworkStateReceiver.NetChangeListener listener;
    private long mLastBackTime = 0;
    private long TIME_DIFF = 2 * 1000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        StatusBarUtil.setRootViewFitsSystemWindows(this, true);
        StatusBarUtil.setTranslucentStatus(this);
        // StatusBarUtil.setStatusBarColor(this, R.color.edit_stroke);
        if (!StatusBarUtil.setStatusBarDarkTheme(this, true)) {
            StatusBarUtil.setStatusBarColor(this, 0x55000000);
        }
        listener = this;
        //Android 7.0以上需要动态注册
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N && !getClass().getSimpleName().equals("SplashScreenActivity")) {
            //实例化IntentFilter对象
            IntentFilter filter = new IntentFilter();
            filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
            receiver = new NetworkStateReceiver();
            //注册广播接收
            registerReceiver(receiver, filter);
        }
    }

    public abstract int getLayout();

    public abstract void initView();

    public <T extends ViewDataBinding> T getBinding(T t) {
        t = DataBindingUtil.setContentView(this, getLayout());
        return t;
    }

    @Override
    public void onChangeListener(int status) {
// TODO Auto-generated method stub
        switch (status) {
            case 0:
                //Toast.makeText(this, "已切换到2G/3G/4G/5G网络,请注意您的流量哦", Toast.LENGTH_SHORT).show();
                break;

            case 1:

                break;
            case -1:
                Toast.makeText(this, "网络连接失败，请检查网络是否可用", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    /**
     * 权限回调接口
     */
    public abstract class PermissionHandler {
        /**
         * 权限通过
         */
        public abstract void onGranted();

        /**
         * 权限拒绝
         */
        public void onDenied() {
            finish();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        BaseActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }
    //-----------------------------------------------------------

    /**
     * 请求读写SD卡权限
     *
     * @param permissionHandler
     */
    protected void requestReadAndWriteSDPermission(PermissionHandler permissionHandler) {
        this.mHandler = permissionHandler;
        BaseActivityPermissionsDispatcher.handleReadAndWriteSDPermissionWithCheck(this);
    }


    @NeedsPermission(value = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void handleReadAndWriteSDPermission() {
        if (mHandler != null)
            mHandler.onGranted();
    }

    @OnPermissionDenied(value = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void deniedReadAndWriteSDPermission() {
        if (mHandler != null)
            mHandler.onDenied();
    }

    @OnNeverAskAgain(value = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void OnReadAndWriteSDNeverAskAgain() {
        showDialog("[存储空间]");
    }

    public void showDialog(String permission) {
        new AlertDialog.Builder(this, R.style.AlertDialog).setTitle("权限申请").setMessage("在设置-应用-腾讯新闻权限中开启" + permission + "权限，以正常使用相关功能").setPositiveButton("去开启", (a, b) -> {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", getPackageName(), null);
            intent.setData(uri);
            startActivity(intent);
            a.dismiss();
        }).setNegativeButton("取消", (a, b) -> {
            if (mHandler != null)
                mHandler.onDenied();
            a.dismiss();
        }).setCancelable(false).show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (isTaskRoot()) {
                long now = new Date().getTime();
                if (now - mLastBackTime < TIME_DIFF) {
                    return super.onKeyDown(keyCode, event);
                } else {
                    mLastBackTime = now;
                    Toast.makeText(this, "再按一次返回键退出", Toast.LENGTH_SHORT).show();
                    return true;
                }
            }
        }
        return super.onKeyDown(keyCode, event);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N && !getClass().getSimpleName().equals("SplashScreenActivity")) {
            unregisterReceiver(receiver);
        }
    }
}

