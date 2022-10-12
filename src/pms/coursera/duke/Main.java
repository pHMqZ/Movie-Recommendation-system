package pms.coursera.duke;

public class Main {
	public static void main(String[] args) {

		// Step one
//		FirstRatings ratings = new FirstRatings();
//		ratings.testLoadMovies();
//		System.out.println("-----");
//		ratings.testLoadRaters();
//		System.out.println("-----");

		MovieRunnerAvarege mra = new MovieRunnerAvarege();
		System.out.println("---------------print test----------------");
		mra.printAverageRatings();
		System.out.println("---------------get average rating one movie test----------------");
		mra.getAverageRatingOneMovie();
	}

}
