
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
		import java.util.Collections;
import java.util.HashSet;
import java.util.List;
		import java.util.regex.Matcher;
		import java.util.regex.Pattern;

		import org.jsoup.Jsoup;

		public class Webcrawler {

			public static final int NO_OF_URLS = 1;
			public static void crawUrls(String url, int noOfUrls, HashSet<String> urlsList) {
				List<String> tmpUrlsList = new ArrayList<String>();
				
				try {
					org.jsoup.nodes.Document doc = Jsoup.connect(url).get();
					String linkRegex = "(http://|https://)(www.)?.*?[.net|.com|.org|.ca].*?\"\\s?";

					String matchedUrl = "";
					Pattern r = Pattern.compile(linkRegex);
					Matcher m = r.matcher(doc.html());
					while (m.find()) {
						matchedUrl = m.group();
						tmpUrlsList.add(matchedUrl.substring(0, matchedUrl.lastIndexOf("\"")));
					}

					// return a random noOfUrls
					if (tmpUrlsList.size() > noOfUrls) {
						Collections.shuffle(tmpUrlsList);
						tmpUrlsList = tmpUrlsList.subList(0, noOfUrls);
					}
					
					urlsList.addAll(tmpUrlsList);
					while (urlsList.size() <= NO_OF_URLS && tmpUrlsList.size() > 0 ) {
						String randomUrl = tmpUrlsList.get((int)(Math.random() * tmpUrlsList.size()));
						crawUrls(randomUrl, noOfUrls, urlsList);
					}
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					return;
				}
			}

			public static void main(String[] args) throws IOException {
				// Start crawling from this web page
				String sourceUrl = "https://windsorstar.com/";
				HashSet<String> urlsList = new HashSet<String>();
				
				// Crawling until we get an expected NO_OF_URLS
				crawUrls(sourceUrl, 100, urlsList);
				
				System.out.println(urlsList.size());
				for (String url : urlsList) {
					System.out.println(url);
					try {
						org.jsoup.nodes.Document doc = Jsoup.connect(url).get();
						
						String html = doc.html();
						String title = doc.title();
						
						// Some title contains this special character so it causes error writing the html onto harddisk
						// Use this trick to avoid the error
						if (title.contains("|")) {
							title = title.split("\\|")[0];
							
						}
					
						//Utils.writeStringToFile("src/files/" + title.trim() + ".htm", html);
					} catch (Exception e) {
						continue;
					}
				}
				PrintWriter out = new PrintWriter("websites"+".txt");
				for (String url : urlsList) {
					out.println(url);}
				
    			out.close();
			}
		}