package com.example.android.wordgameandroid;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class ModifyWords extends AppCompatActivity {

    TextView searchResults;
    EditText searchKey;

    ListView wordPairList;
    ListAdapter listAdapter;

    private Spinner sItems;
    ArrayList<String> spinnerArray =  new ArrayList<String>();
    DbHandler dbHandler = new DbHandler(this);
    ArrayList<Word> wordPairArray = new ArrayList<Word>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_words);
        searchResults = (TextView) findViewById(R.id.searchResultsTextView);
        searchResults.setVisibility(View.INVISIBLE);
        addSpinnerValues();
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
            startIntentActivity.putExtra("CLASS_INFORMATION", ModifyWords.class);
            startActivity(startIntentActivity);
            return true;
        }
        else {
            Intent goBackToMainActivity = new Intent(this, MainActivity.class);
            startActivity(goBackToMainActivity);
            return true;
        }
    }

    public void addSpinnerValues() {

        if(dbHandler.getWordCount() < 1){
            spinnerArray.add("No lists created");
        }
        else {

            spinnerArray = dbHandler.getLists();
            spinnerArray.add("Or search all words from the list");

        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sItems = (Spinner) findViewById(R.id.searchSpinner);
        sItems.setAdapter(adapter);
        sItems.setSelection(spinnerArray.size()-1);
    }

    // Navigation back arrow

    public void navigateBackArrow() {
        ActionBar actionBar = this.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    // Click even for searching word pairs from database

    public void searchBtn(View view) {
        searchResults.setVisibility(View.VISIBLE);
        wordPairArray.clear();

        searchKey = (EditText) findViewById(R.id.searchWord);
        String dbSearchString = searchKey.getText().toString();

        if (sItems.getSelectedItemId() != spinnerArray.size()-1){

            final List<Word> wordObject = dbHandler.getWordsByList(sItems.getSelectedItem().toString());
            String checkId = String.valueOf(wordObject.get(0).getId());
            if (checkId.equals("0")) {
                String errorText = "No words found!";
                Word addWordObject = new Word(0, errorText, "", "");
                wordPairArray.add(addWordObject);
                listAdapter = new ListAdapter(ModifyWords.this, R.layout.wordpair_nofound,
                        wordPairArray);

                wordPairList = (ListView) findViewById(R.id.listView);
                wordPairList.setItemsCanFocus(false);
                wordPairList.setAdapter(listAdapter);
            }

            // If word pairs found, then show them on a list

            else {
                for (Word wordPairObject : wordObject) {
                    Word addWordObject = new Word(wordPairObject.getId(), wordPairObject.getFirstWord(), wordPairObject.getSecondWord(), wordPairObject.getWordList());
                    wordPairArray.add(addWordObject);
                    listAdapter = new ListAdapter(ModifyWords.this, R.layout.wordpair_listrow,
                            wordPairArray);

                    wordPairList = (ListView) findViewById(R.id.listView);
                    wordPairList.setItemsCanFocus(false);
                    wordPairList.setAdapter(listAdapter);
                }
            }
        }
        else if (dbSearchString.length() < 1) {
            searchKey.setError("This field can not be blank");
        } else {

            final List<Word> wordObject = dbHandler.getWord(dbSearchString);
            String checkId = String.valueOf(wordObject.get(0).getId());

            if (checkId.equals("0")) {
                String errorText = "No words found!";
                Word addWordObject = new Word(0, errorText, "", "");
                wordPairArray.add(addWordObject);
                listAdapter = new ListAdapter(ModifyWords.this, R.layout.wordpair_nofound,
                        wordPairArray);

                wordPairList = (ListView) findViewById(R.id.listView);
                wordPairList.setItemsCanFocus(false);
                wordPairList.setAdapter(listAdapter);
            }

            // If word pairs found, then show them on a list

            else {
                for (Word wordPairObject : wordObject) {
                    Word addWordObject = new Word(wordPairObject.getId(), wordPairObject.getFirstWord(), wordPairObject.getSecondWord(), wordPairObject.getWordList());
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
}

