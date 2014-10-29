package com.example.emantest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.os.AsyncTask;
import android.os.StrictMode;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

class JsonReader extends AsyncTask<String, Void, String> {

	final static String code = "&apikey=w6uzftv2tq8esrgeyu49bw77";
	private static String urlStart = "http://api.rottentomatoes.com/api/public/v1.0/lists/movies/upcoming.json?page_limit=5&country=us";
	private static String page = "&page=";

	@Override
	protected String doInBackground(String... pageNumber){
		String json = "";
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		BufferedReader reader = null;
		String address = urlStart + code + page + pageNumber[0];
		try {
			URL url = new URL(address);
			reader = new BufferedReader(new InputStreamReader(url.openStream()));
			/*if(reader == null){
				throw new IllegalArgumentException();
			}*/
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
		return json;
	}

	public List<Movie> parseToObject(String json) {
		List<Movie> result = new ArrayList<Movie>();
		final Gson gson = new GsonBuilder().create();
		String json2 = json.replace("\n", "");
		int one = json2.indexOf("\"movies\":") + 9;
		int two = json2.indexOf("}}]") + 3;
		String s = json2.substring(one, two);
		result = Arrays.asList(gson.fromJson(s, Movie[].class));

		return result;
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
