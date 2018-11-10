package com.example.shivang.mas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class afterlogin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_afterlogin);
    }
    public void gotonstud(View view)
    {
        Intent it = new Intent(this,addnewstud.class);
        startActivity(it);
    }
    public void gotoacls(View view)
    {
        Intent it = new Intent(this,addclass.class);
        startActivity(it);
    }
    public void markup(View v)
    {
        Intent it = new Intent(this, atnd_2.class);
        startActivity(it);
    }
    public void studinfo(View v)
    {
        Intent it = new Intent(this,stud_info.class);
        startActivity(it);
    }
}
