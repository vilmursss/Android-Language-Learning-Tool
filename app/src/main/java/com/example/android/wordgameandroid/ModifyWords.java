package com.example.android.wordgameandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class ModifyWords extends AppCompatActivity {

    TextView searchResults;
    EditText searchKey;
    ListView wordPairList;
    ListAdapter listAdapter;
    ArrayList<Word> wordPairArray = new ArrayList<Word>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_words);
        searchResults = (TextView) findViewById(R.id.searchResultsTextView);
        searchResults.setVisibility(View.INVISIBLE);
    }

    public void searchBtn(View view){
        searchResults.setVisibility(View.VISIBLE);
        wordPairArray.clear();
/*
        searchKey = (EditText) findViewById(R.id.searchWord);
        String dbSearchString = searchKey.getText().toString();

        if(dbSearchString.length() < 3){
            searchKey.setError("You need to have at least three letters in this field");
        }
        else {
            Intent i = new Intent(this, SearchResults.class);
            i.putExtra("searchKey", dbSearchString);
            startActivity(i);

            */

        searchKey = (EditText) findViewById(R.id.searchWord);
        String dbSearchString = searchKey.getText().toString();
       // String searchKey = getIntent().getExtras().getString("searchKey");
        DbHandler dbHandler = new DbHandler(this);
        final List<Word> wordObject = dbHandler.getWord(dbSearchString);
        String checkId = String.valueOf(wordObject.get(0).getId());

        if(checkId.equals("0")){
            String errorText = "No words found!";
            Word addWordObject = new Word(0, errorText, "");
            wordPairArray.add(addWordObject);
            listAdapter = new ListAdapter(ModifyWords.this, R.layout.wordpair_nofound,
                    wordPairArray);

            wordPairList = (ListView) findViewById(R.id.listView);
            wordPairList.setItemsCanFocus(false);
            wordPairList.setAdapter(listAdapter);
        }

        else {
            for (Word wordPairObject  : wordObject ) {
                Word addWordObject = new Word(wordPairObject.getId(), wordPairObject.getFirstWord(), wordPairObject.getSecondWord());
                wordPairArray.add(addWordObject);
                listAdapter = new ListAdapter(ModifyWords.this, R.layout.wordpair_listrow,
                        wordPairArray);

                wordPairList = (ListView) findViewById(R.id.listView);
                wordPairList.setItemsCanFocus(false);
                wordPairList.setAdapter(listAdapter);
            }
        }
        }
}

