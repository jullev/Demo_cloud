package com.cloud.demo.ajk_riset.demo_cloud;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import Service.JSONParser;

/**
 * Created by AJK-Riset on 1/31/2017.
 */

public class LoginActivity extends AppCompatActivity {
    EditText txtUsername, txtPassword;
    Button Login, Clear;
    SharedPreferences pref;
    String nama, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginview);
        pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        txtUsername = (EditText) findViewById(R.id.txtUname);
        txtPassword = (EditText) findViewById(R.id.txtPass);
        Login = (Button) findViewById(R.id.btnLogin);
        Clear = (Button) findViewById(R.id.btnClear);
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nama = txtUsername.getText().toString();
                pass = txtPassword.getText().toString();
                new LongOperation().execute("");

            }
        });
    }

    // Class with extends AsyncTask class
    private class LongOperation extends AsyncTask<String, Void, Void> {

        private ProgressDialog Dialog = new ProgressDialog(LoginActivity.this);
        boolean berhasil = false;


        protected void onPreExecute() {
            // NOTE: You can call UI Element here.

            //UI Element

            Dialog.setMessage("Loading Please Wait..");
            Dialog.show();
        }

        // Call after onPreExecute method
        protected Void doInBackground(String... urls) {
            JSONObject json = JSONParser.getJSONfromURL("http://agarwood.web.id/test_login.php?username=" + nama + "&password=" + pass);
     try {

                JSONArray data = json.getJSONArray("data");
                Log.e("Main Jumlah : ", "" + data.length());

                if (data.length() >= 1) {JSONObject jsonobj = data.getJSONObject(0);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("username", jsonobj.getString("username"));
                    editor.putString("id", jsonobj.getString("id"));
                    editor.commit();
                    //simpan pada database
                    berhasil = true;
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
            Dialog.dismiss();
            if (berhasil) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(LoginActivity.this, "Tidak Berhasil Login", Toast.LENGTH_LONG).show();
            }

        }

    }


}
