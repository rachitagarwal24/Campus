package com.example.rachitagarwal.myapplication;

/**
 * Created by Rachit on 9/8/2016.
 */
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
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

public class NotificationFragment extends Fragment {

    String myJSON;
    JSONParser jsonParser = new JSONParser();
    private static final String URL = "http://192.168.43.47/android4/notification.php";


    private static final String TAG_RESULT = "result";
    private static final String TAG_ID = "id";
    private static final String TAG_NAME = "name";
    private static final String TAG_ADD = "address";

    JSONArray news = null;

    ArrayList<HashMap<String, String>> newsList;
    ListView listView;
    String course;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.news_main, container, false);
        listView = (ListView) rootView.findViewById(R.id.listView);
        newsList = new ArrayList<HashMap<String, String>>();
        course=Main22Activity.course;



        Log.d("course adsdsdasdasdas", Main22Activity.course);





        getData();



        return rootView;
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
                ListAdapter adapter = new SimpleAdapter(getActivity(), newsList, R.layout.note_row, new String[]{TAG_NAME, TAG_ADD}, new int[]{R.id.textView2, R.id.textView3});
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
                mProgressDialog = new ProgressDialog(getActivity());
                mProgressDialog.setTitle("Loading News");
                mProgressDialog.setMessage("Loading...");
                mProgressDialog.setIndeterminate(false);
                mProgressDialog.show();
            }

            @Override
            protected String doInBackground(String... args) {
                String result = null;

                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("course", course));

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