package com.example.schooldiary.repository;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class CardData implements Parcelable {

    private String title;
    private String description;
    private int image;
    private boolean like;
    private Date date;

    public CardData(String title, String description, int image, boolean like, Date date) {
        this.title = title;
        this.description = description;
        this.image = image;
        this.like = like;
        this.date = date;
    }

    protected CardData(Parcel in) {
        title = in.readString();
        description = in.readString();
        image = in.readInt();
        like = in.readByte() != 0;
        date = new Date(in.readLong());
    }

    public static final Creator<CardData> CREATOR = new Creator<CardData>() {
        @Override
        public CardData createFromParcel(Parcel in) {
            return new CardData(in);
        }

        @Override
        public CardData[] newArray(int size) {
            return new CardData[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getImage() {
        return image;
    }

    public boolean isLike() {
        return like;
    }

    public void setLike(boolean like) {
        this.like = like;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(description);
        parcel.writeInt(image);
        parcel.writeByte((byte) (like ? 1 : 0));
        parcel.writeLong(date.getTime());

    }
}
