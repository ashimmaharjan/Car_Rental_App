package com.example.carrental.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.carrental.Model.Users;

public class DatabaseHelper extends SQLiteOpenHelper {


    private static final String DATABASE_NAME="CAR_RENTAL";
    private static final int DATABASE_VERSION=1;


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {

        db.execSQL(Users.TBL_QUERY);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String query2="DROP TABLE IF EXISTS "+ Users.TABLE_NAME;
        db.execSQL(query2);
        onCreate(db);
    }

    public long AddUsers(Users users)
    {
        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
        ContentValues contentValues=new ContentValues();

        contentValues.put("FIRST_NAME",users.getFirst_name());
        contentValues.put("LAST_NAME",users.getLast_name());
        contentValues.put("EMAIL",users.getEmail());
        contentValues.put("USERNAME",users.getUsername());
        contentValues.put("PASSWORD",users.getPassword());
        contentValues.put("PHONE_NUMBER",users.getPhone_number());

        long id=sqLiteDatabase.insert(Users.TABLE_NAME,null,contentValues);
        return id;
    }
}
