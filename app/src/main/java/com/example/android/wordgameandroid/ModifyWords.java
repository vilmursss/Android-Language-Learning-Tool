package com.example.android.wordgameandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class ModifyWords extends AppCompatActivity {

    EditText searchKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_words);
    }

    public void searchBtn(View view){

        searchKey = (EditText) findViewById(R.id.searchWord);
        String dbSearchString = searchKey.getText().toString();

        Intent i = new Intent(this, SearchResults.class);
        i.putExtra("searchKey", dbSearchString);
        startActivity(i);

    }
}
