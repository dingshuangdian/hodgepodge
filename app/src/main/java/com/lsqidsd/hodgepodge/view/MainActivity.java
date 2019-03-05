package com.lsqidsd.hodgepodge.view;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.databinding.DataBindingUtil;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.widget.TabHost;
import android.widget.TabWidget;

import com.lsqidsd.hodgepodge.R;
import com.lsqidsd.hodgepodge.base.BaseActivity;
import com.lsqidsd.hodgepodge.base.BaseConstant;
import com.lsqidsd.hodgepodge.databinding.MainActivityBinding;
import com.lsqidsd.hodgepodge.databinding.TabFootBinding;
import com.lsqidsd.hodgepodge.service.DownLoadService;
import com.lsqidsd.hodgepodge.utils.Jump;
import com.lsqidsd.hodgepodge.utils.TabDb;

public class MainActivity extends BaseActivity implements TabHost.OnTabChangeListener {
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
        //checkDownLoad();
//        requestReadAndWriteSDPermission(new BaseActivity.PermissionHandler() {
//            @Override
//            public void onGranted() {
//            }
//        });
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
                myBinder.startDownload();
                myBinder.getProgress();
                bind = true;

            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
                //服务异常终止调用
                bind = false;

            }
        };
        Jump.jumpToService(this, DownLoadService.class, connection);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BaseConstant.params.clear();
        BaseConstant.params = null;
        if (bind) {
            this.unbindService(connection);
            bind = false;
        }

    }
}
