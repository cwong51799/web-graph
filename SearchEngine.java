/* Christopher Wong (#111386693) 
 * CSE 214 R09
 */
import java.util.*;
public class SearchEngine {
	public static final String PAGES_FILE = "pages.txt";
	public static final String LINKS_FILE = "links.txt";
	private WebGraph web = WebGraph.buildFromFiles(PAGES_FILE, LINKS_FILE);
	public WebGraph getWebGraph() {
		return web;
	}
	/**
	 * User client
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Loading WebGraph data. . .");
		SearchEngine SE = new SearchEngine();
		WebGraph WG = SE.getWebGraph();
		WG.updateIndex();
		System.out.println("Success!");
		Scanner input = new Scanner(System.in);
		boolean cont = true;
		while (cont) {
			try {
				WG.updatePageRanks();
			//	WG.printLinks();
				System.out.println("Menu:");
				String spaces = "   ";
				System.out.println(spaces+"(AP) - Add a new page to the graph.\n"
						+ spaces + "(RP) - Remove a page from the graph. \n"
						+ spaces + "(AL) - Add a link between pages in the graph. \n"
						+ spaces + "(RL) - Remove a link between pages in the graph. \n"
						+ spaces + "(P)  - Print the graph. \n"
						+ spaces + "(S)  - Search for pages with a keyword. \n"
						+ spaces + "(Q)  - Quit. \n");
				System.out.print("Please select an option: ");
				String answer = input.nextLine().toUpperCase();
				System.out.println();
				switch (answer) {
				case "AP":
					System.out.print("Enter a URL: ");
					String newPageURL = input.nextLine().trim();
					System.out.print("Enter keywords (space-separated): ");
					String newPageKeywords = input.nextLine();
					//newPageKeywords = newPageKeywords.replaceAll(" ", ", ");
					String[] keywordArray = newPageKeywords.split(" ");
					ArrayList<String> keywordList = new ArrayList<String>(Arrays.asList(keywordArray));
					WG.addPage(newPageURL, keywordList);
					System.out.println(newPageURL + " successfully added to the WebGraph!");
					break;
				case "RP":
					System.out.print("Enter a URL: ");
					String removePageURL = input.nextLine();
					WG.removePage(removePageURL);
					System.out.println(removePageURL +" has been removed from the graph!");
					break;
				case "AL":
					System.out.print("Enter a source URL: ");
					String sourceURL = input.nextLine();
					System.out.print("Enter a destination URL: ");
					String destinationURL = input.nextLine();
					WG.addLink(sourceURL, destinationURL);
					System.out.println("Link successfully added from " + sourceURL + " to " + destinationURL +"!");
					break;
				case "RL":
					System.out.print("Enter a source URL: ");
					String removedSourceURL = input.nextLine();
					System.out.print("Enter a destination URL: ");
					String removedDestinationURL = input.nextLine();
					WG.removeLink(removedSourceURL, removedDestinationURL);
					System.out.println("Link successfully removed from " + removedSourceURL + " to " + removedDestinationURL +"!");
					break;
				case "P":
					System.out.println("(I) Sort based on index (ASC) \n"
							+ "(U) Sort based on URL (ASC) \n"
							+ "(R) Sort based on rank (DSC) \n");
					System.out.print("Please select an option: ");
					String choice = input.nextLine();
					if (choice.equals("I")) {
						Collections.sort(WG.pages, new IndexComparator());
						WG.printTable();
					}
					if (choice.equals("U")) {
						Collections.sort(WG.pages, new URLComparator());
						WG.printTable();
						Collections.sort(WG.pages, new IndexComparator()); // not sure why but rank sort only works after an index sort, probably something about pageranks not being able to update in the middle of it
					}
					if (choice.equals("R")) {
						Collections.sort(WG.pages, new RankComparator());
						WG.printTable();
					}
					/// not done
					break;
				case "S":
					System.out.print("Search keyword: ");
					String searching = input.nextLine();
					WG.search(searching);
					break;
				case "Q":
					System.out.println("Goodbye.");
					cont = false;
					break;
				case "PL":
					WG.printLinks();
					break;
				default:
					System.out.println("Invalid option, please try again.");
					System.out.println();
					break;
				}
			}
			catch(Exception ex) {
				
			}
		}
	}
}
