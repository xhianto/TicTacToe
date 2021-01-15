package com.example.tictactoe;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class DataBaseHelper extends SQLiteOpenHelper {
    public static final String USER_TABLE = "USER_TABLE";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_USERNAME = "USERNAME";
    public static final String COLUMN_EMAILADDRESS = "EMAILADDRESS";
    public static final String COLUMN_EASYWIN = "EASYWIN";
    public static final String COLUMN_EASYDRAW = "EASYDRAW";
    public static final String COLUMN_EASYLOSE = "EASYLOSE";
    public static final String COLUMN_HARDWIN = "HARDWIN";
    public static final String COLUMN_HARDDRAW = "HARDDRAW";
    public static final String COLUMN_HARDLOSE = "HARDLOSE";
    public static final String COLUMN_FRIENDWIN = "FRIENDWIN";
    public static final String COLUMN_FRIENDDRAW = "FRIENDDRAW";
    public static final String COLUMN_FRIENDLOSE = "FRIENDLOSE";

    public DataBaseHelper(@Nullable Context context) {
        super(context, "user.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + USER_TABLE + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, '" + COLUMN_EMAILADDRESS + "' TEXT NOT NULL UNIQUE, '" + COLUMN_USERNAME + "' TEXT, " + COLUMN_EASYWIN + " INT, " + COLUMN_EASYDRAW + " INT, " + COLUMN_EASYLOSE + " INT, " + COLUMN_HARDWIN + " INT, " + COLUMN_HARDDRAW + " INT, " + COLUMN_HARDLOSE + " INT, " + COLUMN_FRIENDWIN + " INT, " + COLUMN_FRIENDDRAW + " INT, " + COLUMN_FRIENDLOSE + " INT)";

        db.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addUser(UserModel user){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues cv = makeCVWithoutId(user);

        db.insert(USER_TABLE, null, cv);
        db.close();
    }

    public UserModel getUserByEmail(String email){
        UserModel user = new UserModel();
        String query = "SELECT * FROM " + USER_TABLE + " WHERE '" + email + "' = " + COLUMN_EMAILADDRESS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()){
            user.setUserId(cursor.getInt(0));
            user.setEmailAddress(cursor.getString(1));
            user.setUsername(cursor.getString(2));
            user.setEasyWin(cursor.getInt(3));
            user.setEasyDraw(cursor.getInt(4));
            user.setEasyLose(cursor.getInt(5));
            user.setHardWin(cursor.getInt(6));
            user.setHardDraw(cursor.getInt(7));
            user.setHardLose(cursor.getInt(8));
            user.setFriendWin(cursor.getInt(9));
            user.setFriendDraw(cursor.getInt(10));
            user.setFriendLose(cursor.getInt(11));
        }
        cursor.close();
        db.close();
        return user;
    }

    public void updateUser(UserModel user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = makeCVWithoutId(user);
        cv.put(COLUMN_ID, user.getUserId());

        db.update(USER_TABLE, cv, COLUMN_ID + " = ?", new String[] {String.valueOf(user.getUserId())});
        db.close();
    }

    private ContentValues makeCVWithoutId(UserModel user){
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_USERNAME, user.getUsername());
        cv.put(COLUMN_EMAILADDRESS, user.getEmailAddress());
        cv.put(COLUMN_EASYWIN, user.getEasyWin());
        cv.put(COLUMN_EASYDRAW, user.getEasyDraw());
        cv.put(COLUMN_EASYLOSE, user.getEasyLose());
        cv.put(COLUMN_HARDWIN, user.getHardWin());
        cv.put(COLUMN_HARDDRAW, user.getHardDraw());
        cv.put(COLUMN_HARDLOSE, user.getHardLose());
        cv.put(COLUMN_FRIENDWIN, user.getFriendWin());
        cv.put(COLUMN_FRIENDDRAW, user.getFriendDraw());
        cv.put(COLUMN_FRIENDLOSE, user.getFriendLose());
        return cv;
    }
}
