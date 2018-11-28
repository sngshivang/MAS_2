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

public class clslst extends SQLiteOpenHelper{
    public static final int dbver=3;
    private  static final String dbnme = "MAS";
    private String tname = "clslst";
    private  static  final String key_class = "class";
    private  static  final  String key_sub= "sub";

    public clslst(Context ct)
    {
        super(ct,dbnme,null,dbver);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String qry = "CREATE TABLE "+tname+" (class TEXT PRIMARY KEY, sub TEXT)";
        db.execSQL(qry);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldver, int newver)
    {
        String qry = "DROP TABLE IF EXISTS " + tname;
        db.execSQL(qry);
        onCreate(db);
    }
    void settbl(String nme)
    {
        tname = nme;
    }
    void addcls(String nme, String sub)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues val = new ContentValues();
        val.put(key_class,nme);
        val.put(key_sub,sub);
        db.insert(tname,null,val);
        db.close();
    }
    void updatesub(String cls, String sub)
    {
        SQLiteDatabase db = getWritableDatabase();
        String qry = "UPDATE "+tname+" SET sub = '"+sub+"' WHERE class = '"+cls+"'";
        db.execSQL(qry);
        db.close();
    }
    String getsub(String cls)
    {
        SQLiteDatabase db = getWritableDatabase();
        String out="";
        String qry = "SELECT * FROM "+tname+" WHERE class = '"+cls+"'";
        Cursor cst = db.rawQuery(qry, null);
        if (cst.moveToFirst())
        {
            out = cst.getString(1);
        }
        return out;
    }
    void forcecrt()
    {
        SQLiteDatabase db = getWritableDatabase();
        String qry = "CREATE TABLE "+tname+" (class TEXT PRIMARY KEY, sub TEXT)";
        db.execSQL(qry);
    }
    public Cursor getallcls()
    {
        //Vector v = new Vector();
        //ArrayList<String> lst = new ArrayList<>();
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
