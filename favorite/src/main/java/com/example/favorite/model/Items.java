package com.example.favorite.model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.favorite.DbContract;

import static com.example.favorite.DbContract.getColoumnInt;
import static com.example.favorite.DbContract.getColoumnString;

public class Items implements Parcelable {
    int id;
    String  Desc_film,Title_film,Info_film,photo,Rating_bar,Rate, type;

    public Items() {

    }



    public String getDesc_film() {
        return Desc_film;
    }

    public String getTitle_film() {
        return Title_film;
    }

    public String getInfo_film() {
        return Info_film;
    }

    public String getPhoto() {
        return photo;
    }


    public String getRating_bar() {
        return Rating_bar;
    }

    public void setRating_bar(String rating_bar) {
        Rating_bar = rating_bar;
    }

    public String getRate() {
        return Rate;
    }

    public void setRate(String rate) {
        Rate = rate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDesc_film(String desc_film) {
        Desc_film = desc_film;
    }

    public void setTitle_film(String title_film) {
        Title_film = title_film;
    }

    public void setInfo_film(String info_film) {
        Info_film = info_film;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt   (this.id);
        dest.writeString(this.Desc_film);
        dest.writeString(this.Title_film);
        dest.writeString(this.Info_film);
        dest.writeString(this.photo);
        dest.writeString(this.Rating_bar);
        dest.writeString(this.Rate);
        dest.writeString(this.type);

    }

    protected Items(Parcel in) {
        this.id = in.readInt();
        this.Desc_film = in.readString();
        this.Title_film = in.readString();
        this.Info_film = in.readString();
        this.photo = in.readString();
        this.Rating_bar = in.readString();
        this.Rate = in.readString();
        this.type = in.readString();

    }
    public Items(Cursor cursor) {

        this.id = getColoumnInt(cursor, DbContract.MovieEntry._ID);
        this.Title_film = getColoumnString(cursor,  DbContract.MovieEntry.COLUMN_JUDUL);
        this.photo = getColoumnString(cursor, DbContract.MovieEntry.COLUMN_POSTER);
        this.Desc_film = getColoumnString(cursor, DbContract.MovieEntry.COLUMN_OVERVIEW);
        this.Info_film = getColoumnString(cursor, DbContract.MovieEntry.COLUMN_RELEASE);
        this.Rating_bar = getColoumnString(cursor, DbContract.MovieEntry.COLUMN_RATINGBAR);
        this.Rate = getColoumnString(cursor, DbContract.MovieEntry.COLUMN_RATING);

    }

    public static final Parcelable.Creator<Items> CREATOR = new Parcelable.Creator<Items>() {
        @Override
        public Items createFromParcel(Parcel source) {
            return new Items(source);
        }

        @Override
        public Items[] newArray(int size) {
            return new Items[size];
        }
    };
}

