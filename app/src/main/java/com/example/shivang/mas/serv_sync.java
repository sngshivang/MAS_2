package com.example.shivang.mas;

import android.app.ProgressDialog;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
//import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class serv_sync extends AppCompatActivity {
    private DrawerLayout dr;
    NavigationView nv;
    String uname;
    String ini;
    ProgressDialog pd;
    AlertDialog.Builder ad;
    boolean trip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serv_sync);
        ini = new sysfile().readFromFile(this);
        dr=findViewById(R.id.drawer_layout);
        dr = findViewById(R.id.drawer_layout);
        nv = findViewById(R.id.nav_view);
        navstuff();
        navnmefill();
        probar();
        trip = checklogin();
    }
    private void navstuff()
    {
        nv.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem mt) {
                        mt.setChecked(true);
                        if (mt.getItemId()==R.id.nav_home)
                        {
                            Intent it = new Intent(serv_sync.this,MainActivity.class);
                            startActivity(it);
                        }
                        else if (mt.getItemId()==R.id.nav_sync)
                        {
                            Intent it = new Intent(serv_sync.this,serv_sync.class);
                            startActivity(it);
                        }
                        else if (mt.getItemId()==R.id.nav_out)
                        {
                            signfun();
                        }
                        else if (mt.getItemId()==R.id.nav_addad)
                        {
                            Intent it = new Intent(serv_sync.this,adminacc.class);
                            startActivity(it);
                        }
                        dr.closeDrawers();
                        return true;
                    }
                });
    }
    private void navnmefill()
    {
        universaldat dt = new universaldat(this);
        uname="Please Sign in";
        try{
            dt.frcrt();
        }
        catch (Exception e)
        {
            Log.e("serv_sync",e.toString());
        }
        Cursor cr = dt.getAcc();
        if (cr.moveToFirst())
        {
            do {
                uname=cr.getString(0);
            }while (cr.moveToNext());
        }
        View hr = nv.getHeaderView(0);
        TextView tv = hr.findViewById(R.id.nav_urname);
        tv.setText(uname);
    }
    public void signfun()
    {
        universaldat dt = new universaldat(this);
        dt.signout();
        alrtmsg("SIGN OUT","You have been successfully signed out.");
        Intent it = new Intent(this,MainActivity.class);
        startActivity(it);
    }
    public void opendrawer(View v)
    {
        dr.openDrawer(GravityCompat.START);
    }
    public void upload(View v)
    {
        probar();
        View vw = getLayoutInflater().inflate(R.layout.activity_serv_sync, null, false);
        DrawerLayout ct =  findViewById(R.id.drawer_layout);
        uplfile upl = new uplfile(this,vw,ct);
        upl.setbar(pd,this);
        upl.setfile("MAS");
        upl.execute();
        uplfile upl2 = new uplfile(this,vw,ct);
        upl2.setbar(pd,this);
        upl2.setfile("MASREG");
        upl2.execute();
    }
    public void download(View v)
    {
        probar();
        DrawerLayout ct =  findViewById(R.id.drawer_layout);
        View vw = getLayoutInflater().inflate(R.layout.activity_serv_sync, null, false);
        dwnfile upl = new dwnfile(this,vw,ct);
        upl.setbar(pd);
        upl.setfile("MAS");
        upl.execute();
        dwnfile upl2 = new dwnfile(this,vw,ct);
        upl2.setbar(pd);
        upl2.setfile("MASREG");
        upl2.execute();
    }
    public void wpro(View v)
    {
        moveahead();
    }
    private void alrtmsg(String tag, String msg)
    {
        ad = new AlertDialog.Builder(this);
        ad.setTitle(tag);
        ad.setMessage(msg);
        ad.create();
        ad.show();
    }
    private void salrtmsg()
    {
        ad = new AlertDialog.Builder(this);
    }
    public void probar()
    {
        pd = new ProgressDialog(this);
        pd.setIndeterminate(false);
        pd.setMessage("Please wait. Syncronization is currently in process.");
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    }
    private boolean checklogin()
    {
        universaldat dt = new universaldat(this);
        try{
            dt.frcrt();
        }
        catch (Exception e)
        {
            Log.d("Mainactivity==login",e.toString());
        }
        Cursor cr = dt.getAcc();
        String inp="null";
        if (cr.moveToFirst())
        {
            do {
                inp = cr.getString(0);
            }while (cr.moveToNext());
        }
        if (inp.equals("null"))
        {
            return  true;
        }
        else
        {
            return false;
        }
    }
    private void moveahead()
    {
        if (trip) {
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        }
        else
        {
            Intent it = new Intent(this,alreadylogin.class);
            startActivity(it);
        }
    }
}
