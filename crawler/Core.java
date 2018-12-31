package crawler;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;


public class Core {
	
	
	private Crawler crawler = null;
	
	
	public Core() {
		this.crawler = new Crawler();
	}
	
	
	public void pause(int seconds) {
		try {
			TimeUnit.SECONDS.sleep(seconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
	public void run() {
		
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
		
	}
	
	
}
