package com.example.emantest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.emanteast.db.MovieDatabaseHelper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

class JsonReader extends AsyncTask<Integer, Void, Void> {

	Context context;
	MovieDatabaseHelper db;
	final static String code = "&apikey=w6uzftv2tq8esrgeyu49bw77";
	private static String urlStart = "http://api.rottentomatoes.com/api/public/v1.0/lists/movies/upcoming.json?country=us";
	private static String pageLimit = "&page_limit=";
	private static String page = "&page=";
	private MovieAdapter adapter;
	List<MovieEntity> movies;
	View view;

	public JsonReader(Context context, MovieAdapter adapter) {
		super();
		this.context = context;
		this.adapter = adapter;
		db = new MovieDatabaseHelper(context);
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		// ListMovieFragment.bar.setVisibility(View.VISIBLE);
	}

	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
		if (movies == null || movies.size() == 0) {
			onProgressUpdate();
			return;
		}
		adapter.addAll(movies);
		if (ListMovieActivity.ringProgressDialog.isShowing()) {
			ListMovieActivity.ringProgressDialog.dismiss();
		}
		// ListMovieFragment.bar.setVisibility(View.GONE);
	}

	@Override
	protected void onProgressUpdate(Void... values) {
		super.onProgressUpdate(values);
		((Activity) context).runOnUiThread(new Runnable() {
			public void run() {
				Toast.makeText(context, R.string.empty_list_source, Toast.LENGTH_LONG).show();
			}
		});
		this.cancel(true);
	}

	protected Void doInBackground(Integer... params) {
		if (params.length < 2) {
			return null;
		}
		String json = readJson(params[0], params[1]);
		List<Movie> moviesJson = parseToObject(json);
		if (moviesJson == null || moviesJson.size() == 0) {
			onProgressUpdate();
			return null;
		}
		movies = refactorList(moviesJson);
		db.insertObject(movies);
		movies = db.populateMovies(db.getListObject(null));
		return null;
	}

	private String readJson(int pageNumber, int item) {
		String json = "";
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		BufferedReader reader = null;
		String address = urlStart + code + page + pageNumber + pageLimit + item;
		try {
			URL url = new URL(address);
			reader = new BufferedReader(new InputStreamReader(url.openStream()));
			if (reader == null) {
				onProgressUpdate();
			}
			StringBuffer buffer = new StringBuffer();
			int read;
			char[] chars = new char[1024];
			while ((read = reader.read(chars)) != -1)
				buffer.append(chars, 0, read);

			json = buffer.toString();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (json.indexOf(" error ") > 0) {
			throw new IllegalAccessError();
		}

		return json;
	}

	public List<Movie> parseToObject(String json) {
		List<Movie> result = new ArrayList<Movie>();
		final Gson gson = new GsonBuilder().create();
		String json2 = json.replace("\n", "");
		int one = json2.indexOf("\"movies\":") + 9;
		int two = json2.indexOf("}}]") + 3;
		if (one < two) {
			String s = json2.substring(one, two);
			result = Arrays.asList(gson.fromJson(s, Movie[].class));
			return result;
		}
		return null;
	}

	public List<MovieEntity> refactorList(List<Movie> movies) {
		List<MovieEntity> list = new ArrayList<MovieEntity>();
		for (Movie m : movies) {
			MovieEntity en = new MovieEntity(m.getTitle(), m.getYear(), m.getSynopsis());
			if (m.getPosters().size() > 0 && m.getPosters().containsKey("original")) {
				en.setPoster(m.getPosters().get("original"));
			}
			if (m.getAbridged_cast().size() > 0) {
				String cast = "";
				for (Actor a : m.getAbridged_cast()) {
					cast = cast + a.getName() + ",";
				}
				en.setAbridged_cast(cast);
			}
			list.add(en);
		}
		return list;
	}

}
