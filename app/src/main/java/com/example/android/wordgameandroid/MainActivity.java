package com.example.android.wordgameandroid;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // Open add word pair activity

    public void addWord(View view){
        Intent i = new Intent(this, AddWord.class);
        startActivity(i);
    }

    // Open modify word pair activity

    public void modifyWord(View view){
        Intent i = new Intent(this, ModifyWords.class);
        startActivity(i);
    }

    // Toast current row count, mostly for development test purposes

    public void rowCount(View view){
        DbHandler dbHandler = new DbHandler(this);
        int wordCount = dbHandler.getWordCount();

        Toast.makeText(this, "Current row count: " + wordCount + " words", Toast.LENGTH_SHORT).show();
    }

    // Delete everything from database

    public void clearDB(View view){
        final DbHandler dbHandler = new DbHandler(this);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Confirm");
        builder.setMessage("Are you sure you wanna clear everything from database?");

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {

                dbHandler.deleteAllFromDb();
                dialog.dismiss();
                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                alertDialog.setTitle("Database clear");
                alertDialog.setMessage("All word pairs deleted!");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
          }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    // Start game activity

    public void playGame(View view){
        Intent i = new Intent(this, GameActivity.class);
        startActivity(i);
    }
}
