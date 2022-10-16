package pms.coursera;

public class DirectorsFilter implements Filter {

	private String myDirectors;
	
	public DirectorsFilter(String directors) {
		myDirectors = directors;
	}
	
	
	public boolean satisfies(String id) {
		String [] directors = myDirectors.split(",");
		for(int i=0; i < directors.length; i++) {
			if(MovieDatabase.getDirector(id).indexOf(directors[i]) != -1)
				return true;
		}
		return false;
	}

}
