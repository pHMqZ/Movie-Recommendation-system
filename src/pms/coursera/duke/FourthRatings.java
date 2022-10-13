package pms.coursera.duke;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class FourthRatings {

	public FourthRatings() {
		this("ratings.csv");
	}

	public FourthRatings(String ratingsFile) {
		RaterDatabase.initialize(ratingsFile);
	}

	public int getRaterSize() {
		return RaterDatabase.size();
	}

	private double getAverageById(String movieId, int mininalRaters) {
		int ratings = 0;
		double totalScore = 0;
		for (Rater rater : RaterDatabase.getRaters()) {
			ArrayList<String> movies = rater.getItemsRated();
			for (String movie : movies) {
				if (movie.equals(movieId))
					ratings += 1;
				totalScore += rater.getRating(movieId);
			}
		}

		if (ratings < mininalRaters)
			return 0.0;
		return totalScore / ratings;
	}

	public ArrayList<Rating> getAverageRatings(int minimalRaters) {
		ArrayList<String> movies = MovieDatabase.filterBy(new TrueFilter());
		ArrayList<Rating> allAverageRatings = new ArrayList<Rating>();
		for (String movie : movies) {
			double averageRating = getAverageById(movie, minimalRaters);
			allAverageRatings.add(new Rating(movie, averageRating));
		}
		return allAverageRatings;
	}

	public ArrayList<Rating> getAverageRatingsByFilter(int minimalRaters, Filter filter) {
		ArrayList<String> movieIds = MovieDatabase.filterBy(filter);
		ArrayList<Rating> averageRatings = new ArrayList<Rating>();
		for (String movie : movieIds) {
			double ratingValue = getAverageById(movie, minimalRaters);
			averageRatings.add(new Rating(movie, ratingValue));
		}
		return averageRatings;
	}

	private double dotProduct(Rater me, Rater r) {
		double result = 0.0;
		ArrayList<String> myMoviesIds = me.getItemsRated();
		ArrayList<String> otherMoviesIds = r.getItemsRated();
		for (String movie : myMoviesIds) {
			if (otherMoviesIds.contains(movie)) {
				double myValue = me.getRating(movie) - 5.0;
				double otherValue = r.getRating(movie) - 5.0;
				result += myValue * otherValue;
			}
		}
		return result;
	}

	private ArrayList<Rating> getSimilarities(String id) {
		ArrayList<Rating> rating = new ArrayList<Rating>();
		Rater me = RaterDatabase.getRater(id);
		for (Rater rater : RaterDatabase.getRaters()) {
			String otherId = rater.getId();
			if (!otherId.equals(id)) {
				double dotProd = dotProduct(me, rater);
				if (dotProd > 0.0)
					rating.add(new Rating(otherId, dotProd));
			}
		}

		Collections.sort(rating, Collections.reverseOrder());
		return rating;
	}

	public ArrayList<Rating> getSimilarRatings(String id, int similarRaters, int minimalRaters) {
		ArrayList<Rating> similarList = getSimilarities(id);
		HashMap<String, HashMap<String, Double>> map = new HashMap<String, HashMap<String, Double>>();
		if (similarList.size() < similarRaters)
			similarRaters = similarList.size();
		for (int i = 0; i < similarRaters; i++) {
			String raterId = similarList.get(i).getItem();
			Rater rater = RaterDatabase.getRater(raterId);
			ArrayList<String> ratedMovies = rater.getItemsRated();
			for (String movie : ratedMovies) {
				if (map.containsKey(movie))
					map.get(movie).put(raterId, rater.getRating(movie));
				HashMap<String, Double> first = new HashMap<String, Double>();
				first.put(raterId, rater.getRating(movie));
				map.put(movie, first);
			}
		}

		ArrayList<Rating> result = new ArrayList<Rating>();
		for (String movie : map.keySet()) {
			HashMap<String, Double> valueMap = map.get(movie);
			if (valueMap.size() >= minimalRaters) {
				double total = 0.0;
				for (String raterId : valueMap.keySet()) {
					double similarRating = 0.0;
					for (Rating rating : similarList)
						if (rating.getItem().equals(raterId))
							similarRating = rating.getValue();
					total += valueMap.get(raterId) * similarRating;
				}

				double weightedAverage = total / valueMap.size();
				result.add(new Rating(movie, weightedAverage));

			}
		}

		Collections.sort(result, Collections.reverseOrder());
		return result;
	}
}