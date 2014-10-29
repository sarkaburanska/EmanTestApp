package com.example.emanteast.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class OpenHelper extends SQLiteOpenHelper {

	public OpenHelper(Context context) {
		super(context, MovieDatabaseHelper.DATABASE_NAME, null, MovieDatabaseHelper.DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE " + MovieDatabaseHelper.TABLE_NAME + " (" + MovieDatabaseHelper.COLUMN_ID + " INTEGER PRIMARY KEY, " + MovieDatabaseHelper.COLUMN_TITLE + " TEXT NOT NULL, " + MovieDatabaseHelper.COLUMN_YEAR + " INTEGER NOT NULL, " + MovieDatabaseHelper.COLUMN_POSTER + " TEXT NOT NULL, " + MovieDatabaseHelper.COLUMN_SYNOPSIS + " TEXT NOT NULL, " + MovieDatabaseHelper.COLUMN_CAST + " TEXT NOT NULL );");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + MovieDatabaseHelper.TABLE_NAME);
		onCreate(db);
	}
}
