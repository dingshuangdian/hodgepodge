package com.lsqidsd.hodgepodge.view;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.databinding.DataBindingUtil;
import android.os.IBinder;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TabWidget;
import com.lsqidsd.hodgepodge.R;
import com.lsqidsd.hodgepodge.base.BaseActivity;
import com.lsqidsd.hodgepodge.databinding.MainActivityBinding;
import com.lsqidsd.hodgepodge.databinding.TabFootBinding;
import com.lsqidsd.hodgepodge.service.DownLoadService;
import com.lsqidsd.hodgepodge.utils.Jump;
import com.lsqidsd.hodgepodge.utils.TabDb;
public class MainActivity extends BaseActivity implements TabHost.OnTabChangeListener, DownLoadService.DownloadFinish {
    private MainActivityBinding binding;
    private TabFootBinding footBinding;
    private DownLoadService.MyBinder myBinder;
    private ServiceConnection connection;
    private boolean bind = false;

    @Override
    public int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {

        binding = getBinding(binding);
        //设置view
        binding.mainTab.setup(MainActivity.this, getSupportFragmentManager(), binding.mainView.getId());
        //去除分割线
        binding.mainTab.getTabWidget().setDividerDrawable(null);
        binding.mainTab.setOnTabChangedListener(MainActivity.this);
        binding.mainTab.onTabChanged(TabDb.getTabsTxt()[0]);
        initTab();
        showDialog();
    }

    public void initTab() {
        String[] tabs = TabDb.getTabsTxt();
        for (int i = 0; i < tabs.length; i++) {
            TabHost.TabSpec tabSpec = binding.mainTab.newTabSpec(tabs[i]);
            footBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.tab_foot, null, false);
            footBinding.footTv.setText(tabs[i]);
            footBinding.footIv.setImageResource(TabDb.getTabsImg()[i]);
            tabSpec.setIndicator(footBinding.getRoot());
            binding.mainTab.addTab(tabSpec, TabDb.getFragment()[i], null);
        }
    }

    @Override
    public void onTabChanged(String s) {
        TabWidget tabWidget = binding.mainTab.getTabWidget();
        for (int i = 0; i < tabWidget.getTabCount(); i++) {
            footBinding = DataBindingUtil.getBinding(tabWidget.getChildTabViewAt(i));
            if (i == binding.mainTab.getCurrentTab()) {
                footBinding.footTv.setTextColor(getResources().getColor(R.color.colorSelect));
                footBinding.footIv.setImageResource(TabDb.getTabsLightImg()[i]);
            } else {
                footBinding.footTv.setTextColor(getResources().getColor(R.color.colorNormal));
                footBinding.footIv.setImageResource(TabDb.getTabsImg()[i]);
            }
        }
    }


    private void checkDownLoad() {
        connection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                myBinder = (DownLoadService.MyBinder) iBinder;
                //在Activity中调用Service里面的方法。
                myBinder.startDownload(MainActivity.this, MainActivity.this::downfinish);
                bind = true;
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
                //服务异常终止调用
                bind = false;
            }
        };
        Jump.bindService(MainActivity.this, DownLoadService.class, connection);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bind) {
            this.unbindService(connection);
            bind = false;
        }

    }

    @Override
    public void downfinish() {
        if (bind) {
            //this.unbindService(connection);
            bind = false;
        }
    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialog);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        double width = metrics.widthPixels * 0.9;
        AlertDialog alertDialog = builder
                .setTitle("版本更新提醒")
                .setCancelable(false)
                .setMessage("有新版本,是否现在更新？")
                .setPositiveButton("确定", (a, b) -> requestReadAndWriteSDPermission(new BaseActivity.PermissionHandler() {
                    @Override
                    public void onGranted() {
                        checkDownLoad();
                    }
                }))
                .setNegativeButton("取消", (a, b) ->
                        builder.create().dismiss())
                .create();
        alertDialog.show();
        alertDialog.getWindow().setLayout((int) width, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

}
