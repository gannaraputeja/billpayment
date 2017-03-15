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

import java.util.ArrayList;
import java.util.HashMap;

public class PayActivity extends AppCompatActivity {


    JSONObject jobj = null;
    ClientServer clientServerInterface = new ClientServer();
    TextView tp1,tp2,tp3,tp4;
    String tid,mname,amount,desc;
    ArrayList<String> ab  = new ArrayList<String>();
    String d = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        View vy = findViewById(R.id.b2);
        vy.setVisibility(View.INVISIBLE);


      //  TextView v = (TextView) findViewById(R.id.result);
       // v.setText(HomeActivity.scanresult);

        android.support.v7.app.ActionBar bar = getSupportActionBar();
        if(bar != null){
            bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FF6600")));
            bar.setTitle("BillPay");
        }

        tp1 = (TextView) findViewById(R.id.pt1);
        tp2 = (TextView) findViewById(R.id.pt2);
        tp3 = (TextView) findViewById(R.id.pt3);
        tp4 = (TextView) findViewById(R.id.pt4);


        new RetreiveData().execute();

    }





    class RetreiveData extends AsyncTask<String,String,String> {

        @Override
        protected String doInBackground(String... arg0) {
            // TODO Auto-generated method stub
            HashMap<String,String> m = new HashMap<String,String>();
           // m.put("tid",HomeActivity.scanresult);
            m.put("tid",HomeActivity.scanresult);

            jobj = clientServerInterface.makeHttpRequest("http://www.campusdost.com/movies/jbill.php",m);

            try {
                ArrayList<String> ab  = new ArrayList<String>();
                tid = jobj.getString("transid");;
                mname = jobj.getString("merchantname"); ;
                amount = jobj.getString("amount"); ;
                desc = jobj.getString("description");;

                ab.add(tid);
                ab.add(mname);
                ab.add(amount);
                ab.add(desc);
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return tid;
        }
        protected void onPostExecute(String ab){

            tp1.setText(tid);
            tp2.setText(mname);
            tp3.setText(amount);
            tp4.setText(desc);
        }

    }

    public void pay(View v)
    {


        new RetreiveDataPay().execute();




        View vy = findViewById(R.id.b1);
        vy.setVisibility(View.INVISIBLE);

        View vx = findViewById(R.id.b2);
        vx.setVisibility(View.VISIBLE);
    }





    class RetreiveDataPay extends AsyncTask<String,String,String> {

        @Override
        protected String doInBackground(String... arg0) {
            // TODO Auto-generated method stub
            HashMap<String,String> m = new HashMap<String,String>();
            // m.put("tid",HomeActivity.scanresult);
            m.put("merchant",mname);
            m.put("amount",amount);
            m.put("uphone",MainActivity.uemail);

            jobj = clientServerInterface.makeHttpRequest("http://www.campusdost.com/movies/jpay.php",m);

            try {
                d = jobj.getString("status");
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return d;
        }
        protected void onPostExecute(String ab){


        }

    }


}
