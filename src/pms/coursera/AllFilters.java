package pms.coursera;

import java.util.ArrayList;

public class AllFilters implements Filter {

	ArrayList<Filter> filters;
	
	public AllFilters() {
		filters = new ArrayList<Filter>();
	}
	
	public void addFilters(Filter filter) {
		filters.add(filter);
	}
	
	public boolean satisfies(String id) {
		for(Filter filter : filters) {
			if(filter.satisfies(id))
				return true;
		}
		
		return false;
	}
}
