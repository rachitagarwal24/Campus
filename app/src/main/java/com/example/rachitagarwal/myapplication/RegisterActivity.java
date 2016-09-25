package com.example.rachitagarwal.myapplication;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.view.View.OnClickListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rachit agarwal on 5/8/2016.
 */
@SuppressWarnings("ALL")
public class RegisterActivity extends Activity implements OnClickListener
{
    EditText name,email,sem,phone,branch,pass;
    Button bLogin,bRegister;


    private ProgressDialog pDialog;
    // JSON parser class
    JSONParser jsonParser = new JSONParser();
    private static final String REGISTER_URL = "http://192.168.43.47/android4/register.php";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        name= (EditText) findViewById(R.id.name);
        email= (EditText) findViewById(R.id.email);
        sem= (EditText) findViewById(R.id.sem);
        branch= (EditText) findViewById(R.id.branch);
        phone= (EditText) findViewById(R.id.phone);
        pass= (EditText) findViewById(R.id.password);
        bLogin= (Button) findViewById(R.id.login);
        bRegister= (Button) findViewById(R.id.register);
        bLogin.setOnClickListener(this);
        bRegister.setOnClickListener(this);





    }
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                Intent i=new Intent(getBaseContext(),MainActivity.class);
                startActivity(i);

                break;
            case R.id.register:
                new AttemptRegister().execute();

                break;

            default:
                break;
        }

    }

    class AttemptRegister extends AsyncTask<String,String,String>
    {
        boolean failure = false;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(RegisterActivity.this);
            pDialog.setMessage("Attempting for Register...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... args)
        {
            int success;
            String Name = name.getText().toString();
            String Email=email.getText().toString();
            String Phone=phone.getText().toString();
            String Sem=sem.getText().toString();
            String Branch=branch.getText().toString();
            String Password = pass.getText().toString();

            Log.d("request!", "starting");

            try {

                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("Name", Name));
                params.add(new BasicNameValuePair("Email", Email));
                params.add(new BasicNameValuePair("Password", Password));
                params.add(new BasicNameValuePair("Sem", Sem));
                params.add(new BasicNameValuePair("Branch", Branch));
                params.add(new BasicNameValuePair("Phone", Phone));




                Log.d("request!", "starting");

                JSONObject json = jsonParser.makeHttpRequest(
                        REGISTER_URL, "POST", params);

                // checking  log for json response
                Log.d("Register attempt", json.toString());

                // success tag for json
                success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    Log.d("Successfully Register!", json.toString());

                    Intent ii = new Intent(RegisterActivity.this,MainActivity.class);
                    finish();
                    // this finish() method is used to tell android os that we are done with current //activity now! Moving to other activity
                    startActivity(ii);
                    return json.getString(TAG_MESSAGE);
                }else{

                    return json.getString(TAG_MESSAGE);

                }
            }
            catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(String message) {

            pDialog.dismiss();
            if (message != null){
                Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_LONG).show();
            }
        }
        @Override
        protected void onCancelled() {
        }
    }
}
