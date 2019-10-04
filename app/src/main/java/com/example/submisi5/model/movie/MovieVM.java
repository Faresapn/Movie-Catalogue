package com.example.submisi5.model.movie;

import android.app.Application;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.RequestQueue;

import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.submisi5.R;

import com.example.submisi5.model.Items.Items;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MovieVM extends AndroidViewModel {

    private MutableLiveData<ArrayList<Items>> items = new MutableLiveData<>();
    public  ArrayList<Items> mitems = new ArrayList<>();
     private RequestQueue rq;
     private String url,searchrl;

    public MovieVM(@NonNull Application application) {
        super(application);
        rq = Volley.newRequestQueue(application);
        url = application.getResources().getString(R.string.api_movie);
        searchrl=application.getResources().getString(R.string.api_movie_search);
    }



    public void getAPI() {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
            try {
                JSONArray jsonArray = response.getJSONArray("results");
                int length = jsonArray.length();
                for (int i = 0; i < length; i++) {
                    JSONObject result = jsonArray.getJSONObject(i);
                    String title = result.getString    ("title");
                    String photo = result.getString   ("poster_path");
                    String overview = result.getString("overview");
                    String realease = result.getString("release_date");
                    String rating_bar = result.getString("vote_average");
                    String rate = result.getString     ("vote_average");
                    Log.d("title", title);
                    Items items = new Items();
                    items.setTitle_film(title);
                    items.setPhoto(photo);
                    items.setInfo_film(overview);
                    items.setDesc_film(realease);
                    items.setRating_bar(rating_bar);
                    items.setRate(rate);
                    mitems.add(items);
                }

                items.postValue(mitems);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, Throwable::printStackTrace
        );
        rq.add(request);
    }
    public void searchmovie(String tittle){
        String URL_SEARCH = searchrl + tittle;
        JsonObjectRequest mRequest = new JsonObjectRequest(Request.Method.GET, URL_SEARCH, null, response -> {
            try {
                JSONArray jsonArray = response.getJSONArray("results");
                int length = jsonArray.length();
                for(int i = 0;i<length;i++){
                    JSONObject result = jsonArray.getJSONObject(i);
                    String title = result.getString    ("title");
                    String photo = result.getString   ("poster_path");
                    String overview = result.getString("overview");
                    String realease = result.getString("release_date");
                    String rating_bar = result.getString("vote_average");
                    String rate = result.getString     ("vote_average");
                    Items items = new Items();
                    items.setTitle_film(title);
                    items.setPhoto(photo);
                    items.setInfo_film(overview);
                    items.setDesc_film(realease);
                    items.setRating_bar(rating_bar);
                    items.setRate(rate);
                    mitems.add(items);
                }
                items.postValue(mitems);
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
        }, Throwable::printStackTrace);
        rq.add(mRequest);
    }

    public LiveData<ArrayList<Items>> getShow() {
        return items;
    }
}
