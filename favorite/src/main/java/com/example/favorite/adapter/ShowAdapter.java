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
import com.example.favorite.R;
import com.example.favorite.model.ItemsTv;
import com.squareup.picasso.Picasso;


import static com.example.favorite.DetailActivity.EXTRA_TV;


public class ShowAdapter extends RecyclerView.Adapter<ShowAdapter.TvFavAdapterViewHolder> {
    Context context;
    Cursor cursor;
    OnItemClickListener mListener;
    public interface OnItemClickListener{
        void onItemClick(int position);
    }


    public ShowAdapter(Context context) {
        this.context = context;
    }
    public void setmMovieTvItems(Cursor movieTvFavItems) {

        this.cursor = movieTvFavItems;
    }

    @NonNull
    @Override
    public ShowAdapter.TvFavAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item,parent,false);

        return new TvFavAdapterViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ShowAdapter.TvFavAdapterViewHolder holder, int position) {
        final ItemsTv items = getItems(position);
        Picasso.with(context).load("https://image.tmdb.org/t/p/w500"+items.getPhoto()).into(holder.imageView);
        holder.title.setText(items.getTitle_film());
        holder.info.setText(items.getInfo_film());
        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(holder.itemView.getContext(), DetailActivity.class);
            intent.putExtra(EXTRA_TV,items);
            holder.itemView.getContext().startActivity(intent);
        });
    }
    private ItemsTv getItems(int position){
        if (!cursor.moveToPosition(position)){
            throw new IllegalStateException("Invalid");
        }
        return new ItemsTv(cursor);
    }
    @Override
    public int getItemCount() {
        if (cursor==null)return 0;
        return cursor.getCount();
    }

    public class TvFavAdapterViewHolder extends RecyclerView.ViewHolder {
        TextView title,info;
        ImageView imageView;
        public TvFavAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.item_movie_poster);
            title = itemView.findViewById(R.id.item_movie_title);
            info = itemView.findViewById(R.id.releas_show);
            itemView.setOnClickListener(view -> {
                if(mListener !=null){
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION){
                        mListener.onItemClick(position);
                    }
                }
            });
        }
    }
}
