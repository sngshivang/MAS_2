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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;

public class atnd_2 extends AppCompatActivity {

    DrawerLayout dr;
    NavigationView nv;
    String uname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atnd_2);
        dr = findViewById(R.id.drawer_layout);
        cspinfill();
        spinsel();
        sspinsel();
        dr = findViewById(R.id.drawer_layout);
        nv = findViewById(R.id.nav_view);
        navstuff();
        navnmefill();
        setdate();
    }
    long dat=0;
    private void setdate()
    {
        Date df = new Date();
        dat = df.getTime();
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
                            Intent it = new Intent(atnd_2.this,MainActivity.class);
                            startActivity(it);
                        }
                        else if (mt.getItemId()==R.id.nav_sync)
                        {
                            alrt("COMING SOON","This feature is coming soon.");
                        }
                        else if (mt.getItemId()==R.id.nav_out)
                        {
                            signfun();
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
        alrt("SIGN OUT","You have been successfully signed out.");
        Intent it = new Intent(this,MainActivity.class);
        startActivity(it);
    }
    public void opendrawer(View v)
    {
        dr.openDrawer(GravityCompat.START);
    }
    String cout,sout;
    String nmeu[];
    String sidu[];
    Integer preu[];
    Integer absu[];
    Integer lveu[];
    Long tdat[];
    int trip=0,mx;
    addreg ad = new addreg(this);
    private void spinsel()
    {
        Spinner sp = findViewById(R.id.cls_spin2);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                cout=adapterView.getSelectedItem().toString();
                sspinfill();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    private void sspinsel()
    {
        Spinner sp = findViewById(R.id.sub_spin);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                sout=adapterView.getSelectedItem().toString();
                settable();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    private void cspinfill()
    {
        try {
            clslst temp = new clslst(this);
            Cursor cr = temp.getallcls();
            ArrayList<String> lst = new ArrayList<>();
            if (cr.moveToFirst())
            {
                do {
                    lst.add(cr.getString(0));
                }while (cr.moveToNext());
            }
            String tostr[] = new String[lst.size()];
            tostr = lst.toArray(tostr);
            Spinner sp = findViewById(R.id.cls_spin2);
            ArrayAdapter<String> adp = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,tostr);
            //SimpleCursorAdapter ca = new SimpleCursorAdapter(this, android.R.layout.simple_spinner_item, cr, from, to);
            adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            sp.setAdapter(adp);
        }
        catch (Exception e)
        {
            Log.d("addclass.java",e.toString());
        }
    }
    private void sspinfill()
    {
        try {
            clslst temp = new clslst(this);
            Log.d("TESLA",cout);
            String inp = temp.getsub(cout);
            JSONArray jsr = new JSONArray(inp);
            int len = jsr.length();
            String tostr[] = new String[len];
            for (int i=0;i<len;i++)
            {
                tostr[i] = jsr.getString(i);
            }
            Spinner sp = findViewById(R.id.sub_spin);
            ArrayAdapter<String> adp = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,tostr);
            //SimpleCursorAdapter ca = new SimpleCursorAdapter(this, android.R.layout.simple_spinner_item, cr, from, to);
            adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            sp.setAdapter(adp);
        }
        catch (Exception e)
        {
            Log.d("addclass.java",e.toString());
        }
    }
    private void settable()
    {
        String tot = cout+"_"+sout;
        ad.tnameadd(tot);
        setit();
    }
    private void setit()
    {
        try {
            Cursor cr = ad.getallstud();
            ArrayList<String> nme = new ArrayList<>();
            ArrayList<String> sid = new ArrayList<>();
            ArrayList<Integer> pre = new ArrayList<>();
            ArrayList<Integer> abs = new ArrayList<>();
            ArrayList<Integer> lve = new ArrayList<>();
            ArrayList<Long> tdt = new ArrayList<>();
            if (cr.moveToFirst()) {
                do {
                    nme.add(cr.getString(0));
                    sid.add(cr.getString(1));
                    pre.add(cr.getInt(4));
                    abs.add(cr.getInt(5));
                    lve.add(cr.getInt(6));
                    tdt.add(Long.valueOf(cr.getString(7)));
                } while (cr.moveToNext());
            }
            int sze = nme.size();
            nmeu = new String[sze];
            sidu = new String[sze];
            preu = new Integer[sze];
            absu = new Integer[sze];
            lveu = new Integer[sze];
            tdat = new Long[sze];
            nmeu = nme.toArray(nmeu);
            sidu = sid.toArray(sidu);
            preu = pre.toArray(preu);
            absu = abs.toArray(absu);
            lveu = lve.toArray(lveu);
            tdat = tdt.toArray(tdat);
            mx = sze;
            displayit(trip);
        }
        catch (Exception e)
        {
            Log.d("atnd_2==setit",e.toString());
        }
    }
    private boolean vfyatnd()
    {
        if (dat-tdat[trip]>86399999)
        {
           return true;
        }
        else
        return  false;
    }
    private void displayit(int pos)
    {
        TextView tv = findViewById(R.id.nametag);
        tv.setText(nmeu[pos]);
        tv = findViewById(R.id.numtag);
        tv.setText(String.valueOf(pos+1));
    }
    public void setpre(View v)
    {
        if (vfyatnd()) {
            try {
                ad.updtpre(sidu[trip], ++preu[trip],String.valueOf(dat));
                tdat[trip]=dat;
                alrt("SUCCESS", "ATTENDANCE SUCCESSFULLY UPDATED!");
            } catch (Exception e) {
                Log.d("atnd2==setpre", e.toString());
                alrt("OPERATION FAILED", "Critical Error Occurred. Check Logcat for further information. atnd_2==setpre");
            }
        }
        else
        {
            alrt("FORBIDDEN","Attendance of this student has already been marked for this day. Please clear attendance setting or try again after 24 Hours.");
        }
    }
    public void setabs(View v)
    {
        if (vfyatnd()) {
            try {
                ad.updtabs(sidu[trip], ++absu[trip], String.valueOf(dat));
                tdat[trip]=dat;
                alrt("SUCCESS", "ATTENDANCE SUCCESSFULLY UPDATED!");
            } catch (Exception e) {
                Log.d("atnd2==setabs", e.toString());
                alrt("OPERATION FAILED", "Critical Error Occurred. Check Logcat for further information. atnd_2==setabs");
            }
        }
        else
        {
            alrt("FORBIDDEN","Attendance of this student has already been marked for this day. Please clear attendance setting or try again after 24 Hours.");
        }
    }
    public void setlve(View v)
    {
        if (vfyatnd()) {
            try {
                ad.updtlve(sidu[trip], ++lveu[trip], String.valueOf(dat));
                tdat[trip]=dat;
                alrt("SUCCESS", "ATTENDANCE SUCCESSFULLY UPDATED!");
            } catch (Exception e) {
                Log.d("atnd2==setlve", e.toString());
                alrt("OPERATION FAILED", "Critical Error Occurred. Check Logcat for further information. atnd_2==setlve");
            }
        }
        else
        {
            alrt("FORBIDDEN","Attendance of this student has already been marked for this day. Please clear attendance setting or try again after 24 Hours.");        }
    }
    public void proceed(View v)
    {
        if (trip<mx-1)
        {
            trip++;
            displayit(trip);
        }
        else
        {
            alrt("HOLD ON", "You have already reached the end of the attendance list");
        }

    }
    public void clratd(View v)
    {
        try {
            ad.updt(sidu[trip], String.valueOf(dat-86400000));
            tdat[trip]=dat-86400000;
            alrt("SUCCESS", "MARKING CLEARED FOR THIS STUDENT !");
        } catch (Exception e) {
            Log.d("atnd2==clratd", e.toString());
            alrt("OPERATION FAILED", "Critical Error Occurred. Check Logcat for further information. atnd_2==setlve");
        }
    }
    private void alrt(String tag,String msg)
    {
        AlertDialog.Builder ad = new AlertDialog.Builder(this);
        ad.setTitle(tag);
        ad.setMessage(msg);
        ad.create();
        ad.show();
    }
}
