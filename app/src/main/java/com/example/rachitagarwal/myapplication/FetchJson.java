package com.example.rachitagarwal.myapplication;

/**
 * Created by Rachit agarwal on 5/9/2016.
 */
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;

public class FetchJson extends Fragment {

    String myJSON;
    JSONParser jsonParser = new JSONParser();
    private static final String URL = "http://192.168.43.47/android4/news.php";


    private static final String TAG_RESULT="result";
    private static final String TAG_ID="id";
    private static final String TAG_NAME="name";
    private static final String TAG_ADD="address";

    JSONArray news=null;

    ArrayList<HashMap<String,String>> newsList;
    ListView listView;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.list_main, container, false);
        listView=(ListView)rootView.findViewById(R.id.listView2);
        newsList =new ArrayList<HashMap<String, String>>();
        getData();
        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), NewDes.class);

                position=position+1;
                String t=String.valueOf(position);
                intent.putExtra("newpos", t);
                startActivity(intent);


            }
        });



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
                String id=c.getString(TAG_ID);
                String name=c.getString(TAG_NAME);
                String address=c.getString(TAG_ADD);

                if(address.length()>35)
                {
                    address=address.substring(0, 35);
                }

                HashMap<String,String> news1=new HashMap<String,String>();

                news1.put(TAG_ID,id);
                news1.put(TAG_NAME,name);
                news1.put(TAG_ADD, address);

                newsList.add(news1);
                ListAdapter adapter=new SimpleAdapter(getActivity(),newsList,R.layout.news_row,new String[]{TAG_NAME,TAG_ADD},new int[]{R.id.textView2,R.id.textView3});
                listView.setAdapter(adapter);
            }

        }
        catch(IndexOutOfBoundsException e)
        {
            e.printStackTrace();
        }
        catch (JSONException e){
            e.printStackTrace();
        }
    }

    public void getData() {
        class GetDataJSON extends AsyncTask<String,Void,String >{
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
            protected String doInBackground(String... params)
            {
                String result=null;
                /*
                DefaultHttpClient httpClient=new DefaultHttpClient(new BasicHttpParams());
                HttpPost httpPost=new HttpPost("http://192.168.43.47/campus/api/news.php");

                httpPost.setHeader("Content-type","application/json");

                InputStream inputStream=null;
                    String result=null;
                try
                {
                    HttpResponse response=httpClient.execute(httpPost);
                    HttpEntity entity=response.getEntity();

                    inputStream=entity.getContent();
                    BufferedReader reader=new BufferedReader(new InputStreamReader(inputStream,"UTF-8"),8);

                    StringBuilder sb=new StringBuilder();
                    String line=null;
                    while((line=reader.readLine())!=null)
                    {
                        sb.append(line+"\n");
                    }
                    result=sb.toString();
                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                finally {
                    try
                    {
                        if(inputStream!=null)
                        {
                            inputStream.close();
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }


                return result;
                */

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
