package com.example.andre.ideator;

import android.content.Context;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class IdeaAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<Idea> ideas;

    public IdeaAdapter(Context c, ArrayList<Idea> ideas){
         mContext = c;
         this.ideas = ideas;
    }
    @Override
    public int getCount() {
        return ideas.size();
    }



    @Override
    public Object getItem(int position) {
        return ideas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView ideaView;
        final Idea idea = ideas.get(position);

        if(convertView == null){
            ideaView = new TextView(mContext);
            ideaView.setBackgroundResource(R.drawable.back);
            ideaView.setLayoutParams(new ViewGroup.LayoutParams(180,180 ));
            ideaView.setPadding(4,4,4,4);
        }
        else{
            ideaView = (TextView) convertView;
        }

        ideaView.setBackgroundColor(idea.getColor());
        ideaView.setText(idea.getTitle());
        ideaView.setGravity(Gravity.CENTER);
        return ideaView;
    }
}
