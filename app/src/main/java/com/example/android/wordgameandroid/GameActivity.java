package com.example.android.wordgameandroid;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Random;

public class GameActivity extends AppCompatActivity {

    private SoundPool soundPool;
    private int soundID;
    boolean loaded = false;

    Drawable buttonBackGround;
    TextView translatableWord;
    Button firstOption;
    Button secondOption;
    Button thirdOption;
    Button fourthOption;
    DbHandler dbHandler = new DbHandler(this);
    HashMap<String, String> playedWordsHashMap = new HashMap<String, String>();
    int playedWordsCount = 0;
    int correctAnswer = 0;
    String roundCorrectWord = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        translatableWord = (TextView) findViewById(R.id.roundWord);
        firstOption = (Button) findViewById(R.id.firstBtn);
        secondOption = (Button) findViewById(R.id.secondBtn);
        thirdOption = (Button) findViewById(R.id.thirdBtn);
        fourthOption = (Button) findViewById(R.id.fourthBtn);

        buttonBackGround = firstOption.getBackground();

        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId,
                                       int status) {
                loaded = true;
            }
        });
        soundID = soundPool.load(this, R.raw.correct_answer, 1);

        newQuestion();
    }

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
                    break;
                case 2:
                    correctAnswer = 2;
                    setCorrectWord();
                    fillOtherButtons();
                    break;
                case 3:
                    correctAnswer = 3;
                    setCorrectWord();
                    fillOtherButtons();
                    break;
                case 4:
                    correctAnswer = 4;
                    setCorrectWord();
                    fillOtherButtons();
                    break;
                default:
                    translatableWord.setText("Should not happen");
                    break;
            }

        }

    }

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

    public String getRandomWord() {
        Word randWord = dbHandler.getRandomWord();
        return randWord.getFirstWord();
    }

    public int randomNumber() {
        Random rand = new Random();
        int randomReturn = rand.nextInt(4) + 1;

        return randomReturn;
    }

    public void playCorrectAnswerSound(){
        AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        float actualVolume = (float) audioManager
                .getStreamVolume(AudioManager.STREAM_MUSIC);
        float maxVolume = (float) audioManager
                .getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        float volume = actualVolume / maxVolume;
        // Is the sound loaded already?
        if (loaded) {
            soundPool.play(soundID, volume, volume, 1, 0, 1f);
        }
    }

    public void setCorrectWord() {
        boolean check = false;
        while (!check) {
            Word randWord = dbHandler.getRandomWord();

            if (playedWordsHashMap.containsKey(randWord.getSecondWord())) {


            } else {
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

    public boolean gameEnd() {

        if (playedWordsCount == dbHandler.getWordCount()) {
            return true;
        }
        return false;
    }

    public void waitTwoSecTimer(){

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                setAllBackToDefault();
                newQuestion();
            }
        }, 2000);
    }


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

    public void firstBtnClick(View view) {

        if (correctAnswer == 1) {
            playCorrectAnswerSound();
            firstOption.setBackgroundColor(Color.GREEN);
            waitTwoSecTimer();
        } else {
            firstOption.setBackgroundColor(Color.RED);
            wrongAnswer();
        }

    }

    public void secondBtnClick(View view) {

        if (correctAnswer == 2) {
            playCorrectAnswerSound();
            secondOption.setBackgroundColor(Color.GREEN);
            waitTwoSecTimer();
        } else {
            secondOption.setBackgroundColor(Color.RED);
            wrongAnswer();
        }

    }

    public void thirdBtnClick(View view) {

        if (correctAnswer == 3) {
            playCorrectAnswerSound();
            thirdOption.setBackgroundColor(Color.GREEN);
            waitTwoSecTimer();
        } else {
            thirdOption.setBackgroundColor(Color.RED);
            wrongAnswer();
        }

    }

    public void fourthBtnClick(View view) {

        if (correctAnswer == 4) {
            playCorrectAnswerSound();
            fourthOption.setBackgroundColor(Color.GREEN);
            waitTwoSecTimer();
        } else {
            fourthOption.setBackgroundColor(Color.RED);
            wrongAnswer();
        }

    }

    public void wrongAnswer(){
        translatableWord.setText("sry");
    }

}
