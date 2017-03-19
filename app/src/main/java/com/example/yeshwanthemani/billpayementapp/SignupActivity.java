package com.example.yeshwanthemani.billpayementapp;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import android.content.Context;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;

public class SignupActivity extends AppCompatActivity {

    JSONObject jobj = null;
    ClientServer clientServerInterface = new ClientServer();
    TextView textView;
    String ab;

    EditText e1;
    String name ;

    EditText e2 ;
    String phone ;

    EditText e3 ;
    String email ;

    EditText e4;
    String password;

    EditText e5 ;
    String passwordre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        android.support.v7.app.ActionBar bar = getSupportActionBar();
        if(bar != null){
            bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FF6600")));
            bar.setTitle("BillPay");
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void signup(View view)
    {

        e1 = (EditText) findViewById(R.id.editText3);
       e2 = (EditText) findViewById(R.id.editText4);
        e3 = (EditText) findViewById(R.id.editText5);
        e4 = (EditText) findViewById(R.id.editText8);
        e5 = (EditText) findViewById(R.id.editText7);



         name = e1.getText().toString();
         phone = e2.getText().toString();
         email = e3.getText().toString();
         password = e4.getText().toString();
         passwordre = e4.getText().toString();

        int valid = 1;

        if(name.equals("") || email.equals("") || password.equals("") || passwordre.equals("") || phone.equals("") )
        {
            valid = 0;
            Context h2 = getApplicationContext();
            CharSequence n2 = "Fill all the details!! ";
            int dv = Toast.LENGTH_SHORT;

            Toast t1 = Toast.makeText(h2,n2,dv);
            t1.show();
        }

        if(!password.equals(passwordre))
        {
            valid = 0;
            Context c1 = getApplicationContext();
            CharSequence s1g = "Passwords dont match";
            int dv = Toast.LENGTH_SHORT;

            Toast t1 = Toast.makeText(c1,s1g,dv);
            t1.show();
        }
        if(phone.length() != 10)
        {
            valid = 0;
            Context h1 = getApplicationContext();
            CharSequence n1 = "Enter a valid Mobile Number";
            int dv = Toast.LENGTH_SHORT;

            Toast t1 = Toast.makeText(h1,n1,dv);
            t1.show();

        }

        if(valid == 1)
        {
            new RetreiveData().execute();
        }

    }
    class RetreiveData extends AsyncTask<String,String,String> {

        @Override
        protected String doInBackground(String... arg0) {
            // TODO Auto-generated method stub
            String encrypted = new String(password);

            try {
                AESCryptography mcrypt = new AESCryptography();
/* Encrypt */
                encrypted = AESCryptography.bytesToHex(mcrypt.encrypt(password));
/* Decrypt */
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            HashMap<String, String> m = new HashMap<String, String>();
            m.put("name", name);
            m.put("phone", phone);
            m.put("email", email);
            m.put("password", encrypted);


            jobj = clientServerInterface.makeHttpRequest("http://www.campusdost.com/movies/jsignup.php", m);

            try {
                ab = jobj.getString("status");
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return ab;
        }

        protected void onPostExecute(String ab) {

            Context h1 = getApplicationContext();
            CharSequence n1 = "Registered successfully";
            int dv = Toast.LENGTH_SHORT;

            Toast t1 = Toast.makeText(h1,n1,dv);
            t1.show();
            Intent intent = new Intent(SignupActivity.this,MainActivity.class);
            startActivity(intent);
        }
    }

}
