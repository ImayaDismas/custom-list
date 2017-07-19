package com.swift.solutions.customlist;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.swift.solutions.customlist.config.NewsAllConfig;
import com.swift.solutions.customlist.recycler.AllNewsConstructor;
import com.swift.solutions.customlist.recycler.RecyclerViewAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 7/19/17.
 */

public class Techncrunch extends AppCompatActivity {
    ArrayList<AllNewsConstructor> data_news = new ArrayList<>();

    private ProgressDialog loading;
    private LinearLayoutManager lLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_techcrunch);
        lLayout = new LinearLayoutManager(this);

        final RecyclerView rView = (RecyclerView) findViewById(R.id.recycler_view);
        List<AllNewsConstructor> rowListItem = getAllNews();
        final RecyclerViewAdapter rcAdapter = new RecyclerViewAdapter(Techncrunch.this, rowListItem);
        rView.setLayoutManager(lLayout);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                rView.setAdapter(rcAdapter);
            }
        }, 2000);
    }
    public List<AllNewsConstructor> getAllNews(){
        loading = ProgressDialog.show(Techncrunch.this, "Please wait...", "Fetching news...", false, false);

        String url = NewsAllConfig.NEWS_URL;

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.dismiss();
                showJSON(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Techncrunch.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(Techncrunch.this);
        requestQueue.add(stringRequest);

        return data_news;
    }

    private void showJSON(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray("articles");


            for (int i = 0; i < result.length(); i++) {

                JSONObject newsData = result.getJSONObject(i);
                AllNewsConstructor news = new AllNewsConstructor();
                news.setAuthor(newsData.getString("author"));
                news.setTitle(newsData.getString("title"));
                news.setDescription(newsData.getString("description"));
                news.setUrl(newsData.getString("url"));
                news.setUrlToImage(newsData.getString("urlToImage"));
                news.setPublishedAt(newsData.getString("publishedAt"));
                data_news.add(news);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
