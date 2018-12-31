package crawler;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.sql.ResultSet;
import java.sql.SQLException;


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
		
		/*
		
		ArrayList<String> urls = new ArrayList<String>();
		urls.add("https://www.poets.org/poetsorg/poem/sea-glass");
		urls.add("https://www.poets.org/poetsorg/poem/modern-love-x");
		urls.add("https://www.poets.org/poetsorg/poem/largo");
		
		for (int i = 0; i < urls.size(); ++i) {
			String html = this.crawler.connect(urls.get(i));
			Poem poem = this.crawler.parse(html);
			poem.print();
			this.pause(1);
		}
		
		System.out.println("end of program");
		
		*/
		
		
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
