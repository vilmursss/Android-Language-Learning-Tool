package com.example.android.wordgameandroid;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;
import java.util.List;

public class DbHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "wordBase";
    private static final String WORD_TABLE = "words";

    private static final String ID = "id";
    private static final String FIRST_WORD = "firstWord";
    private static final String SECOND_WORD = "secondWord";

    public DbHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + WORD_TABLE + "("
                + ID + " INTEGER PRIMARY KEY," + FIRST_WORD + " TEXT,"
                + SECOND_WORD + " TEXT" + ")";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + WORD_TABLE);
        onCreate(db);
    }

    public void addWord(Word word) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FIRST_WORD, word.getFirstWord());
        values.put(SECOND_WORD, word.getSecondWord());

        db.insert(WORD_TABLE, null, values);
        db.close();
    }

    public int getWordCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        final String DATABASE_COMPARE = "select count(*) from words";

        int rowCount = (int) DatabaseUtils.longForQuery(db, DATABASE_COMPARE, null);

        return rowCount;
    }

    public int getHighestId() {

        SQLiteDatabase db = this.getReadableDatabase();
        final SQLiteStatement stmt = db.compileStatement("SELECT MAX(" + ID + ") FROM " + WORD_TABLE + "");

        if (stmt == null) {
            return 0;
        }
        return (int) stmt.simpleQueryForLong();
    }

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
                wordList.add(wordPair);

            } while (query.moveToNext());
        } else {
            Word wordPair = new Word();
            wordPair.setId(0);
            wordPair.setFirstWord("");
            wordPair.setSecondWord("");
            wordList.add(wordPair);
        }
        return wordList;

    }
}
