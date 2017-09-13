package com.example.android.wordgameandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddWord extends AppCompatActivity {

    EditText firstWord;
    EditText secondWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_word);
    }

    // Function for submit button click event, where checks that fields are not empty

    public void submitBtn(View view){

        DbHandler dbHandler = new DbHandler(this);

        int id = dbHandler.getHighestId();
        id++;

        firstWord = (EditText) findViewById(R.id.firstWord);
        secondWord = (EditText) findViewById(R.id.secondWord);

        String firstWordToString = firstWord.getText().toString();
        String secondWordToString = secondWord.getText().toString();

        if(firstWordToString.length() < 1){
            firstWord.setError("This field can not be blank");
        }

        else if(secondWordToString.length() < 1){
            secondWord.setError("This field can not be blank");
        }

        else {
            dbHandler.addWord(new Word(id, firstWordToString, secondWordToString));

            Toast.makeText(this, "Word pair saved!", Toast.LENGTH_SHORT).show();

            firstWord.setText("");
            secondWord.setText("");
        }

    }

}
