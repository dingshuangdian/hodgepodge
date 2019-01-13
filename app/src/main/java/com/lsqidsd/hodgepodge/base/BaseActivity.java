package com.lsqidsd.hodgepodge.base;
import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import com.lsqidsd.hodgepodge.utils.StatusBarUtil;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public abstract class BaseActivity extends FragmentActivity {
    private PermissionHandler mHandler;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        StatusBarUtil.setRootViewFitsSystemWindows(this, true);
        StatusBarUtil.setTranslucentStatus(this);
        //StatusBarUtil.setStatusBarColor(this, R.color.edit_stroke);
        if (!StatusBarUtil.setStatusBarDarkTheme(this, true)) {
            StatusBarUtil.setStatusBarColor(this, 0x55000000);
        }
    }

    public abstract int getLayout();

    public abstract void initView();

    public <T extends ViewDataBinding> T getBinding(T t) {
        t = DataBindingUtil.setContentView(this, getLayout());
        return t;
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
        new AlertDialog.Builder(this).setTitle("权限申请").setMessage("在设置-应用-大众生活商家版-权限中开启" + permission + "权限，以正常使用大众生活商家版功能").setPositiveButton("去开启", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);

                dialog.dismiss();
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (mHandler != null)
                    mHandler.onDenied();
                dialog.dismiss();
            }
        }).setCancelable(false).show();
    }
}
