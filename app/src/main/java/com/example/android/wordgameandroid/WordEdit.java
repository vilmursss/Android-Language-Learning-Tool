package com.example.android.wordgameandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

        String getFirstWord = getIntent().getExtras().getString("first_word_edit_extra");
        String getSecondWord = getIntent().getExtras().getString("second_word_edit_extra");
        wordPairId = getIntent().getExtras().getString("id_edit_extra");

        firstWord = (EditText) findViewById(R.id.firstWordEdit);
        firstWord.setText(getFirstWord);
        secondWord = (EditText) findViewById(R.id.secondWordEdit);
        secondWord.setText(getSecondWord);
    }

    public void updateWordEditBtn(View view){
        DbHandler dbHandler = new DbHandler(this);

        String getFirstWordText = firstWord.getText().toString();
        String getSecondWordText = secondWord.getText().toString();

       dbHandler.updateWordPair(wordPairId,getFirstWordText,getSecondWordText);
    }

    public void deleteWordEditBtn(View view){
        DbHandler dbHandler = new DbHandler(this);
        dbHandler.deleteWordPair(wordPairId);
    }
}
