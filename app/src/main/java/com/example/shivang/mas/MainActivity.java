package com.example.shivang.mas;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawermain);
    }
    public void login(View view)
    {
        boolean trip = false;
        TextView tv = findViewById(R.id.mainusr);
        String tid = tv.getText().toString();
        tv = findViewById(R.id.mainpwd);
        String pwd = tv.getText().toString();
        DatabaseHandler db = new DatabaseHandler(this);
        try {
            LOGIN cn = db.getlogin(tid);
            AlertDialog.Builder err = new AlertDialog.Builder(this);
            if (cn.getpwd().equals(pwd))
                trip = true;
            else
            {
                alrtmsg("FIELD BLANK","One or more fields are left blank.");
            }
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
                err.setTitle("INCORRECT INFOMRATION");
                err.setMessage("The Teacher ID or Password is incorrect. Please verify those entries");
                err.create();
                err.show();
            }

        }
        catch (Exception e)
        {
            Log.d("EXCEP",e.toString() );
        }
        //Intent it = new Intent(this, atnd_2.class);
        //startActivity(it);
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
