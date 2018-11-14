package com.example.shivang.mas;

import android.content.Intent;
import android.database.Cursor;
import android.media.MediaDataSource;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class alreadylogin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alreadylogin);
        prepop();
    }
    String uname,pass;
    private void prepop()
    {
        universaldat dt = new universaldat(this);
        Cursor cr = dt.getAcc();
        if (cr.moveToFirst())
        {
            do {
                uname=cr.getString(0);
                pass=cr.getString(1);
            }while (cr.moveToNext());
        }
        TextView tv = findViewById(R.id.uname);
        tv.setText(uname);
    }
    public void validate(View v)
    {
        EditText ed = findViewById(R.id.mainpwd);
        String ipass= ed.getText().toString();
        if (pass.equals(pass))
        {
           Intent it = new Intent(this,afterlogin.class);
           startActivity(it);
        }
        else
        {
            alrtmsg("INCORRECT PASSWORD","This is an incorrect password for this account. Please try again");
        }
    }
    private void alrtmsg(String tag,String msg)
    {
        AlertDialog.Builder ad = new AlertDialog.Builder(this);
        ad.setTitle(tag);
        ad.setMessage(msg);
        ad.create();
        ad.show();
    }
}
