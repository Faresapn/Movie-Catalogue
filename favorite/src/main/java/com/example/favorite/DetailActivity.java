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

import static com.example.favorite.DbTvContract.TvEntry.CONTENT_URI_TV;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_TV="extra_tv";
    public static final String EXTRA_MOVIE="extra_movie";
    Items movie,show;
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
        fab.setOnClickListener(view -> {
            if (is_movie){
                deletemovie();
            }
            else {
                deleteshow();
            }
        });
    }

    private void deleteshow() {
        getContentResolver().delete(Uri.parse(CONTENT_URI_TV+"/"+show.getId()),null,null);
        Toast.makeText(DetailActivity.this, R.string.add, Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
    }

    private void deletemovie() {
        getContentResolver().delete(Uri.parse(CONTENT_URI+"/"+movie.getId()),null,null);
        Toast.makeText(DetailActivity.this, R.string.add, Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
    }

    void loadIntent(){
       movie = getIntent().getParcelableExtra(EXTRA_MOVIE);
        show = getIntent().getParcelableExtra(EXTRA_TV);
        if (show != null){
            progressBar.setVisibility(View.GONE);
            is_movie=false;
            Log.d("tv", String.valueOf(show.getId()));
            title.setText(show.getTitle_film());
            info.setText(show.getInfo_film());
            rate.setText(show.getRate());
            ratingbar.setRating(Float.valueOf(show.getRating_bar()) / 2);
            desc.setText(show.getDesc_film());
            Picasso.with(getApplicationContext()).load("https://image.tmdb.org/t/p/w500"+show.getPhoto()).into(photo);
        }else {
            Log.d("movie", String.valueOf(movie.getId()));
            progressBar.setVisibility(View.GONE);
            is_movie=true;
            Log.d("tv", String.valueOf(movie.getId()));
            title.setText(movie.getTitle_film());
            info.setText(movie.getInfo_film());
            rate.setText(movie.getRate());
            ratingbar.setRating(Float.valueOf(movie.getRating_bar()) / 2);
            desc.setText(movie.getDesc_film());
            Picasso.with(getApplicationContext()).load("https://image.tmdb.org/t/p/w500"+movie.getPhoto()).into(photo);
        }
    }
}
