package com.cloud.demo.ajk_riset.demo_cloud;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Adapter.ListViewAdapter;
import Service.JSONParser;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.ClientProtocolException;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.message.BasicNameValuePair;

public class MainActivity extends AppCompatActivity {
    Button tombol1,tombol2;
    public static EditText Isian1,Isian2;
    ListView lv;
    ListViewAdapter adapter;
    String user,pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tombol1 = (Button) findViewById(R.id.button);
        tombol2 = (Button) findViewById(R.id.btnClear);
        Isian1 = (EditText) findViewById(R.id.editText);
        Isian2 = (EditText) findViewById(R.id.editText2);
        lv = (ListView) findViewById(R.id.listview1);
        new LongOperation().execute("");
        tombol1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user = Isian1.getText().toString();
                pass = Isian2.getText().toString();
                new DaftarBaru().execute("");
            }
        });
    }

    // Class with extends AsyncTask class
    private class LongOperation extends AsyncTask<String, Void, Void> {

        private ProgressDialog Dialog = new ProgressDialog(MainActivity.this);
        boolean berhasil = false;
        String[] id,nama;


        protected void onPreExecute() {
            // NOTE: You can call UI Element here.

            //UI Element

            Dialog.setMessage("Loading Please Wait..");
            Dialog.show();
        }

        // Call after onPreExecute method
        protected Void doInBackground(String... urls) {
            JSONObject json = JSONParser.getJSONfromURL("http://agarwood.web.id/data_test_login.php");
            try {

                JSONArray data = json.getJSONArray("data");
                Log.e("Main Jumlah : ", "" + data.length());

                if (data.length() >= 1) {JSONObject jsonobj = data.getJSONObject(0);
                    id = new String[data.length()];
                    nama = new String[data.length()];
                    for(int i=0;i<data.length();i++) {
                        nama[i] = jsonobj.getString("username");
                        id[i] = jsonobj.getString("id");
                    }
                } else {

                }
            } catch (JSONException e) {
                Log.e("log_tag", "Error parsing data " + e.toString());
            }


            return null;
        }

        protected void onPostExecute(Void unused) {
            // NOTE: You can call UI Element here.

            // Close progress dialog

            adapter = new ListViewAdapter(MainActivity.this,id,nama) ;
            lv.setAdapter(adapter);
            Dialog.dismiss();
        }

    }

    //insertdata
    public class DaftarBaru extends AsyncTask<String, Void, Void> {
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://agarwood.web.id/insert_testlogin.php");
        //		HttpPost httppost = new HttpPost("http://192.168.137.1/AppsaniApp_new/update.php");
        private ProgressDialog Dialog = new ProgressDialog(MainActivity.this);
        String jumlah, status, useroid;


        protected void onPreExecute() {
            // NOTE: You can call UI Element here.

            //UI Element

            Dialog.setMessage("Loading Please Wait..");
            Dialog.show();
        }

        // Call after onPreExecute method
        protected Void doInBackground(String... urls) {
            try {
                // Add your data
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(
                        2);

                nameValuePairs.add(new BasicNameValuePair("username", user));
                nameValuePairs.add(new BasicNameValuePair("password", pass));
//					Log.e("data ", kursor.getString(0)+" "+kursor.getString(1)+" "+kursor.getString(2)+" "+kursor.getString(3));
                // Execute HTTP Post Request
                HttpResponse response = httpclient.execute(httppost);
                Log.v("Post Status", "Code: "
                        + response.getStatusLine().getReasonPhrase());


            } catch (ClientProtocolException e) {

                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block

            }

            return null;
        }

        protected void onPostExecute(Void unused) {
            // NOTE: You can call UI Element here.

            // Close progress SUr
            Toast.makeText(MainActivity.this, "Data Sudah Tersimpan", Toast.LENGTH_LONG).show();
            Dialog.dismiss();
        }
    }
}
