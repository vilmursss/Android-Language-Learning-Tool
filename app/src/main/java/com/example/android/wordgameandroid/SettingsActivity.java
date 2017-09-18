package com.example.android.wordgameandroid;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import java.util.HashMap;

public class SettingsActivity extends AppCompatActivity {

    Bundle extras;

    // If call comes from WordEdit.class, these are helper variables

    String getFirstWord;
    String getSecondWord;
    String wordPairId;

    // If call comes from GameActivity.class, these are helper variables

    HashMap<String, String> playedWordsHashMap = new HashMap<String, String>();
    boolean gameStopped;
    String upperText;
    int points;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        extras = getIntent().getExtras();
        navigateBackArrow();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){

        int id = menuItem.getItemId();
        Bundle extras = getIntent().getExtras();
        Class nextActivityClass = (Class<Activity>)extras.getSerializable("CLASS_INFORMATION");
        Intent intent = new Intent(SettingsActivity.this, nextActivityClass);

        if(nextActivityClass == WordEdit.class){

            getFirstWord = getIntent().getExtras().getString("first_word_edit_extra");
            getSecondWord = getIntent().getExtras().getString("second_word_edit_extra");
            wordPairId = getIntent().getExtras().getString("id_edit_extra");

            intent.putExtra("first_word_edit_extra", getFirstWord);
            intent.putExtra("second_word_edit_extra", getSecondWord);
            intent.putExtra("id_edit_extra", wordPairId);
        }

        if(nextActivityClass == GameActivity.class){

            points = getIntent().getExtras().getInt("points");
            gameStopped = getIntent().getExtras().getBoolean("game_stopped");
            playedWordsHashMap = (HashMap<String, String>) getIntent().getExtras().getSerializable("played_words_map");
            upperText = getIntent().getExtras().getString("upper_text");

            intent.putExtra("points", points);
            intent.putExtra("game_stopped", gameStopped);
            intent.putExtra("played_words_map", playedWordsHashMap);
            intent.putExtra("upper_text", upperText);


        }

        if(id != android.R.id.home){
            NavUtils.navigateUpFromSameTask(this);
        }else {
            NavUtils.navigateUpTo(this, intent);
        }
        return super.onOptionsItemSelected(menuItem);
    }

    public void navigateBackArrow(){
        ActionBar actionBar = this.getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
}
