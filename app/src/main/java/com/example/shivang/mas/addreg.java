package com.example.shivang.mas;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Created by Shivang on 31-10-2018.
 */

public class addreg extends SQLiteOpenHelper{
    public static final int dbver=3;
    private  static final String dbnme = "MASREG";
    private String tname;
    private  static  final String key_name = "name";
    private  static  final  String key_sid= "sid";
    private  static final String key_eid="eid";
    private static  final String key_mno="mno";
    private static final  String key_pre="pre";
    private static final String key_abs="abs";
    private static final String key_lve="lve";

    public addreg(Context ct)
    {
        super(ct,dbnme,null,dbver);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String qry = "CREATE TABLE "+tname+" (name TEXT, sid TEXT, eid TEXT, mno TEXT, pre INT, abs INT, lve INT, moddt TEXT DEFAULT '1542870585747')";
        db.execSQL(qry);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldver, int newver)
    {
        String qry = "DROP TABLE IF EXISTS " + tname;
        db.execSQL(qry);
        onCreate(db);
    }
    void tnameadd(String name)
    {
        tname=name;
    }
    void forcecrt()
    {
        SQLiteDatabase db = getWritableDatabase();
        String qry = "CREATE TABLE "+tname+" (name TEXT, sid TEXT, eid TEXT, mno TEXT, pre INT, abs INT, lve INT, moddt TEXT DEFAULT '1542870585747')";
        db.execSQL(qry);
    }
    void addcls(String nme, String sid, String eid, String mno)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues val = new ContentValues();
        val.put(key_name,nme);
        val.put(key_sid,sid);
        val.put(key_eid,eid);
        val.put(key_mno,mno);
        val.put(key_pre,0);
        val.put(key_abs,0);
        val.put(key_lve,0);
        db.insert(tname,null,val);
        db.close();
    }
    /*void addcls2(String nme, String sid, String eid, String mno)
    {
        SQLiteDatabase db = getWritableDatabase();
        String qry= "INSERT INTO "+tname+" (name,sid,eid,mno) VALUES ("+nme+","+
    }*/
    void updtpre(String sid, int pre, String dt)
    {
        SQLiteDatabase db = getWritableDatabase();
        String qry= "UPDATE "+tname+" SET pre="+pre+", moddt="+dt+" WHERE sid='"+sid+"'";
        db.execSQL(qry);
        db.close();
    }
    void updtabs(String sid, int abs,String dt)
    {
        SQLiteDatabase db = getWritableDatabase();
        String qry= "UPDATE "+tname+" SET abs="+abs+", moddt="+dt+" WHERE sid='"+sid+"'";
        db.execSQL(qry);
        db.close();
    }
    void updtlve(String sid, int lve,String dt)
    {
        SQLiteDatabase db = getWritableDatabase();
        String qry= "UPDATE "+tname+" SET lve="+lve+", moddt="+dt+" WHERE sid='"+sid+"'";
        db.execSQL(qry);
        db.close();
    }
    void updt(String sid, String dt)
    {
        SQLiteDatabase db = getWritableDatabase();
        String qry= "UPDATE "+tname+" SET moddt="+dt+" WHERE sid='"+sid+"'";
        db.execSQL(qry);
        db.close();
    }
    public Cursor getallstud()
    {
        SQLiteDatabase db = getWritableDatabase();
        String qry = "SELECT * from "+tname;
        Cursor cst = db.rawQuery(qry,null);
        /*if (cst.moveToFirst())
        {
            do {
                Log.d("tesla",cst.getString(0));
            }while (cst.moveToNext());
        }*/
        return cst;
    }

}
