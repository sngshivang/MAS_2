package com.example.shivang.mas;

import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class adminacc extends AppCompatActivity {
    private DrawerLayout dr;
    NavigationView nv;
    String uname;
    String ini;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminacc);
        ini = new sysfile().readFromFile(this);
        dr=findViewById(R.id.drawer_layout);
        dr = findViewById(R.id.drawer_layout);
        nv = findViewById(R.id.nav_view);
        navstuff();
        navnmefill();
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
                            Intent it = new Intent(adminacc.this,MainActivity.class);
                            startActivity(it);
                        }
                        else if (mt.getItemId()==R.id.nav_sync)
                        {
                            Intent it = new Intent(adminacc.this,serv_sync.class);
                            startActivity(it);
                        }
                        else if (mt.getItemId()==R.id.nav_out)
                        {
                            signfun();
                        }
                        else if (mt.getItemId()==R.id.nav_addad)
                        {
                            Intent it = new Intent(adminacc.this,adminacc.class);
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
    public void proceedad(View v)
    {
        EditText ed = findViewById(R.id.mainusr);
        String iname = ed.getText().toString();
        ed = findViewById(R.id.mainid);
        String iid = ed.getText().toString();
        ed = findViewById(R.id.mainpwd);
        String pwd = ed.getText().toString();
        ed = findViewById(R.id.mainrepwd);
        String repwd = ed.getText().toString();
        if (!iname.equals("")&&!iid.equals("")&&!pwd.equals("")&&!repwd.equals("")&&pwd.equals(repwd))
        {
            datinsti dt = new datinsti(this);
            try{
                dt.frcrt();
            }
            catch (Exception e)
            {
                Log.e("adminaccc",e.toString());
            }
            dt.addnew(iname,iid,pwd);
            alrtmsg("SUCCESS","This admin account was created successfully. You can go ahead and add teacher, classes and students.");
        }
    }
    private void alrtmsg(String tag, String msg)
    {
        AlertDialog.Builder ad = new AlertDialog.Builder(this);
        ad.setTitle(tag);
        ad.setMessage(msg);
        ad.create();
        ad.show();
    }
}
