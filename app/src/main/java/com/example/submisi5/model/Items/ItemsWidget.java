package com.example.submisi5.model.Items;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.submisi5.database.DbContract;

import static com.example.submisi5.database.DbContract.getColoumnString;

public class ItemsWidget implements Parcelable {
    String type,title,photo,overview;
    int id;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.type);
        dest.writeString(this.title);
        dest.writeString(this.photo);
        dest.writeString(this.overview);
        dest.writeInt(this.id);
    }

    public ItemsWidget(Cursor cursor) {
        this.title = getColoumnString(cursor, DbContract.MovieEntry.COLUMN_JUDUL);
        this.photo = getColoumnString(cursor, DbContract.MovieEntry.COLUMN_POSTER);
        this.overview = getColoumnString(cursor, DbContract.MovieEntry.COLUMN_OVERVIEW);
    }

    protected ItemsWidget(Parcel in) {
        this.type = in.readString();
        this.title = in.readString();
        this.photo = in.readString();
        this.overview = in.readString();
        this.id = in.readInt();
    }

    public static final Creator<ItemsWidget> CREATOR = new Creator<ItemsWidget>() {
        @Override
        public ItemsWidget createFromParcel(Parcel source) {
            return new ItemsWidget(source);
        }

        @Override
        public ItemsWidget[] newArray(int size) {
            return new ItemsWidget[size];
        }
    };
}
