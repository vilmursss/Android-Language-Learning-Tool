package com.example.android.wordgameandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class SearchResults extends AppCompatActivity {

    ListView wordPairList;
    ListAdapter listAdapter;
    ArrayList<Word> wordPairArray = new ArrayList<Word>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);


        String searchKey = getIntent().getExtras().getString("searchKey");
        DbHandler dbHandler = new DbHandler(this);
        final List<Word> wordObject = dbHandler.getWord(searchKey);
        String checkId = String.valueOf(wordObject.get(0).getId());

        if(checkId.equals("0")){
            String errorText = "No words found!";
            Word addWordObject = new Word(0, errorText, "");
            wordPairArray.add(addWordObject);
            listAdapter = new ListAdapter(SearchResults.this, R.layout.wordpair_nofound,
                    wordPairArray);

            wordPairList = (ListView) findViewById(R.id.listView);
            wordPairList.setItemsCanFocus(false);
            wordPairList.setAdapter(listAdapter);
        }

        else {
            for (Word wordPairObject  : wordObject ) {
                Word addWordObject = new Word(wordPairObject.getId(), wordPairObject.getFirstWord(), wordPairObject.getSecondWord());
                wordPairArray.add(addWordObject);
                listAdapter = new ListAdapter(SearchResults.this, R.layout.wordpair_listrow,
                        wordPairArray);

                wordPairList = (ListView) findViewById(R.id.listView);
                wordPairList.setItemsCanFocus(false);
                wordPairList.setAdapter(listAdapter);
            }
        }

    }
}
