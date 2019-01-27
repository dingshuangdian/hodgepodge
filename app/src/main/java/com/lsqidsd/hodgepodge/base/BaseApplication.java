package com.lsqidsd.hodgepodge.base;
import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.lsqidsd.hodgepodge.bean.DaoMaster;
import com.lsqidsd.hodgepodge.bean.DaoSession;


public class BaseApplication extends Application {
    private static DaoSession daoSession;
    private static Context mContext;
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();;
        //setupDatabase();
    }

    public static Context getmContext() {
        return mContext;
    }

    /**
     * 配置数据库
     */
    private void setupDatabase() {
        //创建数据库news.db
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "news.db", null);
        //获取可写数据库
        SQLiteDatabase db = helper.getWritableDatabase();
        //获取数据库对象
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    public static DaoSession getDaoSession() {
        return daoSession;
    }


}
