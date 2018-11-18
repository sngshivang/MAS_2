package com.example.shivang.mas;

import android.app.Activity;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;

import org.json.JSONArray;

import java.util.ArrayList;

public class addclass extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addclass);
        flclsspin();
        spinsel();
    }
    //pandey was here
    String out="";
    public void addclass(View view)
    {
        EditText ed = findViewById(R.id.clsnme);
        String cne = ed.getText().toString();
        dbcls(cne);
    }
    private void dbcls(String inp)
    {
        try {
            clslst add = new clslst(this);
            JSONArray ary = new JSONArray();
            ary.put("NOT ADDED");
            String fin=ary.toString();
            try {
                add.forcecrt();
            }
            catch (Exception f)
            {
                Log.e("addclass==dbcls", f.toString());
            }
            add.addcls(inp,fin);
            alrt("SUCCESS","CLASS ADDITION WAS SUCCESSFUL");
            flclsspin();
        }
        catch (Exception e)
        {
            Log.e("addclss.java",e.toString());
        }
    }
    private void flclsspin()
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
    private void spinsel()
    {
        Spinner sp = findViewById(R.id.cls_spin2);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                out=adapterView.getSelectedItem().toString();
                //out=adapterView.getItemAtPosition(0).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    public void adsub(View view)
    {
        EditText ed = findViewById(R.id.add_ssid);
        String fname = ed.getText().toString();
        push_sub(out,fname);
        out+="_"+fname;
        Log.d("addclass",out);
        try
        {
            newreg(out);
            ed.setText("");
            alrt("SUCCESS", "You have successfully added a subject in the selected class. You can add other subjects or go back to perform other operations");
        }
        catch (Exception e)
        {
            Log.d("addclass",e.toString());

        }
    }
    private void newreg(String inp)
    {
        addreg ad = new addreg(this);
        try {
            ad.tnameadd(inp);
        }
        catch (Exception e)
        {
            Log.e("addclass==newreg",e.toString());
        }
        try {
            ad.forcecrt();
        }
        catch (Exception e) {
            Log.e("addclass==newreg", e.toString());
        }
    }
    public void alrt(String tag,String msg)
    {
        AlertDialog.Builder bd = new AlertDialog.Builder(addclass.this);
        bd.setTitle(tag);
        bd.setMessage(msg);
        bd.create();
        bd.show();
    }
    private void push_sub(String cls, String sub)
    {
        clslst cl = new clslst(this);
        String inp = cl.getsub(cls);
        try{
        JSONArray ar = new JSONArray(inp);
            ar.put(sub);
            cl.updatesub(cls, ar.toString());
        }
        catch (Exception e)
        {
            Log.d("addclass",e.toString());
        }
    }
}
