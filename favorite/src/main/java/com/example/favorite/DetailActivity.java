package com.example.favorite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.favorite.model.Items;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import static com.example.favorite.DbContract.MovieEntry.CONTENT_URI;
;
import static com.example.favorite.DbContract.TvEntry.CONTENT_URI_TV;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_TV="extra_tv";
    public static final String EXTRA_MOVIE="extra_movie";
    Items resultmovie,resulttv;
    TextView title,desc,info,rate;
    ImageView photo;
    RatingBar ratingbar;
    ProgressBar progressBar;
    FloatingActionButton fab;
    Boolean is_movie=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        fab = findViewById(R.id.fab);
        title = findViewById(R.id.txt_tittle);
        desc = findViewById(R.id.txt_realis);
        info = findViewById(R.id.txt_det);
        ratingbar = findViewById(R.id.ratingBar);
        rate = findViewById(R.id.txtRate);
        photo = findViewById(R.id.img_film);
        progressBar = findViewById(R.id.loading_film);
        progressBar.setVisibility(View.VISIBLE);

        loadIntent();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (is_movie){
                    removefavmovie();
                }
                else {
                    removefavtv();
                }
            }
        });
    }

    private void removefavtv() {
        getContentResolver().delete(Uri.parse(CONTENT_URI_TV+"/"+resulttv.getId()),null,null);
        Toast.makeText(DetailActivity.this, R.string.add, Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
    }

    private void removefavmovie() {
        getContentResolver().delete(Uri.parse(CONTENT_URI+"/"+resultmovie.getId()),null,null);
        Toast.makeText(DetailActivity.this, R.string.add, Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
    }

    void loadIntent(){
        resultmovie = getIntent().getParcelableExtra(EXTRA_MOVIE);
        resulttv = getIntent().getParcelableExtra(EXTRA_TV);
        if (resulttv != null){
            progressBar.setVisibility(View.GONE);
            is_movie=false;
            Log.d("tv", String.valueOf(resulttv.getId()));
            title.setText(resulttv.getTitle_film());
            info.setText(resulttv.getInfo_film());
            rate.setText(resulttv.getRate());
            ratingbar.setRating(Float.valueOf(resulttv.getRating_bar()) / 2);
            desc.setText(resulttv.getDesc_film());
            Picasso.with(getApplicationContext()).load("https://image.tmdb.org/t/p/w500"+resulttv.getPhoto()).into(photo);
        }else {
            Log.d("movie", String.valueOf(resultmovie.getId()));
            progressBar.setVisibility(View.GONE);
            is_movie=true;
            Log.d("tv", String.valueOf(resultmovie.getId()));
            title.setText(resultmovie.getTitle_film());
            info.setText(resultmovie.getInfo_film());
            rate.setText(resultmovie.getRate());
            ratingbar.setRating(Float.valueOf(resultmovie.getRating_bar()) / 2);
            desc.setText(resultmovie.getDesc_film());
            Picasso.with(getApplicationContext()).load("https://image.tmdb.org/t/p/w500"+resultmovie.getPhoto()).into(photo);
        }
    }
}
