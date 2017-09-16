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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        extras = getIntent().getExtras();

        ActionBar actionBar = this.getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        int id = menuItem.getItemId();
        Bundle extras = getIntent().getExtras();
        Class nextActivityClass = (Class<Activity>)extras.getSerializable("CLASS_INFORMATION");
        Intent intent = new Intent(SettingsActivity.this, nextActivityClass);

        if(id != android.R.id.home){
            NavUtils.navigateUpFromSameTask(this);
        }else {
            NavUtils.navigateUpTo(this, intent);
        }
        return super.onOptionsItemSelected(menuItem);
    }
}
