package com.example.android.wordgameandroid;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class DbHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 4;
    private static final String DATABASE_NAME = "wordBase";
    private static final String WORD_TABLE = "words";

    private static final String ID = "id";
    private static final String FIRST_WORD = "firstWord";
    private static final String SECOND_WORD = "secondWord";
    private static final String WORD_LIST = "wordList";

    public DbHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + WORD_TABLE + "("
                + ID + " INTEGER PRIMARY KEY," + FIRST_WORD + " TEXT,"
                + SECOND_WORD + " TEXT," + WORD_LIST + " TEXT" + ")";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + WORD_TABLE);
        onCreate(db);
    }

    // Add new word pair

    public void addWord(Word word) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FIRST_WORD, word.getFirstWord());
        values.put(SECOND_WORD, word.getSecondWord());
        values.put(WORD_LIST, word.getWordList());

        Log.d("myTag", word.getFirstWord());
        Log.d("myTag", word.getSecondWord());
        Log.d("myTag", word.getWordList());


        db.insert(WORD_TABLE, null, values);
        db.close();
    }

    // Get current database row count

    public int getWordCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        final String DATABASE_COMPARE = "select count(*) from words";

        int rowCount = (int) DatabaseUtils.longForQuery(db, DATABASE_COMPARE, null);

        return rowCount;
    }

    // Return highest word pair ID

    public int getHighestId() {

        SQLiteDatabase db = this.getReadableDatabase();
        final SQLiteStatement stmt = db.compileStatement("SELECT MAX(" + ID + ") FROM " + WORD_TABLE + "");

        if (stmt == null) {
            return 0;
        }
        return (int) stmt.simpleQueryForLong();
    }

    // Update word pair values

    public void updateWordPair(String id, String firstWord, String secondWord, String wordList){
        SQLiteDatabase db = this.getReadableDatabase();
        String updateQuery = "UPDATE " + WORD_TABLE + " SET " + FIRST_WORD + " = '" + firstWord + "', " + SECOND_WORD
                + " = '" + secondWord + "', " + WORD_LIST + " = '" + wordList  +"' WHERE " + ID + " = " + id;
        db.execSQL(updateQuery);
        db.close();
    }

    // Delete word pair from database

    public void deleteWordPair(String id){

        SQLiteDatabase db = this.getReadableDatabase();
        String deleteQuery = "DELETE FROM " + WORD_TABLE + " WHERE " + ID + " = " + id;
        db.execSQL(deleteQuery);
        db.close();

    }

    // Clear whole database

    public void deleteAllFromDb(){

        SQLiteDatabase db = this.getReadableDatabase();
        db.delete(WORD_TABLE, null, null);
        db.close();

    }

    // Return random word object from database

    public Word getRandomWord(String list){

        if(getWordCount() < 1){
            Word returnWordObject = new Word(0, "No words in DB", "", "");

            return returnWordObject;
        }



        SQLiteDatabase db = this.getReadableDatabase();
        List<Word> listWords = new ArrayList<Word>();
        String WHERE = "" + WORD_LIST + " = '" + list +"'";
        String selectQuery = "SELECT * FROM " + WORD_TABLE + " WHERE " + WHERE;
        Cursor query = db.rawQuery(selectQuery, null);

        if(query.moveToFirst()){
            do {

                Word wordPair = new Word();
                wordPair.setId(Integer.parseInt(query.getString(0)));
                wordPair.setFirstWord(query.getString(1));
                wordPair.setSecondWord(query.getString(2));
                wordPair.setWordList(query.getString(3));
                listWords.add(wordPair);

            } while (query.moveToNext());
        }

        int maxSize = listWords.size();
        Random rand = new Random();
        int randomWord = rand.nextInt(maxSize);

        return listWords.get(randomWord);
    }

    public ArrayList<String> getLists(){
        ArrayList<String> allLists = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        Map<String, String> alreadyFound = new HashMap<String, String>();

        String selectQuery ="SELECT * FROM " + WORD_TABLE;
        Cursor query = db.rawQuery(selectQuery, null);

                if(query.moveToFirst()){
                    do {
                        String value = alreadyFound.get(query.getString(3));
                        if (value != null) {
                        } else {
                            alreadyFound.put(query.getString(3), query.getString(3));
                            allLists.add(query.getString(3));
                        }
                    } while (query.moveToNext());
                }

        return allLists;
    }

    public int getWordListCount(String list){
        SQLiteDatabase db = this.getReadableDatabase();
        int counter = 0;

        String WHERE = "" + WORD_LIST + " = '" + list +"'";
        String selectQuery ="SELECT * FROM " + WORD_TABLE  + " WHERE " + WHERE;
        Cursor query = db.rawQuery(selectQuery, null);

        if(query.moveToFirst()){
            do {
               counter++;
            } while (query.moveToNext());
        }

        return counter;
    }

    public ArrayList<String> getAllBesidesCurrentList(String currentList){
        ArrayList<String> allLists = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        Map<String, String> alreadyFound = new HashMap<String, String>();
        alreadyFound.put(currentList, currentList);

        String selectQuery ="SELECT * FROM " + WORD_TABLE;
        Cursor query = db.rawQuery(selectQuery, null);

        if(query.moveToFirst()){
            do {
                String value = alreadyFound.get(query.getString(3));
                if (value != null) {
                } else {
                    alreadyFound.put(query.getString(3), query.getString(3));
                    allLists.add(query.getString(3));
                }
            } while (query.moveToNext());
        }

        if(allLists.size() == 0){
            return null;
        }

        return allLists;
    }

    public List<Word> getWordsByList(String list){

        List<Word> wordList = new ArrayList<Word>();
        SQLiteDatabase db = this.getReadableDatabase();

        String WHERE = "" + WORD_LIST + " = '" + list +"'";
        String selectQuery = "SELECT * FROM " + WORD_TABLE + " WHERE " + WHERE;
        Cursor query = db.rawQuery(selectQuery, null);

        if (query.moveToFirst()) {
            do {
                    Word wordPair = new Word();
                    wordPair.setId(Integer.parseInt(query.getString(0)));
                    wordPair.setFirstWord(query.getString(1));
                    wordPair.setSecondWord(query.getString(2));
                    wordPair.setWordList(query.getString(3));
                    wordList.add(wordPair);

            } while (query.moveToNext());
        } else {
            Word wordPair = new Word();
            wordPair.setId(0);
            wordPair.setFirstWord("");
            wordPair.setSecondWord("");
            wordPair.setWordList("");
            wordList.add(wordPair);
        }
        return wordList;

    }


    // Return all word pairs based on search string

    public List<Word> getWord(String word) {

        List<Word> wordList = new ArrayList<Word>();
        SQLiteDatabase db = this.getReadableDatabase();

        String WHERE = "" + FIRST_WORD + " LIKE '" + word + "%' OR " + SECOND_WORD + " LIKE '" + word + "%' ";
        String selectQuery = "SELECT * FROM " + WORD_TABLE + " WHERE " + WHERE;
        Cursor query = db.rawQuery(selectQuery, null);

        if (query.moveToFirst()) {
            do {
                Word wordPair = new Word();
                wordPair.setId(Integer.parseInt(query.getString(0)));
                wordPair.setFirstWord(query.getString(1));
                wordPair.setSecondWord(query.getString(2));
                wordPair.setWordList(query.getString(3));
                wordList.add(wordPair);

            } while (query.moveToNext());
        } else {
            Word wordPair = new Word();
            wordPair.setId(0);
            wordPair.setFirstWord("");
            wordPair.setSecondWord("");
            wordPair.setWordList("");
            wordList.add(wordPair);
        }
        return wordList;

    }
}
