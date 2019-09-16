package com.example.submisi5.adapter;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.submisi5.R;
import com.example.submisi5.model.Items.Items;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Adapter  extends RecyclerView.Adapter<Adapter.CategoryViewHolder> {
    private Context context;
    private ArrayList<Items> mList = new ArrayList<>();
    View v;
    private  OnItemClickListener mListener;


    public Adapter(Context context) {
        this.context = context;
    }



    @Override
    public int getItemCount() {
        return mList.size();
    }

    public  void setmItems(ArrayList<Items> movieTvItems) {
        mList.clear();
        this.mList.addAll(movieTvItems);
        notifyDataSetChanged();
    }


    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void SetOnItemClickListener(OnItemClickListener mListener){
        this.mListener = mListener;
    }

    //ViewHolder
    @Override
    public void onBindViewHolder(CategoryViewHolder holder, int position) {
        Items items = mList.get(position);
        Log.d("adapter", items.getTitle_film());
        holder.mTextView1.setText(items.getTitle_film());
        holder.mTextView2.setText(items.getDesc_film());
        Picasso.with(context).load("https://image.tmdb.org/t/p/w500" + items.getPhoto()).into(holder.mImageView);


    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;
        public TextView mTextView1;
        public TextView mTextView2;


        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.item_movie_poster);
            mTextView1 = itemView.findViewById(R.id.item_movie_title);
            mTextView2 = itemView.findViewById(R.id.releas_show);

            itemView.setOnClickListener(view -> {
                if (mListener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        mListener.onItemClick(position);
                    }
                }
            });


        }
    }


    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        v = LayoutInflater.from(context).inflate(R.layout.item, viewGroup, false);
        return new CategoryViewHolder(v);
    }
}