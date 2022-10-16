package pms.coursera;

import java.util.ArrayList;

public class SecondRatings {
	 private ArrayList<Movie> myMovies;
	    private ArrayList<Rater> myRaters;
	    
	    public SecondRatings() {
	        this("ratedmoviesfull", "ratings");
	    }
	    
	    public SecondRatings(String moviefile, String ratingsfile) {
	        FirstRatings firstrating = new FirstRatings();
	        myMovies = firstrating.loadMovies(moviefile);
	        myRaters = firstrating.loadRaters(ratingsfile);
	    }
	    
	    
	    public int getMovieSize(){
	        return myMovies.size();
	    }
	    
	    public int getRaterSize(){
	        return myRaters.size();
	    }
	    
	    private double getAverageByID(String movieID, int minimalRaters){
	        int numRatings = 0;
	        double totalScore = 0;
	        for (Rater currRater: myRaters){
	            ArrayList<String> currMovies = currRater.getItemsRated();
	            for (String s: currMovies){
	                if (s.equals(movieID)){
	                    numRatings += 1;
	                    totalScore += currRater.getRating(movieID);
	                }
	            }
	        }
	        
	        if (numRatings < minimalRaters){
	            return 0.0;
	        } else {
	            return totalScore/numRatings;
	        }
	    }
	    
	    public ArrayList<Rating> getAverageRatings(int minimalRaters){
	        ArrayList<Rating> allAverageRatings = new ArrayList<Rating>();
	        for (Movie currMovie: myMovies){
	            String currMovieID = currMovie.getId();
	            double averageRating = getAverageByID(currMovieID, minimalRaters);
	            allAverageRatings.add(new Rating(currMovieID, averageRating));
	        }
	        return allAverageRatings;
	    }
	    
	    public String getTitle(String movieID){
	        for (Movie currMovie: myMovies){
	            if (currMovie.getId().equals(movieID)){
	                return currMovie.getTitle();
	            }
	        }
	        return "N/A";
	    }
	    
	    public String getID(String movieTitle){
	        for (Movie currMovie: myMovies){
	            if (currMovie.getTitle().equals(movieTitle)){
	                return currMovie.getId();
	            }
	        }
	        return "N/A";
	    }
}
