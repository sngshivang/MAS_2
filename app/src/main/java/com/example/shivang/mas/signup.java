package com.example.shivang.mas;

        import android.support.v7.app.AlertDialog;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.View;
        import android.widget.TextView;

        import java.util.List;

public class signup extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
    }

    public void signup_submit(View view){
        AlertDialog.Builder err = new AlertDialog.Builder(signup.this);
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
            err.setTitle("FIELDS BLANK!");
            err.setMessage("One of more important fields are left blank");
            err.create();
            err.show();
        }
        else if (!pwd.equals(repwd))
        {
            err.setTitle("PASSWORD MISMATCH!");
            err.setMessage("Entered are re-entered password do not match. Please verify again.");
            err.create();
            err.show();
        }
        else
        {
            DatabaseHandler db = new DatabaseHandler(this);
            db.addnewteach(new LOGIN(tid,nme,pwd));
            Log.d("Reading: ", "Reading all contacts..");
            List<LOGIN> contacts = db.getalllogin();

            for (LOGIN cn : contacts) {
                String log = "Id: "+cn.getID()+" ,Name: " + cn.getName() + " ,Password: " + cn.getpwd();
                Log.d("Name: ", log);
                String namesql = "Android";
            }
        }
    }
}
