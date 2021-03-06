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
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;

public class stud_info extends AppCompatActivity {

    DrawerLayout dr;
    NavigationView nv;
    String uname,ini;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stud_info);
        ini = new sysfile().readFromFile(this);
        dr = findViewById(R.id.drawer_layout);
        cspinfill();
        spinsel();
        sspinsel();
        instant();
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
                            Intent it = new Intent(stud_info.this,MainActivity.class);
                            startActivity(it);
                        }
                        else if (mt.getItemId()==R.id.nav_sync)
                        {
                            Intent it = new Intent(stud_info.this,serv_sync.class);
                            startActivity(it);
                        }
                        else if (mt.getItemId()==R.id.nav_out)
                        {
                            signfun();
                        }
                        else if (mt.getItemId()==R.id.nav_addad)
                        {
                            Intent it = new Intent(stud_info.this,adminacc.class);
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
        alrt("SIGN OUT","You have been successfully signed out.");
        Intent it = new Intent(this,MainActivity.class);
        startActivity(it);
    }
    String cout,sout;
    public void opendrawer(View v)
    {
        dr.openDrawer(GravityCompat.START);
    }
    addreg ad = new addreg(this);
    ListView lst;
    ArrayList<fieldsinfo> al;
    studadap sd;
    private  void instant()
    {
        lst = findViewById(R.id.studlst);
        al = new ArrayList<>();
        sd = new studadap(this,al);
    }
    private void lstfiller(String nme, String dig, String per)
    {
        fieldsinfo ifo = new fieldsinfo(nme,dig,per);
        sd.add(ifo);
        lst.setAdapter(sd);
    }
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
            temp.settbl(ini+"_clslst");
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
            temp.settbl(ini+"_clslst");
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
        String tot = ini+"_"+cout+"_"+sout;
        ad.tnameadd(tot);
        setit();
    }
    private void setit()
    {
        try {
            Cursor cr = ad.getallstud();
            String tmp[] = new String[2];
            int nums[] = new int[4];
            if (cr.moveToFirst()) {
                do {
                    tmp[0]=cr.getString(0);
                    tmp[1]=cr.getString(1);
                    nums[0]=cr.getInt(4);
                    nums[1]=cr.getInt(5);
                    nums[2]=cr.getInt(6);
                    nums[3]=nums[0]+nums[1]+nums[2];
                    preprocess(tmp,nums);
                } while (cr.moveToNext());
            }
            //displayit(trip);
        }
        catch (Exception e)
        {
            Log.d("atnd_2==setit",e.toString());
        }
    }
    private void preprocess(String nme[],int num[])
    {
        String fis = nme[0]+" ("+nme[1]+")";
        String dig = "P: "+num[0]+" A: "+num[1]+" L: "+num[2];
        float per[]= new float[3];
        Log.d("stud_info",String.valueOf(num[3]));
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.CEILING);
        if (num[3]!=0) {
            per[0] = ((float)num[0] / num[3])*100;
            per[1] = ((float)num[1] / num[3])*100;
            per[2] = ((float)num[2] / num[3])*100;

        }
        else
        {
            per[0]=0;
            per[1]=0;
            per[2]=0;
        }
        String perc = "P: "+df.format(per[0])+"% A: "+df.format(per[1])+"% L: "+df.format(per[2])+"%";
        Log.d("one",fis);
        Log.d("Two",dig);
        Log.d("three",perc);
        lstfiller(fis,dig,perc);
    }
    private void alrt(String tag, String msg)
    {
        AlertDialog.Builder ad = new AlertDialog.Builder(this);
        ad.setTitle(tag);
        ad.setMessage(msg);
        ad.create();
        ad.show();
    }
}
