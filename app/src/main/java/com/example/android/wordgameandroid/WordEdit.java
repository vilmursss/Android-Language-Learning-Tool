package com.example.android.wordgameandroid;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class WordEdit extends AppCompatActivity {

    EditText firstWord;
    EditText secondWord;
    String wordPairId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_edit);

        // Get sent extras to fill fields with these values

        String getFirstWord = getIntent().getExtras().getString("first_word_edit_extra");
        String getSecondWord = getIntent().getExtras().getString("second_word_edit_extra");
        wordPairId = getIntent().getExtras().getString("id_edit_extra");

        firstWord = (EditText) findViewById(R.id.firstWordEdit);
        firstWord.setText(getFirstWord);

        secondWord = (EditText) findViewById(R.id.secondWordEdit);
        secondWord.setText(getSecondWord);

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

       dbHandler.updateWordPair(wordPairId,getFirstWordText,getSecondWordText);
    }

    // Delete word pair click event option

    public void deleteWordEditBtn(View view){
        DbHandler dbHandler = new DbHandler(this);
        dbHandler.deleteWordPair(wordPairId);
    }
}
