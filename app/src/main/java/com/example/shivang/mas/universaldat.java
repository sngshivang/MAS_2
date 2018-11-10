package com.example.shivang.mas;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Shivang on 22-10-2018.
 */

public class universaldat extends SQLiteOpenHelper {
    private  static  final int dbver = 3;
    private  static final String dbnme = "MAS";
    private static  final String tname = "MASUNIDAT";
    private  static  final String key_login = "login";
    private  static  final  String key_pwd = "pwd";
    private  static  final String tandd = "tmdt";

    public universaldat (Context cnt)
    {
        super(cnt,dbnme,null,dbver);
        //getReadableDatabase();
    }

    @Override
    public  void onCreate(SQLiteDatabase db)
    {
        String qry = "CREATE TABLE MASUNIDAT (login TEXT,pwd TEXT)";
        db.execSQL(qry);
    }
    @Override
    public  void onUpgrade(SQLiteDatabase db, int old, int newv)
    {
        db.execSQL("DROP TABLE IF EXISTS " + tname);
        onCreate(db);
    }
    void addnew(String lg,String pwd)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues val = new ContentValues();
        val.put(key_login,lg);
        val.put(key_pwd,pwd);
        db.insert(tname,null,val);
        db.close();
    }

    String getAcc(String id)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cr = db.query(tname, new String[] {key_login,key_pwd}, key_login + "=?",new String[]{id},null,null,null);
        if (cr!=null)
            cr.moveToFirst();
        String out = cr.getString(0);
        cr.close();
        return out;
    }
}
