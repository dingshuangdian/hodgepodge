package com.lsqidsd.hodgepodge.http.download;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.util.Log;

public class DbOpenHelper extends SQLiteOpenHelper {

    public static final String TAG = "DbOpenHelper";

    public DbOpenHelper(@Nullable Context context) {
        super(context, InnerConstant.Db.NAME_DB, null, getVersionCode(context));
    }

    private static int getVersionCode(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String info = "create table if not exists " + InnerConstant.Db.NAME_TABALE
                + "(" +
                InnerConstant.Db.id + " varchar(500)," +
                InnerConstant.Db.downloadUrl + " varchar(100)," +
                InnerConstant.Db.filePath + " varchar(100)," +
                InnerConstant.Db.size + " integer," +
                InnerConstant.Db.downloadLocation + " integer," +
                InnerConstant.Db.downloadStatus + " integer)";

        Log.i(TAG, "onCreate() -> info=" + info);
        sqLiteDatabase.execSQL(info);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //对于下载来讲，其实是不存在这种升级数据库的业务的.所以我们直接删除重新建表
        sqLiteDatabase.execSQL("drop table if exists " + InnerConstant.Db.NAME_TABALE);
        onCreate(sqLiteDatabase);

    }
}
