package com.example.emantest;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.emanteast.db.MovieDatabaseHelper;

public class ListMovieFragment extends ListFragment {

	private static final String TAG = "ListMovieFragment";

	OnMovieClickedListener listener;
	MovieDatabaseHelper db;
	MovieAdapter adapter;
	public static ProgressBar bar;
	View barLayout;
	int page = 1;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		db = new MovieDatabaseHelper(getActivity());
		int count = db.getCountOfMovie();
		if (count != 0) {
			page = count / 5;
		}
		return inflater.inflate(R.layout.movie_list, container, false);
	}
	
	/*@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		getActivity().getSupportFragmentManager().putFragment(outState, getTag(), this);
		//getActivity().getSupportFragmentManager().beginTransaction().add(this, "list_movie_fragment");
	}*/
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		String movieId = ((MovieEntity) l.getItemAtPosition(position)).getId();
		listener.onMovieClickedListener(Long.valueOf(movieId));
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		LayoutInflater inflater = this.getLayoutInflater(savedInstanceState);
		barLayout = inflater.inflate(R.layout.progressbar, null);
		bar = (ProgressBar) barLayout.findViewById(R.id.loading);

		ListView view = (ListView) getView().findViewById(android.R.id.list);
		adapter = new MovieAdapter(getActivity(), R.layout.movie_item);
		view.addFooterView(barLayout);
		view.setAdapter(adapter);
		view.setOnScrollListener(new MyScrollListener(this));

		if (isNetworkAvailable()) {
			getList();
		} else {
			getListHistory();
		}
	}

	public void addNextPageToList() {
		if (isNetworkAvailable()) {
			new JsonReader(getActivity(), adapter).execute(++page, 5);
		} else {
			Toast.makeText(getActivity(), R.string.empty_list_source, Toast.LENGTH_LONG).show();
		}

	}

	private void getListHistory() {
		List<MovieEntity> movies = db.populateMovies(db.getListObject(null));
		if(movies.size() ==0){
			if (ListMovieActivity.ringProgressDialog.isShowing()) {
				ListMovieActivity.ringProgressDialog.dismiss();
			}
			bar.setVisibility(View.INVISIBLE);
			TextView text = (TextView) barLayout.findViewById(R.id.progressText);
			text.setText(R.string.empty_cache);
			// nevim jestli je lepsim resenim text v progress baru nebo toast
			// Toast.makeText(getActivity(), R.string.empty_cache, Toast.LENGTH_LONG).show();
			return;
		}
		addToAdapter(movies);
	}

	private void getList() {
		new JsonReader(getActivity(), adapter).execute(page, 10);
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
			throw new ClassCastException(activity.toString() + " must implement OnMovieClickedListener");
		}

	}

	private boolean isNetworkAvailable() {
		ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
		return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}

}
