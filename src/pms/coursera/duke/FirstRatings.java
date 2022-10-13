package pms.coursera.duke;

import java.util.*;

import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import edu.duke.FileResource;

public class FirstRatings {

	public ArrayList<Movie> loadMovies(String filename) {
		ArrayList<Movie> movieData = new ArrayList<Movie>();
		FileResource fr = new FileResource("data/" + filename + ".csv");
		CSVParser parser = fr.getCSVParser();

		for (CSVRecord record : parser) {
			String ID = record.get(0);
			String TITLE = record.get(1);
			String YEAR = record.get(2);
			String COUNTRY = record.get(3);
			String GENRE = record.get(4);
			String DIRECTOR = record.get(5);
			int LENGTH = Integer.parseInt(record.get(6));
			String POSTER = record.get(7);

			Movie thisMovie = new Movie(ID, TITLE, YEAR, GENRE, DIRECTOR, COUNTRY, POSTER, LENGTH);

			movieData.add(thisMovie);
		}

		return movieData;
	}

	public ArrayList<Rater> loadRaters(String filename) {
		ArrayList<Rater> raters = new ArrayList<Rater>();
		ArrayList<String> id = new ArrayList<String>();

		FileResource fr = new FileResource("data/" + filename + ".csv");
		CSVParser parser = fr.getCSVParser();

		for (CSVRecord record : parser) {
			String raterId = record.get(0);
			String movieId = record.get(1);
			double movieRating = Double.parseDouble(record.get(2));

			if (id.contains(raterId))
				for (int i = 0; i < raters.size(); i++) {
					if (raters.get(i).getId().equals(raterId))
						raters.get(i).addRating(movieId, movieRating);
				}
			else {
				Rater newRater = new EfficientRater(raterId);
				raters.add(newRater);
				newRater.addRating(movieId, movieRating);
			}
			id.add(raterId);
		}
		return raters;
	}

	public void testLoadMovies() {
		ArrayList<Movie> movies = loadMovies("ratedmoviesfull");

		System.out.println("Total number of movies: " + movies.size());

		String genre = "Comedy";
		int countGenre = 0;
		int movieLen = 150;
		int countLen = 0;

		for (Movie movie : movies) {
			if (movie.getGenres().contains(genre))
				countGenre++;
			if (movie.getMinutes() > movieLen)
				countLen++;
		}

		System.out.println("Total comedy movies: " + countGenre);
		System.out.println("Total movie > 150 min: " + countLen);


		HashMap<String, ArrayList<String>> map = new HashMap<String, ArrayList<String>>();
		for (Movie currMovie : movies) {
			String director = currMovie.getDirector().trim();
			if (director.indexOf(",") == -1) {
				if (!map.containsKey(director)) {
					map.put(director, new ArrayList<String>());
				}
				String title = currMovie.getTitle();
				map.get(director).add(title);

			} else {
				while (director.indexOf(",") != -1) {
					int idxComma = director.indexOf(",");
					String currDirector = director.substring(0, idxComma);

					if (!map.containsKey(currDirector)) {
						map.put(currDirector, new ArrayList<String>());
					}
					String title = currMovie.getTitle();
					map.get(currDirector).add(title);

					director = director.substring(idxComma + 1).trim();
				}
			}
		}

		int maxNumOfMoviesByDirector = 0;
		for (String s : map.keySet()) {
			if (map.get(s).size() > maxNumOfMoviesByDirector) {
				maxNumOfMoviesByDirector = map.get(s).size();
			}
		}
		System.out.println("The maximum number of films directed by one director is " + maxNumOfMoviesByDirector);

		String directorWithMaxMovies = "";
		for (String s : map.keySet()) {
			if (map.get(s).size() == maxNumOfMoviesByDirector) {
				directorWithMaxMovies += s + ", ";
			}
		}
		System.out.println("Names of the directors who directed the maximum number of movies "
				+ directorWithMaxMovies.substring(0, directorWithMaxMovies.length() - 2));
	}

	public void testLoadRaters() {
		ArrayList<Rater> raters = loadRaters("ratings");
		System.out.println("Total raters: " + raters);

		HashMap<String, HashMap<String, Double>> map = new HashMap<String, HashMap<String, Double>>();
		for (Rater rater : raters) {
			HashMap<String, Double> ratings = new HashMap<String, Double>();
			ArrayList<String> itemsRated = rater.getItemsRated();

			for (int i = 0; i < itemsRated.size(); i++) {
				String movieId = itemsRated.get(i);
				double movieRating = rater.getRating(movieId);
				ratings.put(movieId, movieRating);
			}
			map.put(rater.getId(), ratings);
		}

		String raterId = "193";
		int ratingSize = map.get(raterId).size();
		System.out.println("Number of ratings by this user [ID:" + raterId + "]: " + ratingSize);

		int maxRating = 0;
		for (String key : map.keySet()) {
			if (map.get(key).size() > maxRating)
				maxRating = map.get(key).size();
		}

		System.out.println("Max number of ratings done by any user: " + maxRating);

		ArrayList<String> raterMax = new ArrayList<String>();
		for (String key : map.keySet()) {
			int ratings = map.get(key).size();
			if (maxRating == ratings)
				raterMax.add(key);
		}
		System.out.println("Rater(s) with the most number of movies rated : " + raterMax);

		String movieId = "1798709";
		int nRatings = 0;
		for (String key : map.keySet()) {
			if (map.get(key).containsKey(movieId))
				nRatings++;
		}

		System.out.println("Number of ratings movie " + movieId + " has: " + nRatings);

		ArrayList<String> uniqueMovies = new ArrayList<String>();
		for (String key : map.keySet()) {
			for (String cMovieId : map.get(key).keySet()) {
				if (!uniqueMovies.contains(cMovieId))
					uniqueMovies.add(cMovieId);
			}
		}
		System.out.println("Total number of movies that were rated : " + uniqueMovies.size());

	}

}
