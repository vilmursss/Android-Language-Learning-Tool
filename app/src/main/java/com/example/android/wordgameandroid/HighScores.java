package com.example.android.wordgameandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class HighScores extends AppCompatActivity {
    private DatabaseReference mDatabase;
    ListView listView;

    final ArrayList<String> highScoreArray = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_scores);
        listView = (ListView) findViewById(R.id.highScoreListView);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        retrieveData();

        //Initialize an ArrayAdapter and data bind with a String Array


    }

    public void retrieveData() {
        mDatabase.orderByValue().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot highScoreDataSnapshot : dataSnapshot.getChildren()) {
                            highScoreArray.add("User: " + highScoreDataSnapshot.getKey().toString() + " max points: " + highScoreDataSnapshot.getValue().toString() + " pts");
                    fillListView();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }


    public void fillListView() {

        Collections.reverse(highScoreArray);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, highScoreArray);
        listView.setAdapter(adapter);
    }

}