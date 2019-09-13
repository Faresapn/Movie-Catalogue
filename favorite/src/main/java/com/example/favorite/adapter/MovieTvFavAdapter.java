package com.example.favorite.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.favorite.DetailActivity;
import com.example.favorite.Items;
import com.example.favorite.R;

import com.squareup.picasso.Picasso;

import static com.example.favorite.DetailActivity.EXTRA_MOVIE;


public class MovieTvFavAdapter extends RecyclerView.Adapter<MovieTvFavAdapter.MovieTvFavAdapterViewHolder> {
    Context context;
    Cursor cursor;

    public MovieTvFavAdapter(Context context) {
        this.context = context;
    }
    public void setmMovieTvItems(Cursor items) {

        this.cursor = items;
    }
    @NonNull
    @Override
    public MovieTvFavAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item,parent,false);

        return new MovieTvFavAdapterViewHolder(v);
    }
    private Items getItems(int position){
        if (!cursor.moveToPosition(position)){
            throw new IllegalStateException("Invalid");
        }
        return new Items(cursor);
    }
    @Override
    public void onBindViewHolder(@NonNull final MovieTvFavAdapterViewHolder holder, int position) {
        final Items movieTvFavItems = getItems(position);
        Picasso.with(context).load("https://image.tmdb.org/t/p/w500"+movieTvFavItems.getPhoto()).into(holder.imageView);
        holder.title.setText(movieTvFavItems.getTitle_film());
        holder.info.setText(movieTvFavItems.getInfo_film());
        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(holder.itemView.getContext(), DetailActivity.class);
            intent.putExtra(EXTRA_MOVIE,movieTvFavItems);
            holder.itemView.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
       return 0;
    }

    public class MovieTvFavAdapterViewHolder extends RecyclerView.ViewHolder {
        TextView title,info;
        ImageView imageView;
        public MovieTvFavAdapterViewHolder(@NonNull final View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.item_movie_poster);
            title = itemView.findViewById(R.id.item_movie_title);
            info = itemView.findViewById(R.id.releas_show);
            itemView.setOnClickListener(view -> itemView.getContext().startActivity(new Intent(itemView.getContext(), DetailActivity.class)));
        }
    }
}
