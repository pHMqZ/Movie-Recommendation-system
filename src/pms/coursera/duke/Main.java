package pms.coursera.duke;

public class Main {
	public static void main(String[] args) {
		FirstRatings ratings = new FirstRatings();
		ratings.testLoadMovies();
		System.out.println("-----");
		ratings.testLoadRaters();
	}
}
