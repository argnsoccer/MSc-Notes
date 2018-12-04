package com.example.andre.ideator;

import android.content.Intent;
import android.media.Image;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import android.net.Uri;

public class MainActivity extends AppCompatActivity implements IdeaFragment.OnFragmentInteractionListener {

    ArrayList<Idea> ideas1 = new ArrayList<Idea>();



    @Override
    protected void onCreate(final Bundle savedInstanceState) {

        final MyApp m = (MyApp)getApplication();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(m.getIdeas() == null){
            m.setIdeas(new ArrayList<Idea>());
        }
        else{
            final ArrayList<Idea>ideas = m.getIdeas();
        }


        ImageButton addButton = (ImageButton) findViewById(R.id.addButton);
        ImageButton refreshButton = (ImageButton) findViewById(R.id.refresh);

        GridView gridview = (GridView) findViewById(R.id.gridview);
        final IdeaAdapter ideaAdapter = new IdeaAdapter(this, m.getIdeas());
        gridview.setAdapter(ideaAdapter);
        ideaAdapter.notifyDataSetChanged();


        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                FragmentManager fm = getSupportFragmentManager();
                IdeaFragment ideaFragment = IdeaFragment.newInstance(m.getIdeas(), position);
                ideaFragment.show(fm, "fragment_edit_idea");
                ideaAdapter.notifyDataSetChanged();
            }
        });



        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent add = new Intent(view.getContext(), IdeaActivity.class);
                add.putParcelableArrayListExtra("ideas", m.getIdeas());
                startActivity(add);
                ideaAdapter.notifyDataSetChanged();

            }
        });

        refreshButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ideaAdapter.notifyDataSetChanged();

            }
        });

    }


    @Override
    public void onFragmentInteraction(Uri uri){
        //you can leave it empty
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        // Save UI state changes to the savedInstanceState.
        // This bundle will be passed to onCreate if the process is
        // killed and restarted.
        savedInstanceState.putParcelableArrayList("ideas", ideas1);
        // etc.
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // Restore UI state from the savedInstanceState.
        // This bundle has also been passed to onCreate.
        ArrayList<Idea> ideas = savedInstanceState.getParcelableArrayList("ideas");
    }

    protected SwipeRefreshLayout.OnRefreshListener mOnRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            SwipeRefreshLayout swipelayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
            if(swipelayout.isRefreshing()){
                swipelayout.setRefreshing(false);
            }
        }
    };

}
