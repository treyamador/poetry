package crawler;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.sql.ResultSet;
import java.sql.SQLException;
// import org.jsoup.nodes.Document;


public class Core {
	
	
	private Crawler crawler = null;
	private Database database = null;
	
	
	public Core() {
		this.crawler = new Crawler();
		this.database = new Database("poetry", "sqluser", "sqluserpw");
	}
	
	
	public void pause(int seconds) {
		try {
			TimeUnit.SECONDS.sleep(seconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
	public void run() {
				
		this.insertTest();
		
	}
	
	
	public void collectPoems() {
		
		int ext_i = 1, ext_f = 947;
		
		// TODO remove this
		ext_f = 28;
		ext_i = 28;
		
		String hostname = "https://www.poets.org";
		String base_url = "/poetsorg/poems?page=";
		for (int i = ext_i; i <= ext_f; ++i) {
			
			String burl = hostname+base_url+i;
			ArrayList<String> a = this.crawler.collectLinks(burl);
			this.pause(10);
			
			for (String link : a) {
				String purl = hostname + link;
				
				Poem poem = this.crawler.collectParsedPoem(purl);
				
				if (poem != null) {
					poem.print();
				} else {
					System.out.println("Poem at "+purl+" missing.");
				}
				this.pause(10);
			}
		}
		
	}
	
	
	public void insertTest() {
		
		ArrayList<String> urls = new ArrayList<String>();
		urls.add("https://www.poets.org/poetsorg/poem/house-called-tomorrow");
		for (int i = 0; i < urls.size(); ++i) {
			Poem poem = this.crawler.collectParsedPoem(urls.get(i));
			this.database.insert(urls.get(i), poem);
		}
		
	}
	
	
	public void crawlTest() {
		
		ArrayList<String> urls = new ArrayList<String>();
		//urls.add("https://www.poets.org/poetsorg/poem/sea-glass");
		//urls.add("https://www.poets.org/poetsorg/poem/modern-love-x");
		//urls.add("https://www.poets.org/poetsorg/poem/largo");
		//urls.add("https://www.poets.org/poetsorg/poem/triple-moments-light-industry");
		//urls.add("https://www.poets.org/poetsorg/poem/winter-walk");
		//urls.add("https://www.poets.org/poetsorg/poem/we-may-no-longer-consider-end");
		urls.add("https://www.poets.org/poetsorg/poem/house-called-tomorrow");
		
		for (int i = 0; i < urls.size(); ++i) {
			Poem poem = this.crawler.collectParsedPoem(urls.get(i));
			poem.print();
			this.pause(10);
		}
		
		System.out.println("end of program");
		
	}
	
	
	public void selectTest() {
		
		ResultSet result = this.database.select();
		
		try {
			while (result.next()) {
				int len = result.getMetaData().getColumnCount();
				for (int i = 1; i <= len; ++i) {
					System.out.print(result.getString(i)+" ");
				}
				System.out.println();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	
}
