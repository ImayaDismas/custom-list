package com.swift.solutions.customlist.recycler;

import android.content.Context;
import android.os.StrictMode;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.swift.solutions.customlist.R;

import java.util.List;

/**
 * Created by root on 7/19/17.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    OnItemClickListener mItemClickListener;

    private List<AllNewsConstructor> newsList;
    private Context context;

    public RecyclerViewAdapter(Context context, List<AllNewsConstructor> itemList) {
        this.newsList = itemList;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView newsTitle;
        public ImageView newsUrlToImage;
        public TextView newsPublishedAt;
        public TextView newsDescription;
        public LinearLayout placeNameHolder;
        public TextView newsAuthor;
        public RelativeLayout placeHolder;

        public ViewHolder(View itemView) {
            super(itemView);
            placeHolder = (RelativeLayout) itemView.findViewById(R.id.mainHolder);
            newsTitle = (TextView) itemView.findViewById(R.id.news_title);
            newsUrlToImage = (ImageView) itemView.findViewById(R.id.news_image);
            newsPublishedAt = (TextView) itemView.findViewById(R.id.news_published);
//            newsDescription = (TextView) itemView.findViewById(R.id.news_description);
//            placeNameHolder = (LinearLayout) itemView.findViewById(R.id.placeNameHolder);
            newsAuthor = (TextView) itemView.findViewById(R.id.news_author);

            placeHolder.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {

            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(itemView, getPosition());
            }

        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_list, null);
//        ViewHolder rcv = new ViewHolder(layoutView);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_places, parent, false);
//        return new ViewHolder(view);
        return new ViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.newsTitle.setText(newsList.get(position).getTitle());//setImageBitmap(itemList.get(position).get_media());
        Picasso.with(context)
                .load(newsList.get(position).getUrlToImage())
                .placeholder(R.drawable.pic2)
                .into(holder.newsUrlToImage);
        holder.newsPublishedAt.setText(newsList.get(position).getPublishedAt());
        holder.newsAuthor.setText(newsList.get(position).getAuthor());

//        Toast.makeText(context, parkList.get(position).getSite_image(), Toast.LENGTH_SHORT).show();
//        try {
//            URL url = new URL(parkList.get(position).getSite_image());
//            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//            connection.setDoInput(true);
//            connection.connect();
//            InputStream input = connection.getInputStream();
//            Bitmap photo = BitmapFactory.decodeStream(input);
//            Palette.generateAsync(photo, new Palette.PaletteAsyncListener() {
//                public void onGenerated(Palette palette) {
//                    int bgColor = palette.getMutedColor(context.getResources().getColor(android.R.color.black));
//                    holder.placeNameHolder.setBackgroundColor(bgColor);
//                }
//            });
//        } catch(IOException e) {
//            System.out.println(e);
//        }
    }

    @Override
    public int getItemCount() {
        return this.newsList.size();
    }
}