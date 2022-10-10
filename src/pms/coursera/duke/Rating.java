package pms.coursera.duke;

public class Rating {
	
	private String item;
	private Double value;
	
	public Rating(String item, Double value) {
		this.item = item;
		this.value = value;
	}

	public String getItem() {
		return item;
	}

	public Double getValue() {
		return value;
	}

	
	public String toString() {
		return "Rating [" + getItem() + ", " + getValue() + "]";
	}
	
	public int compareTo(Rating other) {
		if(value < other.value) return -1;
		if(value > other.value) return 1;
		
		return 0;
	}
	

}
