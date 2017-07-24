package com.swift.solutions.customlist;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.swift.solutions.customlist.config.NewsAllConfig;
import com.swift.solutions.customlist.recycler.AllNewsConstructor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Technology extends AppCompatActivity {
    ArrayList<AllNewsConstructor> data_news = new ArrayList<>();
    private ProgressDialog loading;

    String[] itemname ={
            "How to Use the Safari Web Browser on iPhone - Lifewire",
            "Digital Cameras & Digital Camera Accessories - Best Buy",
            "Global News | Latest & Current News - Weather, Sports & Health News",
            "Mozilla's new Firefox features improve browsing on iOS and Android",
            "How to Block Pop-Ups in UC Browser | NDTV Gadgets360.com",
            "Google brings Motion Stills to Android, where it’s all motion and no stills",
            "16.10 - Something is going buggy when i open vlc player - Ask Ubuntu",
            "Cold War - Causes, Events, Pictures & Videos - History.com"
    };

    Integer[] imgid={
            R.drawable.pic1,
            R.drawable.pic2,
            R.drawable.pic3,
            R.drawable.pic4,
            R.drawable.pic5,
            R.drawable.pic6,
            R.drawable.pic7,
            R.drawable.pic8,
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tech);

        final  ListView list = (ListView)findViewById(R.id.list);

        // Construct the data source
        List<AllNewsConstructor> rowListItem = getAllNews();
        // Create the adapter to convert the array to views
        final CustomListAdapter adapter=new CustomListAdapter(this, rowListItem);
        // Attach the adapter to a ListView
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 3s = 3000ms
                list.setAdapter(adapter);
            }
        }, 3000);

//        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view,
//                                    int position, long id) {
//                // TODO Auto-generated method stub
//                String Slecteditem= itemname[+position];
//                Toast.makeText(getApplicationContext(), Slecteditem, Toast.LENGTH_SHORT).show();
//
//            }
//        });
    }

    public List<AllNewsConstructor> getAllNews(){
        loading = ProgressDialog.show(Technology.this, "Please wait...", "Fetching news...", false, false);

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
                        Toast.makeText(Technology.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(Technology.this);
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
