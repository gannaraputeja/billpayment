package com.example.yeshwanthemani.billpayementapp;

/**
 * Created by yeshwanth emani on 10-03-2017.
 */

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.io.BufferedReader;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.json.JSONException;
import org.json.JSONObject;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* this class helps to get data from server*/
public class ClientServer {
    //input stream deals with bytes
    static InputStream is = null;
    static JSONObject jobj = null;
    static String json = "";
    //constructor
    public ClientServer(){

    }
    //this method returns json object.
    public JSONObject makeHttpRequest(String url, HashMap<String,String> code){
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        Iterator it = code.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            System.out.println(pair.getKey() + " = " + pair.getValue());
            nameValuePairs.add(new BasicNameValuePair((String) pair.getKey(),(String) pair.getValue()));
            it.remove(); // avoids a ConcurrentModificationException
        }
       // nameValuePairs.add(new BasicNameValuePair("city", "boduppal"));
//http client helps to send and receive data
        DefaultHttpClient httpclient = new DefaultHttpClient();
//our request method is post
        HttpPost httppost = new HttpPost(url);
        try {
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
        }
        catch (UnsupportedEncodingException e)
        {

        }
        try {
//get the response
            HttpResponse httpresponse = httpclient.execute(httppost);
            HttpEntity httpentity = httpresponse.getEntity();
// get the content and store it into inputstream object.
            is = httpentity.getContent();
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        try {
//convert byjavate-stream to character-stream.
            BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            try {
                while((line = reader.readLine())!=null){
                    sb.append(line+"\n");

                }
//close the input stream
                is.close();
                json = sb.toString();
                try {
                    jobj = new JSONObject(json);
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return jobj;
    }
}