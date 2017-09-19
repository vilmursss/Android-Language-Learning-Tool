package com.example.android.wordgameandroid;

import android.content.Intent;
import android.content.res.Resources;
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
import java.util.List;

public class AddWord extends AppCompatActivity {

    EditText firstWord;
    EditText secondWord;
    EditText newWordList;
    DbHandler dbHandler = new DbHandler(this);
    private Spinner sItems;
    ArrayList<String> spinnerArray =  new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_word);
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

    public void addSpinnerValues() {

        if(dbHandler.getWordCount() < 1){
            spinnerArray.add("Default List");
        }
        else {
            spinnerArray = dbHandler.getLists();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sItems = (Spinner) findViewById(R.id.spinner1);
        sItems.setAdapter(adapter);
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

        int id = dbHandler.getHighestId();
        id++;

        firstWord = (EditText) findViewById(R.id.firstWord);
        secondWord = (EditText) findViewById(R.id.secondWord);
        newWordList = (EditText) findViewById(R.id.addWordList);

        String firstWordToString = firstWord.getText().toString();
        String secondWordToString = secondWord.getText().toString();
        String getListToString = sItems.getSelectedItem().toString();

        if(firstWordToString.length() < 1){
            firstWord.setError("This field can not be blank");
        }

        else if(secondWordToString.length() < 1){
            secondWord.setError("This field can not be blank");
        }

        else {
            dbHandler.addWord(new Word(id, firstWordToString, secondWordToString, getListToString));

            Toast.makeText(this, "Word pair " + firstWordToString + " = " + secondWordToString +  " saved to list: " + sItems.getSelectedItem().toString(), Toast.LENGTH_LONG).show();

            firstWord.setText("");
            secondWord.setText("");
            newWordList.setText("");

        }

    }

    public void createNewList(View view){

        newWordList = (EditText) findViewById(R.id.addWordList);

        if(newWordList.getText().toString().length() < 1){
            newWordList.setError("This field can not be blank");
        }
        else {
            spinnerArray.add(newWordList.getText().toString());
            sItems.setSelection(spinnerArray.size() - 1);
            Toast.makeText(this, "New word list created!", Toast.LENGTH_SHORT).show();
        }

    }

}
