package com.example.android.wordgameandroid;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    //  FireBase authentication

    private FirebaseAuth mFireBaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;


    public static final int RC_SIGN_IN = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize firebase authentication

        mFireBaseAuth = FirebaseAuth.getInstance();
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {


                FirebaseUser user = firebaseAuth.getCurrentUser();

                if(user != null){
                }
                else {
                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setIsSmartLockEnabled(false)
                                    .setAvailableProviders(
                                            Arrays.asList(
                                                    new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build()))
                                    .build(),
                            RC_SIGN_IN);


                }
            }
        };
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RC_SIGN_IN){
            if(resultCode == RESULT_OK){
                Toast.makeText(MainActivity.this, "Signed in!", Toast.LENGTH_SHORT).show();
            }
            else if(resultCode == RESULT_CANCELED){
                Toast.makeText(MainActivity.this, "Sign in canceled", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
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
        switch(menuItem.getItemId()){
            case R.id.settings_menu:
                Intent startIntentActivity = new Intent(this, SettingsActivity.class);
                startIntentActivity.putExtra("CLASS_INFORMATION", MainActivity.class);
                startActivity(startIntentActivity);
                return true;
            case R.id.sign_out_menu:
                AuthUI.getInstance().signOut(this);
                return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    protected void onPause(){
        super.onPause();
        mFireBaseAuth.removeAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onResume(){
        super.onResume();
        mFireBaseAuth.addAuthStateListener(mAuthStateListener);
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

    // Show high scores

    public void showHighScores(View view){
        Intent i = new Intent(this, HighScores.class);
        startActivity(i);
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
