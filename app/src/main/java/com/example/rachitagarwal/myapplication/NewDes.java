package com.example.rachitagarwal.myapplication;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Rachit agarwal on 5/9/2016.
 */
public class NewDes extends Activity {

    String newpos;

    String myJSON;
    JSONParser jsonParser = new JSONParser();
    private static final String URL = "http://192.168.43.47/android4/NewDes.php";


    private static final String TAG_RESULT = "result";
    private static final String TAG_ID = "id";
    private static final String TAG_NAME = "name";
    private static final String TAG_ADD = "address";

    JSONArray news = null;
     ArrayList<HashMap<String, String>> newsList;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_main);

        listView = (ListView)findViewById(R.id.listView);
        newsList = new ArrayList<HashMap<String, String>>();

        newpos=getIntent().getStringExtra("newpos");

        getData();
    }

    protected void showList() {
        try {
            JSONObject jsonObject = new JSONObject(myJSON);
            news = jsonObject.getJSONArray(TAG_RESULT);

            for (int i = 0; i < news.length(); i++) {
                JSONObject c = news.getJSONObject(i);
                String id = c.getString(TAG_ID);
                String name = c.getString(TAG_NAME);
                String address = c.getString(TAG_ADD);



                HashMap<String, String> news1 = new HashMap<String, String>();

                news1.put(TAG_ID, id);
                news1.put(TAG_NAME, name);
                news1.put(TAG_ADD, address);

                newsList.add(news1);
                ListAdapter adapter = new SimpleAdapter(NewDes.this, newsList, R.layout.note_row, new String[]{TAG_NAME, TAG_ADD}, new int[]{R.id.textView2, R.id.textView3});
                listView.setAdapter(adapter);
            }

        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void getData() {
        class GetDataJSON extends AsyncTask<String, Void, String> {
            ProgressDialog mProgressDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                mProgressDialog = new ProgressDialog(NewDes.this);
                mProgressDialog.setTitle("Loading News");
                mProgressDialog.setMessage("Loading...");
                mProgressDialog.setIndeterminate(false);
                mProgressDialog.show();
            }

            @Override
            protected String doInBackground(String... args) {
                String result = null;

                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("newpos", newpos));

                JSONObject json = jsonParser.makeHttpRequest(
                        URL, "POST", params);
                result = json.toString();


                return result;
            }

            @Override
            protected void onPostExecute(String result) {
                myJSON = result;
                showList();
                mProgressDialog.dismiss();
            }
        }
        GetDataJSON g = new GetDataJSON();

        g.execute();
    }

}
