package pms.coursera.duke;

import java.util.ArrayList;
import java.util.Collections;

public class MovieRunnerAvarege {

	private String movieFileName;
	private String ratingFileName;
	private SecondRatings sr;

	public MovieRunnerAvarege() {
		movieFileName = "ratedmoviesfull";
		ratingFileName = "ratings";
		sr = new SecondRatings(movieFileName, ratingFileName);
	}

	public void printAverageRatings() {
		System.out.println("There are " + sr.getMovieSize() + " movies in the file.");
		System.out.println("There are " + sr.getRaterSize() + " raters in the file.");

		int numRating = 50;
		ArrayList<Rating> ratings = sr.getAverageRatings(numRating);
		Collections.sort(ratings);

		System.out.println("Rating values of Movies with at least " + numRating + " ratings:");
		for (Rating currRating : ratings) {
			double currValue = currRating.getValue();
			if (currValue != 0.0) {
				String currMovieID = currRating.getItem();
				System.out.println(currValue + "  " + sr.getTitle(currMovieID));
			}
		}
	}

	public void getAverageRatingOneMovie() {
		int numRating = 0;
		ArrayList<Rating> ratings = sr.getAverageRatings(numRating);

		String movieTitle = "Vacation";
		String movieID = sr.getID(movieTitle);
		double value = -1;
		for (Rating currRating : ratings) {
			if (currRating.getItem().equals(movieID)) {
				value = currRating.getValue();
			}
		}
		System.out.println("The average rating for " + movieTitle + " is " + value + ".");
	}
}