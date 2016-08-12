package com.example.denniskim.crisp.UI.Login_SignUp;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.content.Context;
import android.content.ContentValues;

/**
 * Created by denniskim on 10/26/15.
 */
public class UserDB extends SQLiteOpenHelper {

        private static final int DATABASE_VERSION = 1;
        private static final String DATABASE_NAME = "users.db";
        private static final String TABLE_USERS = "users";
        private static final String COLUMN_ID = "_id";
        private static final String COLUMN_USERNAME = "username";
        private static final String COLUMN_PASSWORD = "password";


        public UserDB(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, DATABASE_NAME, factory, DATABASE_VERSION);
        }

        // called when database is first created
        @Override
        public void onCreate(SQLiteDatabase db) {
            String query = "CREATE TABLE " + TABLE_USERS + "(" +
                    //   COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL DEFAULT 1" +
                    COLUMN_USERNAME + " TEXT, " +
                    COLUMN_PASSWORD + " TEXT " +
                    ");";

            db.execSQL(query);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(" DROP TABLE IF EXISTS " + TABLE_USERS);
            onCreate(db);
        }

        // add new user to database
        public void addUser(User user){
            ContentValues values = new ContentValues();
            values.put(COLUMN_USERNAME,user.getUsername());
            values.put(COLUMN_PASSWORD,user.getPassword());
            SQLiteDatabase db = getWritableDatabase();
            db.insert(TABLE_USERS, null, values);
            db.close();
        }

        // checks to see if user is in database
        public String getUser(String userName){
            SQLiteDatabase db = getWritableDatabase();
            Cursor cursor = db.query("users", null, " username=?", new String[]{userName}, null, null, null);
            if(cursor.getCount()<1) // UserName Not Exist
            {
                cursor.close();
                return "NOT EXIST";
            }
            cursor.moveToFirst();
            String password = cursor.getString(cursor.getColumnIndex("password"));
            cursor.close();
            return password;
        }

        // to delete all data from database
        public void removeAll(){
            SQLiteDatabase sdb= this.getWritableDatabase();
            sdb.delete("users", null, null);
        }

    /* Test function to verify users input is going in
        // make sure database is working, Prints the usernames
        public String userNameToString(){
            String dbString = "";
            SQLiteDatabase db = getWritableDatabase();
            String query = "SELECT * FROM " + TABLE_USERS + " WHERE 1";

            // point to a result in database
            Cursor c = db.rawQuery(query,null);
            // move to first row in your results
            c.moveToFirst();


            while (!c.isAfterLast()){
                if (c.getString(c.getColumnIndex("username")) != null){
                    dbString += c.getString(c.getColumnIndex("username")) +
                            c.getString(c.getColumnIndex("password"));
                    dbString += "\n";
                }
                c.moveToNext();
            }

            db.close();

            return dbString;
        } */



}
