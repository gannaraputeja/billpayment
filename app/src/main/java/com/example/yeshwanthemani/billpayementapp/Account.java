package com.example.yeshwanthemani.billpayementapp;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Account extends AppCompatActivity {

    JSONObject jobj = null;
    ClientServer clientServerInterface = new ClientServer();
    TextView tex;
    String ab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        android.support.v7.app.ActionBar bar = getSupportActionBar();
        if(bar != null){
            bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FF6600")));
            bar.setTitle("BillPay");
        }


        new RetreiveData().execute();
    }



    class RetreiveData extends AsyncTask<String,String,String> {

        @Override
        protected String doInBackground(String... arg0) {
            // TODO Auto-generated method stub
            HashMap<String,String> m = new HashMap<String,String>();
            m.put("uemail",MainActivity.uemail);

            jobj = clientServerInterface.makeHttpRequest("http://www.campusdost.com//movies/jbalance.php",m);

            try {
                ab = jobj.getString("status");
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return ab;
        }
        protected void onPostExecute(String ab){
            tex = (TextView) findViewById(R.id.bal);
            tex.setText(ab);
        }

    }

}
