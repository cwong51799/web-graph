/* Christopher Wong (#111386693) 
 * CSE 214 R09
 */
import java.util.*;
/**
 * Sorts pages by their index.
 * @author cwong
 *
 */
class IndexComparator implements Comparator{
	public int compare(Object o1, Object o2) {
		WebPage page1 = (WebPage) o1;
		WebPage page2 = (WebPage) o2;
		return(page1.compareTo(page2));
	}
}
/**
 * Sorts pages by their URL alphabetically
 * @author cwong
 *
 */
class URLComparator implements Comparator{
	public int compare(Object o1, Object o2) {
		WebPage page1 = (WebPage) o1;
		WebPage page2 = (WebPage) o2;
		return (page1.getUrl().compareTo(page2.getUrl()));
	}
}
/**
 * Sorts pages by their PageRank
 * @author cwong
 *
 */
class RankComparator implements Comparator{
	public int compare(Object o1, Object o2) { // page ranks are off in the middle of it
		WebPage page1 = (WebPage) o1;
		WebPage page2 = (WebPage) o2;
		if (page1.getPageRank() == page2.getPageRank()) {
			//System.out.println(page1.getUrl() + " has the same page rank than " + page2.getUrl());
			return 0;
		}
		else if (page1.getPageRank() > page2.getPageRank()) {
			//System.out.println(page1.getUrl() + " has a higher page rank than " + page2.getUrl());
			return -1;
		}
		else {
			//System.out.println(page1.getUrl() + " has a lower page rank than " + page2.getUrl());
			return 1;
		}
	}
}

