package com.cloud.demo.ajk_riset.demo_cloud;

import android.app.ProgressDialog;
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
    EditText txtUsername,txtPassword;
    Button Login,Clear;
    SharedPreferences pref;
    String nama,pass;
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
                if(nama.equals("admin") && pass.equals("admin")){
                    Toast.makeText(LoginActivity.this,"Login Berhasil",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(LoginActivity.this,"Login Gagal",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    // Class with extends AsyncTask class
    private class LongOperation extends AsyncTask<String, Void, Void> {

        private ProgressDialog Dialog = new ProgressDialog(LoginActivity.this);
        String jumlah, status, useroid;


        protected void onPreExecute() {
            // NOTE: You can call UI Element here.

            //UI Element

            Dialog.setMessage("Loading Please Wait..");
            Dialog.show();
        }

        // Call after onPreExecute method
        protected Void doInBackground(String... urls) {


            // Call long running operations here (perform background computation)
            // NOTE: Don't call UI Element here.

            // Server url call by GET method

            JSONObject json = JSONParser.getJSONfromURL("http://agarwood.web.id/login.php?username=" + nama + "&password=" + pass);
//            	JSONObject json = JSONFunctions.getJSONfromURL("http://192.168.137.1/AppsaniApp_new/login.php?username="+uname+"&password="+pass);
            try {

                JSONArray data = json.getJSONArray("data");
                Log.e("Main Jumlah : ", "" + data.length());

                if (data.length() >= 1) {
//                    Cek(useroid);
//                    berhasil();
                    JSONObject jsonobj = data.getJSONObject(0);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("username", jsonobj.getString("pengguna"));
                    editor.putString("email", jsonobj.getString("email"));
                    editor.putString("role", jsonobj.getString("jenis_pengguna"));
                    editor.putString("id", jsonobj.getString("idm_pengguna"));
                    editor.commit();
                    //simpan pada database
                    String id_pengguna = jsonobj.getString("idm_pengguna");
                    String nama = jsonobj.getString("nama");
                    String alamat = jsonobj.getString("alamat");
                    String username = jsonobj.getString("pengguna");
                    String pass = jsonobj.getString("password");
                    String email = jsonobj.getString("email");
                    String nohp = jsonobj.getString("no_hp");
                    String keterangan = jsonobj.getString("keterangan");
                    String role = jsonobj.getString("jenis_pengguna");
                    useroid =role;
                    Log.i("Data", id_pengguna + " " + nama + " " + alamat);

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

        }

    }


}
