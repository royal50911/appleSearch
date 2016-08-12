package com.example.denniskim.crisp;

/**
 * Created by denniskim on 11/16/15.
 */
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;


public class MyDatabase extends SQLiteAssetHelper {

    private static final String DATABASE_NAME = "appleDB_final.sqlite";
    private static final int DATABASE_VERSION = 13;

    public MyDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        // you can use an alternate constructor to specify a database location
        // (such as a folder on the sd card)
        // you must ensure that this folder is available and you have permission
        // to write to it
        //super(context, DATABASE_NAME, context.getExternalFilesDir(null).getAbsolutePath(), null, DATABASE_VERSION);

        // we have supplied no upgrade path from version 1 to 2
        setForcedUpgrade(2);
    }

    public Cursor getApple(String data) {
        Log.e("before open", "error");
        SQLiteDatabase db = getReadableDatabase();
        Log.e("after open", "error");
        String query =  "SELECT * FROM apple_table WHERE Available  LIKE '%" +data+ "%' OR Name LIKE "+"'"+data+"'" ;
        Cursor mCur = db.rawQuery(query, null);

        mCur.moveToFirst();
        return mCur;

    }

    public Cursor getFav(String apple, String month) {
        SQLiteDatabase db = getReadableDatabase();
        String query =  "SELECT * FROM apple_table WHERE Available LIKE '%" +month+ "%' AND Name LIKE "+"'"+apple+"'" ;
        Cursor mCur = db.rawQuery(query, null);
        mCur.moveToFirst();
        return mCur;

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
