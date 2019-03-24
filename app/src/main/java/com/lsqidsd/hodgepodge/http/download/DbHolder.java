package com.lsqidsd.hodgepodge.http.download;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import java.io.File;

public class DbHolder {
    private Context context;
    private SQLiteDatabase db;

    public DbHolder(Context context) {
        this.context = context;
        db = new DbOpenHelper(context).getWritableDatabase();
    }

    public void saveFile(FileInfo fileInfo) {
        if (null == fileInfo)
            return;
        ContentValues values = new ContentValues();
        values.put(InnerConstant.Db.id, fileInfo.getId());
        values.put(InnerConstant.Db.downloadUrl, fileInfo.getDownloadUrl());
        values.put(InnerConstant.Db.filePath, fileInfo.getFilePath());
        values.put(InnerConstant.Db.size, fileInfo.getSize());
        values.put(InnerConstant.Db.downloadLocation, fileInfo.getDownloadLocation());
        values.put(InnerConstant.Db.downloadStatus, fileInfo.getDownloadStatus());
        if (has(fileInfo.getId())) {
            db.update(InnerConstant.Db.NAME_TABALE, values, InnerConstant.Db.id + "=?", new String[]{fileInfo.getId()});
        } else {
            db.insert(InnerConstant.Db.NAME_TABALE, null, values);
        }


    }

    public void updateState(String id, int state) {
        if (TextUtils.isEmpty(id)) {
            return;
        }
        ContentValues values = new ContentValues();
        values.put(InnerConstant.Db.downloadStatus, state);
        db.update(InnerConstant.Db.NAME_TABALE, values, InnerConstant.Db.id + "=?", new String[]{id});
    }

    public FileInfo getFileInfo(String id) {
        Cursor cursor = db.query(InnerConstant.Db.NAME_TABALE, null, "" + InnerConstant.Db.id + "=?", new String[]{id}, null, null, null);
        FileInfo fileInfo = null;
        while (cursor.moveToNext()) {
            fileInfo = new FileInfo();
            fileInfo.setId(cursor.getString(cursor.getColumnIndex(InnerConstant.Db.id)));
            fileInfo.setDownloadUrl(cursor.getString(cursor.getColumnIndex(InnerConstant.Db.downloadUrl)));
            fileInfo.setFilePath(cursor.getString(cursor.getColumnIndex(InnerConstant.Db.filePath)));
            fileInfo.setSize(cursor.getLong(cursor.getColumnIndex(InnerConstant.Db.size)));
            fileInfo.setDownloadLocation(cursor.getLong(cursor.getColumnIndex(InnerConstant.Db.downloadLocation)));
            fileInfo.setDownloadStatus(cursor.getInt(cursor.getColumnIndex(InnerConstant.Db.downloadStatus)));
            File file = new File(fileInfo.getFilePath());
            if (!file.exists()) {
                deleteFileInfo(id);
                return null;
            }
        }
        cursor.close();
        return fileInfo;
    }

    public void deleteFileInfo(String id) {
        if (has(id)) {
            db.delete(InnerConstant.Db.NAME_TABALE, InnerConstant.Db.id + "=?", new String[]{id});
        }
    }

    private boolean has(String id) {
        Cursor cursor = db.query(InnerConstant.Db.NAME_TABALE, null, "" + InnerConstant.Db.id + "=?", new String[]{id}, null, null, null);
        boolean has = cursor.moveToNext();
        cursor.close();
        return has;
    }
}
