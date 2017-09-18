package com.example.android.wordgameandroid;

// Basic word pair class

public class Word {

    private int id;
    private String firstWord;
    private String secondWord;
    private String wordList;

    public Word(){
    }

    public Word(int id, String firstWord, String secondWord, String wordList) {
        this.id = id;
        this.firstWord = firstWord;
        this.secondWord = secondWord;
        this.wordList = wordList;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFirstWord(String firstWord) {
        this.firstWord = firstWord;
    }

    public void setSecondWord(String secondWord) {
        this.secondWord = secondWord;
    }

    public void setWordList(String wordList) {
        this.wordList = wordList;
    }

    public int getId() {
        return id;
    }

    public String getFirstWord() {
        return firstWord;
    }

    public String getSecondWord() {
        return secondWord;
    }

    public String getWordList() {
        return wordList;
    }

}