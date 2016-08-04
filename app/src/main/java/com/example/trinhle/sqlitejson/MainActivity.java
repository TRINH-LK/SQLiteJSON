package com.example.trinhle.sqlitejson;

import android.nfc.Tag;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.trinhle.sqlitejson.adapter.CategoryAdapter;
import com.example.trinhle.sqlitejson.database.DBHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private ListView listView;
    private CategoryAdapter adapter;
    DBHandler handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.lv_categories);
        handler = new DBHandler(this);

        if(handler.getCategoryCount() == 0) {
            requestCategoryJSON();
        } else {
            adapter = new CategoryAdapter(MainActivity.this, handler.getAllCategory());
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Toast.makeText(getApplicationContext(), "SQLite", Toast.LENGTH_LONG).show();
                }
            });
        }
    }



    private void requestCategoryJSON() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://sflashcard.com/service/category/get";
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Response" + response);
                GsonBuilder builder = new GsonBuilder();
                Gson categoryGSON = builder.create();
                //List<Category> categoryList = Arrays.asList(categoryGSON.fromJson(response, Category[].class));
                Type type = new TypeToken<List<Category>>(){}.getType();
                List<Category> list = categoryGSON.fromJson(response, type);
                for (int i = 0; i < list.size(); i++) {
                    Category item = list.get(i);
                    handler.addCategory(item);
                }
                ArrayList<Category> returnList = handler.getAllCategory();
                adapter = new CategoryAdapter(MainActivity.this, returnList);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Category item = (Category) listView.getItemAtPosition(i);
                        Toast.makeText(getApplicationContext(),"CategoryID: " + item.getCategoryId(), Toast.LENGTH_LONG).show();
                    }
                });


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "Error" + error.getMessage());
            }
        });
        queue.add(request);
    }
}
