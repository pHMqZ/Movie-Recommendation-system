package pms.coursera.duke;

public class Movie {
	
	private String id;
	private String title;
	private int year;
	private String genres;
	private String director;
	private String country;
	private int minutes;
	private String poster;
	
	
	
	public Movie(String id, String title, String year, String genres) {
		this.id = id.trim();
		this.title = title.trim();
		this.year = Integer.parseInt(year.trim());
		this.genres = genres;
	}

	public Movie(String id, String title, String year, String genres, String director, String country, String poster, int minutes) {
		this.id = id.trim();
		this.title = title.trim();
		this.year = Integer.parseInt(year.trim());
		this.genres = genres;
		this.director = director;
		this.country = country;
		this.poster = poster;
		this.minutes = minutes;
	}

	public String getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public int getYear() {
		return year;
	}

	public String getGenres() {
		return genres;
	}

	public String getDirector() {
		return director;
	}

	public String getCountry() {
		return country;
	}

	public int getMinutes() {
		return minutes;
	}

	public String getPoster() {
		return poster;
	}

	public String toString() {
		String result = "Movie [id= "+ id + ", title= "+ title +", year="+ year +", genres="+ genres+"]";
		return result;
	}

}
