package com.example.emanteast.db;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.emantest.MovieEntity;

public class MovieDatabaseHelper implements MyDatabaseHelper<MovieEntity> {

	protected static final String DATABASE_NAME = "movie_db";
	protected static final String TABLE_NAME = "movies";
	protected static final int DATABASE_VERSION = 2;

	protected static final String COLUMN_ID = "_id";
	protected static final String COLUMN_TITLE = "title";
	protected static final String COLUMN_YEAR = "year";
	protected static final String COLUMN_POSTER = "poster";
	protected static final String COLUMN_SYNOPSIS = "synopsis";
	protected static final String COLUMN_CAST = "abridged_cast";

	public static final String[] columns = { COLUMN_ID, COLUMN_TITLE, COLUMN_YEAR, COLUMN_POSTER, COLUMN_SYNOPSIS, COLUMN_CAST };

	private SQLiteOpenHelper openHelper;

	public MovieDatabaseHelper(Context ctx) {
		this.openHelper = new OpenHelper(ctx);
	}

	@Override
	public Cursor getObject(long id) {
		SQLiteDatabase db = openHelper.getReadableDatabase();
		String[] values = { String.valueOf(id) };
		return db.query(TABLE_NAME, columns, COLUMN_ID + "= ?", values, null, null, null);
	}

	public Cursor getObject(String title) {
		SQLiteDatabase db = openHelper.getReadableDatabase();
		String[] values = { title };
		return db.query(TABLE_NAME, columns, COLUMN_TITLE + "= ?", values, null, null, null);
	}

	@Override
	public Cursor getListObject(Map<String, String> params) {
		SQLiteDatabase db = openHelper.getReadableDatabase();
		return db.query(TABLE_NAME, columns, null, null, null, null, null);
	}

	@Override
	public Cursor getListObjectNew(int offset) {
		SQLiteDatabase db = openHelper.getReadableDatabase();
		return db.query(TABLE_NAME, columns, null, null, null, null, COLUMN_ID + " DESC", String.valueOf(offset));
	}

	@Override
	public void insertObject(final MovieEntity object) {
		/*
		 * if (getObject(object.getTitle()).getCount() > 0) {
		 * System.out.println("Movie with title " + object.getTitle() +
		 * " exist."); return; }
		 */
		SQLiteDatabase db = openHelper.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(COLUMN_TITLE, object.getTitle());
		values.put(COLUMN_POSTER, object.getPoster());
		values.put(COLUMN_SYNOPSIS, object.getSynopsis());
		values.put(COLUMN_CAST, object.getAbridged_cast());
		values.put(COLUMN_YEAR, object.getYear());
		db.insertOrThrow(TABLE_NAME, null, values);

		try {
			db.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		db.close();

	}

	public int getCountOfMovie() {
		SQLiteDatabase db = openHelper.getReadableDatabase();
		Cursor c = db.rawQuery("select count(*) from " + TABLE_NAME, null);
		c.moveToFirst();
		int count = c.getInt(0);
		c.close();
		return count;
	}

	public void insertObject(final List<MovieEntity> objects) {

		SQLiteDatabase db = openHelper.getWritableDatabase();
		for (MovieEntity object : objects) {
			/*
			 * if (getObject(object.getTitle()).getCount() > 0) { continue; }
			 */
			ContentValues values = new ContentValues();
			values.put(COLUMN_TITLE, object.getTitle());
			values.put(COLUMN_POSTER, object.getPoster());
			values.put(COLUMN_SYNOPSIS, object.getSynopsis());
			values.put(COLUMN_CAST, object.getAbridged_cast());
			values.put(COLUMN_YEAR, object.getYear());
			try {
				db.insertOrThrow(TABLE_NAME, null, values);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		db.close();
	}

	@Override
	public void updateObject(MovieEntity object) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteObject(long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteObject(MovieEntity object) {
		// TODO Auto-generated method stub

	}

	public MovieEntity populateMovie(Cursor object) {

		object.moveToFirst();
		if (object != null && object.getCount() > 0 && object.moveToFirst()) {
			MovieEntity movie = new MovieEntity(object.getString(object.getColumnIndex(COLUMN_ID)), object.getString(object.getColumnIndex(COLUMN_TITLE)), object.getInt(object.getColumnIndex(COLUMN_YEAR)), object.getString(object.getColumnIndex(COLUMN_SYNOPSIS)));
			movie.setAbridged_cast(object.getString(object.getColumnIndex(COLUMN_CAST)));
			movie.setPoster(object.getString(object.getColumnIndex(COLUMN_POSTER)));
			return movie;
		}
		return null;
	}

	public List<MovieEntity> populateMovies(Cursor object) {
		List<MovieEntity> movies = new ArrayList<MovieEntity>();
		if (object == null) {
			return null;
		}
		if (object.moveToFirst()) {

			do {
				MovieEntity movie = new MovieEntity(object.getString(object.getColumnIndex(COLUMN_ID)), object.getString(object.getColumnIndex(COLUMN_TITLE)), object.getInt(object.getColumnIndex(COLUMN_YEAR)), object.getString(object.getColumnIndex(COLUMN_SYNOPSIS)));
				movie.setAbridged_cast(object.getString(object.getColumnIndex(COLUMN_CAST)));
				movie.setPoster(object.getString(object.getColumnIndex(COLUMN_POSTER)));
				movies.add(movie);
			} while (object.moveToNext());
		}

		return movies;
	}

}
