package com.hikage.japanmahjong;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class GameLogDAO {

    public final static String TABLE_NAME = "info_table";
    public final static String ROW_ID = "rowID";
    public final static String INFO = "info";
    public final static String DATETIME = "dateTime";

    public final static String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
            + ROW_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + INFO + " TEXT NOT NULL, "
            + DATETIME + " TEXT);";

    private SQLiteDatabase db;

    public GameLogDAO(Context context) {
        db = DBHelper.getDatabase(context);
    }

    public void close() {
        db.close();
    }

    public GameLog insert(GameLog gameLog) {

        ContentValues cv = new ContentValues();
        cv.put(INFO, gameLog.getInfo());
        cv.put(DATETIME, gameLog.getDatetime());

        db.insert(TABLE_NAME, null, cv);

        Cursor c = db.query(
                TABLE_NAME, null, null, null, null, null, null);
        if (c.getCount() > 20) {
            c.moveToFirst();
            int id = c.getInt(0);
            remove(id);
        }

        return gameLog;
    }

    public boolean remove(int id) {
        String where = ROW_ID + "=" + id;
        return db.delete(TABLE_NAME, where, null) > 0;
    }

    public List getAll() {

        List<HashMap<String, String>> list = new ArrayList<>();

        Cursor c = db.query(
                TABLE_NAME, null, null, null, null, null, null);

        if (c.getCount() != 0) {
            c.moveToLast();
            for (int i = 0; i < c.getCount(); i++) {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put(INFO, c.getString(1));
                hashMap.put(DATETIME, c.getString(2));
                list.add(hashMap);
                c.moveToPrevious();
            }
        } else {
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put(INFO, "目前無牌局紀錄");
            hashMap.put(DATETIME, "");
            list.add(hashMap);
        }

        return list;
    }
}
