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
        import android.widget.TextView;

        import java.util.List;

public class signup extends AppCompatActivity {

    DrawerLayout dr;
    NavigationView nv;
    String uname;
    String ini;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ini = new sysfile().readFromFile(this);
        dr = findViewById(R.id.drawer_layout);
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
                            Intent it = new Intent(signup.this,MainActivity.class);
                            startActivity(it);
                        }
                        else if (mt.getItemId()==R.id.nav_sync)
                        {
                            Intent it = new Intent(signup.this,serv_sync.class);
                            startActivity(it);
                        }
                        else if (mt.getItemId()==R.id.nav_out)
                        {
                            signfun();
                        }
                        else if (mt.getItemId()==R.id.nav_addad)
                        {
                            Intent it = new Intent(signup.this,adminacc.class);
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
    public void opendrawer(View v)
    {
        dr.openDrawer(GravityCompat.START);
    }
    public void signup_submit(View view){
        TextView tx = findViewById(R.id.name);
        String nme = tx.getText().toString();
        tx = findViewById(R.id.tid);
        String tid = tx.getText().toString();
        tx = findViewById(R.id.pwd);
        String pwd = tx.getText().toString();
        tx = findViewById(R.id.repwd);
        String repwd = tx.getText().toString();
        if (nme.equals("")||tid.equals("")||pwd.equals("")||repwd.equals(""))
        {
            alrt("FIELDS BLANK!","One of more important fields are left blank");
        }
        else if (!pwd.equals(repwd))
        {
            alrt("PASSWORD MISMATCH!","Entered are re-entered password do not match. Please verify again.");
        }
        else
        {
            DatabaseHandler db = new DatabaseHandler(this);
            sysfile sy = new sysfile();
            db.settbl(sy.readFromFile(this)+"_loginmgmt");
            try{
                db.frcrt();
            }
            catch (Exception e)
            {
                Log.e("signup==sign_up_submit",e.toString());
            }
            try {
                db.addnewteach(new LOGIN(tid, nme, pwd));
                Log.d("Reading: ", "Reading all contacts..");
                List<LOGIN> contacts = db.getalllogin();
                alrt("SUCCESS","Successfully added a new Teacher");

                for (LOGIN cn : contacts) {
                    String log = "Id: " + cn.getID() + " ,Name: " + cn.getName() + " ,Password: " + cn.getpwd();
                    Log.d("Name: ", log);
                }
            }
            catch (Exception e)
            {
                Log.e("signup==sign_up_submit",e.toString());
            }
        }
    }
    private void alrt(String tag, String msg)
    {
        AlertDialog.Builder er= new AlertDialog.Builder(this);
        er.setMessage(msg);
        er.setTitle(tag);
        er.create();
        er.show();
    }
}
