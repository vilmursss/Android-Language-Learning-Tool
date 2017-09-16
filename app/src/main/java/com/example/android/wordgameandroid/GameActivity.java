package com.example.android.wordgameandroid;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Random;

public class GameActivity extends AppCompatActivity {

    // SoundPool

    private SoundPool soundPool;
    private int soundID;
    private int failureID;
    boolean loaded = false;

    // Copy button background object

    Drawable buttonBackGround;

    // Necessary textviews

    TextView translatableWord;
    TextView pointsTextView;
    TextView gameOverText;
    TextView mNotEnoughWords;

    // Clickable option buttons

    Button firstOption;
    Button secondOption;
    Button thirdOption;
    Button fourthOption;
    Button moveToAddWords;

    // DbHandler

    DbHandler dbHandler = new DbHandler(this);

    // Data structure for already played words and other helping variables

    HashMap<String, String> playedWordsHashMap = new HashMap<String, String>();
    int playedWordsCount = 0;
    int correctAnswer = 0;
    String roundCorrectWord = "";

    // ProgressBar

    ProgressBar mProgressBar;
    CountDownTimer mCountDownTimer;
    int pbTimer = 0;

    // Points

    int gamePoints = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        if(dbHandler.getWordCount() >= 4) {

            setContentView(R.layout.activity_game);

            gameOverText = (TextView) findViewById(R.id.PickText);
            translatableWord = (TextView) findViewById(R.id.roundWord);
            pointsTextView = (TextView) findViewById(R.id.points);
            pointsTextView.setText("Points: " + String.valueOf(gamePoints));

            firstOption = (Button) findViewById(R.id.firstBtn);
            secondOption = (Button) findViewById(R.id.secondBtn);
            thirdOption = (Button) findViewById(R.id.thirdBtn);
            fourthOption = (Button) findViewById(R.id.fourthBtn);
            buttonBackGround = firstOption.getBackground();

            // SoundPool manager loader

            soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
            soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
                @Override
                public void onLoadComplete(SoundPool soundPool, int sampleId,
                                           int status) {
                    loaded = true;
                }
            });
            soundID = soundPool.load(this, R.raw.correct_answer, 1);
            failureID = soundPool.load(this, R.raw.fail_answer, 1);

            newQuestion();
        }
        else {
            setContentView(R.layout.not_enough_words_in_db);

            moveToAddWords = (Button) findViewById(R.id.saveWordsBtn);
            mNotEnoughWords = (TextView) findViewById(R.id.notEnoughWords);
            mNotEnoughWords.setText("For starting the game, you need to have at least 4 saved word pairs. Currently you have " + String.valueOf(dbHandler.getWordCount()) + " saved word pairs.");
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
        int id = menuItem.getItemId();
        if(id == R.id.settings_menu){
            Intent startIntentActivity = new Intent(this, SettingsActivity.class);
            startIntentActivity.putExtra("CLASS_INFORMATION", AddWord.class);
            startActivity(startIntentActivity);
            return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }


    // Countdown timer for the progressBar

    public void progressBarCountDown(){

        pbTimer = 0;

        mProgressBar=(ProgressBar)findViewById(R.id.progressBar);
        mProgressBar.setProgress(pbTimer);
        mCountDownTimer=new CountDownTimer(10700,50) {

            @Override
            public void onTick(long millisUntilFinished) {
                pbTimer++;
                mProgressBar.setProgress((int)pbTimer);

            }

            @Override
            public void onFinish() {

                playWrongAnswerSound();
                mCountDownTimer.cancel();
                wrongAnswer();
            }
        };
        mCountDownTimer.start();
    }

    // Give new question

    public void newQuestion() {

        if (gameEnd()) {
            translatableWord.setText("All words played");
        } else {


            int randInt = randomNumber();

            switch (randInt) {
                case 1:
                    correctAnswer = 1;
                    setCorrectWord();
                    fillOtherButtons();
                    progressBarCountDown();
                    break;
                case 2:
                    correctAnswer = 2;
                    setCorrectWord();
                    fillOtherButtons();
                    progressBarCountDown();
                    break;
                case 3:
                    correctAnswer = 3;
                    setCorrectWord();
                    fillOtherButtons();
                    progressBarCountDown();
                    break;
                case 4:
                    correctAnswer = 4;
                    setCorrectWord();
                    fillOtherButtons();
                    progressBarCountDown();
                    break;
                default:
                    translatableWord.setText("Should not happen");
                    break;
            }

        }

    }

    // Fill other options with random words from database besides correct answer

    public void fillOtherButtons() {

        HashMap<String, String> usedWords = new HashMap<String, String>();
        int i = 0;

        while (i < 4) {

            String getWord = getRandomWord();

            if (i == 0 && !usedWords.containsKey(getWord)) {

                if (correctAnswer == 1) {
                    i = 1;
                }

                else if (!getWord.equals(roundCorrectWord)) {
                    firstOption.setText(getWord);
                    usedWords.put(getWord, getWord);
                    i = 1;
                }

                else {
                    usedWords.put(getWord, getWord);
                }
            }

            else if (i == 1 && !usedWords.containsKey(getWord)) {

                if (correctAnswer == 2) {
                    i = 2;
                }

                else if (!getWord.equals(roundCorrectWord)) {
                    secondOption.setText(getWord);
                    usedWords.put(getWord, getWord);
                    i = 2;
                }

                else {
                    usedWords.put(getWord, getWord);
                }
            }

            else if (i == 2 && !usedWords.containsKey(getWord)) {

                if (correctAnswer == 3) {
                    i = 3;
                }

                else if (!getWord.equals(roundCorrectWord)) {
                    thirdOption.setText(getWord);
                    usedWords.put(getWord, getWord);
                    i = 3;
                }

                else {
                    usedWords.put(getWord, getWord);
                }
            }

            else if (i == 3 && !usedWords.containsKey(getWord)) {

                if (correctAnswer == 4) {
                    i = 4;
                }

                else if (!getWord.equals(roundCorrectWord)) {
                    fourthOption.setText(getWord);
                    usedWords.put(getWord, getWord);
                    i = 4;
                }
            }
        }
    }

    // Get random word from database

    public String getRandomWord() {
        Word randWord = dbHandler.getRandomWord();
        return randWord.getFirstWord();
    }

    // Random number generator

    public int randomNumber() {
        Random rand = new Random();
        int randomReturn = rand.nextInt(4) + 1;

        return randomReturn;
    }

    // If chosen option is correct, play this sound

    public void playCorrectAnswerSound(){
        AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        float actualVolume = (float) audioManager
                .getStreamVolume(AudioManager.STREAM_MUSIC);
        float maxVolume = (float) audioManager
                .getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        float volume = actualVolume / maxVolume;

        if (loaded) {
            soundPool.play(soundID, volume, volume, 1, 0, 1f);
        }
    }

    // If chosen option is wrong, play this sound

    public void playWrongAnswerSound(){
        AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        float actualVolume = (float) audioManager
                .getStreamVolume(AudioManager.STREAM_MUSIC);
        float maxVolume = (float) audioManager
                .getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        float volume = actualVolume / maxVolume;

        if (loaded) {
            soundPool.play(failureID, volume, volume, 1, 0, 1f);
        }
    }

    // Set correct word using random number generator to one of options

    public void setCorrectWord() {
        boolean check = false;
        while (!check) {
            Word randWord = dbHandler.getRandomWord();

            if (playedWordsHashMap.containsKey(randWord.getSecondWord())) {

            }

            else {
                playedWordsHashMap.put(randWord.getSecondWord(), randWord.getSecondWord());
                translatableWord.setText(randWord.getSecondWord());
                playedWordsCount++;
                check = true;

                roundCorrectWord = randWord.getFirstWord();

                if (correctAnswer == 1) {
                    firstOption.setText(randWord.getFirstWord());
                } else if (correctAnswer == 2) {
                    secondOption.setText(randWord.getFirstWord());
                } else if (correctAnswer == 3) {
                    thirdOption.setText(randWord.getFirstWord());
                } else {
                    fourthOption.setText(randWord.getFirstWord());
                }
            }

        }
    }

    // If whole database is played, open new activity

    public boolean gameEnd() {

        if (playedWordsCount == dbHandler.getWordCount()) {
            return true;
        }
        return false;
    }

    // Timer, which is used when option is chosen to mask the option button

    public void waitTwoSecTimer(){

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                setAllBackToDefault();
                newQuestion();
            }
        }, 2000);
    }

    // Set all buttons back to default color

    public void setAllBackToDefault(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            firstOption.setBackground(buttonBackGround);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            secondOption.setBackground(buttonBackGround);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            thirdOption.setBackground(buttonBackGround);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            fourthOption.setBackground(buttonBackGround);
        }
    }

    // Updates points, if answer was correct

    public void updateGamePoints(){

        gamePoints = gamePoints+(1500/pbTimer);
        pointsTextView.setText("Points: "+ String.valueOf(gamePoints));

    }

    // First option click

    public void firstBtnClick(View view) {

        if (correctAnswer == 1) {
            playCorrectAnswerSound();
            firstOption.setBackgroundColor(Color.GREEN);
            updateGamePoints();
            mCountDownTimer.cancel();
            waitTwoSecTimer();
        } else {
            playWrongAnswerSound();
            firstOption.setBackgroundColor(Color.RED);
            wrongAnswer();
        }
    }

    // Second option click

    public void secondBtnClick(View view) {

        if (correctAnswer == 2) {
            playCorrectAnswerSound();
            secondOption.setBackgroundColor(Color.GREEN);
            updateGamePoints();
            mCountDownTimer.cancel();
            waitTwoSecTimer();
        } else {
            playWrongAnswerSound();
            secondOption.setBackgroundColor(Color.RED);
            wrongAnswer();
        }
    }

    // Third option click

    public void thirdBtnClick(View view) {

        if (correctAnswer == 3) {
            playCorrectAnswerSound();
            thirdOption.setBackgroundColor(Color.GREEN);
            updateGamePoints();
            mCountDownTimer.cancel();
            waitTwoSecTimer();
        } else {
            playWrongAnswerSound();
            thirdOption.setBackgroundColor(Color.RED);
            wrongAnswer();
        }
    }

    // Fourth option click

    public void fourthBtnClick(View view) {

        if (correctAnswer == 4) {
            playCorrectAnswerSound();
            fourthOption.setBackgroundColor(Color.GREEN);
            updateGamePoints();
            mCountDownTimer.cancel();
            waitTwoSecTimer();

        } else {
            playWrongAnswerSound();
            fourthOption.setBackgroundColor(Color.RED);
            wrongAnswer();
        }
    }

    // Function for wrong answer

    public void wrongAnswer(){
        hideAllButtons();
        mCountDownTimer.cancel();
        gameOverText.setText("Wrong answer :(");
        translatableWord.setTextSize(20);
        translatableWord.setText("You got " + gamePoints + " points \n" + "Do you wanna save this result? ");
    }

    // Hide all buttons

    public void hideAllButtons(){

        firstOption.setVisibility(View.INVISIBLE);
        secondOption.setVisibility(View.INVISIBLE);
        thirdOption.setVisibility(View.INVISIBLE);
        fourthOption.setVisibility(View.INVISIBLE);

        mProgressBar.setVisibility(View.INVISIBLE);


    }

    public void saveWordsBtn(View view){

        Intent i = new Intent(this, AddWord.class);
        startActivity(i);
    }

}
