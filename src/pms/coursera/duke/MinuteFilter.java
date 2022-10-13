package pms.coursera.duke;

public class MinuteFilter implements Filter {
	
	private int myMin;
	private int myMax;
	
	public MinuteFilter(int minMinutes, int maxMinutes) {
		myMin = minMinutes;
		myMax = maxMinutes;
	}

	
	public boolean satisfies(String id) {
		int duration = MovieDatabase.getMinutes(id);
		return (duration >= myMin) && (duration <= myMax);
	}

}
