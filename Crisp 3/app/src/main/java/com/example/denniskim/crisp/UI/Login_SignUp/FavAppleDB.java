package com.example.denniskim.crisp.UI.Login_SignUp;

/**
 * Created by Royal-Dell on 11/22/2015.
 */
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;


public class FavAppleDB extends SQLiteAssetHelper {

    private static final String DATABASE_NAME = "favApple.sqlite";
    private static final int DATABASE_VERSION = 13;

    public FavAppleDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        // you can use an alternate constructor to specify a database location
        // (such as a folder on the sd card)
        // you must ensure that this folder is available and you have permission
        // to write to it
        //super(context, DATABASE_NAME, context.getExternalFilesDir(null).getAbsolutePath(), null, DATABASE_VERSION);

        // we have supplied no upgrade path from version 1 to 2
        setForcedUpgrade(2);
    }

    public Cursor getApple(String table) {
        SQLiteDatabase db = getReadableDatabase();
        String query =  "SELECT * FROM " + table ;
        Cursor mCur = db.rawQuery(query, null);
        mCur.moveToFirst();
        return mCur;

    }

    public Cursor checkApple(String table ,String data) {
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM "+ table + " WHERE Name LIKE "+"'"+data+"'" ;
        Cursor mCur = db.rawQuery(query, null);
        mCur.moveToFirst();
        return mCur;

    }

    public void insert(String table, String data) {
        SQLiteDatabase db = getReadableDatabase();
        String sql =  "INSERT INTO " + table + " VALUES ('" + data+ "')";
        db.execSQL(sql);

    }

    public void delete(String table, String data) {
        SQLiteDatabase db = getReadableDatabase();
        String sql =  "DELETE FROM " + table + " WHERE Name LIKE "+"'"+data+"'";
        Log.e("delete", sql);
        db.execSQL(sql);

    }

    public Cursor checkTable(String table) {
        SQLiteDatabase db = getReadableDatabase();
        String query =  "SELECT * FROM sqlite_master WHERE type='table' AND name='" + table + "'";
        Cursor mCur = db.rawQuery(query, null);
        mCur.moveToFirst();
        Log.e("checked", table);
        return mCur;
    }

    public void createTable(String table) {
        SQLiteDatabase db = getReadableDatabase();
        String sql =  "CREATE TABLE " + table + " (Name TEXT)";
        db.execSQL(sql);
        Log.e("table created", table);


    }


    public int getUpgradeVersion() {

        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String [] sqlSelect = {"MAX (version)"};
        String sqlTables = "upgrades";

        qb.setTables(sqlTables);
        Cursor c = qb.query(db, sqlSelect, null, null,
                null, null, null);

        int v = 0;
        c.moveToFirst();
        if (!c.isAfterLast()) {
            v = c.getInt(0);
        }
        c.close();
        return v;
    }

}

