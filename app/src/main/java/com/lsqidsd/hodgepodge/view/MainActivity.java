package com.lsqidsd.hodgepodge.view;
import android.databinding.DataBindingUtil;
import android.util.Log;
import android.view.LayoutInflater;
import android.webkit.JavascriptInterface;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TabHost;
import android.widget.TabWidget;
import com.lsqidsd.hodgepodge.R;
import com.lsqidsd.hodgepodge.base.BaseActivity;
import com.lsqidsd.hodgepodge.base.BaseConstant;
import com.lsqidsd.hodgepodge.base.OnWriteDataFinishListener;
import com.lsqidsd.hodgepodge.databinding.MainActivityBinding;
import com.lsqidsd.hodgepodge.databinding.TabFootBinding;
import com.lsqidsd.hodgepodge.http.OnSuccessAndFaultListener;
import com.lsqidsd.hodgepodge.http.OnSuccessAndFaultSub;
import com.lsqidsd.hodgepodge.utils.TabDb;
import com.lsqidsd.hodgepodge.viewmodel.MainViewModel;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
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
        MainViewModel viewModel = new MainViewModel(this);
        binding.setMainview(viewModel);
        //设置view
        binding.mainTab.setup(MainActivity.this, getSupportFragmentManager(), binding.mainView.getId());
        //去除分割线
        binding.mainTab.getTabWidget().setDividerDrawable(null);
        binding.mainTab.setOnTabChangedListener(MainActivity.this);
        binding.mainTab.onTabChanged(TabDb.getTabsTxt()[0]);
        //initTab();
        requestReadAndWriteSDPermission(new BaseActivity.PermissionHandler() {
            @Override
            public void onGranted() {
                binding.webview.getSettings().setJavaScriptEnabled(true);
                binding.webview.getSettings().setJavaScriptEnabled(true);
                binding.webview.addJavascriptInterface(new InJavaScriptLocalObj(), "local_obj");
                binding.webview.getSettings().setSupportZoom(true);
                binding.webview.getSettings().setDomStorageEnabled(true);
                binding.webview.requestFocus();
                binding.webview.getSettings().setUseWideViewPort(true);
                binding.webview.getSettings().setLoadWithOverviewMode(true);
                binding.webview.getSettings().setSupportZoom(true);
                binding.webview.getSettings().setBuiltInZoomControls(true);
                binding.webview.setWebViewClient(new WebViewClient(){
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                        view.loadUrl(BaseConstant.BASE_URL);
                        return true;
                    }

                    @Override
                    public void onPageFinished(WebView view, String url) {
                        super.onPageFinished(view, url);
                        view.loadUrl("javascript:window.local_obj.showSource('<head>'+"
                                + "document.getElementsByTagName('html')[0].innerHTML+'</head>');");
                    }
                });
                binding.webview.loadUrl(BaseConstant.BASE_URL);
            }
        });
    }

    final class InJavaScriptLocalObj {
        @JavascriptInterface
        public void showSource(String html) {
            Log.e("====>html=",html);
        }
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
