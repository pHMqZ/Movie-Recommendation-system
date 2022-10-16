package pms.coursera;

import java.util.ArrayList;
import java.util.Random;

public class RecommendationRunner implements Recommender {

	@Override
	public ArrayList<String> getItemsToRate() {
		ArrayList<String> toBeRate = new ArrayList<>();
		ArrayList<String> movieId = MovieDatabase.filterBy(new TrueFilter());
		for (int i = 0; toBeRate.size() < 10; i++) {
			Random random = new Random();
			int r = random.nextInt(movieId.size());
			if (!toBeRate.contains(movieId.get(r)))
				toBeRate.add(movieId.get(r));
		}
		return toBeRate;
	}

	@Override
	public void printRecommendationsFor(String webRaterId) {
		MovieDatabase.initialize("ratedmoviesfull.csv");
		FourthRatings fr = new FourthRatings();
		ArrayList<Rating> ratings = fr.getSimilarRatings(webRaterId, 20, 5);
		if (ratings.size() == 0)
			System.out.println("<h2>Sorry, there are no movie recommend for you, based on your ratings</h2>");
		ArrayList<String> toBeRate = getItemsToRate();
		ArrayList<Rating> id = new ArrayList<>();
		int count = 0;
		for (int i = 0; id.size() + count != ratings.size() && id.size() < 10; i++) {
			if (!toBeRate.contains(ratings.get(i).getItem()))
				id.add(ratings.get(i));
			count++;
		}

		System.out.println("id size = " + id.size());

		System.out.println("<style>");
		System.out.println("h2,h3{");
		System.out.println("  text-align: center;");
		System.out.println("  height: 50px;");
		System.out.println("  line-height: 50px;");
		System.out.println("  font-family: Arial, Helvetica, sans- serif;");
		System.out.println("  background-color: black;");
		System.out.println("   color:  #ff6600 }");

		System.out.println(" table {");
		System.out.println("   border-collapse: collapse;");
		System.out.println("   margin: auto;}");
		System.out.println("table, th, td {");
		System.out.println("    border: 2px solid white;");
		System.out.println("    font-size: 15px;");

		System.out.println("    padding: 2px 6px 2px 6px; }");
		System.out.println(" td img{");
		System.out.println("    display: block;");
		System.out.println("    margin-left: auto;");
		System.out.println("    margin-right: auto; }");
		System.out.println("th {");
		System.out.println("    height: 40px;");
		System.out.println("    font-size: 18px;");

		System.out.println("  background-color: black;");
		System.out.println(" color: white;");
		System.out.println("text-align: center; }");

		System.out.println(" tr:nth-child(even) {");
		System.out.println("     background-color: #f2f2f2; }");
		System.out.println("  tr:nth-child(odd) {");
		System.out.println("background-color: #cccccc; }");
		System.out.println(" tr:hover {");
		System.out.println(" background-color: #666666; ");
		System.out.println("  color:white;}");

		System.out.println("table td:first-child {");
		System.out.println(" text-align: center; }");

		System.out.println(" tr {");
		System.out.println(" font-family: Arial, Helvetica, sans-serif; }");
		System.out.println(".rating{");
		System.out.println("    color:#ff6600;");
		System.out.println("    padding: 0px 10px;");
		System.out.println("   font-weight: bold; }");
		System.out.println("</style>");

		System.out.println("<h2>Movies Recommend for you^^</h2>");
		System.out.println("<table id = \"rater\">");
		System.out.println("<tr>");
		System.out.println("<th>Rank</th>");

		System.out.println("<th>Poster</th>");
		System.out.println("<th>Title & Rating</th>");
		System.out.println("<th>Genre</th>");
		System.out.println("<th>Country</th>");
		System.out.println("</tr>");

		int rank = 1;
		for (Rating rating : id) {
			System.out.println("<tr><td>" + rank + "</td>" + "<td><img src = \""
					+ MovieDatabase.getPoster(rating.getItem()) + "\" width=\"50\" height=\"70\"></td> " + "<td>"
					+ MovieDatabase.getYear(rating.getItem()) + "&ensp;&ensp; <a href=\"https://www.imdb.com/title/tt"
					+ rating.getItem() + "\">" + MovieDatabase.getTitle(rating.getItem())
					+ "</a><br><div class = \"rating\">&starf; &ensp;&ensp;&ensp;"
					+ String.format("%.1f", rating.getValue()) + "/10</td>" + "<td>"
					+ MovieDatabase.getGenres(rating.getItem()) + "</td>" + "<td>"
					+ MovieDatabase.getCountry(rating.getItem()) + "</td>" + "</tr> ");
			rank++;
		}

		System.out.println("</table>");
		System.out.println(
				"<h3>*The rank of movies is based on other raters who have the most similar rating to yours.</h3>");
	}

}
