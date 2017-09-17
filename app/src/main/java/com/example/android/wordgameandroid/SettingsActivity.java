package com.example.android.wordgameandroid;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

public class SettingsActivity extends AppCompatActivity {
    Bundle extras;
    String getFirstWord;
    String getSecondWord;
    String wordPairId;

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
