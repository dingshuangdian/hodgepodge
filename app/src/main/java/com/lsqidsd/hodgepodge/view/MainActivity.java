package com.lsqidsd.hodgepodge.view;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.widget.TabHost;
import android.widget.TabWidget;
import com.lsqidsd.hodgepodge.R;
import com.lsqidsd.hodgepodge.base.BaseActivity;
import com.lsqidsd.hodgepodge.databinding.MainActivityBinding;
import com.lsqidsd.hodgepodge.databinding.TabFootBinding;
import com.lsqidsd.hodgepodge.utils.TabDb;

public class MainActivity extends BaseActivity implements TabHost.OnTabChangeListener {
    private MainActivityBinding binding;
    private TabFootBinding footBinding;

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
        requestReadAndWriteSDPermission(new BaseActivity.PermissionHandler() {
            @Override
            public void onGranted() {
            }
        });
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
}
