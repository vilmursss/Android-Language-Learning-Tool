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
import android.widget.Spinner;

import java.util.ArrayList;

public class WordEdit extends AppCompatActivity {

    EditText firstWord;
    EditText secondWord;
    EditText wordList;
    String currentWordList;
    String wordPairId;

    DbHandler dbHandler = new DbHandler(this);
    private Spinner sItems;
    ArrayList<String> spinnerArray =  new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_edit);

        // Get sent extras to fill fields with these values

        String getFirstWord = getIntent().getExtras().getString("first_word_edit_extra");
        String getSecondWord = getIntent().getExtras().getString("second_word_edit_extra");
        String getWordList = getIntent().getExtras().getString("word_list");
        wordPairId = getIntent().getExtras().getString("id_edit_extra");

        firstWord = (EditText) findViewById(R.id.firstWordEdit);
        firstWord.setText(getFirstWord);

        secondWord = (EditText) findViewById(R.id.secondWordEdit);
        secondWord.setText(getSecondWord);

        wordList = (EditText) findViewById(R.id.editWordList);
        wordList.setText(getWordList);

        currentWordList = getWordList;

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
            startIntentActivity.putExtra("CLASS_INFORMATION", WordEdit.class);
            startIntentActivity.putExtra("id_edit_extra", wordPairId);
            startIntentActivity.putExtra("first_word_edit_extra", firstWord.getText().toString());
            startIntentActivity.putExtra("second_word_edit_extra", secondWord.getText().toString());
            startActivity(startIntentActivity);
            return true;
        }
        else {
            Intent goBackToModifyWordsActivity = new Intent(this, ModifyWords.class);
            startActivity(goBackToModifyWordsActivity);
            return true;
        }
    }

    public void addSpinnerValues() {

        if(dbHandler.getWordCount() < 1){
            spinnerArray.add("Default List");
        }
        else {
            if(dbHandler.getAllBesidesCurrentList(currentWordList) == null){
                spinnerArray.add(currentWordList);
            }
            else {
                spinnerArray = dbHandler.getAllBesidesCurrentList(currentWordList);
                spinnerArray.add(currentWordList);
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sItems = (Spinner) findViewById(R.id.changeWordPairList);
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

    // Update word pair values

    public void updateWordEditBtn(View view){
        DbHandler dbHandler = new DbHandler(this);

        String getFirstWordText = firstWord.getText().toString();
        String getSecondWordText = secondWord.getText().toString();
        String getWordListText = wordList.getText().toString();

       dbHandler.updateWordPair(wordPairId,getFirstWordText,getSecondWordText, getWordListText);
    }

    // Delete word pair click event option

    public void deleteWordEditBtn(View view){
        DbHandler dbHandler = new DbHandler(this);
        dbHandler.deleteWordPair(wordPairId);
    }
}
