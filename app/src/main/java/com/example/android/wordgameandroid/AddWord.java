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
import android.widget.Toast;

import java.util.ArrayList;

public class AddWord extends AppCompatActivity {

    EditText firstWord;
    EditText secondWord;
    EditText wordList;
    Spinner listSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_word);

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

    // Function for submit button click event, where checks that fields are not empty

    public void submitBtn(View view){

        DbHandler dbHandler = new DbHandler(this);

        int id = dbHandler.getHighestId();
        id++;

        firstWord = (EditText) findViewById(R.id.firstWord);
        secondWord = (EditText) findViewById(R.id.secondWord);
        wordList = (EditText) findViewById(R.id.addWordList);
        listSpinner = (Spinner) findViewById(R.id.addListSpinner);

        String firstWordToString = firstWord.getText().toString();
        String secondWordToString = secondWord.getText().toString();
        String wordListToString = wordList.getText().toString();

        if(firstWordToString.length() < 1){
            firstWord.setError("This field can not be blank");
        }

        else if(secondWordToString.length() < 1){
            secondWord.setError("This field can not be blank");
        }

        else {
            dbHandler.addWord(new Word(id, firstWordToString, secondWordToString, wordListToString));

            Toast.makeText(this, "Word pair saved!", Toast.LENGTH_SHORT).show();

            firstWord.setText("");
            secondWord.setText("");
            wordList.setText("");

        }

    }

}
