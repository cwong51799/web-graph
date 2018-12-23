/* Christopher Wong (#111386693) 
 * CSE 214 R09
 */
import java.util.*;
import java.io.*;
public class WebGraph {
	public static int MAX_PAGES = 40;
	public int spacesUsed = 0; //tracks logically used space
	public ArrayList<WebPage> pages = new ArrayList<WebPage>();
	public int[][] links = new int[MAX_PAGES][MAX_PAGES];
	int[][] pageRanks = new int[100][100];
	/**
	 * Constructs a WebGraph from given files.
	 * @param pageFile
	 * @param linksFile
	 * @return
	 * @throws IllegalArgumentException
	 */
	public static WebGraph buildFromFiles(String pageFile, String linksFile) throws IllegalArgumentException{
		WebGraph myWG = new WebGraph();
		try {
			Scanner pageInput = new Scanner(new File(pageFile));
			while (pageInput.hasNextLine()) {
				String line = pageInput.nextLine().trim();
				String pageName = line.substring(0,line.indexOf(".")+4).trim();
				String keywords = line.substring(line.indexOf(".")+5, line.length());
				String[] keywordsArray = keywords.split(" ");
				ArrayList<String> keywordsList = new ArrayList<String>(Arrays.asList(keywordsArray));
				myWG.addPage(pageName, keywordsList);
			}
			Scanner linkInput = new Scanner(new File(linksFile));
			while (linkInput.hasNextLine()) {
				String line = linkInput.nextLine().trim();
				String source = line.substring(0,line.indexOf(".")+4);
				String destination = line.substring(line.indexOf(".")+5, line.length());
				myWG.addLink(source, destination);
			}
			myWG.updatePageRanks();
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		finally {
			return myWG;
		}
	}
	public ArrayList<WebPage> getPages(){
		return pages;
	}
	/**
	 * Adds a page to pages.
	 * @param url
	 * @param keywords
	 * @throws IllegalArgumentException
	 */
	public void addPage(String url, ArrayList<String> keywords) throws IllegalArgumentException{
		for (int i=0;i<pages.size();i++) {
			if (pages.get(i).getUrl().equals(url)) {
				System.out.println("Error: " + url + " already exists in the WebGraph. \nCould not add new WebPage.");
				throw new IllegalArgumentException();
			}
		}
		if (url != null && keywords != null) {
			WebPage newPage = new WebPage(url, keywords);
			pages.add(newPage);
			spacesUsed++;
			updateIndex();
		}
	}
	/**
	 * Adds a link to the link array.
	 * @param source
	 * @param destination
	 * @throws IllegalArgumentException
	 */
	public void addLink(String source, String destination) throws IllegalArgumentException{
		int indexOfSource = -1;
		int indexOfDestination = -1;
		for (int i=0;i<pages.size();i++) {
			if (pages.get(i).getUrl().equals(source)) {
				indexOfSource = i;
			}
		}
		if (indexOfSource == -1) {
			System.out.println("Error: " + source + " could not be found in the WebGraph.");
			throw new IllegalArgumentException();
		}
		for (int i=0;i<pages.size();i++) {
			if (pages.get(i).getUrl().equals(destination)) {
				indexOfDestination = i;
			}
		}
		if (indexOfDestination == -1) {
			System.out.println("Error: " + destination + " could not be found in the WebGraph.");
			throw new IllegalArgumentException();
		}
		links[indexOfSource][indexOfDestination] = 1;
	}
	/** 
	 * Removes the page from pages and accordingly adjusts the links.
	 * @param url
	 */
	public void removePage(String url) {
		//printLinks();
		int indexOfURL = -1;
		for (int i=0;i<pages.size();i++) {
			if (pages.get(i).getUrl().equals(url)) {
				indexOfURL = i;
			}
		}
		if (indexOfURL == -1) {
			System.out.println("The page " + url + " was not found.");
			return;
		}
		pages.remove(indexOfURL);
		spacesUsed--;
		for (int j=0;j<spacesUsed+1;j++) { //shifts everything left
			for (int i=indexOfURL;i<spacesUsed;i++) {
				links[j][i] = links[j][i+1];
			}
		}
		//printLinks();
		for (int i=0;i<spacesUsed+1;i++) { //shifts everything up
			for (int j=indexOfURL;j<spacesUsed+1;j++) {
				links[j][i] = links[j+1][i];
			}
		}
		//printLinks();
		for (int i=indexOfURL;i<pages.size();i++) {
			pages.get(i).setIndex(pages.get(i).getIndex()-1);
		}
		updateIndex();
	}
	/**
	 * Removes the given link from links array.
	 * @param source
	 * @param destination
	 */
	public void removeLink(String source, String destination) {
		int indexOfSource = -1;
		int indexOfDestination = -1;
		for (int i=0;i<pages.size();i++) {
			if (pages.get(i).getUrl().equals(source)) {
				indexOfSource = i;
			}
		}
			if (indexOfSource == -1) {
				System.out.println("The source could not be found.");
				throw new IllegalArgumentException();
			}
		for (int i=0;i<pages.size();i++) {
			//System.out.println("Looking at " + pages.get(i).getUrl());
			if (pages.get(i).getUrl().equals(destination)) {
				indexOfDestination = i;
			}
		}
			if (indexOfDestination == -1) {
				System.out.println("The destination could not be found.");
				throw new IllegalArgumentException();
			}
		links[indexOfSource][indexOfDestination] = 0;
	}
	/**
	 * Sets the index of the page according to their index in pages.
	 */
	public void updateIndex() {
		for (int i=0;i<pages.size();i++) {
			pages.get(i).setIndex(i);
		}
	}
	/**
	 * Prints out the links array (used for testing)
	 */
	public void printLinks() {
		for (int i=0;i<spacesUsed;i++) {
			for (int j=0;j<spacesUsed;j++) {
				System.out.print(links[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}
	/**
	 * Counts the amount of connections for each link and 
	 * sets the page's rank accordingly.
	 */
	public void updatePageRanks() { //top is index of URL, bottom is amount of connections, index+1 is the rank
		int[][] pageRanks = new int[2][spacesUsed];
		for (int i=0;i<pages.size();i++) { // adds the # of connections to bot
			pageRanks[0][i] = i;
			int connections = 0;
			for (int j=0;j<pages.size();j++) {
				connections += links[j][i];
			}
			pageRanks[1][i] = connections; 
			pages.get(i).setPageRank(connections);
		}
		
	/*	for (int i=0;i<pages.size();i++) { // works well
			System.out.println(pages.get(i).getUrl() + " has " + pageRanks[1][i] + " connections.");
		}
		for (int i=0;i<pages.size()-1;i++) { // sort
			if (pageRanks[1][i+1] > pageRanks[1][i]) {
				System.out.println(pages.get(pageRanks[0][i+1]).getUrl() + " has more connections than " + pages.get(pageRanks[0][i]).getUrl());
				int temp = pageRanks[1][i];
				pageRanks[1][i] = pageRanks[1][i+1];
				pageRanks[1][i+1] = temp; // swaps their connections
				int temp2 = pageRanks[0][i];
				pageRanks[0][i] = pageRanks[0][i+1];
				pageRanks[0][i+1] = temp2; //swaps their url
			}
		} ENDS UP YOU DON'T NEED TO SORT IT YET */
		this.pageRanks = pageRanks;
	}
	/**
	 * Searches for a given keyword and prints out any elements containing it.
	 * @param keyword
	 */
	public void search(String keyword){
		ArrayList<WebPage> results = new ArrayList<WebPage>();
		for (int i=0;i<pages.size();i++) { //gathers the ones that match
			keyword = keyword.trim();
			if (pages.get(i).getKeywords().contains(keyword)) {
				//System.out.println("adding " + pages.get(i).getUrl());
				results.add(pages.get(i));
			}
		}
		for (int i=0;i<results.size()-1;i++) {
			if (results.get(i).getPageRank() < results.get(i+1).getPageRank()) {
				WebPage temp = results.get(i);
				results.set(i, results.get(i+1));
				results.set(i+1, temp);
			}
		}
		if (results.size() == 0) {
			System.out.println("No search results found for the keyword " + keyword+".");
			return;
		}
		System.out.println(String.format("%-10s%-15s%-25s", "Rank","PageRank","URL"));
		System.out.println("---------------------------------------------------------");
		for (int i=0;i<results.size();i++) {
			System.out.println(String.format("%-10d%-15d%-25s", i+1, results.get(i).getPageRank(), results.get(i).getUrl()));
		}
	}
	/**
	 * Prints the WebPages in a neat table.
	 */
	public void printTable() {
		System.out.println(String.format("%-10s%-25s%-15s%-25s%-32s", "Index","URL","PageRank","Links","Keywords"));
		System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------------------");
		for (int i=0;i<pages.size();i++) {
			String url = pages.get(i).getUrl();
			int PageRank = pageRanks[1][pages.get(i).getIndex()];
			String listedLink = "";
			String keywords = "";
		//	System.out.println("Keywords: " + pages.get(i).getKeywords());
			for (int p=0;p<pages.get(i).getKeywords().size();p++) {
				keywords += pages.get(i).getKeywords().get(p) +", ";
			}
			for (int k=0;k<pages.size();k++) {
				if (links[pages.get(i).getIndex()][k] == 1) { // adds it to the string if link is found
				//	System.out.println("Found a link between " + pages.get(i).getUrl() +" and "+ pages.get(k).getUrl());
					listedLink = listedLink + "" + k + ", ";
				}
			}
			listedLink = listedLink.trim();
			keywords = keywords.trim();
			if (listedLink.length() != 0) {
				listedLink = listedLink.substring(0, listedLink.length()-1);
			}
			if (keywords.length() != 0) {
				keywords = keywords.substring(0, keywords.length()-1);
			}
			System.out.println(String.format("%-10d%-25s%-15s%-25s%-32s", pages.get(i).getIndex(), url, PageRank, listedLink, keywords));
		}
	}
}