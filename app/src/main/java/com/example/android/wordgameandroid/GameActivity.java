package com.example.android.wordgameandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class GameActivity extends AppCompatActivity {

    TextView translatableWord;
    DbHandler dbHandler = new DbHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        translatableWord = (TextView) findViewById(R.id.roundWord);
    }

    public void firstOption(View view){

        Word randWord = dbHandler.getRandomWord();
        translatableWord.setText(randWord.getSecondWord());

    }
}
