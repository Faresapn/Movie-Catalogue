package com.example.submisi5.model.Items;

import android.os.Parcel;
import android.os.Parcelable;

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
        dest.writeInt   (id);
        dest.writeString(Desc_film);
        dest.writeString(Title_film);
        dest.writeString(Info_film);
        dest.writeString(photo);

        dest.writeString(Rating_bar);
        dest.writeString(Rate);
        dest.writeString(type);
    }

    protected Items(Parcel in) {
        id = in.readInt();
        Desc_film = in.readString();
        Title_film = in.readString();
        Info_film = in.readString();
        photo = in.readString();

        Rating_bar = in.readString();
        Rate = in.readString();
        type = in.readString();
    }

    public static final Creator<Items> CREATOR = new Creator<Items>() {
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

