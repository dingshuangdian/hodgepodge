package com.lsqidsd.hodgepodge.http.download;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.lsqidsd.hodgepodge.base.BaseApplication;
import org.greenrobot.greendao.query.QueryBuilder;
import java.util.List;
public class DaoUtil {
    private static DaoUtil daoUtil;
    private final static String dbName = "dao_db";
    private Context context;
    private DaoMaster.DevOpenHelper openHelper;

    public DaoUtil() {
        this.context = BaseApplication.getmContext();
        this.openHelper = new DaoMaster.DevOpenHelper(context, dbName, null);
    }

    public static DaoUtil getInstance() {
        if (daoUtil == null) {
            synchronized (DaoUtil.class) {
                if (daoUtil == null) {
                    daoUtil = new DaoUtil();
                }
            }
        }
        return daoUtil;
    }

    /**
     * 获取可读数据库
     */
    private SQLiteDatabase getReadableDatabase() {
        if (openHelper == null) {
            openHelper = new DaoMaster.DevOpenHelper(context, dbName, null);
        }
        SQLiteDatabase db = openHelper.getReadableDatabase();
        return db;
    }

    /**
     * 获取可写数据库
     */
    private SQLiteDatabase getWritableDatabase() {
        if (openHelper == null) {
            openHelper = new DaoMaster.DevOpenHelper(context, dbName, null);
        }
        SQLiteDatabase db = openHelper.getWritableDatabase();
        return db;
    }

    public void save(Info state) {
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        InfoDao downStateDao = daoSession.getInfoDao();
        downStateDao.insert(state);
    }

    public void updata(Info state) {
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        InfoDao downStateDao = daoSession.getInfoDao();
        downStateDao.update(state);
    }

    public void deleteDownState(Info state) {
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        InfoDao downStateDao = daoSession.getInfoDao();
        downStateDao.delete(state);
    }

    public Info queryDownBy(long id) {
        DaoMaster daoMaster = new DaoMaster(getReadableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        InfoDao downStateDao = daoSession.getInfoDao();
        QueryBuilder<Info> qb = downStateDao.queryBuilder();
        qb.where(InfoDao.Properties.Id.eq(id));
        List<Info> list = qb.list();
        return list.isEmpty() ? null : list.get(0);

    }

    public List<Info> queryDownAll() {
        DaoMaster daoMaster = new DaoMaster(getReadableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        InfoDao downStateDao = daoSession.getInfoDao();
        QueryBuilder<Info> qb = downStateDao.queryBuilder();
        return qb.list();
    }
}
