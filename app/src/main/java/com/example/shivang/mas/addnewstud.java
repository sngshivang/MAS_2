package com.example.shivang.mas;

import android.database.Cursor;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import org.json.JSONArray;

import java.util.ArrayList;

public class addnewstud extends AppCompatActivity {

    DrawerLayout dr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dr = findViewById(R.id.drawer_layout);
        setContentView(R.layout.activity_addnewstud);
        cspinfill();
        spinsel();
        sspinsel();
    }
    String cout,sout;
    public void opendrawer(View v)
    {
        dr.openDrawer(GravityCompat.START);
    }
    private void spinsel()
    {
        Spinner sp = findViewById(R.id.cls_spin2);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                cout=adapterView.getSelectedItem().toString();
                sspinfill();
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
                //out=adapterView.getItemAtPosition(0).toString();

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
    public void enterqr(View v)
    {
        EditText ev = findViewById(R.id.add_sname);
        String nme = ev.getText().toString();
        ev = findViewById(R.id.add_ssid);
        String sid = ev.getText().toString();
        ev = findViewById(R.id.add_semail);
        String em = ev.getText().toString();
        ev = findViewById(R.id.add_smob);
        String mo = ev.getText().toString();
        String tbl = cout+"_"+sout;
        addreg ad = new addreg(this);
        try {
            ad.tnameadd(tbl);
            ad.addcls(nme, sid, em, mo);
            alrt("SUCCESS","Student Addition is successfull. You can either add more students or go back to perform other operations.");
        }
        catch (Exception e)
        {
            Log.e("addnewstud==enterqr",e.toString());
            alrt("ERROR!", "Failed to add student. Contact the developer or check the Logcat for further information");
        }
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
