package crawler;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;


public class Logger {
	
	
	private String CHECKPOINT_FILE = "./src/files/checkpoint.txt";
	private BufferedWriter crawl_writer;
	private BufferedReader crawl_reader;
	
	
	public Logger() {
		this.initWriter();
		this.initReader();
	}
	
	
	private void initReader() {
		try {
			this.crawl_reader = new BufferedReader(
					new FileReader(this.CHECKPOINT_FILE));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	
	public void initWriter() {
		try {
			this.crawl_writer = new BufferedWriter(
					new FileWriter(this.CHECKPOINT_FILE, true));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public String currentEntry() {
		
		String entry = null;
		
		try {
			while (this.crawl_reader.read() != -1)
				entry = this.crawl_reader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return entry;
		
	}
	
	
	public void appendEntry(String entry) {
		
		try {
			this.crawl_writer.append(entry);
			this.crawl_writer.newLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
}
