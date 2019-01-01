package crawler;


import java.util.ArrayList;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;


public class Crawler {

	
	public Crawler() {
		
	}
	
	
	public Document connect(String url) {
		Document doc = null;
		try {
			doc = Jsoup.connect(url).get();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return doc;
	}
	
	
	private String stringify(Element element) {
		
		String html = element.html();
		
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
	
	
	public Poem parse(Document doc) {
		
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
	
	
	public ArrayList<String> link(Document doc) {
		Elements content = doc.select("div.view-poems tbody td.views-field-title a");
		ArrayList<String> links = new ArrayList<String>();
		for (Element field : content)
			links.add(field.attr("href"));
		return links;
	}
	
	
	public Poem collectParsedPoem(String url) {
		Document html = this.connect(url);
		Poem poem = this.parse(html);
		return poem;
	}
	
	
	public ArrayList<String> collectLinks(String url) {
		Document html = this.connect(url);
		ArrayList<String> links = this.link(html);
		return links;
	}
	
	
}

