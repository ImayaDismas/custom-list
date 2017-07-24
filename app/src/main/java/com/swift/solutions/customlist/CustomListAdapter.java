package com.swift.solutions.customlist;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.swift.solutions.customlist.recycler.AllNewsConstructor;

import java.util.List;

/**
 * Created by root on 7/14/17.
 */

public class CustomListAdapter extends ArrayAdapter<AllNewsConstructor> {

//    private final Activity context;
//    private final String[] itemname;
//    private final Integer[] imgid;

    private List<AllNewsConstructor> newsList;
    private Context context;

//    public RecyclerViewAdapter(Context context, List<AllNewsConstructor> itemList) {
//        this.newsList = itemList;
//        this.context = context;
//    }

    public CustomListAdapter(Context context, List<AllNewsConstructor> itemList) {
        super(context, 0,itemList);
        // TODO Auto-generated constructor stub
        this.newsList = itemList;
        this.context = context;
    }

    public View getView(int position, View view, ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.custom_list, parent, false);
        }

        TextView txtTitle = (TextView) view.findViewById(R.id.title);
        ImageView imageView = (ImageView) view.findViewById(R.id.icon);
        TextView authortxt = (TextView) view.findViewById(R.id.author);
        TextView datetxt = (TextView) view.findViewById(R.id.date_time);

        txtTitle.setText(newsList.get(position).getTitle());
        Picasso.with(context)
                .load(newsList.get(position).getUrlToImage())
                .placeholder(R.drawable.pic2)
                .into(imageView);
        authortxt.setText(newsList.get(position).getAuthor());
        datetxt.setText(newsList.get(position).getPublishedAt());
        return view;

    };
}
