package pms.coursera;

import java.util.ArrayList;
import java.util.Collections;

public class MovieRunnerSimilarRatings {

	private FourthRatings fourth;

	public MovieRunnerSimilarRatings() {
		fourth = new FourthRatings("ratings.csv");
		MovieDatabase.initialize("ratedmoviesfull.csv");
	}

	public void printAverageRatings() {
		System.out.println("There are " + MovieDatabase.size() + " movies in the file");
		System.out.println("There are " + fourth.getRaterSize() + " raters in the file");

		int ratings = 35;
		ArrayList<Rating> rating = fourth.getAverageRatings(ratings);
		Collections.sort(rating);

		int num = 0;
		System.out.println("Rating values of Movies with at least " + ratings + " ratings:");
		for (Rating rate : rating) {
			double value = rate.getValue();
			if (value != 0.0)
				num += 1;
			String movieId = rate.getItem();
			System.out.println(value + " " + MovieDatabase.getTitle(movieId));
		}

		System.out.println("There are " + num + " movies have at least " + ratings + " ratings");
	}

	public void printSimilarRatings() {
		System.out.println("There are " + MovieDatabase.size() + " movies in the file.");
		System.out.println("There are " + fourth.getRaterSize() + " raters in the file.");
		ArrayList<Rating> rating = fourth.getSimilarRatings("337", 10, 3);
		for (Rating rater : rating) {
			System.out.println(MovieDatabase.getTitle(rater.getItem()) + " : " + rater.getValue());
		}

		System.out.println("There are " + rating.size() + " recommended movies were found");
	}

	public void printSimilarRatingByGenre() {
		System.out.println("There are " + MovieDatabase.size() + " movies in the file.");
		System.out.println("There are " + fourth.getRaterSize() + " raters in the file.");
		Filter genreFilter = new GenreFilter("Mystery");
		ArrayList<String> movieIds = MovieDatabase.filterBy(genreFilter);
		ArrayList<Rating> rating = fourth.getSimilarRatings("964", 20, 5);
		int num = 0;
		for (Rating rater : rating) {
			if (movieIds.contains(rater.getItem()))
				System.out.println(MovieDatabase.getTitle(rater.getItem()) + " : " + rater.getValue());
			System.out.println("  " + MovieDatabase.getGenres(rater.getItem()));
			num += 1;
		}
		System.out.println("There are " + num + " recommended movies were found");
	}

	public void printSimilarRatingsByDirector() {
		System.out.println("There are " + MovieDatabase.size() + " movies in the file.");
		System.out.println("There are " + fourth.getRaterSize() + " raters in the file.\n");
		Filter directorFilter = new DirectorsFilter(
				"Clint Eastwood,J.J. Abrams,Alfred Hitchcock,Sydney Pollack,David Cronenberg,Oliver Stone,Mike Leigh");
		ArrayList<String> movieIds = MovieDatabase.filterBy(directorFilter);
		ArrayList<Rating> rating = fourth.getSimilarRatings("120", 10, 2);
		int num = 0;
		for (Rating rater : rating) {
			if (movieIds.contains(rater.getItem()))
				System.out.println(MovieDatabase.getTitle(rater.getItem()) + " : " + rater.getValue());
			System.out.println("    " + MovieDatabase.getDirector(rater.getItem()));
			num += 1;
		}
		System.out.println("There are " + num + " recommended movies were found");
	}

	public void printSimilarRatingsByGenreAndMinutes() {
		System.out.println("There are " + MovieDatabase.size() + " movies in the file.");
		System.out.println("There are " + fourth.getRaterSize() + " raters in the file.\n");
		Filter genreFilter = new GenreFilter("Drama");
		Filter minutesFilter = new MinuteFilter(80, 160);
		AllFilters all = new AllFilters();
		all.addFilters(genreFilter);
		all.addFilters(minutesFilter);
		ArrayList<String> movieIds = MovieDatabase.filterBy(all);

		ArrayList<Rating> rating = fourth.getSimilarRatings("168", 10, 3);
		int num = 0;
		for (Rating rater : rating) {
			if (movieIds.contains(rater.getItem()))
				System.out.println(MovieDatabase.getTitle(rater.getItem()) + " : " + "Duration-"
						+ MovieDatabase.getMinutes(rater.getItem()) + " Rating-" + rater.getValue());
			System.out.println("    " + MovieDatabase.getGenres(rater.getItem()));
			num += 1;
		}
		System.out.println("There are " + num + " recommended movies were found.");
	}

	public void printSimilarRatingsByYearAfterAndMinutes() {
		System.out.println("There are " + MovieDatabase.size() + " movies in the file.");
		System.out.println("There are " + fourth.getRaterSize() + " raters in the file.\n");
		Filter yearAfterFilter = new YearAfterFilter(1975);
		Filter minutesFilter = new MinuteFilter(70, 200);
		AllFilters all = new AllFilters();
		all.addFilters(yearAfterFilter);
		all.addFilters(minutesFilter);
		ArrayList<String> movieIds = MovieDatabase.filterBy(all);

		ArrayList<Rating> rating = fourth.getSimilarRatings("314", 10, 5);
		int num = 0;
		for (Rating rater : rating) {
			if (movieIds.contains(rater.getItem()))
				System.out.println(MovieDatabase.getTitle(rater.getItem()) + " : " + "Duration-"
						+ MovieDatabase.getMinutes(rater.getItem()) + " Rating-" + rater.getValue());
			System.out.println("    " + MovieDatabase.getYear(rater.getItem()));
			num += 1;
		}
		System.out.println("There are " + num + " recommended movies were found.");
	}
}
