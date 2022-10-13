package pms.coursera.duke;

import java.util.ArrayList;
import java.util.Collections;

public class MovieRunnerSimilarRatings {
	
	private FourthRatings fourth;
	
	public MovieRunnerSimilarRatings() {
		fourth = new FourthRatings("ratings.csv");
		MovieDatabase.initialize("ratedmoviesfull.csv");
	}
	
	public void printAverageRatings() {
		System.out.println("There are " + MovieDatabase.size()+ " movies in the file");
		System.out.println("There are " +fourth.getRaterSize()+ " raters in the file");
		
		int ratings = 35;
		ArrayList<Rating> rating = fourth.getAverageRatings(ratings);
		Collections.sort(rating);
		
		int num = 0;
		 System.out.println("Rating values of Movies with at least " + ratings + " ratings:");
		 for(Rating rate : rating) {
			 double value = rate.getValue();
			 if(value !=0.0)
				num +=1;
			 	String movieId = rate.getItem();
			 	System.out.println(value + " " + MovieDatabase.getTitle(movieId));
		 }
		 
		 System.out.println("There are " + num + " movies have at least " + ratings + " ratings");
	}

}
