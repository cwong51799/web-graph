/* Christopher Wong (#111386693) 
 * CSE 214 R09
 */
import java.util.*;
public class WebPage implements Comparable{
	private String url;
	private int index, rank, pageRank;
	private ArrayList<String> keywords = new ArrayList<String>();
	/**
	 * @return the url
	 */
	/**
	 * Compares WebPages based on their index.
	 */
	public int compareTo(Object o) {
		WebPage wp = (WebPage) o;
		if (index == wp.getIndex())
			return 0;
		else if (index > wp.getIndex())
			return 1;
		else 
			return -1;
	}
	/**
	 * @param pageRank the pageRank to set
	 */
	public void setPageRank(int pageRank) {
		this.pageRank = pageRank;
	}
	/**
	 * @return pageRank
	 */
	public int getPageRank() {
		return pageRank;
	}
	/**
	 * @return url
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	/**
	 * @return the index
	 */
	public int getIndex() {
		return index;
	}
	/**
	 * @param index the index to set
	 */
	public void setIndex(int index) {
		this.index = index;
	}
	/**
	 * @return the rank
	 */
	public int getRank() {
		return rank;
	}
	/**
	 * @param rank the rank to set
	 */
	public void setRank(int rank) {
		this.rank = rank;
	}
	/**
	 * @return the keywords
	 */
	public ArrayList<String> getKeywords() {
		return keywords;
	}
	/**
	 * @param keywords the keywords to set
	 */
	public void setKeywords(ArrayList<String> keywords) {
		this.keywords = keywords;
	}
	/** 
	 * Usable constructor for WebPage
	 * @param url
	 * @param keywords
	 */
	public WebPage(String url, ArrayList<String> keywords) {
		this.url = url;
		this.keywords = keywords;
	}
	/**
	 * Returns information about the WebPage in the form of a string.
	 */
	public String toString() {
		String keywordsList = "|***| ";
		for (int i=0;i<keywords.size();i++) {
			keywordsList += keywords.get(i) +", ";
		}
		return index + " | " + url + "      " + keywordsList;
	}
	
}
