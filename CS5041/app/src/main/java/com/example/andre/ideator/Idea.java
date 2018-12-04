package com.example.andre.ideator;
import android.graphics.Color;
import android.os.Parcel;
import android.os.Parcelable;


public class Idea implements Parcelable {


    private String title;
    private String desc;
    private int color;

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Idea createFromParcel(Parcel in) {
            return new Idea(in);
        }

        public Idea[] newArray(int size) {
            return new Idea[size];
        }
    };


    public Idea(Parcel in){
        this.title = in.readString();
        this.desc = in.readString();
        this.color = in.readInt();

    }

    public Idea(){
        title = "title";
        desc = "desc";
        color = Color.WHITE;

    }

    public Idea(String t, String d){
        title = t;
        desc = d;
        color = Color.WHITE;
    }
    public String getTitle(){
        return title;
    }

    public String getDesc(){
        return desc;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void setDesc(String d){
        this.desc = d;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.desc);
        dest.writeInt(this.color);

    }

    @Override
    public String toString(){
        return "Idea{title: " + title + '\'' + ", desc: "  + desc +"}";
    }
}
