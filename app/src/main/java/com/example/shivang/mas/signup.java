package com.example.shivang.mas;

        import android.support.v4.view.GravityCompat;
        import android.support.v4.widget.DrawerLayout;
        import android.support.v7.app.AlertDialog;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.View;
        import android.widget.TextView;

        import java.util.List;

public class signup extends AppCompatActivity {

    DrawerLayout dr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        dr = findViewById(R.id.drawer_layout);
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
                    String namesql = "Android";
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
