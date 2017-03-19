package com.example.yeshwanthemani.billpayementapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;

import android.graphics.drawable.ColorDrawable;
import android.graphics.Color;
import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.view.Window;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;


public class MainActivity extends AppCompatActivity {

    TextView t1,t2;
    String a,b;
    int valid;
    public static String uemail = "";

    JSONObject jobj = null;
    ClientServer clientServerInterface = new ClientServer();
    TextView textView;
    String ab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        android.support.v7.app.ActionBar bar = getSupportActionBar();
        if(bar != null){
            bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FF6600")));
            bar.setTitle("BillPay");
        }

        TextView t  = (TextView)findViewById(R.id.textView2);
        t.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), SignupActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        switch(item.getItemId()) {
            case R.id.action_settings:
                return true;
            //  case R.id.action_youtube:
            //     Intent i1 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=okVhbNJizPk"));
            //   startActivity(i1);
            //   return true;

            default:
                return super.onOptionsItemSelected(item);
        }



    }

    public void press(View view)
    {
        Intent i = new Intent(this, testActivity.class);
        startActivity(i);
    }


    public void login(View view)
    {



         t1 =(TextView) findViewById(R.id.editText);
        a  = t1.getText().toString();
         t2 =(TextView) findViewById(R.id.editText2);
         b = t2.getText().toString();



         valid = 1;

        if( a.equals("") || b.equals(""))
        {
            valid =0;
            Context b1 = getApplicationContext();
            CharSequence d1 = "Enter all the details!";
            int di = Toast.LENGTH_SHORT;

            Toast t1 = Toast.makeText(b1,d1,di);
            t1.show();

        }
        else
        {
            new RetreiveData().execute();
        }


    }



    class RetreiveData extends AsyncTask<String,String,String> {

        @Override
        protected String doInBackground(String... arg0) {
            // TODO Auto-generated method stub



            try {
                AESCryptography mcrypt = new AESCryptography();
/* Encrypt */
                b = AESCryptography.bytesToHex(mcrypt.encrypt(b));
/* Decrypt */
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }



            HashMap<String, String> m = new HashMap<String, String>();

            m.put("phone", a);
            m.put("password", b);



            jobj = clientServerInterface.makeHttpRequest("http://www.campusdost.com/movies/jlogin.php", m);

            try {
                ab = jobj.getString("status");
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return ab;
        }

        protected void onPostExecute(String ab) {

            if(valid == 1) {

                if(ab.equals("failure"))
                {
                    Context b1 = getApplicationContext();
                    CharSequence d1 = "Invalid Credentials!";
                    int di = Toast.LENGTH_SHORT;

                    Toast t1 = Toast.makeText(b1,d1,di);
                    t1.show();
                }
                else
                {
                   if(b.equals(ab))
                   {
                       uemail = a;
                       Intent i = new Intent(MainActivity.this,HomeActivity.class);
                       startActivity(i);

                   }
                    else
                   {
                       Context b1 = getApplicationContext();
                       CharSequence d1 = "Invalid Credentials!";
                       int di = Toast.LENGTH_SHORT;

                       Toast t1 = Toast.makeText(b1,d1,di);
                       t1.show();
                   }
                }
            }
        }
    }

}