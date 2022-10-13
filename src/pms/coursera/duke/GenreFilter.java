package pms.coursera.duke;

public class GenreFilter implements Filter {
	
	private String myGenre;

	public GenreFilter(String genre) {
		myGenre = genre;
	}
	
	public boolean satisfies(String id) {
		int index = MovieDatabase.getGenres(id).indexOf(myGenre);
		if(index !=-1)
			return true;
		return false;
	}

}
