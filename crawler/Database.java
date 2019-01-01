package crawler;


import java.util.ArrayList;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;


public class Database {
	
	
	private final String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";
	private Connection connect = null;
	
	
	private final int DEFAULT_SUBSECTION_LENGTH = 250;
	private final String TEXT_COLUMN_NAME = "Text";
	
	
    public Database(String db, String user, String pwd) {
    	this.init(db, user, pwd);
    }
	
	
    private void init(String db, String user, String pwd) {
    	try {
			Class.forName(this.DRIVER_NAME);
			this.connect = DriverManager.getConnection(
					"jdbc:mysql://localhost/" + db + "?" + 
							"user=" + user + "&password=" + pwd +
							"&allowPublicKeyRetrieval=true&useSSL=false");
		} catch (ClassNotFoundException e) {
			System.out.println("ERROR! "+ e.getMessage());
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
    
    private ResultSetMetaData getMetaData(String table) {
    	ResultSetMetaData metadata = null;
    	Statement statement = null;
    	try {
    		statement = this.connect.createStatement();
			metadata = statement.executeQuery("SELECT * from "+table).getMetaData();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
    	return metadata;
    }
    
	
    public ResultSet select() {
    	
    	ResultSet result = null;
    	
    	try {
			Statement statement = this.connect.createStatement();
			result = statement.executeQuery("select * from poemsection");
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    	return result;
    	
    }
    
    
    private int getSubsectionPrecision() {
    	
    	int precision = this.DEFAULT_SUBSECTION_LENGTH;
    	
    	try {
    		
    		ResultSetMetaData metadata = this.getMetaData("Poemsection");
			for (int i = 1; i <= metadata.getColumnCount(); ++i) {
				String column_name = metadata.getColumnName(i);
				if (column_name == this.TEXT_COLUMN_NAME) {
					precision = metadata.getPrecision(i);
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    	return precision;
    	
    }
    
    
    private ArrayList<String> createSubsections(Poem poem) {
    	
    	int len_text = poem.poem.length();
    	int subsect_prec = this.getSubsectionPrecision();
	    int num_subsects = (int)(len_text / subsect_prec) + 1;
		
	    ArrayList<String> subsects = new ArrayList<String>();
    	for (int i = 0; i < num_subsects; ++i) {
    		
    		int sub_i = i*subsect_prec,
    			sub_f = (i+1)*subsect_prec;
    		if (sub_i < len_text) {
    			String subsect = poem.poem.substring(sub_i, 
    					sub_f < len_text ? sub_f : len_text-1);
    			subsects.add(subsect);
    		}
    		
    	}
    	
    	return subsects;
    	
    }
    
    
    private String createSubsectionInsert(String url, Poem poem) {
    	
    	String query = "INSERT into Poem(Title, NumSections, Date, PoetID, URL) VALUES ";
    	
    	int len_text = poem.poem.length();
    	int subsect_prec = this.getSubsectionPrecision();
	    int num_subsects = (int)(len_text / subsect_prec) + 1;
		
	    System.out.println(subsect_prec+"\n");
    	for (int i = 0; i < num_subsects; ++i) {
    		
    		int sub_i = i*subsect_prec;
    		int sub_f = (i+1)*subsect_prec;
    		if (sub_i < len_text) {
    			String subsect = poem.poem.substring(sub_i, 
    					sub_f < len_text ? sub_f : len_text-1);
    			System.out.println(subsect+"\n");
    		}
    		
    	}
    	
    	return query;
    	
    }
    
    
    public boolean insert(String url, Poem poem) {
    	
    	try {
			
    		Statement statement = this.connect.createStatement();
    		ArrayList<String> subsections = this.createSubsections(poem);
    		
    		for (int i = 0; i < subsections.size(); ++i) {
    			System.out.println(subsections.get(i)+"\n\n");
    		}
    		
			
    		// statement.executeQuery("");
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    	return false;
    }
    
	

}










