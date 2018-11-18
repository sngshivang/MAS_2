package com.example.shivang.mas;

import android.content.Intent;
import android.database.Cursor;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout dr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawermain);
        checklogin();
        dr = findViewById(R.id.drawer_layout);
    }
    public void opendrawer(View v)
    {
        dr.openDrawer(GravityCompat.START);
    }
    public void login(View view)
    {
        boolean trip = false,eqps=false;
        TextView tv = findViewById(R.id.mainusr);
        String tid = tv.getText().toString();
        tv = findViewById(R.id.mainpwd);
        String pwd = tv.getText().toString();
        DatabaseHandler db = new DatabaseHandler(this);
        try {
            LOGIN cn = db.getlogin(tid);
            try{
                eqps=cn.getpwd().equals(pwd);
            }
            catch (Exception fp)
            {
                Log.e("mainactivity==login",fp.toString());
            }
            if (eqps)
                trip = true;
            if (trip) {
                universaldat add = new universaldat(this);
                try{
                    add.frcrt();
                }
                catch (Exception e)
                {
                    Log.d("Mainactivity==login",e.toString());
                }
                add.addnew(tid,pwd);
                Intent nit = new Intent(this,afterlogin.class);
                startActivity(nit);
            } else {
                alrtmsg("INCORRECT INFOMRATION","The Teacher ID or Password is incorrect. Please verify those entries");
            }

        }
        catch (Exception e)
        {
            alrtmsg("USERNAME NOT FOUND","The mentioned username has not been found in the records. Please verify its existence.");
            Log.d("mainactivity",e.toString() );
        }
    }
    void checklogin()
    {
        universaldat dt = new universaldat(this);
        Cursor cr = dt.getAcc();
        String inp="null";
        if (cr.moveToFirst())
        {
            do {
                inp = cr.getString(0);
            }while (cr.moveToNext());
        }
        if (!inp.equals("null"))
        {
            Intent it = new Intent(this,alreadylogin.class);
            startActivity(it);
        }
    }
    public void signup(View view)
    {
        Intent it = new Intent(this,signup.class);
        startActivity(it);
    }
    public void alrtmsg(String tag, String msg)
    {
        AlertDialog.Builder err= new AlertDialog.Builder(this);
        err.setTitle(tag);
        err.setMessage(msg);
        err.create();
        err.show();
    }
}
