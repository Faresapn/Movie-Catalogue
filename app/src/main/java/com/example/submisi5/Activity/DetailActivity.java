package com.example.submisi5.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.submisi5.R;

import com.example.submisi5.database.MovieHelper;
import com.example.submisi5.database.TvHelper;

import com.example.submisi5.model.Items.Items;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;




public class DetailActivity extends AppCompatActivity {
    public static final String EXTRA_DETAIL="extra_detail";
    TextView title,desc,info,rate;
    ImageView photo;
    ProgressBar progressBar;
    String type,name;
    RatingBar ratingbar;
    Items mMovieTvItems;
    MovieHelper movieHelper;
    TvHelper mTvHelper;
    Items movieTvItems;
    FloatingActionButton fab;
    Boolean act = true;
    Boolean insert = true;
    Boolean delete = true;
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
        Detail();
        Helper();
        Type();
        fab.setOnClickListener(view -> FabClick());
        setDetail();
    }

    private void setDetail(){
        progressBar.setVisibility(View.GONE);
        Items items = getIntent().getParcelableExtra(EXTRA_DETAIL);
        title.setText(items.getTitle_film());
        desc.setText(items.getDesc_film());
        info.setText(items.getInfo_film());
        ratingbar.setRating(Float.valueOf(items.getRating_bar()) / 2);
        rate.setText(items.getRating_bar());
        Picasso.with(this).load("https://image.tmdb.org/t/p/w500" + items.getPhoto()).into(photo);
    }
    private void FabClick() {
        if (insert && act && type.equals("MOVIE")){
            mMovieTvItems.setTitle_film(movieTvItems.getTitle_film());
            mMovieTvItems.setDesc_film(movieTvItems.getDesc_film());
            mMovieTvItems.setInfo_film(movieTvItems.getInfo_film());
            mMovieTvItems.setRating_bar(movieTvItems.getRating_bar());
            mMovieTvItems.setRate(movieTvItems.getRate());
            mMovieTvItems.setPhoto(movieTvItems.getPhoto());
            Log.d("savemovie",desc.getText().toString());
            long result = movieHelper.insertMovie(mMovieTvItems);

            if(result > 0){
                Toast.makeText(DetailActivity.this, R.string.add, Toast.LENGTH_SHORT).show();
                fab.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_is_favorite));
            }else {
                Toast.makeText(DetailActivity.this, R.string.addf, Toast.LENGTH_SHORT).show();
            }
        }else if(!insert&&act && type.equals("TV") ){
            mMovieTvItems.setTitle_film(movieTvItems.getTitle_film());
            mMovieTvItems.setDesc_film(movieTvItems.getDesc_film());
            mMovieTvItems.setInfo_film(movieTvItems.getInfo_film());
            mMovieTvItems.setRating_bar(movieTvItems.getRating_bar());
            mMovieTvItems.setRate(movieTvItems.getRate());
            mMovieTvItems.setPhoto(movieTvItems.getPhoto());
            Log.d("savetv",desc.getText().toString());
            long result = mTvHelper.insertTv(mMovieTvItems);
            if(result > 0){
                Toast.makeText(DetailActivity.this, R.string.add, Toast.LENGTH_SHORT).show();
                fab.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_is_favorite));

            }else {
                Toast.makeText(DetailActivity.this, R.string.addf, Toast.LENGTH_SHORT).show();
            }
        }else if(delete && !act && type.equals("MOVIE")){
            Log.d("deletemovie",desc.getText().toString());

            long result = movieHelper.deleteMovie(movieTvItems.getTitle_film());
            if(result > 0 ){
                Toast.makeText(DetailActivity.this, R.string.add, Toast.LENGTH_SHORT).show();
                startActivity(new Intent(DetailActivity.this,MainActivity.class));
            }else {
                Toast.makeText(DetailActivity.this, R.string.addf, Toast.LENGTH_SHORT).show();

            }
        }else if(!delete && !act && type.equals("TV")){

            Log.d("deletetv",desc.getText().toString());
            long result = mTvHelper.deleteTv(movieTvItems.getTitle_film());
            if(result > 0 ){
                Toast.makeText(DetailActivity.this, R.string.add, Toast.LENGTH_SHORT).show();
                startActivity(new Intent(DetailActivity.this,MainActivity.class));
            }else {
                Toast.makeText(DetailActivity.this, R.string.addf, Toast.LENGTH_SHORT).show();

            }
        }
    }

    private void Type() {
        if(type.equals("MOVIE")  && movieHelper.getOne(name) ){
            //delete movie
            Log.d("movie test","test");
            act = false;
            delete = true;
            fab.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_is_favorite));
        }else if(type.equals("MOVIE") && !movieHelper.getOne(name)){
            // savemovie
            Log.d("movie test","test");
            act = true;
            insert = true;
            fab.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_not_favorite));
        }
        else if (type.equals("TV") && mTvHelper.getOne(name)){
            //delete tv
            act = false;
            delete = false;
            fab.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_is_favorite));

        }else if(type.equals("TV") && !mTvHelper.getOne(name)){
            //save tv
            act = true;
            insert = false;
            fab.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_not_favorite));
        }
    }

    private void Helper() {
        movieHelper = MovieHelper.getInstance(DetailActivity.this);
        mTvHelper = TvHelper.getInstance(DetailActivity.this);
    }

    private void Detail() {
        mMovieTvItems = new Items();

        movieTvItems = getIntent().getParcelableExtra(EXTRA_DETAIL);
        type = movieTvItems.getType();
        Log.d("type",type);
        name = movieTvItems.getTitle_film();
    }



}
