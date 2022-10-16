package pms.coursera;

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
		return myRatings.containsKey(item);
	}

	public String getId() {
		return myId;
	}


	public double getRating(String item) {
		if(hasRating(item))
			return myRatings.get(item).getValue();
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
