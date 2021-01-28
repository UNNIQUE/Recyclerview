package com.example.recyclerviewpractice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.LauncherActivity;
import android.app.ProgressDialog;
import android.icu.text.LocaleDisplayNames;
import android.os.Bundle;
import android.widget.Adapter;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity  {
    RecyclerView recyclerView;
    MyAdapter myAdapter;
    ArrayList<News> list;
    RequestQueue requestQueue;


    static final String KEY_TITLE = "title";
    static final String KEY_AUTHOR = "author";
    static final String KEY_URLTOIMAGE = "urlToImage";
    static final String KEY_URL = "url";
    String urlWebsite = "http://newsapi.org/v2/top-headlines?country=us&category=business&apiKey=e66412c5b30a41d781f4985c29325bd8";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        loadRecyclerMethod();


    }

    private void loadRecyclerMethod() {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Data...");
        progressDialog.show();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, urlWebsite, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("articles");
                    for (int i = 0; i <jsonArray.length() ; i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String author = jsonObject.getString("title");
                        String title = jsonObject.getString("author");
                        list.add(new News(author ,title));

                    }
                        myAdapter = new MyAdapter(list, MainActivity.this);
                    recyclerView.setAdapter(myAdapter);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });


         requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
    }




}