package com.example.rachitagarwal.myapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Rachit on 9/7/2016.
 */
public class ContactFragment extends Fragment{

    String myJSON;
    JSONParser jsonParser = new JSONParser();
    private static final String URL = "http://192.168.43.47/android4/contact.php";

    private static final String TAG_RESULT="result";
    private static final String BOOK_ID="contact_id";
    private static final String BOOK_NAME="contact_name";
    private static final String BOOK_WRI="post";
    private static final String BOOK_WRI1="num";

    JSONArray news=null;

    ArrayList<HashMap<String,String>> newsList;
    ListView listView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.list_main, container, false);

        listView=(ListView)rootView.findViewById(R.id.listView2);
        newsList =new ArrayList<HashMap<String, String>>();
        getData();

        //      listview = (ListView) rootView.findViewById(R.id.listView);
        //    shared = getActivity().getSharedPreferences("UserInfo", Context.MODE_PRIVATE);


//        new DownloadJSON().execute();

        return rootView;
    }

    protected void showList()
    {
        try
        {
            JSONObject jsonObject=new JSONObject(myJSON);
            news=jsonObject.getJSONArray(TAG_RESULT);

            for(int i=0;i<news.length();i++)
            {
                JSONObject c=news.getJSONObject(i);
                String id=c.getString(BOOK_ID);
                String name=c.getString(BOOK_NAME);
                String address=c.getString(BOOK_WRI);
                String address1=c.getString(BOOK_WRI1);


                HashMap<String,String> news1=new HashMap<String,String>();

                news1.put(BOOK_ID,id);
                news1.put(BOOK_NAME,name);
                news1.put(BOOK_WRI, address);
                news1.put(BOOK_WRI1, address1);

                newsList.add(news1);
                ListAdapter adapter=new SimpleAdapter(getActivity(),newsList,R.layout.contact_main,new String[]{BOOK_ID,BOOK_NAME,BOOK_WRI,BOOK_WRI1},new int[]{R.id.subid,R.id.subname,R.id.fbook,R.id.num});
                listView.setAdapter(adapter);
            }

        }
        catch (JSONException e){
            e.printStackTrace();
        }
    }

    public void getData() {
        class GetDataJSON extends AsyncTask<String,Void,String > {
            ProgressDialog mProgressDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                mProgressDialog = new ProgressDialog(getActivity());
                mProgressDialog.setTitle("Loading Contact Details");
                mProgressDialog.setMessage("Loading...");
                mProgressDialog.setIndeterminate(false);
                mProgressDialog.show();
            }

            @Override
            protected String doInBackground(String... params)
            {
                String result=null;
                JSONObject json = jsonParser.getJSONFromUrl(URL);
                result=json.toString();
                return result;
            }

            @Override
            protected void onPostExecute(String result) {
                myJSON=result;
                showList();
                mProgressDialog.dismiss();
            }
        }
        GetDataJSON g=new GetDataJSON();

        g.execute();
    }





}
