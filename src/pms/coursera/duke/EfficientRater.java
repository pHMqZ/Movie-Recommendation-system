package pms.coursera.duke;

import java.util.ArrayList;
import java.util.HashMap;

public class EfficientRater implements Rater {
	
	private String myId;
	private HashMap<String, Rating> myRatings;
	
	public EfficientRater(String id) {
        myId = id;
        myRatings = new HashMap<String, Rating>();
    }
	
	public void addRating(String item, double rating) {
		myRatings.put(item, new Rating(item, rating));
	}

	public boolean hasRating(String item) {
		if(myRatings.containsKey(item))
			return true;
		return false;
	}

	public String getId() {
		return myId;
	}


	public double getRating(String item) {
		for(String rating : myRatings.keySet()) {
			if(rating.equals(item))
				myRatings.get(rating).getValue();
		}
		return -1;
	}


	public int numRatings() {
		return myRatings.size();
	}


	public ArrayList<String> getItemsRated() {
		ArrayList<String> list = new ArrayList<String>();
		for(String rating : myRatings.keySet()) {
			list.add(rating);
		}
		return list;
	}

}
