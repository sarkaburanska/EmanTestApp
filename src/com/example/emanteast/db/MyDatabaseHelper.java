package com.example.emanteast.db;

import java.util.Map;

import android.database.Cursor;

public interface MyDatabaseHelper<E> {
	
	
	public Cursor getObject(long id);

	public Cursor getListObject(Map<String, String> params);
	
	public Cursor getListObjectNew(int offset);
	
	public void insertObject(E object);
	
	public void updateObject(E object);
	
	public void deleteObject(long id);

	public void deleteObject(E object);
}
