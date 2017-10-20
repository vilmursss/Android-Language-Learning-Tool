package com.example.android.wordgameandroid;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class HighScores extends AppCompatActivity {
    private DatabaseReference mDatabase;
    ListView listView;

    final ArrayList<Spanned> highScoreArray = new ArrayList<Spanned>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_scores);
        listView = (ListView) findViewById(R.id.highScoreListView);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        retrieveData();
        navigateBackArrow();

    }
    // Create options menu

    @Override
    public boolean onCreateOptionsMenu(Menu menu){

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settings_menu, menu);
        return true;
    }

    // Create items selectable on items menu

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){

        int id = menuItem.getItemId();
        if(id == R.id.settings_menu){
            Intent startIntentActivity = new Intent(this, SettingsActivity.class);
            startIntentActivity.putExtra("CLASS_INFORMATION", AddWord.class);
            startActivity(startIntentActivity);
            return true;
        }
        else if(id == R.id.sign_out_menu){
            AuthUI.getInstance().signOut(this);
            Intent startIntentActivity = new Intent(this, MainActivity.class);
            startActivity(startIntentActivity);
            return true;
        }
        else {
            Intent goBackToMainActivity = new Intent(this, MainActivity.class);
            startActivity(goBackToMainActivity);
            return true;
        }
    }

    // Navigation back arrow

    public void navigateBackArrow() {

        ActionBar actionBar = this.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    // Fetch data from firebase

    public void retrieveData() {

        mDatabase.orderByValue().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot highScoreDataSnapshot : dataSnapshot.getChildren()) {

                            highScoreArray.add(Html.fromHtml("User: " + "<b>" +highScoreDataSnapshot.getKey().toString() + "</b>" + " Highscore: " + "<b>" + highScoreDataSnapshot.getValue().toString() + "</b>" +" pts"));
                }

                fillListView();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }

    // Fill listview with retrieved data

    public void fillListView() {

        Collections.reverse(highScoreArray);
        ArrayAdapter<Spanned> adapter = new ArrayAdapter<Spanned>
                (this, android.R.layout.simple_list_item_1, highScoreArray);
        listView.setAdapter(adapter);
    }

}