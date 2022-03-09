package com.example.ejemploapi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.ejemploapi.model.Post;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    ArrayAdapter arrayAdapter;
    ArrayList<String> datos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView=findViewById(R.id.listViewDatos);
        arrayAdapter= new ArrayAdapter(this, android.R.layout.simple_list_item_1,datos);

        listView.setAdapter(arrayAdapter);
        obtenerDatos();

    }

    private void obtenerDatos(){
        String url = "https://anime-facts-rest-api.herokuapp.com/api/v1";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("data");
                    pasarJson(jsonArray);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });


        Volley.newRequestQueue(this).add(jsonObjectRequest);

    }

    private void pasarJson(JSONArray jsonArray) {
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = null;
            Post post = new Post();

            try {
                jsonObject = jsonArray.getJSONObject(i);
                post.setAnime_id(jsonObject.getInt("anime_id"));
                post.setAnime_name(jsonObject.getString("anime_name"));
                post.setAnime_img(jsonObject.getString("anime_img"));

                datos.add(post.getAnime_id() +" "+ post.getAnime_name() +" "+ post.getAnime_img());

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        arrayAdapter.notifyDataSetChanged();

    }
}