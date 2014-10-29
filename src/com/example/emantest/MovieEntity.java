package com.example.emantest;


public class MovieEntity {

	private String id;
	private String title;
	private long year;
	private String synopsis;
	private String poster;
	private String abridged_cast;
	
	
	
	public MovieEntity( String title, long year, String synopsis) {
		super();
		this.title = title;
		this.year = year;
		this.synopsis = synopsis;
	}
	
	public MovieEntity(String id, String title, long year, String synopsis) {
		super();
		this.id = id;
		this.title = title;
		this.year = year;
		this.synopsis = synopsis;
	}

	public MovieEntity(String id, String title, long year, String synopsis, String posters, String abridged_cast) {
		super();
		this.id = id;
		this.title = title;
		this.year = year;
		this.synopsis = synopsis;
		this.poster = posters;
		this.abridged_cast = abridged_cast;
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
	public String getSynopsis() {
		return synopsis;
	}
	public void setSynopsis(String synopsis) {
		this.synopsis = synopsis;
	}
	public String getPoster() {
		return poster;
	}
	public void setPoster(String poster) {
		this.poster = poster;
	}
	public String getAbridged_cast() {
		return abridged_cast;
	}
	public void setAbridged_cast(String abridged_cast) {
		this.abridged_cast = abridged_cast;
	}
	
	
}
