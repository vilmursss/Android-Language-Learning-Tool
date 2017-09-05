package com.example.android.wordgameandroid;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
        + ID + " INTEGER PRIMARY KEY," + FIRST_WORD +  " TEXT,"
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

    public int getShopsCount() {
        String countQuery = "SELECT * FROM " + WORD_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

// return count
        return cursor.getCount();
    }
}
