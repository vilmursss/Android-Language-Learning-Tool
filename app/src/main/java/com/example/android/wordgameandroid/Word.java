package com.example.android.wordgameandroid;

public class Word {

    private String id;
    private String firstWord;
    private String secondWord;
    public Word(){
    }
    public Word(String id, String firstWord, String secondWord) {
        this.id = id;
        this.firstWord = firstWord;
        this.secondWord = secondWord;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setFirstWord(String firstWord) {
        this.firstWord = firstWord;
    }

    public void setSecondWord(String secondWord) {
        this.secondWord = secondWord;
    }

    public String getId() {
        return id;
    }

    public String getFirstWord() {
        return firstWord;
    }

    public String getSecondWord() {
        return secondWord;
    }

}