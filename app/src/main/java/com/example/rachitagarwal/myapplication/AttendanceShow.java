package com.example.rachitagarwal.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created by Rachit agarwal on 5/15/2016.
 */
public class AttendanceShow extends Fragment
{
    String myJSON;
    JSONParser jsonParser = new JSONParser();
    private static final String URL = "http://192.168.43.47/android4/attendance1.php";
    String name;


    private static final String TAG_RESULT="result";
    private static final String TAG_ID="attendance_id";
    private static final String TAG_DATE="date";
    private static final String TAG_ATT="attendance";
    TextView textView;
    JSONArray attendance=null;

    ArrayList<HashMap<String,String>> newsList;
    ListView listView;
    /*protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.attendance_view);
        //textView= (TextView) findViewById(R.id.textView5);
        setContentView(R.layout.news_main);
        listView=(ListView)findViewById(R.id.listView);
        newsList =new ArrayList<HashMap<String, String>>();
        getData();



    }
    */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.news_main, container, false);
        listView=(ListView)rootView.findViewById(R.id.listView);
        newsList =new ArrayList<HashMap<String, String>>();
        getData();
         return rootView;
    }

    protected void showList()
    {
        try {
            JSONObject jsonObject = new JSONObject(myJSON);
            attendance=jsonObject.getJSONArray(TAG_RESULT);
            for(int i=0;i<attendance.length();i++)
            {
                JSONObject c=attendance.getJSONObject(i);
                String id=c.getString(TAG_ID);
                String date=c.getString(TAG_DATE);
                String present=c.getString(TAG_ATT);

                if(present.equals("P"))
                {
                    present="PRESENT";
                }
                else {
                    present="ABSENT";
                }

                HashMap<String,String> news1=new HashMap<String,String>();

                news1.put(TAG_ID,id);
                news1.put(TAG_DATE,date);
                news1.put(TAG_ATT, present);

                newsList.add(news1);
                ListAdapter adapter=new SimpleAdapter(getContext(),newsList,R.layout.single_row,new String[]{TAG_DATE,TAG_ATT},new int[]{R.id.textView2,R.id.textView3});

                listView.setAdapter(adapter);


            }

        }
        catch(JSONException e)
        {

        }
    }


        public void getData() {
        class GetDataJSON extends AsyncTask<String,Void,String > {

            @Override
            protected String doInBackground(String... args)
            {
                String result=null;
                SharedPreferences shared = getActivity().getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
                String user_name = (shared.getString("user_name", "")).toString();
                Log.d("Rachit",user_name);

                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("user_name", user_name));
                JSONObject json = jsonParser.makeHttpRequest(URL,"POST",params);
                result=json.toString();
                return result;


            }

            @Override
            protected void onPostExecute(String result) {
                myJSON=result;
                showList();
            }
        }
        GetDataJSON g=new GetDataJSON();

        g.execute();
    }



}
