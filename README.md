# 高仿腾讯新闻
mvvm+databinding+rxjava2实现高仿腾讯新闻客户端

<h3>app所有数据来源互联网抓取，仅供学习交流</h3>




<h2>项目会一直持续更新，感谢大家的支持！！！</h2>




<h2>欢迎start&fork</h2>



<p>*部分页面效果图*</p>

<img src="https://raw.githubusercontent.com/dingshuangdian/hodgepodge/master/img/预览01.gif" width="200px" height="400px" alt="首页">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src="https://raw.githubusercontent.com/dingshuangdian/hodgepodge/master/img/预览02.gif" width="200px" height="400px" alt="热点精选">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src="https://raw.githubusercontent.com/dingshuangdian/hodgepodge/master/img/预览03.gif" width="200px" height="400px" alt="视频">




<h5>*懒加载封装基类*</h5>

public abstract class BaseLazyFragment<T extends ViewDataBinding> extends Fragment {
    /**
     * @param isVisibleToUser
     */
    public boolean inInit = false;
    public boolean isLoad = false;
    private LayoutInflater mInflater;
    private ViewGroup mContainer;
    private T t;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mInflater = inflater;
        mContainer = container;
        if (t != null && t.getRoot() != null) {
            return t.getRoot();
        } else {
            initFragment();
            return t.getRoot();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        inInit = true;
        initData();
        isCanLoadData();
    }
    public T setBinding(T k) {
        k = DataBindingUtil.inflate(mInflater, setContentView(), mContainer, false);
        t = k;
        return k;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isCanLoadData();
    }

    public abstract void initFragment();
    public abstract void initData();

    private void isCanLoadData() {
        if (!inInit) {
            return;
        }
        if (getUserVisibleHint()) {
            lazyLoad();
            isLoad = true;
        } else {
            if (isLoad) {
                stopLoad();
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        inInit = false;
        isLoad = false;
    }

    public abstract int setContentView();

    public abstract void lazyLoad();

    public void stopLoad() {

    }


<h5>*带全局网络监听，权限控制，沉浸式状态栏等的activity基类*</h5>
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

<h5>混淆打包(已经适配大多数框架，拿来直接用)</h5>

 buildTypes {
        release {
            //开启代码混淆
            minifyEnabled true
            //Zipalign优化
            zipAlignEnabled true
            // 移除无用的resource文件
            shrinkResources true
            proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'
        }

    }
 <p>部分代码</p>
 -optimizationpasses 5
 # 混淆时不使用大小写混合，混淆后的类名为小写
 -dontusemixedcaseclassnames
 
 # 指定不去忽略非公共的库的类
 -dontskipnonpubliclibraryclasses
 
 # 指定不去忽略非公共的库的类的成员
 -dontskipnonpubliclibraryclassmembers
 
 # 不做预校验，可加快混淆速度
 # preverify是proguard的4个步骤之一
 # Android不需要preverify，去掉这一步可以加快混淆速度
 -dontpreverify
 
 # 不优化输入的类文件
 -dontoptimize
 
 # 混淆时生成日志文件，即映射文件
 -verbose
 
 # 指定映射文件的名称
 -printmapping proguardMapping.txt
 
 #混淆时所采用的算法
 -optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
 
 # 保护代码中的Annotation不被混淆
 -keepattributes *Annotation*

    
  






