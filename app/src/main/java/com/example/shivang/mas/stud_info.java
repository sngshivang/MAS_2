package com.example.shivang.mas;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import org.json.JSONArray;

import java.util.ArrayList;

public class stud_info extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stud_info);
        cspinfill();
        spinsel();
        sspinsel();
    }
    String cout,sout;
    String nmeu[];
    String sidu[];
    Integer preu[];
    Integer absu[];
    Integer lveu[];
    int trip=0,mx;
    addreg ad = new addreg(this);
    private void lstfiller(String nme, String dig, String per)
    {
        ListView lst = findViewById(R.id.studlst);
        fieldsinfo ifo = new fieldsinfo(nme,dig,per);
        ArrayList<fieldsinfo> al = new ArrayList<fieldsinfo>();
        studadap sd = new studadap(this,al);
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
                //settable();
                //out=adapterView.getItemAtPosition(0).toString();

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
            String tmp[] = new String[2];
            int nums[] = new int[4];
            if (cr.moveToFirst()) {
                do {
                    tmp[0]=cr.getString(0);
                    tmp[1]=cr.getString(1);
                    nums[0]=cr.getInt(4);
                    nums[1]=cr.getInt(5);
                    nums[2]=cr.getInt(6);
                    preprocess(tmp,nums);
                } while (cr.moveToNext());
            }
            int sze = nme.size();
            nmeu = new String[sze];
            sidu = new String[sze];
            preu = new Integer[sze];
            absu = new Integer[sze];
            lveu = new Integer[sze];
            nmeu = nme.toArray(nmeu);
            sidu = sid.toArray(sidu);
            preu = pre.toArray(preu);
            absu = abs.toArray(absu);
            lveu = lve.toArray(lveu);
            mx = sze;
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
        //per[0] = num[0]/num[3];
        //per[1] = num[1]/num[3];
        //per[2] = num[2]/num[3];
        String perc= "hello";
        //String perc = "P: "+per[0]+"% A: "+per[1]+"% L: "+per[2]+"%";
        Log.d("one",fis);
        Log.d("Two",dig);
        Log.d("three",perc);
        lstfiller(fis,dig,perc);
    }
}
