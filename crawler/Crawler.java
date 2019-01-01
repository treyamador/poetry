package crawler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import java.util.ArrayList;

import java.net.URL;
import java.net.URLConnection;
import java.net.MalformedURLException;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;


public class Crawler {

	
	public Crawler() {
		
	}
	
	
	public String connect(String url) {
		
		String html = null;
		
		try {
			
			URLConnection connection = new URL(url).openConnection();
			connection.setRequestProperty("User-Agent", 
					"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
			connection.connect();
			
			BufferedReader r  = new BufferedReader(
				new InputStreamReader(
					connection.getInputStream(), 
					Charset.forName("UTF-8")));
			StringBuilder sb = new StringBuilder();
			
			String line;
			while ((line = r.readLine()) != null)
			    sb.append(line);
			
			html = sb.toString();
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return html;
		
	}
	
	
	private String stringify(Element element) {
		
		String html = element.html();
		
		// TODO check the string replacement more rigorously
		html = html
			.replaceAll("&nbsp;", " ")
			.replaceAll("<br>", "\n")
			.replaceAll("(\\s*)<div>(\\s*)\n(\\s*)", "")
			.replaceAll("(\\s*)\n(\\s*)</div>(\\s*)", "\n")
			.replaceAll("\n(\\s+)<p>", "\n")
			.replaceAll("</p>", "\n")
			.replaceAll("<p>", "").replaceAll("</p>", "\n");
		html = html.replaceAll("<[^>]*>", "").trim();
		
		return html;

	}
	
	
	public Poem parse(String html) {
		
		Document doc = Jsoup.parse(html);

		try {
			
			Element author = doc.select("span[itemprop=name]").first();
			Element title = doc.select("h1#page-title").first();
			Element body = doc.select("div#poem-content div.field-items").first();
			
			Poem poem = new Poem();
			if (title != null)
				poem.title = title.text();
			if (author != null)
				poem.author = author.text();
			if (body != null)
				poem.poem = this.stringify(body);
			return poem;
			
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		
		return null;
		
	}
	
	
	public ArrayList<String> link(String html) {
		Document doc = Jsoup.parse(html);		
		Elements content = doc.select("div.view-poems tbody td.views-field-title a");
		ArrayList<String> links = new ArrayList<String>();
		for (Element field : content)
			links.add(field.attr("href"));
		return links;
	}
	
	
	
	public Poem collectParsedPoem(String url) {
		String html = this.connect(url);
		Poem poem = this.parse(html);
		return poem;
	}
	
	
	public ArrayList<String> collectLinks(String url) {
		String html = this.connect(url);
		ArrayList<String> links = this.link(html);
		return links;
	}
	
	
}

