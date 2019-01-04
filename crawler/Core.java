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
		
		//ArrayList<String> urls = new ArrayList<String>();
		//urls.add("https://www.poets.org/poetsorg/poem/house-called-tomorrow");
		//for (int i = 0; i < urls.size(); ++i) {
		//	Poem poem = this.crawler.collectParsedPoem(urls.get(i));
		//	this.database.insert(urls.get(i), poem);
		//}
		
		Poem poem = new Poem();
		poem.title = "An apex";
		poem.author = "Greg Neilson";
		poem.poem = "The time of birds died sometime between\r\n" + 
				"When Robert Kennedy, Jr. disappeared and the Berlin\r\n" + 
				"Wall came down. Hope was pro forma then.\r\n" + 
				"We�d begun to talk about shelf-life. Parents\r\n" + 
				"Thought they�d gotten somewhere. I can�t tell you\r\n" + 
				"What to make of this now without also saying that when\r\n" + 
				"I was 19 and read in a poem that the pure products of America go crazy\r\n" + 
				"I felt betrayed. My father told me not to whistle because I\r\n" + 
				"Was a girl. He gave me my first knife and said to keep it in my right\r\n" + 
				"Hand and to keep my right hand in my right pocket when I walked at night.\r\n" + 
				"He showed me the proper kind of fist and the sweet spot on the jaw\r\n" + 
				"To leverage my shorter height and upper-cut someone down.\r\n" + 
				"There were probably birds on the long walk home but I don�t\r\n" + 
				"Remember them because pastoral is not meant for someone\r\n" + 
				"With a fist in each pocket waiting for a reason. ";
		String url = "poem.com/onething";
		
		this.database.insert(url, poem);
		
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
