package com.example.donghyunlee.project2w;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by DONGHYUNLEE on 2017-07-18.
 */

public class DBManager extends SQLiteOpenHelper {


    public DBManager(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //(String storeAddress, int storeImg, String storeName, String storeContent, double dist, double popular, Date recent,int checkbutton)
        db.execSQL("CREATE TABLE STORE_LIST( _id INTEGER PRIMARY KEY AUTOINCREMENT, storeName TEXT, storeAddress TEXT, storeContent TEXT, " +
                "dist REAL, popular REAL, recent NUMERIC, check INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public void insert(String _query) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(_query);
        db.close();
    }

    public void update(String _query) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(_query);
        db.close();
    }

    public void delete(String _query) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(_query);
        db.close();
    }

    public String PrintData() {
        SQLiteDatabase db = getReadableDatabase();
        String str = "";

        Cursor cursor = db.rawQuery("select * from STORE_LIST", null);
        while(cursor.moveToNext()) {
            str += cursor.getInt(0)
                    + " : foodName "
                    + cursor.getString(1)
                    + ", price = "
                    + cursor.getInt(2)
                    + "\n";
        }

        return str;
    }
}

