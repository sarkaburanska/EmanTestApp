package com.example.emantest;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;
import android.widget.Toast;

import com.example.emanteast.db.MovieDatabaseHelper;

public class ListMovieFragment extends ListFragment {

	private static final String TAG = "ListMovieFragment";

	OnMovieClickedListener listener;
	MovieDatabaseHelper db;
	JsonReader reader;
	MovieAdapter adapter;

	private static int SCROLL_OFFSET = 1;
	int page = 1;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		db = new MovieDatabaseHelper(getActivity());
		reader = new JsonReader();
		// aby se při online režimu nastavil počet dostupných page podle db
		// cache
		int count = db.getCountOfMovie();
		page = count / 5;
		return inflater.inflate(R.layout.movie_list, container, false);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		String movieId = ((MovieEntity) l.getItemAtPosition(position)).getId();
		listener.onMovieClickedListener(Long.valueOf(movieId));
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		ListView view = (ListView) getView().findViewById(android.R.id.list);
		adapter = new MovieAdapter(getActivity(), R.layout.movie_item);
		view.setAdapter(adapter);
		view.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
				if (totalItemCount - (firstVisibleItem + 1 + visibleItemCount) < SCROLL_OFFSET && visibleItemCount < totalItemCount) {
					Log.v(TAG, "scroll");
					addNextPageToList();
				}

			}
		});
		getList();
		Log.v(TAG, "onActivityCreated end");
		addNextPageToList();
	}

	public void insertJsonToDb() {
		// pri online rezimu nechci znovu stahovat stranky ktere uz mam, tak to
		// preskocim a vezmu je z db
		int count = db.getCountOfMovie();
		if (count >= page * 5) {
			return;
		}
		// pokud mam min zaznamu v db nez by byla pozadovana stranka prejdu na
		// stahovani
		try {
			String json = reader.doInBackground(String.valueOf(page));
			List<Movie> moviesJson = reader.parseToObject(json);
			List<MovieEntity> movies = reader.refactorList(moviesJson);
			db.insertObject(movies);
		} catch (Exception e) {
			// chyba zdroje, offline rezim

			// TextView view = (TextView)
			// getView().findViewById(R.id.empty_list);
			// view.setText(R.string.empty_list_source);
			Toast.makeText(getActivity(), R.string.empty_list_source, Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}
	}

	/*
	 * pridani nove stranky filmu do listu
	 */
	private void addNextPageToList() {
		page++;
		insertJsonToDb();
		List<MovieEntity> movies = db.populateMovies(db.getListObjectNew(5));
		addToAdapter(movies);
	}

	/*
	 * nacteni listu z db nebo pocatecni stranky
	 */
	private void getList() {
		insertJsonToDb();
		List<MovieEntity> movies = db.populateMovies(db.getListObject(null));
		addToAdapter(movies);
	}

	public void addToAdapter(List<MovieEntity> movies) {
		if (adapter != null) {
			adapter.addAll(movies);
		}
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			listener = (OnMovieClickedListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString() + " must implement OnNoteClickedListener");
		}

	}

}
