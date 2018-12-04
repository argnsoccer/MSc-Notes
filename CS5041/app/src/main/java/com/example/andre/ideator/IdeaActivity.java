package com.example.andre.ideator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class IdeaActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idea);
        final MyApp ma= (MyApp) getApplication();

//       ideas = this.getIntent().getParcelableArrayListExtra("ideas");

        final EditText setTitle = (EditText) findViewById(R.id.setTitle);
        final EditText setDesc = (EditText) findViewById(R.id.setDesc);

        Button confirm = (Button) findViewById(R.id.confirm);
        Button cancel = (Button) findViewById(R.id.cancel);

        confirm.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String title = setTitle.getText().toString();
                String desc = setDesc.getText().toString();

                Idea i = new Idea(title,desc);
                ma.getIdeas().add(i);

                Intent added = new Intent(view.getContext(), MainActivity.class);
                added.putExtra("title", title);
                added.putExtra("desc", desc);
                startActivity(added);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                Intent cancelled = new Intent(view.getContext(), MainActivity.class);
                startActivity(cancelled);
            }
        });







    }
}
