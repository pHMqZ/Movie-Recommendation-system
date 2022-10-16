package pms.coursera;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import edu.duke.FileResource;

public class RaterDatabase {

	private static HashMap<String, Rater> ourRaters;
	
	public static void initialize() {
		if(ourRaters == null)
			ourRaters = new HashMap<String, Rater>();
	}
	
	public static void initialize(String ratingsFile) {
		if (ourRaters == null) {
 			ourRaters= new HashMap<String,Rater>();
 			addRatings("data/" + ratingsFile);
 		}
		
	}

	private static void addRatings(String fileName) {
		initialize();
		FileResource fr = new FileResource(fileName);
		CSVParser parser = fr.getCSVParser();
		for(CSVRecord record : parser) {
			String id = record.get(0);
			String item = record.get(1);
			String rating = record.get(2);
			addRaterRating(id, item, Double.parseDouble(rating));
		}
	}

	private static void addRaterRating(String raterId, String movieId, double rating) {
		initialize();
		Rater rater = null;
		if(ourRaters.containsKey(raterId));
			rater = ourRaters.get(raterId);
		rater = new EfficientRater(raterId);
		ourRaters.put(raterId, rater);
		
		rater.addRating(movieId, rating);
	}

	public static int size() {
		return ourRaters.size();
	}

	public static ArrayList<Rater> getRaters() {
		initialize();
		ArrayList<Rater> rater = new ArrayList<Rater>(ourRaters.values());
		
		return rater;
	}

	public static Rater getRater(String id) {
		initialize();
		return ourRaters.get(id);
	}

}
