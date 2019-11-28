package com.olpos;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;



public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    //Dibawah ini merupakan perintah untuk mendefinikan View
    private EditText editTextUserId;
    private EditText editTextPassword;
    private EditText editTextLevel;

    private Button buttonAdd;
    private Button buttonView;

   // @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Inisialisasi dari View
        editTextUserId = (EditText) findViewById(R.id.editTextUserID);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextLevel = (EditText) findViewById(R.id.editTextLevel);

        buttonAdd = (Button) findViewById(R.id.buttonAdd);
        buttonView = (Button) findViewById(R.id.buttonView);

        //Setting listeners to button
        buttonAdd.setOnClickListener(this);
        buttonView.setOnClickListener(this);
    }


    //Dibawah ini merupakan perintah untuk Menambahkan Pegawai (CREATE)
    private void AddUser(){

        final String UserId = editTextUserId.getText().toString().trim();
        final String Password = editTextPassword.getText().toString().trim();
        final String Level = editTextLevel.getText().toString().trim();

        class AddUser extends AsyncTask<Void,Void,String>{

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(MainActivity.this,"Menambahkan...","Tunggu...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(MainActivity.this,s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String,String> params = new HashMap<>();
                params.put(config.KEY_EMP_USERID,UserId);
                params.put(config.KEY_EMP_PASSWORD,Password);
                params.put(config.KEY_EMP_LEVEL,Level);

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(config.URL_ADD, params);
                return res;
            }
        }

        AddUser ae = new AddUser();
        ae.execute();
    }

    @Override
    public void onClick(View v) {
        if(v == buttonAdd){
            AddUser();
        }

        if(v == buttonView){
            startActivity(new Intent(this,ShowAll.class));
        }
    }
}