package com.example.android.wordgameandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ListView;

import java.util.List;

public class SearchResults extends AppCompatActivity {

    EditText eText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        eText = (EditText) findViewById(R.id.editText);

        String searchKey = getIntent().getExtras().getString("searchKey");
        DbHandler dbHandler = new DbHandler(this);
        List<Word> wordObject = dbHandler.getWord(searchKey);
        String checkId = String.valueOf(wordObject.get(0).getId());

        if(checkId.equals("0")){
            String errorText = "No words found!";
            eText.setText(errorText);
        }

        else {
            String firstWord = wordObject.get(0).getSecondWord();
            eText.setText(firstWord);
        }

    }
}
