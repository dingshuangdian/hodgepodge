package com.lsqidsd.hodgepodge.utils;

import com.lsqidsd.hodgepodge.base.BaseApplication;

import java.util.List;

public class BaseDataDao {
    /**
     * 添加数据，如果有重复则覆盖
     */
    public static <T> void insertData(T t) {
        BaseApplication.getDaoSession().insertOrReplace(t);
    }
    /**
     * 删除数据
     */
    public static <T> void deleteData(T t) {
        BaseApplication.getDaoSession().delete(t);
    }
    /**
     * 更新数据
     */
    public static <T> void updateData(T t) {
        BaseApplication.getDaoSession().update(t);
    }
    /**
     * 查询全部数据
     */
    public static <T> List<T> queryAllData(Class<T> tClass) {
        return BaseApplication.getDaoSession().loadAll(tClass);
    }
    /**
     * 删除全部数据
     */
    public static <T> void deleteAllData(Class<T> tClass) {
        BaseApplication.getDaoSession().deleteAll(tClass);
    }
}

