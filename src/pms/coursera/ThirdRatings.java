package pms.coursera;

import java.util.ArrayList;

public class ThirdRatings {

	private ArrayList<Rater> myRaters;

	public ThirdRatings() {
		this("ratings.csv");
	}

	public ThirdRatings(String ratingsFile) {
		FirstRatings fr = new FirstRatings();
		myRaters = fr.loadRaters(ratingsFile);
	}

	public int getRaterSize() {
		return myRaters.size();
	}

	private double getAverageById(String movieId, int minimalRaters) {
		int ratings = 0;
		double totalScore = 0.0;
		for (Rater rater : myRaters) {
			if (rater.hasRating(movieId))
				totalScore += rater.getRating(movieId);
				ratings++;
		}
		if(ratings >= minimalRaters)
			return totalScore / ratings;
		return 0.0;
	}

	public ArrayList<Rating> getAverageRatings(int minimalRaters) {
		ArrayList<Rating> allAverageRatings = new ArrayList<Rating>();
		ArrayList<String> movies = MovieDatabase.filterBy(new TrueFilter());
		
		for(String movieId : movies) {
			double average = Math.round(getAverageById(movieId, minimalRaters)*100.0)/100.0;
			if(average != 0.0) {
				Rating rating = new Rating(movieId, average);
				allAverageRatings.add(rating);
			}
		}
		return allAverageRatings;
	}

	public ArrayList<Rating> getAverageRatingsByFilter(int minimalRaters, Filter filter) {
		ArrayList<Rating> averageRatings = new ArrayList<Rating>();
		ArrayList<String> movieIds = MovieDatabase.filterBy(filter);
		for (String movie : movieIds) {
			double ratingValue = Math.round(getAverageById(movie, minimalRaters)*100.0)/100.0;
			if(ratingValue != 0.0) {
				Rating rating = new Rating(movie, ratingValue);
				averageRatings.add(rating);
			}
		}
		return averageRatings;
	}
}
