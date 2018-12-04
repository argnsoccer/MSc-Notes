package com.example.andre.ideator;

import android.app.Application;

import java.util.ArrayList;

public class MyApp extends Application {
    private ArrayList<Idea> ideas;

    public ArrayList<Idea> getIdeas(){
        return this.ideas;
    }

    public void setIdeas(ArrayList<Idea> i){
        this.ideas = i;
    }


}
