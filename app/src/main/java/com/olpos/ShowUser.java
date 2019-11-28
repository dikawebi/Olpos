package com.olpos;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;



public class ShowUser extends AppCompatActivity implements View.OnClickListener{

    private EditText editTextID;
    private EditText editTextUserId;
    private EditText editTextPassword;
    private EditText editTextLevel;

    private Button buttonUpdate;
    private Button buttonDelete;

    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_user);

        Intent intent = getIntent();

        id = intent.getStringExtra(config.EMP_ID);

        editTextID = (EditText) findViewById(R.id.editTextID);
        editTextUserId = (EditText) findViewById(R.id.editTextUserID);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextLevel = (EditText) findViewById(R.id.editTextLevel);

        buttonUpdate = (Button) findViewById(R.id.buttonUpdate);
        buttonDelete = (Button) findViewById(R.id.buttonDelete);

        buttonUpdate.setOnClickListener(this);
        buttonDelete.setOnClickListener(this);

        editTextID.setText(id);

        getUser();
    }

    private void getUser(){
        class getUser extends AsyncTask<Void,Void,String>{
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(ShowUser.this,"Fetching...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                showEmployee(s);
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(config.URL_GET_EMP,id);
                return s;
            }
        }
        getUser ge = new getUser();
        ge.execute();
    }

    private void showEmployee(String json){
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(config.TAG_JSON_ARRAY);
            JSONObject c = result.getJSONObject(0);
            String userid = c.getString(config.TAG_USERID);
            String password = c.getString(config.TAG_PASSWORD);
            String level = c.getString(config.TAG_LEVEL);

            editTextUserId.setText(userid);
            editTextPassword.setText(password);
            editTextLevel.setText(level);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void UpdateUser(){
        final String name = editTextUserId.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();
        final String level = editTextLevel.getText().toString().trim();

        class UpdateUser extends AsyncTask<Void,Void,String>{
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(ShowUser.this,"Updating...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(ShowUser.this,s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... params) {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put(config.KEY_EMP_ID,id);
                hashMap.put(config.KEY_EMP_USERID,name);
                hashMap.put(config.KEY_EMP_PASSWORD,password);
                hashMap.put(config.KEY_EMP_LEVEL,level);

                RequestHandler rh = new RequestHandler();

                String s = rh.sendPostRequest(config.URL_UPDATE_EMP,hashMap);

                return s;
            }
        }

        UpdateUser ue = new UpdateUser();
        ue.execute();
    }

    private void deleteEmployee(){
        class DeleteEmployee extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(ShowUser.this, "Updating...", "Tunggu...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(ShowUser.this, s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(config.URL_DELETE_EMP, id);
                return s;
            }
        }

        DeleteEmployee de = new DeleteEmployee();
        de.execute();
    }

    private void confirmDeleteUser(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure to delete this user?");

        alertDialogBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        deleteEmployee();
                        startActivity(new Intent(ShowUser.this,ShowAll.class));
                    }
                });

        alertDialogBuilder.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    public void onClick(View v) {
        if(v == buttonUpdate){
            UpdateUser();
        }

        if(v == buttonDelete){
            confirmDeleteUser();
        }
    }
}