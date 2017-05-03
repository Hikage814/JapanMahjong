package com.hikage.japanmahjong;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by W7Users on 2017/3/31.
 */

public class DBHelper extends SQLiteOpenHelper {

    public static final int DB_VERSION = 1;
    final static String DB_NAME = "japanMahjong.db"; //資料庫名稱，附檔名為db
    private static SQLiteDatabase db;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public static SQLiteDatabase getDatabase(Context context) {
        if (db == null || !db.isOpen()) {
            db = new DBHelper(context).getWritableDatabase();
        }
        return db;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(GameLogDAO.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 刪除原有的表格
        db.execSQL("DROP TABLE IF EXISTS " + GameLogDAO.TABLE_NAME);
        // 呼叫onCreate建立新版的表格
        onCreate(db);
    }

}
