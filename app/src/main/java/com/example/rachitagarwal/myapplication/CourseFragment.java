package com.example.rachitagarwal.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Rachit on 9/7/2016.
 */
public class CourseFragment extends android.support.v4.app.Fragment implements AdapterView.OnItemClickListener {

    GridView gridView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_other, container, false);

        gridView = (GridView) rootView.findViewById(R.id.gridView);
        gridView.setAdapter(new RacAdapter(getContext()));
        gridView.setOnItemClickListener(this);


        //   listView=(ListView)rootView.findViewById(R.id.listView);
        // newsList =new ArrayList<HashMap<String, String>>();
        // getData();

        //      listview = (ListView) rootView.findViewById(R.id.listView);
        //    shared = getActivity().getSharedPreferences("UserInfo", Context.MODE_PRIVATE);


//        new DownloadJSON().execute();

        return rootView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent intent = new Intent(getActivity(), Main22Activity.class);


        position=position+1;
        String t=String.valueOf(position);
        intent.putExtra("course", t);

        Log.d("VALUE is",t);
        startActivity(intent);

       /* if(position==1)
        {
            Intent intent=new Intent(getActivity(),Main22Activity.class);

            intent.putExtra("course",position+1);
            startActivity(intent);

        }
        */
    }


}

class SingleItem {
    int imageId;
    String name;

    SingleItem(int imageId, String name) {
        this.imageId = imageId;
        this.name = name;
    }
}

class RacAdapter extends BaseAdapter {

    ArrayList<SingleItem> list;
    Context context;

    RacAdapter(Context context) {
        this.context = context;
        list = new ArrayList<SingleItem>();
        Resources res = context.getResources();
        String[] names = res.getStringArray(R.array.name);
        int[] images = {R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher};
        for (int i = 0; i < 6; i++) {
            SingleItem singleItem = new SingleItem(images[i], names[i]);
            list.add(singleItem);
        }
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class ViewHolder {
        ImageView imageView;
        TextView textView;

        ViewHolder(View view) {
            imageView = (ImageView) view.findViewById(R.id.imageView);
            textView = (TextView) view.findViewById(R.id.textView);

        }

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder = null;
        if (row == null) {

            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            row = layoutInflater.inflate(R.layout.grid_item, parent, false);


            holder = new ViewHolder(row);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }
        SingleItem temp = list.get(position);


        holder.imageView.setImageResource(temp.imageId);
        holder.textView.setText(temp.name);
        return row;
    }
}