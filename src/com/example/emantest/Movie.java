package com.example.emantest;

import java.util.List;
import java.util.Map;

public class Movie {

	private String id;
	private String title;
	private long year;
	private String mpaa_rating;
	private int runtime;
	private Object release_dates;
	private Object ratings;
	private String synopsis;
	private Map<String, String> posters;
	private List<Actor> abridged_cast;
	private Object alternate_ids;
	private Object links;

	
	
	public Movie(String id, String title, long year, String synopsis) {
		super();
		this.id = id;
		this.title = title;
		this.year = year;
		this.synopsis = synopsis;
	}
	
	

	public Movie(String id, String title, long year, String mpaa_rating, int runtime, Object release_dates, Object ratings, String synopsis, Map<String, String> posters, List<Actor> abridged_cast, Object alternate_ids, Object links) {
		super();
		this.id = id;
		this.title = title;
		this.year = year;
		this.mpaa_rating = mpaa_rating;
		this.runtime = runtime;
		this.release_dates = release_dates;
		this.ratings = ratings;
		this.synopsis = synopsis;
		this.posters = posters;
		this.abridged_cast = abridged_cast;
		this.alternate_ids = alternate_ids;
		this.links = links;
	}



	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public long getYear() {
		return year;
	}

	public void setYear(long year) {
		this.year = year;
	}

	public String getMpaa_rating() {
		return mpaa_rating;
	}

	public void setMpaa_rating(String mpaa_rating) {
		this.mpaa_rating = mpaa_rating;
	}

	public int getRuntime() {
		return runtime;
	}

	public void setRuntime(int runtime) {
		this.runtime = runtime;
	}

	public Object getRelease_dates() {
		return release_dates;
	}

	public void setRelease_dates(Object release_dates) {
		this.release_dates = release_dates;
	}

	public Object getRatings() {
		return ratings;
	}

	public void setRatings(Object ratings) {
		this.ratings = ratings;
	}

	public String getSynopsis() {
		return synopsis;
	}

	public void setSynopsis(String synopsis) {
		this.synopsis = synopsis;
	}

	public Map<String, String> getPosters() {
		return posters;
	}

	public void setPosters(Map<String, String> posters) {
		this.posters = posters;
	}

	public List<Actor> getAbridged_cast() {
		return abridged_cast;
	}

	public void setAbridged_cast(List<Actor> abridged_cast) {
		this.abridged_cast = abridged_cast;
	}

	public Object getAlternate_ids() {
		return alternate_ids;
	}

	public void setAlternate_ids(Object alternate_ids) {
		this.alternate_ids = alternate_ids;
	}

	public Object getLinks() {
		return links;
	}

	public void setLinks(Object links) {
		this.links = links;
	}

}
