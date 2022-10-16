package pms.coursera;

import java.util.ArrayList;
import java.util.Collections;

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

	private double dotProduct(Rater me, Rater r) {
		double result = 0.0;
		ArrayList<String> myMoviesIds = me.getItemsRated();
		for (String movie : myMoviesIds) {
			if (r.getItemsRated().contains(movie))
				result += (me.getRating(movie) - 5) * (r.getRating(movie) - 5);
		}
		return result;
	}

	private ArrayList<Rating> getSimilarities(String id) {
		ArrayList<Rating> rating = new ArrayList<Rating>();
		ArrayList<Rater> raters = RaterDatabase.getRaters();
		for (Rater rater : raters) {
			if (!rater.getId().equals(id)) {
				double dp = dotProduct(RaterDatabase.getRater(id), rater);
				if (dp > 0)
					rating.add(new Rating(rater.getId(), dp));
			}
		}
		Collections.sort(rating);
		Collections.reverse(rating);
		return rating;
	}

	public ArrayList<Rating> getSimilarRatings(String id, int similarRaters, int minimalRaters) {
		ArrayList<Rating> ratingList = new ArrayList<>();
		ArrayList<String> movidIDByTopSimilar = new ArrayList<>();
		ArrayList<Rating> simiList1 = getSimilarities(id);
		// System.out.println("Total number of similar raters : " + simiList1.size());
		int num = simiList1.size();
		if (similarRaters > num) {
			similarRaters = num;
		}
		for (int i = 0; i < similarRaters; i++) {
			String raterID1 = simiList1.get(i).getItem();
			ArrayList<String> movieRated1 = RaterDatabase.getRater(raterID1).getItemsRated();
			for (String movieID : movieRated1) {
				if (!movidIDByTopSimilar.contains(movieID)) {
					movidIDByTopSimilar.add(movieID);
				}
			}
		}
		for (String j : movidIDByTopSimilar) {
			double ave = 0;
			ArrayList<Rating> simiList = getSimilarities(id);
			int count = 0;
			double total = 0;
			int simiweighttotal = 0;
			for (int i = 0; i < similarRaters; i++) {
				double rating = RaterDatabase.getRater(simiList.get(i).getItem()).getRating(j);
				if (rating != -1) {
					count++;
					total += rating * simiList.get(i).getValue();
					simiweighttotal += simiList.get(i).getValue();
				}
			}
			if (count >= minimalRaters)
				ave = total / simiweighttotal;
			if (ave > 0)
				ratingList.add(new Rating(j, ave));
		}
		Collections.sort(ratingList);
		Collections.reverse(ratingList);

		return ratingList;
	}

	public ArrayList<Rating> getAverageRatingsByFilter(String id, int similarRaters, int minimalRaters, Filter filter) {
		ArrayList<Rating> ratingList = new ArrayList<>();
		ArrayList<String> movidIDByTopSimilar = new ArrayList<>();
		ArrayList<Rating> simiList1 = getSimilarities(id);
		for (int i = 0; i < similarRaters; i++) {
			String raterID1 = simiList1.get(i).getItem();
			ArrayList<String> movieRated1 = RaterDatabase.getRater(raterID1).getItemsRated();
			for (String movieID : movieRated1) {
				if (!movidIDByTopSimilar.contains(movieID)) {
					movidIDByTopSimilar.add(movieID);
				}
			}
		}

		for (String j : movidIDByTopSimilar) {
			if (filter.satisfies(j)) {
				double ave = 0;
				ArrayList<Rating> simiList = getSimilarities(id);
				int count = 0;
				double total = 0;
				double simiweighttotal = 0;
				for (int i = 0; i < similarRaters; i++) {
					double rating = RaterDatabase.getRater(simiList.get(i).getItem()).getRating(j);
					if (rating != -1) {
						count++;
						total += rating * simiList.get(i).getValue();
						simiweighttotal += simiList.get(i).getValue();

					}
				}
				if (count >= minimalRaters)
					ave = total / simiweighttotal;

				if (ave > 0)
					ratingList.add(new Rating(j, ave));
			}

		}
		Collections.sort(ratingList);
		Collections.reverse(ratingList);

		return ratingList;
	}
}